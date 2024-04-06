package MenuScene;

import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import App.App;
import SearchScene.ContainerSearchSceneController;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class MenuSceneController implements Initializable {
    static MenuSceneController gInstance;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane mainMenuPane;

    @FXML
    private AnchorPane centerPane = new AnchorPane();

    @FXML
    private AnchorPane sideBarPane;

    @FXML
    private ImageView iconBack;

    @FXML
    private Button exportNavButton;

    @FXML
    private Button gameNavButton;

    @FXML
    private Button importNavButton;

    @FXML
    private Button menuButton;

    @FXML
    private Button searchNavButton;

    @FXML
    private Button updateNavButton;

    @FXML
    private Button hideSideBarButton;

    @FXML
    private ImageView iconExportBtn;

    @FXML
    private ImageView iconGameBtn;

    @FXML
    private ImageView iconImportBtn;

    @FXML
    private ImageView iconSearchBtn;

    @FXML
    private ImageView iconUpdateBtn;

    @FXML 
    private ImageView iconFavoritesBtn;

    private String currentCenterScene = "haha";

    public static MenuSceneController getInstance() {
        return (gInstance == null)? new MenuSceneController() : gInstance;
    }

    public void setCenterPane(String path, String newCenterScene) throws Exception {
        // System.out.println("1: " + currentCenterScene);
        if (currentCenterScene.equals(newCenterScene)) {
            return;
        }
        currentCenterScene = newCenterScene;
        // System.out.println("2: " + currentCenterScene);
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        
        AnchorPane newCenterPane = loader.load();
        newCenterPane.setPrefHeight(centerPane.getPrefHeight());
        newCenterPane.setPrefWidth(centerPane.getPrefWidth());
        centerPane.getChildren().clear();
        //centerPane.getChildren().addAll(newCenterPane.getChildren());
        centerPane.getChildren().add(newCenterPane);
        if (newCenterScene.equals("UpdateScene")) {
            CM.CM.getInstance().setUpdateSceneController(loader.getController());
            //System.out.println("updatescene loading...");
            if(CM.CM.getInstance().getUpdateSceneController() != null) {
                //System.out.println("update ready!");
            } else {
                //System.out.println("update failed!");
            }
        }
        transitionShowScene(newCenterPane);
    }

    public void setCenterPane(AnchorPane newCenterPane, String newCenterScene) throws Exception {
        if (currentCenterScene.equals(newCenterScene)) {
            return;
        }

        currentCenterScene = newCenterScene;

        centerPane.getChildren().remove(newCenterPane);
        centerPane.getChildren().add(newCenterPane);

        transitionShowScene(newCenterPane);
    }

    private void transitionShowScene(AnchorPane newCenterPane) {
        // TranslateTransition transition = new TranslateTransition(Duration.seconds(1), newCenterPane);
        // int r = (int) (Math.random() * 2);

        // if (r > 0) {
        //     int pos = ((int) (Math.random() * 2) > 0) ? 1 : -1;

        //     transition.setFromY(pos * newCenterPane.getPrefHeight());
        //     transition.setToY(0);
        // } 

        // r = (int) (Math.random() * 2);
        // if (r > 0) {
        //     int pos = ((int) (Math.random() * 2) > 0) ? 1 : -1;
        //     transition.setFromX(pos * newCenterPane.getPrefWidth());
        //     transition.setToX(0);
        // }

        // transition.play();
    }

    @FXML
    void changeColorButtonToDark(MouseEvent event) {

    }

    @FXML
    void changeColorButtonToLight(MouseEvent event) {

    }

    @FXML
    void hideSideBar(MouseEvent event) {
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.8), sideBarPane);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.play();

        TranslateTransition translateTransition=new TranslateTransition(Duration.seconds(0.8), sideBarPane);
        translateTransition.setByX(-(68 + sideBarPane.getPrefWidth()));
        translateTransition.play();

        fadeTransition.setOnFinished(event1 -> {
            sideBarPane.setVisible(false);
        });
    }

    @FXML
    void openExportScene(MouseEvent event) {
        try {
            setCenterPane("../ExportScene/ExportScene.fxml", "ExportScene");
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    @FXML
    void openGameScene(MouseEvent event) {
        try {
            setCenterPane("../Game/GameScene.fxml", "GameScene");
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    @FXML 
    void openFavoritesScene(MouseEvent event) {
        try {
            setCenterPane("../FavoritesScene/FavoritesScene.fxml", "FavoritesScene");
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    @FXML
    void openImportScene(MouseEvent event) {
        try {
            setCenterPane("../ImportScene/ImportScene.fxml", "ImportScene");
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    @FXML
    void openMenuNavigation(MouseEvent event) {

        if (sideBarPane.isVisible()) {
            return;
        }

        sideBarPane.setVisible(true);

        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.8), sideBarPane);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();

        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.8), sideBarPane);
        translateTransition.setByX(68 + sideBarPane.getPrefWidth());
        translateTransition.play();
    }

    @FXML
    void openSearchScene(MouseEvent event) {
        toSearchScene();
    }

    public void toSearchScene() {
        try {
            ContainerSearchSceneController
            .getContainerSearchSceneController()
            .addScene("SearchScene.fxml");
            
            setCenterPane(ContainerSearchSceneController.
            getContainerSearchSceneController().
            getMaiAnchorPane()
            , "SearchScene");

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    @FXML
    void openUpdateScene(MouseEvent event) {
        try {
            setCenterPane("../UpdateDictionary/UpdateScene.fxml", "UpdateScene");
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    @FXML
    void initialize() {
        assert centerPane != null : "fx:id=\"centerPane\" was not injected: check your FXML file 'MenuScene.fxml'.";
        assert exportNavButton != null : "fx:id=\"exportNavButton\" was not injected: check your FXML file 'MenuScene.fxml'.";
        assert gameNavButton != null : "fx:id=\"gameNavButton\" was not injected: check your FXML file 'MenuScene.fxml'.";
        assert hideSideBarButton != null : "fx:id=\"hideSideBarButton\" was not injected: check your FXML file 'MenuScene.fxml'.";
        assert iconExportBtn != null : "fx:id=\"iconExportBtn\" was not injected: check your FXML file 'MenuScene.fxml'.";
        assert iconGameBtn != null : "fx:id=\"iconGameBtn\" was not injected: check your FXML file 'MenuScene.fxml'.";
        assert iconImportBtn != null : "fx:id=\"iconImportBtn\" was not injected: check your FXML file 'MenuScene.fxml'.";
        assert iconSearchBtn != null : "fx:id=\"iconSearchBtn\" was not injected: check your FXML file 'MenuScene.fxml'.";
        assert iconUpdateBtn != null : "fx:id=\"iconUpdateBtn\" was not injected: check your FXML file 'MenuScene.fxml'.";
        assert importNavButton != null : "fx:id=\"importNavButton\" was not injected: check your FXML file 'MenuScene.fxml'.";
        assert mainMenuPane != null : "fx:id=\"mainMenuPane\" was not injected: check your FXML file 'MenuScene.fxml'.";
        assert menuButton != null : "fx:id=\"menuButton\" was not injected: check your FXML file 'MenuScene.fxml'.";
        assert searchNavButton != null : "fx:id=\"searchNavButton\" was not injected: check your FXML file 'MenuScene.fxml'.";
        assert sideBarPane != null : "fx:id=\"sideBarPane\" was not injected: check your FXML file 'MenuScene.fxml'.";
        assert updateNavButton != null : "fx:id=\"updateNavButton\" was not injected: check your FXML file 'MenuScene.fxml'.";
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        sideBarPane.setVisible(false);
        sideBarPane.setTranslateX(-(sideBarPane.getLayoutX() + sideBarPane.getPrefWidth())); 

        try {
            setCenterPane(ContainerSearchSceneController
            .getContainerSearchSceneController()
            .getMaiAnchorPane()
            , "SearchScene");
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }
    
    @FXML
    void getBack(MouseEvent event) {
        // try {
        //     int size = centerPane.getChildren().size();
        //     if (size > 1) {
        //         System.out.println(size);
        //         centerPane.getChildren().remove(size - 1);
        //         // currentState.remove(size - 1);
        //     }
        // }
        // catch (Exception e) {
        //     System.out.println(e.getMessage());
        // }
    }

    @FXML
    private void closeApp(MouseEvent event) {
        App.closeApp();
    }

}
