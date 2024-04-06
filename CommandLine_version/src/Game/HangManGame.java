package Game;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import DictionaryPg.Dictionary;
import DictionaryPg.Word;
import DictionaryPg.WordType;

public class HangManGame {
    private String finalGuess = "";
    private String pickedWord = "";
    private int score = 0;
    private int round = 0;
    private int attempts = 6;
    private List<WordType> wordTypes;
    private String meaning;
    private Dictionary dictionary;
    private Set<Character> guessedCharacters = new HashSet<>();


    /**
     * HangMan.
     */
    public HangManGame(Dictionary dictionary) {
        this.dictionary = dictionary;
        String randomWord = dictionary.getRandomWord();
        if(randomWord != null) {
            pickedWord = randomWord;
            finalGuess = "-".repeat(pickedWord.length());
        } else {
            System.out.println("Khong co tu gi trong tu dien :v");
        }

        // lay nghia cua tu do de them vao goi y.
        Word word = dictionary.getWordExplainOf(pickedWord);
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

    }


    /**
     * ham thay dau gach bang tu da doan dc.
     */
    public String DisplayLetter(char letter, String selectedWord) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < selectedWord.length(); i++) {
            char currentLetter = selectedWord.charAt(i);
            if (finalGuess.length() > i) {
                if (finalGuess.charAt(i) != '-') {
                    result.append(finalGuess.charAt(i));
                } else if (currentLetter == letter) {
                    result.append(letter);
                } else {
                    result.append('-');
                }
            } else {
                if (currentLetter == letter) {
                    result.append(letter);
                } else {
                    result.append('-');
                }
            }
        }

        finalGuess = result.toString();
        return finalGuess;
    }

    /**
     * hien thi hinh HangMan.
     */
    private void DisplayHangMan() {
        switch(attempts) {
            case 6:
                System.out.println("\t        _______");
                System.out.println("\t       |     |");
                System.out.println("\t       |");
                System.out.println("\t       |");
                System.out.println("\t       |");
                System.out.println("\t       |");
                break;
            case 5:
                System.out.println("\t        _______");
                System.out.println("\t       |     |");
                System.out.println("\t       |     O");
                System.out.println("\t       |");
                System.out.println("\t       |");
                System.out.println("\t       |");
                break;
            case 4:

                System.out.println("\t        _______");
                System.out.println("\t       |     |");
                System.out.println("\t       |     O");
                System.out.println("\t       |     |");
                System.out.println("\t       |");
                System.out.println("\t       |");
                break;
            case 3:
                System.out.println("\t        _______");
                System.out.println("\t       |     |");
                System.out.println("\t       |     O");
                System.out.println("\t       |    /|");
                System.out.println("\t       |");
                System.out.println("\t       |");
                break;
            case 2:
                System.out.println("\t        _______");
                System.out.println("\t       |     |");
                System.out.println("\t       |     O");
                System.out.println("\t       |    /|\\");
                System.out.println("\t       |");
                System.out.println("\t       |");
                break;
            case 1:
                System.out.println("\t        _______");
                System.out.println("\t       |     |");
                System.out.println("\t       |     O");
                System.out.println("\t       |    /|\\");
                System.out.println("\t       |    /");
                System.out.println("\t       |");
                break;
            case 0:
                System.out.println("\t        _______");
                System.out.println("\t       |     |");
                System.out.println("\t       |     O");
                System.out.println("\t       |    /|\\");
                System.out.println("\t       |    / \\");
                System.out.println("\t       |");
        }
    }

    /**
     * cho nguoi choi co hoi lam lai hoac choi tiep.
     */
    private void RePlay(Scanner scanner) {
        finalGuess = "";
        attempts = 6;

        System.out.println("\tBan co muon choi lai khong (y/n)?: ");
        char playAgain = scanner.next().charAt(0);

        if (playAgain == 'y' || playAgain == 'Y') {
            String randomWord = dictionary.getRandomWord();
            if(randomWord != null) {
                pickedWord = randomWord;
                finalGuess = "-".repeat(pickedWord.length());
                guessedCharacters = new HashSet<>();
            }
            startGame();
        } else {
            System.out.println("\t\tCam on ban da choi tro choi!");
        }
    }


    /**
     * ham khoi tao game.
     */
    public void startGame() {
        Scanner scanner = new Scanner(System.in);
        int wrongGuessCount = 0;
        System.out.println("\t\t\tWelCome to HangMan");

        // Ramdom tu moi.
        String randomWord = dictionary.getRandomWord();
        if(randomWord != null) {
            pickedWord = randomWord;
            finalGuess = "-".repeat(pickedWord.length());
        }

        // Lay nghia va tu loai cua tu moi
        Word word = dictionary.getWordExplainOf(pickedWord);
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


        while (attempts > 0) {
            System.out.println("\tTu hien tai: " + finalGuess);
            System.out.println("\tSo lan du doan con lai: " + attempts);
            System.out.println("\tCac chu cai da doan duoc trong tu: " + guessedCharacters);
            System.out.println("\tSo van thang hien tai: " + round);

            DisplayHangMan();
            System.out.print("\tDu doan cua ban: ");
            char letter = scanner.next().charAt(0);
            
            //check lap tu
            if (guessedCharacters.contains(letter)) {
                System.out.println("\t\t\tTu do da duoc doan roi.\n");
                attempts--;
                wrongGuessCount++;
                if(wrongGuessCount == 2) {
                    System.out.println("\t         Goi y 1: loai tu cua tu do la " + wordTypes.get(0).getTypeName() + "\n");
                }

                if (wrongGuessCount == 4) {
                    System.out.println("\t          Goi y 2 la: nghia cua tu do la " + meaning + "\n");
                }
                continue;
            }

            guessedCharacters.add(letter);

            // kiem tra
            if (pickedWord.indexOf(letter) >= 0) {
                finalGuess = DisplayLetter(letter, pickedWord);
                System.out.println("\t\t\tBan doan dung tu roi!\n");
            } else {
                System.out.println("\t\t\tBan da doan sai.\n");
                guessedCharacters.add(letter);
                attempts--;
                wrongGuessCount++;
                if(wrongGuessCount == 2) {
                    System.out.println("\t         Goi y 1: loai tu cua tu do la " + wordTypes.get(0).getTypeName() + "\n");
                }

                if (wrongGuessCount == 4) {
                    System.out.println("\t          Goi y 2 la: nghia cua tu do la " + meaning + "\n");
                }
            }


            // neu doan dung
            if (finalGuess.equals(pickedWord)) {
                score += attempts;
                round++;
                System.out.println("\t    Chuc mung ban da du doan dung, tu la: " + pickedWord + "\n");
                System.out.println("\t\t Diem so cua ban la: " + score);
                break;
            }
        }

        // Game over
        if (attempts == 0) {
            DisplayHangMan();
            score = 0;
            round = 0;
            System.out.println("\t     Ban da tra loi het luot du doan va mat het diem, tu can doan la: " + pickedWord + "\n");
        }

        RePlay(scanner);
    }
}