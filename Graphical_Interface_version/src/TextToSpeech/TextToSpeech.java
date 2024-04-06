package TextToSpeech;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.IOException;

import App.App;

public class TextToSpeech {
    private static final String API_KEY = "bb895085f8364319bae420ae3df12842"; // Thay thế bằng API key của bạn
    private static final String API_URL = "https://api.voicerss.org/";
    public static final String LANGUAGE_EN_US = "en-us";
    public static final String LANGUAGE_EN_GB = "en-gb";
    public static final String LANGUAGE_VI = "vi-vn";
    private static final String FORMAT = "mp3";
    public static final String[] VOICE_EN_US = {"Mary", "Mike"};
    public static final String[] VOICE_EN_GB = {"Lily", "Harry"};
    public static final String[] VOICE_VI = {"Chi"};

    public static String getAudioUrl(String textToSpeech, String language, String voice) throws IOException {
        String encodedText = java.net.URLEncoder.encode(textToSpeech, "UTF-8");
        String requestUrl = API_URL 
        + "?key=" + API_KEY 
        + "&hl=" + language 
        + "&v=" + voice
        + "&src=" + encodedText;

        return requestUrl;
    }

    public static void playAudio(String audioUrl) {
        try {
            App.checkNetworkStatus();
        } catch (Exception e) {
            // TODO: handle exception
            return;
        }

        Media media = new Media(audioUrl);
        MediaPlayer mediaPlayer = new MediaPlayer(media);

        mediaPlayer.setOnReady(() -> {
            mediaPlayer.play();
        });

        mediaPlayer.setOnEndOfMedia(() -> {
            mediaPlayer.stop();
        });
    }
}