package Game.HangMan;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class HangManController {
    private String currentPath = "haha";

    @FXML private Stage stage;
    @FXML private Scene scene;
    @FXML private Button playButton;
    @FXML private AnchorPane InGamePane;
    @FXML private AnchorPane HangManPane;

    public void setCenterPane(String path) throws Exception {

        if (currentPath.equals(path)) {
            return;
        }

        currentPath = path;
        AnchorPane newCenterPane = FXMLLoader.load(getClass().getResource(path));
        InGamePane.getChildren().clear();
        InGamePane.getChildren().add(newCenterPane);
    }

    @FXML
    void BackToGameScene(ActionEvent event) {
        try {
            setCenterPane("../GameScene.fxml");
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    @FXML
    void StartGame(ActionEvent event) {
        try {
            setCenterPane("./InGameHMScene.fxml");
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

}
