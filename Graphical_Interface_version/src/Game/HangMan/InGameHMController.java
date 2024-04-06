package Game.HangMan;


import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import DictionaryPg.Dictionary;
import DictionaryPg.Word;
import DictionaryPg.WordType;
import Game.BaseGame;
import Game.Coin.Coin;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Pair;

public class InGameHMController extends BaseGame{
    private String finalGuess = "";
    private String pickedWord = "";
    private int length;
    private int attempts = 6;
    private List<WordType> wordTypes;
    private String meaning;
    private boolean isPlaying;
    private int hintPrice = 1;
    private Set<String> existedCharList = new HashSet<>();

    @FXML
    private Label amountOfCoin;

    @FXML
    private Button hintButton;

    @FXML
    private Label hintCost;

    @FXML private ImageView myImageView;
    @FXML private Image HMImage6 = new Image(getClass().getResourceAsStream("Image/HM6.png"));
    @FXML private Image HMImage5 = new Image(getClass().getResourceAsStream("Image/HM5.png"));
    @FXML private Image HMImage4 = new Image(getClass().getResourceAsStream("Image/HM4.png"));
    @FXML private Image HMImage3 = new Image(getClass().getResourceAsStream("Image/HM3.png"));
    @FXML private Image HMImage2 = new Image(getClass().getResourceAsStream("Image/HM2.png"));
    @FXML private Image HMImage1 = new Image(getClass().getResourceAsStream("Image/HM1.png"));
    @FXML private Image HMImage0 = new Image(getClass().getResourceAsStream("Image/HM0.png"));
    @FXML private Button replayButton;
    @FXML private Label HintLabel;
    @FXML private Label displayLabel;

    @FXML private Button buttonA;
    @FXML private Button buttonB;
    @FXML private Button buttonC;
    @FXML private Button buttonD;
    @FXML private Button buttonE;
    @FXML private Button buttonF;
    @FXML private Button buttonG;
    @FXML private Button buttonH;
    @FXML private Button buttonI;
    @FXML private Button buttonJ;
    @FXML private Button buttonK;
    @FXML private Button buttonL;
    @FXML private Button buttonM;
    @FXML private Button buttonN;
    @FXML private Button buttonO;
    @FXML private Button buttonP;
    @FXML private Button buttonQ;
    @FXML private Button buttonR;
    @FXML private Button buttonS;
    @FXML private Button buttonT;
    @FXML private Button buttonU;
    @FXML private Button buttonV;
    @FXML private Button buttonW;
    @FXML private Button buttonX;
    @FXML private Button buttonY;
    @FXML private Button buttonZ;
    @FXML private Button dashbutton;

    private void getExistedCharList() {
        for (int i = 0; i < length; i++) {
            boolean existed = false;
            for (int j = 0; j < existedCharList.size(); j++) {
                if (existedCharList.contains("" + pickedWord.charAt(i))) {
                    existed = true;
                    break;
                } 
            }
            if (!existed) {
                existedCharList.add("" + pickedWord.charAt(i));
            }
        }
    }

    public void displayHangMan() {
        switch(attempts) {
            case 5:
                myImageView.setImage(HMImage5);
                break;
            case 4:
                myImageView.setImage(HMImage4);
                break;
            case 3:
                myImageView.setImage(HMImage3);
                break;
            case 2:
                myImageView.setImage(HMImage2);
                break;
            case 1:
                myImageView.setImage(HMImage1);
                break;
            case 0:
                myImageView.setImage(HMImage0);
                break;
        }
    }

    public void selectRandomWordFromDictionary() {
        String randomWord = getRandomWordFromDictionary();
    
        if (randomWord != null) {
            // Lấy số lượng chữ cái trong từ
            int wordLength = randomWord.length();

            length = wordLength;
            
            // Tạo một chuỗi dấu gạch "-" tương ứng với số lượng chữ cái
            String dashedWord = "_".repeat(wordLength);
    
            // Gán từ và dấu gạch vào displayLabel
            finalGuess = dashedWord;
            pickedWord = randomWord;
            displayLabel.setText(dashedWord);
        }
    }

    public void initialize(URL url, ResourceBundle rb) {
        selectRandomWordFromDictionary();
        replayButton.setVisible(false);
        isPlaying = true;
        length = pickedWord.length();
        getExistedCharList();
        renderCoin();
        //print();
    }

    public void print() {
        for (String a : existedCharList) {
            System.out.println(a);
        }
    }

    // Cập nhật chữ cái.
    public void guessLetter(char letter) {
        if(isPlaying) {
            switch (letter) {
                case 'a':
                    buttonA.setVisible(false);
                    break;
                case 'b':
                    buttonB.setVisible(false);
                    break;
                case 'c':
                    buttonC.setVisible(false);
                    break;
                case 'd':
                    buttonD.setVisible(false);
                    break;
                case 'e':
                    buttonE.setVisible(false);
                    break;
                case 'f':
                    buttonF.setVisible(false);
                    break;
                case 'g':
                    buttonG.setVisible(false);
                    break;
                case 'h':
                    buttonH.setVisible(false);
                    break;
                case 'i':
                    buttonI.setVisible(false);
                    break;
                case 'j':
                    buttonJ.setVisible(false);
                    break;
                case 'k':
                    buttonK.setVisible(false);
                    break;
                case 'l':
                    buttonL.setVisible(false);
                    break;
                case 'm':
                    buttonM.setVisible(false);
                    break;
                case 'n':
                    buttonN.setVisible(false);
                    break;
                case 'o':
                    buttonO.setVisible(false);
                    break;
                case 'p':
                    buttonP.setVisible(false);
                    break;
                case 'q':
                    buttonQ.setVisible(false);
                    break;
                case 'r':
                    buttonR.setVisible(false);
                    break;
                case 's':
                    buttonS.setVisible(false);
                    break;
                case 't':
                    buttonT.setVisible(false);
                    break;
                case 'u':
                    buttonU.setVisible(false);
                    break;
                case 'v':
                    buttonV.setVisible(false);
                    break;
                case 'w':
                    buttonW.setVisible(false);
                    break;
                case 'x':
                    buttonX.setVisible(false);
                    break;
                case 'y':
                    buttonY.setVisible(false);
                    break;
                case 'z':
                    buttonZ.setVisible(false);
                    break;
                case '-':
                    dashbutton.setVisible(false);
                    break;
            }

            Word word = Dictionary.getDictionary().getWordExplainOf(pickedWord);
            if (word != null) {
                wordTypes = word.getListWordTypes();
                if (!wordTypes.isEmpty()) {
                    if (wordTypes.get(0).getListMeanings().isEmpty()) {
                        meaning = "tu do khong co nghia";
                    } else {
                        meaning = wordTypes.get(0).getListMeanings().get(0).getExplain();
                    }
                }
            }

            StringBuilder updatedGuess = new StringBuilder(finalGuess);
            boolean correctGuess = false;

            for (int i = 0; i < pickedWord.length(); i++) {
                if (pickedWord.charAt(i) == letter) {
                    updatedGuess.setCharAt(i, letter);
                    correctGuess = true;
                    existedCharList.remove("" + pickedWord.charAt(i));
                }
            }

            if (correctGuess) {
                finalGuess = updatedGuess.toString();
                displayLabel.setText(finalGuess);
            } else {
                attempts--;
                displayHangMan();
            }
            if (attempts == 4) {
                HintLabel.setText("Gợi ý 1: " + wordTypes.get(0).getTypeName());
            }
            if (attempts == 2) {
                HintLabel.setText("Gợi ý 2: " + meaning);
            }

            if (finalGuess.equals(pickedWord)) {
                displayLabel.setText("Your Mom!\nThe Word is: " + pickedWord);
                replayButton.setVisible(true);
                Coin.getInstance().addCoin(pickedWord.length());
                renderCoin();
                isPlaying = false;
            } else if (attempts == 0) {
                displayLabel.setText("You have failed!\nThe Word is: " + pickedWord);
                replayButton.setVisible(true);
                isPlaying = false;
            }
        }
    }

    @FXML
    void hint(ActionEvent event) {
        if (isPlaying) {
            hint();
        }
    }

    void renderCoin() {
        amountOfCoin.setText(""  + Coin.getInstance().getAmount());
        Coin.getInstance().saveAmountToFile();
        hintCost.setText("" + hintPrice);
    }

    public void alertNotEnoughCoins() {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        String headerext = "Your amount of Coins is not enough to get Hint";
        alert.setTitle("Out of Coins");
        alert.setHeaderText(headerext);
        alert.setContentText(null);
        // Tùy chọn Yes và No
        ButtonType buttonTypeYes = new ButtonType("OK");
        alert.getButtonTypes().setAll(buttonTypeYes);
        // Hiển thị hộp thoại và xử lý kết quả
        alert.showAndWait().ifPresent(result -> {
            if (result == buttonTypeYes) {
                // Nếu người dùng chọn Yes
            }
        });
    }

    void hint() {
        if (!Coin.getInstance().minusCoin(hintPrice)) {
            alertNotEnoughCoins();
            return;
        }
        if (!existedCharList.isEmpty()) {
            for (String temp : existedCharList) {
                guessLetter(temp.charAt(0));
                hintPrice *= 2;
                renderCoin();
                break;
            }
        } else {

        }
    }

    @FXML
    void PressedA(ActionEvent event) {
        guessLetter('a');
    }
    @FXML
    void PressedB(ActionEvent event) {
        guessLetter('b');
    }
    @FXML
    void PressedC(ActionEvent event) {
        guessLetter('c');
    }
    @FXML
    void PressedD(ActionEvent event) {
        guessLetter('d');
    }
    @FXML
    void PressedE(ActionEvent event) {
        guessLetter('e');
    }
    @FXML
    void PressedF(ActionEvent event) {
        guessLetter('f');
    }
    @FXML
    void PressedG(ActionEvent event) {
        guessLetter('g');
    }
    @FXML
    void PressedH(ActionEvent event) {
        guessLetter('h');
    }
    @FXML
    void PressedI(ActionEvent event) {
        guessLetter('i');
    }
    @FXML
    void PressedJ(ActionEvent event) {
        guessLetter('j');
    }
    @FXML
    void PressedK(ActionEvent event) {
        guessLetter('k');
    }
    @FXML
    void PressedL(ActionEvent event) {
        guessLetter('l');
    }
    @FXML
    void PressedM(ActionEvent event) {
        guessLetter('m');
    }
    @FXML
    void PressedN(ActionEvent event) {
        guessLetter('n');
    }
    @FXML
    void PressedO(ActionEvent event) {
        guessLetter('o');
    }
    @FXML
    void PressedP(ActionEvent event) {
        guessLetter('p');
    }
    @FXML
    void PressedQ(ActionEvent event) {
        guessLetter('q');
    }
    @FXML
    void PressedR(ActionEvent event) {
        guessLetter('r');
    }
    @FXML
    void PressedS(ActionEvent event) {
        guessLetter('s');
    }
    @FXML
    void PressedT(ActionEvent event) {
        guessLetter('t');
    }
    @FXML
    void PressedU(ActionEvent event) {
        guessLetter('u');
    }
    @FXML
    void PressedV(ActionEvent event) {
        guessLetter('v');
    }
    @FXML
    void PressedW(ActionEvent event) {
        guessLetter('w');
    }
    @FXML
    void PressedX(ActionEvent event) {
        guessLetter('x');
    }
    @FXML
    void PressedY(ActionEvent event) {
        guessLetter('y');
    }
    @FXML
    void PressedZ(ActionEvent event) {
        guessLetter('z');
    }
    @FXML 
    void Presseddash(ActionEvent event) {
        guessLetter('-');
    }

    @FXML
    void replay(ActionEvent event) {
        isPlaying = true;
        hintPrice = 1;
        selectRandomWordFromDictionary();
        existedCharList.clear();
        getExistedCharList();
        //print();
        renderCoin();
        attempts = 6;
        myImageView.setImage(HMImage6);
        HintLabel.setText(" ");
        buttonA.setVisible(true);
        buttonB.setVisible(true);
        buttonC.setVisible(true);
        buttonD.setVisible(true);
        buttonE.setVisible(true);
        buttonF.setVisible(true);
        buttonG.setVisible(true);
        buttonH.setVisible(true);
        buttonI.setVisible(true);
        buttonJ.setVisible(true);
        buttonK.setVisible(true);
        buttonL.setVisible(true);
        buttonM.setVisible(true);
        buttonN.setVisible(true);
        buttonO.setVisible(true);
        buttonP.setVisible(true);
        buttonQ.setVisible(true);
        buttonR.setVisible(true);
        buttonS.setVisible(true);
        buttonT.setVisible(true);
        buttonU.setVisible(true);
        buttonV.setVisible(true);
        buttonW.setVisible(true);
        buttonX.setVisible(true);
        buttonY.setVisible(true);
        buttonZ.setVisible(true);
        dashbutton.setVisible(true);
    }
}
