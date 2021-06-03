package controller;

import com.google.inject.Inject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import Game.results.TopPlayer;
import Game.results.TopPlayerDao;

import java.io.IOException;

import java.util.List;

@Slf4j
public class TopFiveController {

    @Inject
    private FXMLLoader fxmlLoader;
    @Inject
    TopPlayerDao topPlayerDao;
    @FXML
    private TableView<TopPlayer> highScoreTable;

    @FXML
    private TableColumn<TopPlayer, String> name;

    @FXML
    private TableColumn<TopPlayer, Integer> numOfGames;






    public void back(ActionEvent actionEvent) throws IOException {

        log.info("Loading launch scene...");
        fxmlLoader.setLocation(getClass().getResource("/fxml/launch.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }


    @FXML
    public void initialize() {

        List<TopPlayer> topFiveList = topPlayerDao.findBest(5);

        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        numOfGames.setCellValueFactory(new PropertyValueFactory<>("numOfGames"));


        ObservableList<TopPlayer> observableResult = FXCollections.observableArrayList();
        observableResult.addAll(topFiveList);

        highScoreTable.setItems(observableResult);
    }

}
