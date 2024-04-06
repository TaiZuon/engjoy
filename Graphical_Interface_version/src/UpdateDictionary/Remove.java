package UpdateDictionary;

import java.net.URL;
import java.util.ResourceBundle;

import DictionaryPg.Dictionary;
import DictionaryPg.Word;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class Remove implements Initializable{

    private Label intro;

    private boolean introOpened = false;

    @FXML
    private Button backButton;

    @FXML
    private Button deleteButton;

    @FXML
    private AnchorPane introPane;

    @FXML
    private AnchorPane mainPane;

    @FXML
    private TextField word;

    public void alertEmptyWord() {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Empty Word");
        alert.setHeaderText("You have not enter any word");
        alert.setContentText(null);
        ButtonType buttonTypeYes = new ButtonType("OK");

        alert.getButtonTypes().setAll(buttonTypeYes);
        // Hiển thị hộp thoại và xử lý kết quả
        alert.showAndWait().ifPresent(result -> {
            if (result == buttonTypeYes) {
                // Nếu người dùng chọn Ye
            }
        });
    }

    public void alertNewWord(String newWord) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Word Not Found");
        alert.setHeaderText("\"" + newWord + "\" is a new word");
        alert.setContentText(null);
        // Tùy chọn Yes và No
        ButtonType buttonTypeYes = new ButtonType("OK");
        alert.getButtonTypes().setAll(buttonTypeYes);
        // Hiển thị hộp thoại và xử lý kết quả
        alert.showAndWait().ifPresent(result -> {
            if (result == buttonTypeYes) {
                // Nếu người dùng chọn Ye
            } 
        });
    }

    public void alertRemoveSuccess(String word) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        String headerext = "\"" + word + "\"" +" is successfully removed from Our Dictionary!";
        alert.setTitle("Remove Success");
        alert.setHeaderText(headerext);
        alert.setContentText("Keep removing words?");
        // Tùy chọn Yes và No
        ButtonType buttonTypeYes = new ButtonType("Yes");
        ButtonType buttonTypeNo = new ButtonType("Back");
        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
        // Hiển thị hộp thoại và xử lý kết quả
        alert.showAndWait().ifPresent(result -> {
            if (result == buttonTypeYes) {
                // Nếu người dùng chọn Yes
            } else {
                // Nếu người dùng chọn No, không làm gì
                goBack();
            }
        });
    }

    @FXML
    void getBack(ActionEvent event) {
        goBack();
    }

    public void goBack() {
        mainPane.getChildren().clear();
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("UpdateScene.fxml"));

            mainPane.getChildren().add(pane);
            
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void showIntro(MouseEvent event) {
        if (!introOpened) {
            MyEffect.growPane(introPane,127, 86, 1040, 550, 300);
            intro.setText("Fill in the blank the new word you want to remove\n\n\nClick \"Delete\" button to remove");
            intro.setLayoutX(300);
            intro.setLayoutY(250);
            MyEffect.fadeIn(intro, 500);
            introOpened = true;
        } else {
            closeIntro();
        }
    }

    void closeIntro(){
        if(introOpened) {
            MyEffect.shrinkPane(introPane, 1040, 550, 127, 86, 500);
            intro.setText("");
            intro.setLayoutX(0);
            intro.setLayoutY(0);
            introOpened = false;
            MyEffect.fadeOut(intro, 200);
        }
    }

    @FXML
    void removeWord(ActionEvent event) {
        String target = word.getText().toLowerCase();
        if (target.equals("")) {
            alertEmptyWord();
            return;
        }
        Word w = Dictionary.getDictionary().searchWord(target);
        if(w == null) {
            alertNewWord(target);
        } else {
            Dictionary.getDictionary().remove(target);
            Word test = Dictionary.getDictionary().searchWord(target);
            if (test == null) {
                alertRemoveSuccess(target);
            } else {
                System.out.println("not remove yet!");
            }
        }
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        MyEffect.fadeIn(mainPane, 500);
        introPane.setStyle("-fx-background-color: rgba(0, 100, 200, 0.6); -fx-background-radius: 10;");
        intro = new Label();
        introPane.getChildren().add(intro);
        intro.setVisible(false);
    }

}
