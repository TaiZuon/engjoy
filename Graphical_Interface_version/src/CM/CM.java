package CM;

import MenuScene.MenuSceneController;
import SearchScene.SearchSceneController;
import UpdateDictionary.Add;
import UpdateDictionary.UpdateScene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

public class CM {
    private static CM gInstance;

    MenuSceneController menuSceneController;

    SearchSceneController searchSceneController;

    UpdateScene updateSceneController;

    Add addController; 

    FXMLLoader loader;

    Scene mainScene;

    private CM() {};

    public static CM getInstance() {
        if (gInstance == null) {
            gInstance = new CM();
        }
        return gInstance;
    }

    public void setScene(Scene a) {
        this.mainScene = a;
    }

    public Scene getScene() {
        return this.mainScene;
    }

    public UpdateScene getUpdateSceneController() {
        return updateSceneController;
    }

    public void setUpdateSceneController(UpdateScene a) {
        this.updateSceneController = a;
    }

    public Add getAddController() {
        return addController;
    }

    public void setAddController(Add a) {
        this.addController = a;
    }

    public FXMLLoader getFXMLLoader() {
        return loader;
    }

    public void setFXMLLoader(FXMLLoader a) {
        this.loader = a;
    }

    public SearchSceneController getSearchSceneController() {
        return searchSceneController;
    }

    public void setSearchSceneController(SearchSceneController a) {
        this.searchSceneController = a;
        // if (this.menuSceneController != null) {
        //     System.out.println("set controller done");
        // } else {
        //     System.out.println("set failed");
        // }
    }

    public MenuSceneController getMenuSceneController() {
        return menuSceneController;
    }

    public void setMenuSceneController(MenuSceneController a) {
        this.menuSceneController = a;
        // if (this.menuSceneController != null) {
        //     System.out.println("set controller done");
        // } else {
        //     System.out.println("set failed");
        // }
    }

    public void print() {
        System.out.println("test");
    }
}
