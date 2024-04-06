package UpdateDictionary;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import CM.CM;
import DictionaryPg.Dictionary;
import DictionaryPg.Idioms;
import DictionaryPg.Meaning;
import DictionaryPg.Word;
import DictionaryPg.WordType;
import MenuScene.MenuSceneController;
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

public class Add implements Initializable{

    private boolean introOpened = false;
    
    private Label intro;

    @FXML
    private Button backButton;

    @FXML
    private Label a;

    @FXML
    private Button addButton;

    @FXML
    private AnchorPane introPane;

    @FXML
    private TextField pronounceText;

    @FXML
    private TextField exampleText;

    @FXML
    private TextField idiomText;

    @FXML
    private AnchorPane mainPane;

    @FXML
    private TextField meaningText;

    @FXML
    public TextField wordText;

    @FXML
    private TextField wordTypeText;

    public void gotoSearchScene() {
        CM.getInstance().getMenuSceneController().toSearchScene();
        CM.getInstance().getSearchSceneController().searchBar.setText(wordText.getText().toLowerCase());
        CM.getInstance().getSearchSceneController().showWord();
    }

    @FXML
    void showIntro(MouseEvent event) {
        if (!introOpened) {
            MyEffect.growPane(introPane,115, 50, 1040, 570, 500);
            intro.setText("Fill in the blank the new word and its explain\n\n\nClick \"Add\" button to submit your new word");
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
            MyEffect.shrinkPane(introPane, 1040, 570, 115, 50, 500);
            intro.setText("");
            intro.setLayoutX(0);
            intro.setLayoutY(0);
            introOpened = false;
            MyEffect.fadeOut(intro, 200);
        }
    }

    public void clearAllTextField() {
        wordText.clear();
        wordTypeText.clear();
        meaningText.clear();
        exampleText.clear();
        idiomText.clear();
    }

    public void alertAddSuccess(String word) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        String headerext = "\"" + word + "\"" +" is successfully added to Our Dictionary!";
        alert.setTitle("Add Success");
        alert.setHeaderText(headerext);
        alert.setContentText("Keep adding?");
        // Tùy chọn Yes và No
        ButtonType buttonTypeYes = new ButtonType("Yes");
        ButtonType buttonTypeNo = new ButtonType("Back");
        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
        // Hiển thị hộp thoại và xử lý kết quả
        alert.showAndWait().ifPresent(result -> {
            if (result == buttonTypeYes) {
                // Nếu người dùng chọn Yes, go to search scene
                
            } else {
                // Nếu người dùng chọn No, không làm gì
                goBack();
            }
        });
    }

    public void alertEmptyWord() {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        String headerext = "You have not entered any words";
        alert.setTitle("Empty Word");
        alert.setHeaderText(headerext);
        alert.setContentText("Please enter a word");
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

    public void alertExistedWord(String word) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        String headerext = "\"" + word + "\"" +" has existed in Our Dictionary already!";
        alert.setTitle("Existed Word");
        alert.setHeaderText(headerext);
        alert.setContentText("Go and check it out?");
        // Tùy chọn Yes và No
        ButtonType buttonTypeYes = new ButtonType("Yes");
        ButtonType buttonTypeNo = new ButtonType("No");
        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
        // Hiển thị hộp thoại và xử lý kết quả
        alert.showAndWait().ifPresent(result -> {
            if (result == buttonTypeYes) {
                // Nếu người dùng chọn Yes, go to search scene
                gotoSearchScene();
                System.out.println("go to search scene?");
            } else {
                // Nếu người dùng chọn No, không làm gì
            
            }
        });
    }

    @FXML
    void addWord(ActionEvent event) {
        String word = wordText.getText().toLowerCase();
        if (word.equals("")) {
            alertEmptyWord();
            wordText.clear();
            return;
        }
        if (Dictionary.getDictionary().searchWord(word) != null) {
            alertExistedWord(word);
            wordText.clear();
            return;
        } 
        String pronounce = "//";
        
        ArrayList<String> ex = new ArrayList<>();
        ex.add(exampleText.getText().toLowerCase());

        Meaning meaning = new Meaning(meaningText.getText().toLowerCase(), ex);
        ArrayList<Meaning> lm = new ArrayList<>();
        lm.add(meaning);

        String wt = wordTypeText.getText().toLowerCase();
        WordType wordType = new WordType(wt, lm);
        // ArrayList<WordType> lwt = new ArrayList<>();
        // lwt.add(wordType);

        Idioms idi = new Idioms(idiomText.getText().toLowerCase(), null);
        // ArrayList<Idioms> li = new ArrayList<>();
        // li.add(idi);

        Word w = new Word(word, pronounce);
        w.addIdioms(idi);
        w.addWordType(wordType);
        Dictionary.getDictionary().add(word, w);
        Word test = Dictionary.getDictionary().searchWord(word); 

        clearAllTextField();  
        alertAddSuccess(word);  
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

    public void initialize(URL url, ResourceBundle rb) {
        MyEffect.fadeIn(mainPane, 500);
        introPane.setStyle("-fx-background-color: rgba(0, 100, 200, 0.6); -fx-background-radius: 10;");
        intro = new Label();
        introPane.getChildren().add(intro);
        intro.setVisible(false);
    }
}
