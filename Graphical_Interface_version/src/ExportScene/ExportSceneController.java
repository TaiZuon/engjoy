package ExportScene;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import App.App;
import DictionaryPg.DictionaryManagement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ExportSceneController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label noticeLabel;

    @FXML
    private TextField pathFolderTextField;

    @FXML
    void chooseFoldePath(ActionEvent event) {
        try {
            File selectedFile = App.openFileChooser();
            if (selectedFile != null) {
                pathFolderTextField.setText(selectedFile.getAbsolutePath());
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    @FXML
    void exportToFile(ActionEvent event) {
        String path = pathFolderTextField.getText();
        if (path.isEmpty()) {
            App.showAlert("Lỗi chọn đường dẫn file", "Vui Lòng chọn lại đường dẫn");
            return;
        }
        String ext = path.substring(path.length() - 3, path.length());
        if (ext.equals("txt")) {
            DictionaryManagement.getDictionaryManagement().ExportToFile(path);
            noticeLabel.setText("Xuất dữ liệu thành công!!!");
        } else {
            App.showAlert("Lỗi xuất file", "Vui Lòng chọn file .txt");
            return;
        }

    }

    @FXML
    void initialize() {
        assert noticeLabel != null : "fx:id=\"noticeLabel\" was not injected: check your FXML file 'ExportScene.fxml'.";
        assert pathFolderTextField != null
                : "fx:id=\"pathFolderTextField\" was not injected: check your FXML file 'ExportScene.fxml'.";

    }

}
