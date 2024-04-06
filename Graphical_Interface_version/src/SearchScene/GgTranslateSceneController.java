package SearchScene;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ResourceBundle;

import App.App;
import GoogleTranslate.GoogleTranslate;
import TextToSpeech.TextToSpeech;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

public class GgTranslateSceneController implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label languageFromLabel;

    @FXML
    private Label languageToLabel;

    @FXML
    private Button openSearchScene;

    @FXML
    private ImageView swapLanguageBtn;

    @FXML
    private TextArea textFrom;

    @FXML
    private TextArea textTo;

    @FXML
    private Button translateBtn;

    private String langFrom = GoogleTranslate.ENGLISH_LANG;
    private String langTo = GoogleTranslate.VIETNAM_LANG;

    private String langFromVoice = TextToSpeech.LANGUAGE_EN_US;
    private String langToVoice = TextToSpeech.LANGUAGE_VI;

    private String voiceFrom = TextToSpeech.VOICE_EN_US[0];
    private String voiceTo = TextToSpeech.VOICE_VI[0];

    @FXML
    void changeToSearchScene(ActionEvent event) {
        ContainerSearchSceneController.getContainerSearchSceneController().addScene("SearchScene.fxml");
        
        AnchorPane SearchScene = ContainerSearchSceneController.getContainerSearchSceneController().getLastAnchorPane();
        
        // Di chuyển Scene mới từ bên phải vào màn hình chính
        TranslateTransition transition = new TranslateTransition(Duration.seconds(1), SearchScene);
        transition.setFromY(-SearchScene.getPrefHeight());
        transition.setToY(0);

        transition.play();
    }

    @FXML
    void swapLanguage(MouseEvent event) {
        String tmp = langFrom;
        langFrom = langTo;
        langTo = tmp;

        tmp = languageFromLabel.getText();
        languageFromLabel.setText(languageToLabel.getText());
        languageToLabel.setText(tmp);

        tmp = textFrom.getText();
        textFrom.setText(textTo.getText());
        textTo.setText(tmp);

        tmp = langFromVoice;
        langFromVoice = langToVoice;
        langToVoice = tmp;

        tmp = voiceFrom;
        voiceFrom = voiceTo;
        voiceTo = tmp;
    }

    private void initGgTranslateBtn() {
        openSearchScene.setOnMouseEntered(e -> {
            openSearchScene.setText("Switch to Search");
            // openSearchScene.setStyle("-fx-background-color: #3399ff;
            // -fx-background-radius: 20 20 20 20;");
        });

        openSearchScene.setOnMouseExited(e -> {
            openSearchScene.setText(null);
            // openSearchScene.setStyle("-fx-background-color: #66b3ff;
            // -fx-background-radius: 25 25 25 25;");
        });
    }

    @FXML
    private void translateSubmit(ActionEvent event) {

        try {
            App.checkNetworkStatus();
        } catch (Exception e) {
            // TODO: handle exception
            return;
        }

        String text = textFrom.getText();
        try {
            // System.out.println(text);
            String resultTranslate = GoogleTranslate.translate(langFrom, langTo, text);
            // System.out.println(resultTranslate);
            textTo.setText(resultTranslate);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    @FXML
    void speechTextFrom(MouseEvent event) {
        try {
            String auidoUrl = TextToSpeech.getAudioUrl(textFrom.getText(), langFromVoice, voiceFrom);
            TextToSpeech.playAudio(auidoUrl);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    @FXML
    void speechTextTo(MouseEvent event) {
        try {
            String auidoUrl = TextToSpeech.getAudioUrl(textTo.getText(), langToVoice, voiceTo);
            TextToSpeech.playAudio(auidoUrl);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    private void initTextArea() {
        // Thiết lập mã hóa ký tự cho TextArea
        // textFrom.setCharset(StandardCharsets.UTF_8);
        // textTo.setC
        textFrom.setFont(Font.font("Arial", FontWeight.NORMAL, FontPosture.REGULAR, 16));
        textTo.setFont(Font.font("Arial", FontWeight.NORMAL, FontPosture.REGULAR, 16));

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO Auto-generated method stub
        initGgTranslateBtn();
        initTextArea();
    }

}
