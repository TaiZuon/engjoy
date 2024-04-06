package UpdateDictionary;

import java.beans.EventHandler;
import java.net.URL;
import java.time.Duration;
import java.util.ResourceBundle;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import DictionaryPg.Dictionary;
import MenuScene.MenuSceneController;
import UpdateDictionary.MyEffect;
import MenuScene.MenuSceneController;

public class UpdateScene implements Initializable{

    @FXML
    private Text addExplain;

    @FXML
    private AnchorPane addMainPane;

    @FXML
    private AnchorPane addPane;

    @FXML
    private Text addText;

    @FXML
    private AnchorPane mainPane;

    @FXML
    private Text modifyExplain;

    @FXML
    private AnchorPane modifyPane;

    @FXML
    private Text modifyText;

    @FXML
    private Text removeExplain;

    @FXML
    private AnchorPane removePane;

    @FXML
    private Text removeText;

    //ADD area
    @FXML
    void addPaneClick(MouseEvent event) {
        toAddScene();
    }

    public void toAddScene() {
        MyEffect.fadeOut(addPane, 200);
        MyEffect.fadeOut(modifyPane, 200);
        MyEffect.fadeOut(removePane, 200);
        //add
        try {
            //mainPane.getChildren().clear();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Add.fxml"));
            AnchorPane pane = loader.load();
            CM.CM.getInstance().setAddController(loader.getController());

            mainPane.getChildren().add(pane);
            
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void addPaneMouseEntered(MouseEvent event) {
        MyEffect.moveIn(addPane,  0, -3, 300);

        MyEffect.moveIn(addText, 0, -80, 300);

        addExplain.setVisible(true);
        MyEffect.fadeIn(addExplain, 300);
    }

    @FXML
    void addPaneMouseExited(MouseEvent event) {
        MyEffect.moveIn(addPane, 0, 0, 300);

        MyEffect.moveIn(addText, 0, 0, 300);

        MyEffect.fadeOut(addExplain, 300);

    }

    //MODIFY area
    @FXML
    void modifyPaneClick(MouseEvent event) {
        MyEffect.fadeOut(addPane, 200);
        MyEffect.fadeOut(modifyPane, 200);
        MyEffect.fadeOut(removePane, 200);
        //add
        try {
            //mainPane.getChildren().clear();
            AnchorPane pane = FXMLLoader.load(getClass().getResource("./Modify.fxml"));

            mainPane.getChildren().add(pane);
            
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void modifyPaneMouseEntered(MouseEvent event) {
        MyEffect.moveIn(modifyPane,  0, -3, 300);

        MyEffect.moveIn(modifyText, 0, -80, 300);

        MyEffect.fadeIn(modifyExplain, 300);
    }

    @FXML
    void modifyPaneMouseExited(MouseEvent event) {
        MyEffect.moveIn(modifyPane, 0, 0, 300);

        MyEffect.moveIn(modifyText, 0, 0, 300);

        MyEffect.fadeOut(modifyExplain, 300);
    }

    //REMOVE area
    @FXML
    void removePaneClick(MouseEvent event) {
        try {
            modifyPane.setVisible(false);
            removePane.setVisible(false);
            addPane.setVisible(false);

            // mainPane.getChildren().remove(0);
            AnchorPane pane = FXMLLoader.load(getClass().getResource("Remove.fxml"));

            mainPane.getChildren().add(pane);
            
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("remove failed");
            e.printStackTrace();
        }
    }

    @FXML
    void removePaneMouseEntered(MouseEvent event) {
        MyEffect.moveIn(removePane, 0, -3, 300);

        MyEffect.moveIn(removeText, 0, -80, 300);

        MyEffect.fadeIn(removeExplain, 300);
    }

    @FXML
    void removePaneMouseExited(MouseEvent event) {
        MyEffect.moveIn(removePane, 0, 0, 300);

        MyEffect.moveIn(removeText, 0, 0, 300);
        
        MyEffect.fadeOut(removeExplain, 300);
    }

    public void initialize(URL url, ResourceBundle rb) {
        addExplain.setVisible(false);
        modifyExplain.setVisible(false);
        removeExplain.setVisible(false);
        MyEffect.fadeIn(addPane, 200);
        MyEffect.fadeIn(modifyPane, 200);
        MyEffect.fadeIn(removePane, 200);
    }
}
