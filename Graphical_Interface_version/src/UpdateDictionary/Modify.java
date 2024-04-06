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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class Modify implements Initializable{
    private boolean isExpand;

    private String theWord;

    private Word modifyWord;
    
    private boolean introOpened = false;

    private Label intro;

    @FXML
    private Button backButton;

    @FXML
    private Button changeButton;

    @FXML
    private AnchorPane changeIdiomPane;

    @FXML
    private AnchorPane changeTargetPane;

    @FXML
    private AnchorPane changeWordTypePane;

    @FXML
    private Button collapseButton;

    @FXML
    private Button expandButton;

    @FXML
    private Button findButton;

    @FXML
    private AnchorPane introPane;

    @FXML
    private AnchorPane mainPane;

    @FXML
    private TextField newIdiom;

    @FXML
    private TextField newMeaningI;

    @FXML
    private TextField newMeaningWT;

    @FXML
    private TextField newTarget;

    @FXML
    private TextField newWordType;

    @FXML
    private TextField wordField;

    public void clearAllTextField() {
        newIdiom.clear();
        newMeaningI.clear();
        newMeaningWT.clear();
        newTarget.clear();
        newWordType.clear();
        wordField.clear();
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
            // introPane.setMinSize(1020, 520);
            MyEffect.growPane(introPane,119, 100, 1140, 600, 500);
            intro.setText("Fill in the blank the word's properties\n\nClick \"Check\" to check if there is the word in our Dictionary\nClick \"Change\" button to submit your change\n\nClick \"Collapse\" for moving pane ^^\nClick \"Expand\" for fixed pane");
            intro.setLayoutX(320);
            intro.setLayoutY(200);
            MyEffect.fadeIn(intro, 500);
            introOpened = true;
        } else {
            closeIntro();
        }
    }

    void closeIntro() {
        if(introOpened) {
            MyEffect.shrinkPane(introPane, 1140, 600, 119, 100, 500);
            intro.setText("");
            intro.setLayoutX(0);
            intro.setLayoutY(0);
            introOpened = false;
            MyEffect.fadeOut(intro, 200);
        }
    }

    public void alertModifySuccess(String word) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        String headerext = "\"" + word + "\"" +" is successfully changed in Our Dictionary!";
        alert.setTitle("Modify Success");
        alert.setHeaderText(headerext);
        alert.setContentText("Keep updating?");
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
        alert.setTitle("Empty Word");
        alert.setHeaderText("You have not enter any word");
        alert.setContentText(null);
        ButtonType buttonTypeYes = new ButtonType("Keep Updating");
        alert.getButtonTypes().setAll(buttonTypeYes);
        // Hiển thị hộp thoại và xử lý kết quả
        alert.showAndWait().ifPresent(result -> {
            if (result == buttonTypeYes) {
                // Nếu người dùng chọn Ye
            }
        });
    }

    public void alertFoundWord(String theWord) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Word Found");
        alert.setHeaderText("There's \"" + theWord + "\" in our Dictionary");
        alert.setContentText(null);
        // Tùy chọn Yes và No
        ButtonType buttonTypeYes = new ButtonType("Keep Updating");
        alert.getButtonTypes().setAll(buttonTypeYes);
        // Hiển thị hộp thoại và xử lý kết quả
        alert.showAndWait().ifPresent(result -> {
            if (result == buttonTypeYes) {
                // Nếu người dùng chọn Yes, go to search scene
            }
        });     
    }

    public void alertNewWord(String newWord) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Word Not Found");
        alert.setHeaderText("\"" + newWord + "\" is a new word");
        alert.setContentText("Add to Dictionary?");
        // Tùy chọn Yes và No
        ButtonType buttonTypeYes = new ButtonType("Yes");
        ButtonType buttonTypeNo = new ButtonType("No");
        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
        // Hiển thị hộp thoại và xử lý kết quả
        alert.showAndWait().ifPresent(result -> {
            if (result == buttonTypeYes) {
                // Nếu người dùng chọn Yes
                gotoAddScene();
            } else {
                // Nếu người dùng chọn No, không làm gì
            
            }
        });
    }

    public void gotoAddScene() {
        CM.getInstance().getUpdateSceneController().toAddScene();
        CM.getInstance().getAddController().wordText.setText(theWord);
    }

    public boolean valid() {
        boolean val = false;
        try {
            theWord = wordField.getText().toLowerCase();
            if(theWord.equals("")) {
                alertEmptyWord();
            } else {
                modifyWord = Dictionary.getDictionary().searchWord(theWord);
                if(modifyWord == null) {
                    alertNewWord(theWord);
                } else {
                    alertFoundWord(theWord);
                    val = true;
                }
            } 
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
        return val;
    }
    @FXML
    void findWord(ActionEvent event) {
        if(valid()) {

        }
    }

    @FXML
    void collapsePane(ActionEvent event) {
        isExpand = false;
        MyEffect.moveIn(changeTargetPane, 0, 0, 200);
        MyEffect.moveIn(changeWordTypePane, 0, 0, 200);
        MyEffect.moveIn(changeIdiomPane, 0, 0, 200);
    }

    public void expandPane() {
        if(!isExpand) {
            changeTargetPane.setMaxWidth(200);
            changeTargetPane.setMinWidth(200);

            MyEffect.moveTo(changeTargetPane, 180, 200, 200);
            MyEffect.moveTo(changeWordTypePane, 420, 200, 200);
            MyEffect.moveTo(changeIdiomPane, 810, 200, 200);

            isExpand = true;
        }
    }

    @FXML
    void expandPane(ActionEvent event) {
        expandPane();
    }

    @FXML
    void submitChange(ActionEvent event) {
        try {
            if(valid()) {
                String target = newTarget.getText().toLowerCase();
                String pronounce = "";
                
                String wordtype = newWordType.getText().toLowerCase();
                String meaningwt = newMeaningWT.getText().toLowerCase();
                String idiom = newIdiom.getText().toLowerCase();
                String meaningi = newMeaningI.getText().toLowerCase();

                //wordtype
                Meaning meaningWT = new Meaning(meaningwt, null);
                ArrayList<Meaning> listMeaningWT = new ArrayList<>();
                listMeaningWT.add(meaningWT);
                WordType wordType = new WordType(wordtype, listMeaningWT);

                //idioms
                ArrayList<String> listMeaningI = new ArrayList<>();
                listMeaningI.add(meaningi);
                Idioms idioms = new Idioms(idiom,listMeaningI);

                if (target != null && !target.equals("")) {
                    String oldtarget = modifyWord.getWord();
                    modifyWord.setWord(target);
                    Dictionary.getDictionary().add(target,modifyWord);
                    Dictionary.getDictionary().remove(oldtarget);
                    System.out.println("Target changed");
                }
                if (pronounce != null && !pronounce.equals("")) {
                    modifyWord.setPronounce(pronounce);
                    System.out.println("pronounce changed");
                }
                if (wordtype != null && !wordtype.equals("")) {
                    ArrayList<WordType> listWordTypes = new ArrayList<>();
                    listWordTypes.add(wordType);
                    modifyWord.setListWordTypes(listWordTypes);
                    System.out.println("wt changed");
                }
                if (idiom != null && !idiom.equals("")) {
                    ArrayList<Idioms> listIdioms = new ArrayList<>();
                    listIdioms.add(idioms);
                    modifyWord.setListIdioms(listIdioms); 
                    System.out.println("i changed");;
                }
                alertModifySuccess(theWord);
                clearAllTextField();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void zoomInIdiom(MouseEvent event) {
        if (!isExpand) {
            MyEffect.moveIn(changeTargetPane, -200, 0, 200);
            MyEffect.moveIn(changeWordTypePane, -260, 0, 200);
            MyEffect.moveIn(changeIdiomPane, -100, 0, 200);
        }
    }

    @FXML
    void zoomInTarget(MouseEvent event) {
        if (!isExpand) {
            MyEffect.moveIn(changeTargetPane, 200, 0, 200);
            MyEffect.moveIn(changeWordTypePane, 150, 0, 200);
            MyEffect.moveIn(changeIdiomPane, 90, 0, 200);
        }
    }

    @FXML
    void zoomInWordType(MouseEvent event) {
        if (!isExpand) {
            MyEffect.moveIn(changeTargetPane, -160, 0, 200);
            MyEffect.moveIn(changeWordTypePane, 0, 0, 200);
            MyEffect.moveIn(changeIdiomPane, 10, 0, 200);
        }
    }

    @FXML
    void zoomOutIdiom(MouseEvent event) {

    }

    @FXML
    void zoomOutPronunciation(MouseEvent event) {

    }

    @FXML
    void zoomOutTarget(MouseEvent event) {

    }

    @FXML
    void zoomOutWordType(MouseEvent event) {

    }

    public void initialize(URL url, ResourceBundle rb) {
        MyEffect.fadeIn(mainPane, 500);
        introPane.setStyle("-fx-background-color: rgba(0, 100, 200, 0.6); -fx-background-radius: 10;");
        intro = new Label();
        introPane.getChildren().add(intro);
        intro.setVisible(false);
        expandPane();
    }

}
