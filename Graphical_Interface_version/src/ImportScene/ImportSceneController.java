package ImportScene;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.event.AncestorEvent;

import App.App;
import DictionaryPg.DictionaryManagement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

public class ImportSceneController {
    @FXML
    private Button chooseFileBtn;

    @FXML
    private Button importToFileBtn;

    @FXML
    private TextField pathToFileTextField;

    @FXML
    private AnchorPane mainAnchorPane;

    @FXML
    private Label noticeLabel;

    @FXML
    void chooseFile(ActionEvent event) {
        File selectedFile = App.openFileChooser();
        if (selectedFile != null) {
            pathToFileTextField.setText(selectedFile.getAbsolutePath());
        }
    }

    @FXML
    void importToFile(ActionEvent event) {
        String path = pathToFileTextField.getText();
        if (path.isEmpty()) {
            App.showAlert("Lỗi chọn đường dẫn file", "Vui Lòng chọn lại đường dẫn");
            return;
        }
        String ext = path.substring(path.length() - 3, path.length());
        boolean ret = false;
        if (ext.equals("txt")) {
            DictionaryManagement.getDictionaryManagement().resetDictonaryToNone();
            ret = DictionaryManagement.getDictionaryManagement().insertFromFile(path);
        } else {
            App.showAlert("Lỗi đọc file", "Vui Lòng chọn file .txt");
            return;
        }
        if (ret == true) {
            noticeLabel.setText("Nạp dữ liệu thành công!!!");
        } else {
            noticeLabel.setText("Nạp dữ liệu không thành công :((");
            DictionaryManagement.getDictionaryManagement().resetDictonaryToNone();
            DictionaryManagement.getDictionaryManagement().insertFromFile("src/DictionaryPg/tudien.txt");
        }
    }

    @FXML
    private void resetToDefault(MouseEvent event) {
        DictionaryManagement.getDictionaryManagement().resetDictonaryToNone();
        DictionaryManagement.getDictionaryManagement().insertFromFile("src/DictionaryPg/tudienDefault.txt");
        noticeLabel.setText("Dữ liệu từ điển đã về mặc định");
    }

    @FXML
    void initialize() {
        assert chooseFileBtn != null
                : "fx:id=\"chooseFileBtn\" was not injected: check your FXML file 'ImportScene.fxml'.";
        assert importToFileBtn != null
                : "fx:id=\"importToFileBtn\" was not injected: check your FXML file 'ImportScene.fxml'.";
        assert pathToFileTextField != null
                : "fx:id=\"pathToFileTextField\" was not injected: check your FXML file 'ImportScene.fxml'.";

    }

}
