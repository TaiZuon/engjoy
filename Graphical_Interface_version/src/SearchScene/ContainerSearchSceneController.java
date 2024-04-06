package SearchScene;

import java.net.URL;
import java.util.ResourceBundle;

import CM.CM;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

public class ContainerSearchSceneController {
    private static AnchorPane mainAnchorPane;
    private static StackPane mainStackPane;

    private static ContainerSearchSceneController containerSearchSceneController;

    private ContainerSearchSceneController() {
        mainAnchorPane = new AnchorPane();
        mainAnchorPane.setPrefWidth(1160);
        mainAnchorPane.setPrefHeight(666);

        mainStackPane = new StackPane();
        mainStackPane.setPrefWidth(1160);
        mainStackPane.setPrefHeight(666);

        mainAnchorPane.getChildren().add(mainStackPane);

        addScene("SearchScene.fxml");
    }

    public static ContainerSearchSceneController getContainerSearchSceneController() {
        if (containerSearchSceneController == null) {
            containerSearchSceneController = new ContainerSearchSceneController();
        }
        return containerSearchSceneController;
    }


    private void maintainLength() {
        if (mainStackPane.getChildren().size() > 2) {
            mainStackPane.getChildren().remove(0);
        } 
    }

    public void addScene(String path) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
            AnchorPane root = loader.load();
            mainStackPane.getChildren().addAll(root);
            maintainLength();

            CM.getInstance().setSearchSceneController(loader.getController());            
        } catch (Exception e) {
            // TODO: handle exception
            // e.printStackTrace();
        }
    }

    public AnchorPane getMaiAnchorPane() {
        return mainAnchorPane;
    }

    public AnchorPane getLastAnchorPane() {
        return (AnchorPane) mainStackPane.getChildren().get(mainStackPane.getChildren().size() - 1);
    }
}
