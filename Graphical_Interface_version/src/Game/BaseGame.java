package Game;

import java.net.URL;
import java.util.ResourceBundle;

import DictionaryPg.Dictionary;

// BaseController.java

import javafx.fxml.Initializable;

public abstract class BaseGame implements Initializable {
    
    // :v
    public abstract void initialize(URL url, ResourceBundle rb);


    // Lay random tu
    protected String getRandomWordFromDictionary() {
        return Dictionary.getDictionary().getRandomWord();
    }

}

