package Game.state;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;

/**
 * Class representing the state of the puzzle.
 */
@Data
@Slf4j
public class GameState {



    private  Player FirstPlayer;

    private  Player SecondPlayer;

    private boolean isGameFinihed ;

    private  Player currentPlayer ;
    int Type = 0;


    /**
     *  an array that sets the position of the cream and brown colors of the squares on the board.
     *  where cream =0, brown =-1
     */
    public static final int[][] INITIAL = {
            {2,-1,0,-1,0,-1,0,-1},
            {-1,0,-1,0,-1,0,-1,0},
            {0,-1,0,-1,0,-1,0,-1},
            {-1,0,-1,0,-1,0,-1,0},
            {0,-1,0,-1,0,-1,0,-1},
            {-1,0,-1,0,-1,0,-1,0},
            {0,-1,0,-1,0,-1,0,-1},
            {-1,0,-1,0,-1,0,-1,3}
    };


    /**
     * The array storing the current configuration of the board.
     */
    private cell[][] board;


    /**
     * Creates a {@code GameState} object representing the (original)
     * initial state of the game.
     * @param FirstPlayer the first player by definition
     * @param SecondPlayer the second player by definition
     */
    public GameState(Player FirstPlayer, Player SecondPlayer) {
        this(INITIAL , FirstPlayer, SecondPlayer);
    }

    /**
     * constructor with one argument for testing purposes
     * Creates a {@code GameState} object representing the (original)
     * initial state of the game.
     *   @param a an array of size 8&#xd7;8 representing the initial configuration
     *       of the board
     */
    public GameState(int[][] a ) {
        this(a , new Player("john") , new Player("Doe"));
    }

    public GameState() {
        this(INITIAL , new Player("john") , new Player("Doe"));
    }

    /**
     * Creates a {@code GameState} object that is initialized it with
     * the specified array.
     *
     * @param a an array of size 8&#xd7;8 representing the initial configuration
     *          of the board
     */
    public GameState(int[][] a , Player FirstPlayer, Player SecondPlayer) {
        this.FirstPlayer = FirstPlayer;
        this.SecondPlayer = SecondPlayer;
        this.currentPlayer = FirstPlayer;
        initBoard(a);

    }



    private void initBoard(int[][] a) {
        this.board = new cell[8][8];
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                this.board[i][j] = new cell(i,j);
                this.board[i][j].setType(a[i][j]);
            }
        }
    }



    /**
     * Returns whether the cell at the specified position can a knight move to it or not
     *
     * @param row represent  the row cell
     * @param col the column the cell
     * @param prevRow represent  the row of the cell from which the knight is about to move
     * @param prevCol represent  the column of the cell from which the knight is about to move
     * @return {@code true} if the  cell a the specified position can a knight move to it
     * , {@code false} otherwise
     */
    public boolean canKnightMoveTo(int row, int col,int prevRow , int prevCol) {
       if(validMoveForKnight(row, col,prevRow,prevCol) &&(this.board[row][col].getType()==0 ||this.board[row][col].getType()==-1 )){
           return true;
       }
       if (this.board[row][col].getType() == 1){
           log.info("cell at ({},{}) can not be chosen by the player because knight has been here before.", row, col);
           return false;
       }
       if(!validMoveForKnight(row, col,prevRow,prevCol)){
           log.info("cell at ({},{}) can not be chosen by the player because it not a valid move for a knight.", row, col);
           return false;
       }
       return false;

    }


    /**
     * Checks whether the game is finished.
     *
     * @return {@code true} if the game is finished, {@code false} otherwise
     */
    public boolean isGameFinished(int nextKnightRow , int nextKnightCol){
        isGameFinihed = true;
        ArrayList<Integer[]> validMoves = new ArrayList<>();
        validMoves.add(new Integer [] {nextKnightRow+2,nextKnightCol+1});
        validMoves.add(new Integer [] {nextKnightRow+2,nextKnightCol-1});
        validMoves.add(new Integer [] {nextKnightRow-2,nextKnightCol+1});
        validMoves.add(new Integer [] {nextKnightRow-2,nextKnightCol-1});
        validMoves.add(new Integer [] {nextKnightRow+1,nextKnightCol+2});
        validMoves.add(new Integer [] {nextKnightRow+1,nextKnightCol-2});
        validMoves.add(new Integer [] {nextKnightRow-1,nextKnightCol+2});
        validMoves.add(new Integer [] {nextKnightRow-1,nextKnightCol-2});

        for (Integer [] cell :validMoves){
            if(0 <= cell[0] && cell[0] <= 7 && 0 <= cell[1] && cell[1] <= 7){
                if(this.board[cell[0]][cell[1]].getType()==0 ||this.board[cell[0]][cell[1]].getType()==-1 ){
                    isGameFinihed=false;
                }
            }
        }
        return isGameFinihed;
    }

    /**
     * choose a cell at the specified position and move a knight to it .
     *
     * @param row the row of the Cell to which the knight will be moved
     * @param col the column of the Cell to which the knight will be moved
     * @param prevRow the row of the Cell from which the knight will be moved
     * @param prevCol the column of the Cell from which the knight will be moved
     */
    public void moveKnight(int row, int col ,int prevRow , int prevCol) {
        if(this.currentPlayer.equals(this.FirstPlayer)){
              this.currentPlayer = this.SecondPlayer;
              this.board[row][col].setType(2);
              this.board[prevRow][prevCol].setType(1);
              log.info("cell at ({},{}) has be occupied by the a white knight.", row, col);
        } else {
              this.currentPlayer = this.FirstPlayer;
              this.board[row][col].setType(3);
              this.board[prevRow][prevCol].setType(1);
              log.info("cell at ({},{}) has be occupied by the a black knight.", row, col);
        }

        log.info(String.valueOf(this));

    }

    private boolean validMoveForKnight(int row, int col,int prevRow ,int prevCol){
        if(Math.abs(prevRow-row) == 2 && Math.abs(prevCol-col) == 1){
            return true;
        }
        if (Math.abs(prevRow-row) == 1 && Math.abs(prevCol-col) == 2){
            return true;
        }
        return false;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (cell[] row : board) {
            for (cell cell : row) {
                sb.append(cell).append(' ');
            }
            sb.append('\n');
        }
        return sb.toString();
    }


}
