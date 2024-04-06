package FavoritesScene;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import App.App;
import DictionaryPg.Dictionary;
import DictionaryPg.Idioms;
import DictionaryPg.Word;
import DictionaryPg.WordType;
import TextToSpeech.TextToSpeech;
import WordStorage.WordStorageFavorites;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.RotateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Accordion;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.RotateEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.util.Duration;
import javafx.scene.transform.Rotate;

public class FavoritesSceneController implements Initializable {

    private static final int PREF_WIDTH_WORD_PANE = 640;
    private static final int PREF_HEIGHT_WORD_PANE = 440;

    @FXML
    private AnchorPane mainAnchorPane;

    private boolean isWordAnchorPaneVisible = true;

    @FXML
    private Button nextBtn;

    @FXML
    private AnchorPane explainAnchorPane;

    @FXML
    private ListView<String> favoritesListView;

    @FXML
    private Button prevBtn;

    @FXML
    private AnchorPane wordAnchorPane;

    private List<String> favoritesList;

    private List<AnchorPane> listWordAnchorPanes;

    private List<Accordion> listExpainAccordionPanes;

    private int currentIndexWord;

    @FXML
    private ImageView rotatePaneBtn;

    @FXML
    ImageView removeFavoriteWordBtn;

    private final PauseTransition pauseTransition = new PauseTransition(Duration.seconds(0.5));

    private AnchorPane setContentAccordionPane(WordType target) {
        AnchorPane newAnchorPane = new AnchorPane();
        Label label = new Label(target.toContentForSearchScene());
        label.setPadding(new Insets(10, 10, 10, 10));
        newAnchorPane.getChildren().add(label);
        return newAnchorPane;
    }

    private AnchorPane setContentAccordionPane(ArrayList<Idioms> listIdioms) {
        AnchorPane newAnchorPane = new AnchorPane();
        String text = "";
        for (Idioms idioms : listIdioms) {
            text += idioms.toContentForSearchScene();
        }
        Label label = new Label(text);
        label.setPadding(new Insets(10, 10, 10, 10));
        newAnchorPane.getChildren().add(label);
        return newAnchorPane;
    }

    private Accordion createExpainAccordion(String target) {
        Accordion newAccordion = new Accordion();
        newAccordion.setPrefHeight(PREF_HEIGHT_WORD_PANE);
        newAccordion.setPrefWidth(PREF_WIDTH_WORD_PANE);
        newAccordion.setLayoutX(30);
        newAccordion.setLayoutY(30);

        Word explain = Dictionary.getDictionary().getWordExplainOf(target);

        ArrayList<WordType> listWordTypes = explain.getListWordTypes();

        for (WordType wordType : listWordTypes) {
            ScrollPane scrollPane = new ScrollPane(setContentAccordionPane(wordType));
            scrollPane.setFitToWidth(true);
            scrollPane.setFitToHeight(true);

            TitledPane titledPane = new TitledPane(wordType.getTypeName(), scrollPane);
            newAccordion.getPanes().add(titledPane);
        }

        newAccordion.setExpandedPane(newAccordion.getPanes().get(0));

        ArrayList<Idioms> listIdioms = explain.getListIdioms();
        if (listIdioms.size() > 0) {
            ScrollPane scrollPane = new ScrollPane(setContentAccordionPane(listIdioms));
            scrollPane.setFitToWidth(true);
            scrollPane.setFitToHeight(true);
            TitledPane titledPane = new TitledPane("Idioms", scrollPane);
            newAccordion.getPanes().add(titledPane);
        }

        return newAccordion;
    }

    private void initListExpainAccordionPanes() {
        listExpainAccordionPanes = new ArrayList<Accordion>();
        for (int i = 0; i < favoritesList.size(); ++i) {
            listExpainAccordionPanes.add(createExpainAccordion(favoritesList.get(i)));
        }
    }

    private AnchorPane initSpeechAnchorPane() {
        AnchorPane speechAnchorPane = new AnchorPane();
        ImageView femaleSpeechBtn = new ImageView("image/search/female.png");
        ImageView maleSpeechBtn = new ImageView("image/search/male.png");

        speechAnchorPane.getChildren().add(femaleSpeechBtn);
        speechAnchorPane.getChildren().add(maleSpeechBtn);

        speechAnchorPane.setPrefWidth(100);
        speechAnchorPane.setPrefHeight(40);

        femaleSpeechBtn.setLayoutX(0);
        femaleSpeechBtn.setLayoutY(0);
        femaleSpeechBtn.setFitWidth(40);
        femaleSpeechBtn.setFitHeight(40);
        femaleSpeechBtn.setOnMouseClicked(event -> {
            event.consume();
            femaleSpeech();
        });
        femaleSpeechBtn.setCursor(Cursor.HAND);

        maleSpeechBtn.setLayoutX(100 - 40);
        maleSpeechBtn.setLayoutY(0);
        maleSpeechBtn.setFitWidth(40);
        maleSpeechBtn.setFitHeight(40);
        maleSpeechBtn.setOnMouseClicked(event -> {
            event.consume();
            maleSpeech();
        });
        maleSpeechBtn.setCursor(Cursor.HAND);

        return speechAnchorPane;
    }

    private AnchorPane createWordAnchorPane(String target) {
        AnchorPane newAnchorPane = new AnchorPane();

        newAnchorPane.setPrefWidth(PREF_WIDTH_WORD_PANE);
        newAnchorPane.setPrefHeight(PREF_HEIGHT_WORD_PANE);

        Label wordLabel = new Label(target);
        Label pronounceLabel = new Label(Dictionary.getDictionary().getWordExplainOf(target).getPronounce());
        newAnchorPane.getChildren().add(wordLabel);
        newAnchorPane.getChildren().add(pronounceLabel);

        AnchorPane speeAnchorPane = initSpeechAnchorPane();
        newAnchorPane.getChildren().add(speeAnchorPane);

        wordLabel.setAlignment(Pos.CENTER);
        wordLabel.setPrefWidth(PREF_WIDTH_WORD_PANE);
        AnchorPane.setTopAnchor(wordLabel, 100.0);
        wordLabel.getStyleClass().add("word-label");

        pronounceLabel.setAlignment(Pos.BASELINE_CENTER);
        pronounceLabel.setPrefWidth(PREF_WIDTH_WORD_PANE);
        AnchorPane.setTopAnchor(pronounceLabel, 150.0);
        pronounceLabel.getStyleClass().add("pronounce-label");

        AnchorPane.setTopAnchor(speeAnchorPane, 250.0);
        AnchorPane.setLeftAnchor(speeAnchorPane, (PREF_WIDTH_WORD_PANE - speeAnchorPane.getPrefWidth()) / 2.0);

        AnchorPane.setTopAnchor(newAnchorPane, 50.0);
        AnchorPane.setLeftAnchor(newAnchorPane, 50.0);
        return newAnchorPane;
    }

    private void initListWordAnchorPanes() {
        listWordAnchorPanes = new ArrayList<AnchorPane>();

        for (int i = 0; i < favoritesList.size(); ++i) {
            listWordAnchorPanes.add(createWordAnchorPane(favoritesList.get(i)));
        }
    }

    private void setWordAnchorPane() {
        try {
            wordAnchorPane.getChildren().clear();
            wordAnchorPane.getChildren().add(listWordAnchorPanes.get(currentIndexWord));
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

    }

    private void setExplainAnchorPane() {
        try {
            explainAnchorPane.getChildren().clear();
            explainAnchorPane.getChildren().add(listExpainAccordionPanes.get(currentIndexWord));
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

    }

    void nextFlashCard() {
        currentIndexWord = (currentIndexWord + 1) % (favoritesList.size());
        resetPane();
        setFlashCard();
        favoritesListView.getSelectionModel().select(currentIndexWord);
    }

    void prevFlashCard() {
        currentIndexWord = (currentIndexWord - 1 + favoritesList.size()) % (favoritesList.size());
        resetPane();
        setFlashCard();
        favoritesListView.getSelectionModel().select(currentIndexWord);
    }

    private void setFlashCard() {
        setExplainAnchorPane();
        setWordAnchorPane();
    }

    private void initListView() {
        favoritesListView.setEditable(true);

        favoritesListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE); // Chọn một mục duy nhất

        // Add listener to handle item selection in ListView
        favoritesListView.setOnMousePressed(event -> {
            String selectedItem = favoritesListView.getSelectionModel().getSelectedItem();
            int selectedIndex = favoritesListView.getSelectionModel().getSelectedIndex();
            if (selectedItem != null) {
                currentIndexWord = selectedIndex;
                if (!isWordAnchorPaneVisible) {
                    resetPane();
                }
                setFlashCard();
            }
        });

        setItemsListView();
    }

    private void setItemsListView() {
        favoritesList = WordStorageFavorites.getWordStorageFavorites().getAllWordFromTrie();

        favoritesList.removeIf(favorites -> !Dictionary.getDictionary().isExistInDictionary(favorites));

        if (favoritesList.isEmpty()) {
            App.showAlert("", "Không có từ yêu thích :((");
            return;
        }

        // Filter items based on the search text
        ObservableList<String> filteredItems = FXCollections
                .observableArrayList(favoritesList);

        // Update the ListView with the filtered items
        favoritesListView.setItems(filteredItems);
    }

    void femaleSpeech() {
        String voice = TextToSpeech.VOICE_EN_US[0];
        String language = TextToSpeech.LANGUAGE_EN_US;
        String word = favoritesList.get(currentIndexWord);

        try {
            String audioUrl = TextToSpeech.getAudioUrl(word, language, voice);

            TextToSpeech.playAudio(audioUrl);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    void maleSpeech() {
        String voice = TextToSpeech.VOICE_EN_US[1];
        String language = TextToSpeech.LANGUAGE_EN_US;
        String word = favoritesList.get(currentIndexWord);

        try {
            String audioUrl = TextToSpeech.getAudioUrl(word, language, voice);

            TextToSpeech.playAudio(audioUrl);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    private void switchPanes(double time) {
        if (!pauseTransition.getStatus().equals(PauseTransition.Status.RUNNING)) {
            // Xử lý sự kiện chỉ khi PauseTransition không chạy
            if (isWordAnchorPaneVisible) {
                // Tạo hiệu ứng lật mặt cho pane1 và pane2
                RotateTransition rotateOut = new RotateTransition(Duration.seconds(time), wordAnchorPane);
                rotateOut.setAxis(Rotate.Y_AXIS);
                rotateOut.setFromAngle(0);
                rotateOut.setToAngle(90);

                RotateTransition rotateIn = new RotateTransition(Duration.seconds(time), explainAnchorPane);
                rotateIn.setAxis(Rotate.Y_AXIS);
                rotateIn.setFromAngle(-90);
                rotateIn.setToAngle(0);

                // Xử lý sự kiện khi kết thúc hiệu ứng rotateOut
                rotateOut.setOnFinished(event -> {
                    // Sau khi pane1 lật mặt đi, đưa nó về phía sau
                    wordAnchorPane.toBack();

                    // Sau đó, lật mặt pane2 để hiển thị nó
                    rotateIn.play();
                });

                // Chơi hiệu ứng rotateOut trước
                rotateOut.play();
            } else {
                // Tạo hiệu ứng lật mặt cho pane1 và pane2
                RotateTransition rotateOut = new RotateTransition(Duration.seconds(time), explainAnchorPane);
                rotateOut.setAxis(Rotate.Y_AXIS);
                rotateOut.setFromAngle(0);
                rotateOut.setToAngle(90);

                RotateTransition rotateIn = new RotateTransition(Duration.seconds(time), wordAnchorPane);
                rotateIn.setAxis(Rotate.Y_AXIS);
                rotateIn.setFromAngle(-90);
                rotateIn.setToAngle(0);

                // Xử lý sự kiện khi kết thúc hiệu ứng rotateOut
                rotateOut.setOnFinished(event -> {
                    // Sau khi pane1 lật mặt đi, đưa nó về phía sau
                    explainAnchorPane.toBack();

                    // Sau đó, lật mặt pane2 để hiển thị nó
                    rotateIn.play();
                });

                // Chơi hiệu ứng rotateOut trước
                rotateOut.play();
            }

            // Đảo ngược trạng thái của isPane1Visible
            isWordAnchorPaneVisible = !isWordAnchorPaneVisible;

            // Bắt đầu PauseTransition để ngăn chặn việc bấm liên tục trong 1 giây
            pauseTransition.playFromStart();
        }

    }

    private void resetPane() {
        if (!isWordAnchorPaneVisible) {
            switchPanes(0.1);
        }
    }

    private void removeWordFavorite() {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Xác nhận xóa");
        alert.setHeaderText(null);
        alert.setContentText("Bạn có chắc chắn muốn xóa từ này khỏi danh sách yêu thích?");

        // Tùy chọn Yes và No
        ButtonType buttonTypeYes = new ButtonType("Yes");
        ButtonType buttonTypeNo = new ButtonType("No");

        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

        // Hiển thị hộp thoại và xử lý kết quả
        alert.showAndWait().ifPresent(result -> {
            if (result == buttonTypeYes) {
                // Nếu người dùng chọn Yes, đóng ứng dụng
                String target = favoritesList.get(currentIndexWord);
                listExpainAccordionPanes.remove(currentIndexWord);
                listWordAnchorPanes.remove(currentIndexWord);
                WordStorageFavorites.getWordStorageFavorites().deleteWord(target);
                currentIndexWord = currentIndexWord % favoritesList.size();
                setItemsListView();
                resetPane();
                setFlashCard();
            } else {
                // Nếu người dùng chọn No, không làm gì
            }
        });
    }

    private void handleKeyPress(javafx.scene.input.KeyEvent event) {
        // Lấy mã phím được nhấn từ sự kiện
        KeyCode keyCode = event.getCode();
        System.out.println(keyCode.toString());
        if (keyCode.equals(KeyCode.ENTER)) {
            switchPanes(0.5);
        } else if (keyCode.equals(KeyCode.LEFT)) {
            prevFlashCard();
        } else if (keyCode.equals(KeyCode.RIGHT)) {
            nextFlashCard();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        currentIndexWord = 0;
        initListView();

        initListWordAnchorPanes();

        initListExpainAccordionPanes();

        setFlashCard();

        rotatePaneBtn.setOnMouseClicked(event -> switchPanes(0.5));
        prevBtn.setOnMouseClicked(event -> prevFlashCard());
        nextBtn.setOnMouseClicked(event -> nextFlashCard());

        removeFavoriteWordBtn.setOnMouseClicked(event -> removeWordFavorite());

        wordAnchorPane.setOnMouseClicked(event -> switchPanes(0.5));
        explainAnchorPane.setOnMouseClicked(event -> switchPanes(0.5));

        removeFavoriteWordBtn.setOnKeyPressed(event -> {
            event.consume();
            handleKeyPress(event);
        });
    }

}
