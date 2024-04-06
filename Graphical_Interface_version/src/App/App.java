package App;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;

import javax.crypto.spec.DESKeySpec;

import CM.CM;
import DictionaryPg.Dictionary;
import DictionaryPg.DictionaryManagement;
import MenuScene.MenuSceneController;
import WordStorage.WordStorageFavorites;
import WordStorage.WordStorageHistory;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class App extends Application {
    double x, y = 0;

    private static Stage mainStage;

    public static void main(String[] args) throws Exception {
        initApp();
        System.out.println(Dictionary.getDictionary().listWord.size());
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            primaryStage.initStyle(StageStyle.UNDECORATED); // Ẩn title bar

            FXMLLoader loader = new FXMLLoader(App.class.getResource("../MenuScene/MenuScene.fxml"));
            
            Parent root = loader.load();

            Scene scene = new Scene(root);

            root.setOnMousePressed(event -> {
                x = event.getSceneX();
                y = event.getSceneY();
            });

            root.requestFocus();

            root.setOnMouseDragged(event -> {
                primaryStage.setX(event.getScreenX() - x);
                primaryStage.setY(event.getScreenY() - y);
            });

            mainStage = primaryStage;

            // Xử lý sự kiện khi người dùng cố gắng đóng ứng dụng
            primaryStage.setOnCloseRequest(event -> {
                // Hiển thị hộp thoại xác nhận tắt
                event.consume(); // Ngăn chặn đóng tự động, để xử lý xác nhận trước
                confirmClose(primaryStage);
            });

            primaryStage.setScene(scene);
            primaryStage.show();

            MenuSceneController controller = loader.getController();
            CM.getInstance().setMenuSceneController(controller);
        } catch (Exception e) {
            // handle exception
            System.out.println(e.getMessage());
        }
    }

    public static Stage getMainStage() {
        return mainStage;
    }

    private static boolean isNetworkAvailable() {
        try {
            URL url = new URL("https://www.google.com");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000); // Timeout in milliseconds

            int responseCode = connection.getResponseCode();
            return responseCode == HttpURLConnection.HTTP_OK;
        } catch (IOException e) {
            return false; // Unable to connect
        }
    }

    public static void checkNetworkStatus() throws Exception{
        if (!isNetworkAvailable()) {
            showAlert("Network Status", "Network is not available. Please check your connection.");
            throw new Exception("Network is not available!!");
        }
    }

    public static void showAlert(String title, String content) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static File openFileChooser() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");

        return fileChooser.showOpenDialog(mainStage);
    }

    private static void confirmClose(Stage primaryStage) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Xác nhận tắt");
        alert.setHeaderText(null);
        alert.setContentText("Bạn có chắc chắn muốn tắt ứng dụng?");

        // Tùy chọn Yes và No
        ButtonType buttonTypeYes = new ButtonType("Yes");
        ButtonType buttonTypeNo = new ButtonType("No");

        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

        // Hiển thị hộp thoại và xử lý kết quả
        alert.showAndWait().ifPresent(result -> {
            if (result == buttonTypeYes) {
                // Nếu người dùng chọn Yes, đóng ứng dụng
                DictionaryManagement.getDictionaryManagement().ExportToFile("Graphical_Interface_version\\src\\DictionaryPg\\tudien.txt");
                primaryStage.close();
                exportToSaveFile();
            } else {
                // Nếu người dùng chọn No, không làm gì
            }
        });
    }

    private static void initApp() {
        DictionaryManagement.getDictionaryManagement().insertFromFile("Graphical_Interface_version\\src\\DictionaryPg\\tudien.txt");
        Locale.setDefault(new Locale("vi", "VN"));
        WordStorageFavorites.getWordStorageFavorites().importFromFileSave();
        WordStorageHistory.getWordStorageHistory().importFromFileSave();
    }

    public static void closeApp() {
        confirmClose(mainStage);
    }

    private static void exportToSaveFile() {
        WordStorageFavorites.getWordStorageFavorites().exportFromFileSave();
        WordStorageHistory.getWordStorageHistory().exportFromFileSave();
    }
}