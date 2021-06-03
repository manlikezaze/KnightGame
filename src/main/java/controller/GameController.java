package controller;


import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import Game.results.GameResult;
import Game.results.GameResultDao;
import Game.results.TopPlayer;
import Game.results.TopPlayerDao;
import Game.state.Player;
import Game.state.GameState;

import javax.inject.Inject;
import java.io.IOException;
import java.time.Duration;
import java.time.ZonedDateTime;

@Slf4j
public class GameController {

    @Inject
    private FXMLLoader fxmlLoader;

    @Inject
    private GameResultDao gameResultDao;

    @Inject
    private TopPlayerDao topPlayerDao;

    Image blackKnightImage = new Image(getClass().getResource("/pictures/blackhorse.png").toExternalForm());
    Image whiteKnightImage = new Image(getClass().getResource("/pictures/whitehorse.png").toExternalForm());

    ImageView iv1 = new ImageView();
    ImageView iv2 = new ImageView();

    private GameState gameState;

    private  int Column;
    private  int  Row;

    private int whiteKnightRow ;
    private int whiteknightCol ;

    private int blackKnightRow ;
    private int blackknightCol ;

    private int currentKnightRow = 0;
    private int currentknightCol = 0;

    private int otherKnightRow = 7 ;
    private int otherknightCol = 7 ;

    private int stepCountFirstPlayer;
    private int stepCountSecondPlayer;

    private Player FirstPlayer;

    private Player SecondPlayer;

    private Player winner = new Player("");

    private BooleanProperty gameOver = new SimpleBooleanProperty();

    private ZonedDateTime beginGame;




    @FXML
    private GridPane Grid;


    @FXML
    private Label messageLabel;

    @FXML
    private Label usernameLabel1;
    @FXML
    private Label usernameLabel2;

    @FXML
    private Circle player1turn;
    @FXML
    private Circle player2turn;

    @FXML
    private Button EndGameButton;





    /**
     * Sets the name of the players in Start.fxml.
     * @param FirstPlayer the name of the player 1.
     * @param SecondPlayer the name of player 2.
     */
    public void initdata(String FirstPlayer , String SecondPlayer) {
        this.FirstPlayer = new Player(FirstPlayer);
        this.SecondPlayer = new Player(SecondPlayer);


        usernameLabel1.setText("" + this.FirstPlayer);
        usernameLabel2.setText("" + this.SecondPlayer);

        player1turn.setOpacity(1);
        player2turn.setOpacity(0);
    }

    /**
     * Initializes the game by setting the squares and implementing the colors of the squares on the board.
     * Sets the pieces starting positions on the board.
     */

    @FXML
    public void initialize() {
        gameOver.addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                log.info("Game is over");
                log.debug("Saving result to database...");
                gameResultDao.persist(getResult());
                TopPlayer TopPlayer =topPlayerDao.find(winner.getName());
                if(TopPlayer==null){
                    topPlayerDao.persist(new TopPlayer(winner.getName(),1));
                }
                else{
                    topPlayerDao.update(TopPlayer.getName(),TopPlayer.getNumOfGames()+1);
                }

            }
        });

        Platform.runLater(this::start);
    }

    private void drawBoard() {
        Grid.getChildren().clear();
        for (int i = 0; i < Grid.getRowCount(); i++) {
            for (int j = 0; j < Grid.getColumnCount(); j++) {
                StackPane square;
                if(gameState.getBoard()[i][j].getType() == -1){
                    square = createSquare("square-black");
                }
                else if (gameState.getBoard()[i][j].getType() == 0) {
                    square = createSquare("square");
                } else {
                    square = createSquare("visited");

                }
                if(gameState.getBoard()[i][j].getType() == 2){ whiteknightCol = j ; whiteKnightRow = i;}
                if(gameState.getBoard()[i][j].getType() == 3){ blackknightCol = j ; blackKnightRow = i;}
              Grid.add(square, j, i);
            }
        }

        placePiece();
    }


   public void placePiece(){
       iv1.setImage(blackKnightImage);
       iv2.setImage(whiteKnightImage);
       iv1.setFitWidth(70);
       iv2.setFitWidth(70);
       iv1.setFitHeight(70);
       iv2.setFitHeight(70);
       iv1.setSmooth(true);
       iv1.setCache(true);
       iv2.setSmooth(true);
       iv2.setCache(true);

       GridPane.setHalignment(iv1, HPos.CENTER);
       GridPane.setValignment(iv1, VPos.CENTER);
       GridPane.setHalignment(iv2, HPos.CENTER);
       GridPane.setValignment(iv2, VPos.CENTER);


       Grid.add(iv2,whiteknightCol,whiteKnightRow);
       Grid.add(iv1,blackknightCol,blackKnightRow);
    }


    private StackPane createSquare(String cls) {
        var square = new StackPane();
        square.getStyleClass().add(cls);
        square.setOnMouseClicked(this::handleMouseClick);
        return square;
    }
    private void switchPlayerTurn() {
        if (player1turn.getOpacity() == 1) {
            player1turn.setOpacity(0);
            player2turn.setOpacity(1);
        } else {
            player1turn.setOpacity(1);
            player2turn.setOpacity(0);
        }
    }

    /**
     * a void method that checks if the user is trying to click on one of the positions,
     * if so then the program would send a log with a message stating the row and column number of the square.
     * @param mouseEvent a mouse event.
     */

    public void handleMouseClick(MouseEvent mouseEvent) {


        Column = GridPane.getColumnIndex((Node)mouseEvent.getSource());
         Row = GridPane.getRowIndex((Node)mouseEvent.getSource());
       if (!gameState.isGameFinished(currentKnightRow,currentknightCol)
           && gameState.canKnightMoveTo(Row, Column,currentKnightRow,currentknightCol)) {

           gameState.moveKnight(Row, Column,currentKnightRow,currentknightCol);
           switchPlayerTurn();
           currentKnightRow = otherKnightRow;
           currentknightCol = otherknightCol;
           otherknightCol = Column;
           otherKnightRow = Row;


           messageLabel.setText("Player " + gameState.getCurrentPlayer().getName() + " is playing now");
           if (gameState.getCurrentPlayer().equals(gameState.getFirstPlayer())) {
               stepCountSecondPlayer++;
           } else {
               stepCountFirstPlayer++;
           }
           if (gameState.isGameFinished(currentKnightRow,currentknightCol)) {
               int stepCount;
               if (gameState.getCurrentPlayer().equals(gameState.getFirstPlayer())) {
                   stepCount = stepCountSecondPlayer;
                   winner = SecondPlayer;
               } else {
                   stepCount = stepCountFirstPlayer;
                   winner =FirstPlayer;
               }
               gameOver.setValue(true);
               log.info("Player {} won the game in {} steps.", winner.getName(), stepCount);
               messageLabel.setText(" Game is finished. Player "+ winner.getName() + " won the game");

               EndGameButton.setText("Finish");
           }
       }
           drawBoard();
    }



    public void start() {
        gameOver.setValue(false);
        stepCountFirstPlayer = 0;
        stepCountSecondPlayer = 0;
        currentKnightRow = 0;
        currentknightCol = 0;
        otherKnightRow = 7 ;
        otherknightCol = 7 ;
        player1turn.setOpacity(1);
        player2turn.setOpacity(0);
        messageLabel.setText("Player " + this.FirstPlayer.getName()+ " has the first turn");
        EndGameButton.setText("End Game");
        gameState = new GameState(this.FirstPlayer,this.SecondPlayer);
        drawBoard();
        beginGame = ZonedDateTime.now();

        log.info("Game start.");
    }
    /**
     * Resets the game to a starting state.
     * @param actionEvent An action which is sent when a button is pressed.
     */
    public void resetGame(ActionEvent actionEvent)  {
        log.debug("{} is pressed", ((Button) actionEvent.getSource()).getText());
        log.info("Resetting game...");

        start();
    }

    private GameResult getResult() {

      return   GameResult.builder()
                                    .FirstPlayer(gameState.getFirstPlayer().getName())
                                    .SecondPlayer(gameState.getSecondPlayer().getName())
                                    .winner(winner.getName())
                                    .stepsFirstPlayer(stepCountFirstPlayer)
                                    .stepsSecondPlayer(stepCountSecondPlayer)
                                    .duration(Duration.between(beginGame, ZonedDateTime.now()))
                                    .build();

    }

    public void endGame(ActionEvent actionEvent) throws IOException {
        if (!winner.getName().equals("")) gameOver.setValue(true);
        log.info("Loading high scores scene...");
        fxmlLoader.setLocation(getClass().getResource("/fxml/highscores.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();

    }
}
