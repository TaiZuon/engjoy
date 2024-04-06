package Game;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import CM.CM;
import Game.Coin.Coin;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class GameSceneController implements Initializable{

    private String currentPath = "haha";

    @FXML
    private Stage stage;
    private Scene scene;

    @FXML
    private AnchorPane HangManPane;

    @FXML
    private AnchorPane GamePane;

    public void setCenterPane(String path) throws Exception {

        if (currentPath.equals(path)) {
            return;
        }
        currentPath = path;
        AnchorPane newCenterPane = FXMLLoader.load(getClass().getResource(path));
        HangManPane.getChildren().clear();
        HangManPane.getChildren().add(newCenterPane);
        System.out.println("set " + path + "done!");
    }

    void alertComingSoon() {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        String headerext = "New Game is coming soon";
        alert.setTitle("Not Available");
        alert.setHeaderText(headerext);
        alert.setContentText(null);
        // Tùy chọn Yes và No
        ButtonType buttonTypeYes = new ButtonType("OK");
        alert.getButtonTypes().setAll(buttonTypeYes);
        // Hiển thị hộp thoại và xử lý kết quả
        alert.showAndWait().ifPresent(result -> {
            if (result == buttonTypeYes) {
                // Nếu người dùng chọn Yes
                
            }
        });
    }

    // game2
    @FXML
    void gotoGame2(ActionEvent event) {
        try {
            alertComingSoon();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    @FXML
    void GoToHangManScene(ActionEvent event) throws IOException {
        try {
            setCenterPane("./HangMan/HangManScene.fxml");
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        Coin.getInstance().setAmountFromFile();
        //System.out.println(Coin.getInstance().getAmount());
    }

}
