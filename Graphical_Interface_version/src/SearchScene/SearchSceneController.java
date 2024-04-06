package SearchScene;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import CM.CM;
import DictionaryPg.Dictionary;
import DictionaryPg.Idioms;
import DictionaryPg.Word;
import DictionaryPg.WordType;
import TextToSpeech.TextToSpeech;
import WordStorage.WordStorageFavorites;
import WordStorage.WordStorageHistory;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Accordion;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class SearchSceneController implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane mainSearchPane;

    @FXML
    private Label pronounceLabel;

    @FXML
    public TextField searchBar;

    @FXML
    private Button searchButton;

    @FXML
    private ImageView speechButton;

    @FXML
    private Label wordLabel;

    @FXML
    private Accordion wordTypeAccordion;

    @FXML
    private Button ggTranslateBtn;

    @FXML
    private ListView<String> suggestionBox = new ListView();

    @FXML
    private Button favoriteBtn;

    @FXML
    private ImageView iconFavorite;

    @FXML
    private Tooltip tooltipFavorite;

    private void setIsFavorite() {
        iconFavorite.setImage(new Image("image/search/heart2.png"));
        tooltipFavorite.setText("Remove from favorites");
        tooltipFavorite.setStyle("-fx-background-color: lightblue; -fx-text-fill: white; -fx-font-size: 12px;");
    }

    private void setIsNotFavorite() {
        iconFavorite.setImage(new Image("image/search/heart1.png"));
        tooltipFavorite.setText("Add to favorites");
        tooltipFavorite.setStyle("-fx-background-color: #1a000d; -fx-text-fill: white; -fx-font-size: 12px;");
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
                gotoAddScene(newWord);
            } else {
                // Nếu người dùng chọn No, không làm gì
            
            }
        });
    }

    public void gotoAddScene(String word) {
        CM.getInstance().getUpdateSceneController().toAddScene();
        CM.getInstance().getAddController().wordText.setText(word);
    }
    
    public void showWord() {
        Word target = Dictionary.getDictionary().searchWord(searchBar.getText().toLowerCase());
        if (target == null) {
            alertNewWord(searchBar.getText().toLowerCase());
            return;
        }
        wordLabel.setText(target.getWord());
        pronounceLabel.setText(target.getPronounce());
        setTitledPaneOfWordTypeAccordion(target);

        boolean isExist = WordStorageFavorites.getWordStorageFavorites().isExistInFavorites(target.getWord());
        if (isExist) {
            setIsFavorite();
        } else {
            setIsNotFavorite();
        }

        WordStorageHistory.getWordStorageHistory().addWord(target.getWord());
    }

    @FXML
    void clickSearch(ActionEvent event) {
        showWord();
    }

    private void setContentTitledPane(AnchorPane anchorPane, WordType target) {
        Label label = new Label(target.toContentForSearchScene());
        label.setPadding(new Insets(10, 10, 10, 10));
        anchorPane.getChildren().add(label);
    }

    private void setContentTitledPane(AnchorPane anchorPane, ArrayList<Idioms> listIdioms) {
        String text = "";
        for (Idioms idioms : listIdioms) {
            text += idioms.toContentForSearchScene();
        }
        Label label = new Label(text);
        label.setPadding(new Insets(10, 10, 10, 10));
        anchorPane.getChildren().add(label);
    }

    private void setTitledPaneOfWordTypeAccordion(Word target) {
        wordTypeAccordion.getPanes().clear();
        ArrayList<WordType> listWordTypes = target.getListWordTypes();
        for (int i = 0; i < listWordTypes.size(); ++i) {
            AnchorPane anchorPane = new AnchorPane();
            anchorPane.setPrefHeight(1000);
            setContentTitledPane(anchorPane, listWordTypes.get(i));

            ScrollPane scrollPane = new ScrollPane(anchorPane);
            scrollPane.setFitToWidth(true);
            scrollPane.setFitToHeight(true);

            TitledPane titledPane = new TitledPane(listWordTypes.get(i).getTypeName(), scrollPane);
            titledPane.getStyleClass().add("titledPane");
            wordTypeAccordion.getPanes().add(titledPane);
        }
        wordTypeAccordion.setExpandedPane(wordTypeAccordion.getPanes().get(0));
        if (target.getListIdioms().size() > 0) {
            AnchorPane anchorPane = new AnchorPane();
            anchorPane.setPrefHeight(1000);
            setContentTitledPane(anchorPane, target.getListIdioms());

            ScrollPane scrollPane = new ScrollPane(anchorPane);
            scrollPane.setFitToWidth(true);
            scrollPane.setFitToHeight(true);
            TitledPane titledPane = new TitledPane("Idioms", scrollPane);
            titledPane.getStyleClass().add("titledPane");
            wordTypeAccordion.getPanes().add(titledPane);
        }
    }

    private void initSuggestionBox() {

        // hide
        suggestionBox.setVisible(false);

        suggestionBox.setEditable(true);

        suggestionBox.getSelectionModel().setSelectionMode(SelectionMode.SINGLE); // Chọn một mục duy nhất

        // Add listener to handle item selection in ListView
        suggestionBox.setOnMousePressed(event -> {
            String selectedItem = suggestionBox.getSelectionModel().getSelectedItem();
            System.out.println(selectedItem);
            if (selectedItem != null) {
                // Set the selected item to the TextField
                searchBar.setText(selectedItem);
                showWord();

                // Hide the suggestionBox
                suggestionBox.setVisible(false);
            }
        });

        // Filter items based on the search text
        ObservableList<String> filteredItems = FXCollections
                .observableArrayList(WordStorageHistory.getWordStorageHistory().getHistoryWordList());

        // Update the ListView with the filtered items
        suggestionBox.setItems(filteredItems);

    }

    private void initSearchBar() {
        // Listen for key events in the search bar
        searchBar.addEventHandler(KeyEvent.KEY_RELEASED, event -> {
            String searchText = searchBar.getText().toLowerCase();

            if (searchText != "") {
                // Filter items based on the search text
                ObservableList<String> filteredItems = FXCollections
                        .observableArrayList(Dictionary.getDictionary().searchAllWordStartWith(searchText));

                // Update the ListView with the filtered items
                suggestionBox.setItems(filteredItems);
            } else {
                // Filter items based on the search text
                ObservableList<String> filteredItems = FXCollections
                        .observableArrayList(WordStorageHistory.getWordStorageHistory().getHistoryWordList());

                // Update the ListView with the filtered items
                suggestionBox.setItems(filteredItems);
            }
        });

        // Add listener to show ListView on focus
        searchBar.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                // TextField is focused, show the suggestionBox
                suggestionBox.setVisible(true);
            } else {
                // TextField lost focus, hide the suggestionBox
                suggestionBox.setVisible(false);
            }
        });
    }

    @FXML
    void femaleSpeech(MouseEvent event) {
        String voice = TextToSpeech.VOICE_EN_US[0];
        String language = TextToSpeech.LANGUAGE_EN_US;
        String word = wordLabel.getText();
        try {
            String audioUrl = TextToSpeech.getAudioUrl(word, language, voice);

            TextToSpeech.playAudio(audioUrl);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    @FXML
    void maleSpeech(MouseEvent event) {
        String voice = TextToSpeech.VOICE_EN_US[1];
        String language = TextToSpeech.LANGUAGE_EN_US;
        String word = wordLabel.getText();

        try {
            String audioUrl = TextToSpeech.getAudioUrl(word, language, voice);

            TextToSpeech.playAudio(audioUrl);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    @FXML
    private void changeToGgTranslateScene(ActionEvent event) {
        ContainerSearchSceneController.getContainerSearchSceneController().addScene("GgTranslateScene.fxml");

        AnchorPane GgTranslateScene = ContainerSearchSceneController.getContainerSearchSceneController()
                .getLastAnchorPane();

        // Di chuyển Scene mới từ bên phải vào màn hình chính
        TranslateTransition transition = new TranslateTransition(Duration.seconds(1), GgTranslateScene);
        transition.setFromY(GgTranslateScene.getPrefHeight());
        transition.setToY(0);

        transition.play();
    }

    private void initGgTranslateBtn() {
        ggTranslateBtn.setOnMouseEntered(e -> {
            ggTranslateBtn.setText("Switch to Google Translate");
            ggTranslateBtn.setStyle("-fx-background-color: #3399ff; -fx-background-radius: 20 20 20 20;");
        });

        ggTranslateBtn.setOnMouseExited(e -> {
            ggTranslateBtn.setText(null);
            ggTranslateBtn.setStyle("-fx-background-color: #66b3ff; -fx-background-radius: 25 25 25 25;");
        });
    }

    @FXML
    private void handleFavorite(MouseEvent event) {
        String word = wordLabel.getText().toLowerCase();
        boolean isExist = WordStorageFavorites.getWordStorageFavorites().isExistInFavorites(word);
        if (isExist) {
            WordStorageFavorites.getWordStorageFavorites().deleteWord(word);
            setIsNotFavorite();
        } else {
            WordStorageFavorites.getWordStorageFavorites().addWord(word);
            setIsFavorite();
        }
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        initSuggestionBox();
        initSearchBar();
        initGgTranslateBtn();
    }

}
