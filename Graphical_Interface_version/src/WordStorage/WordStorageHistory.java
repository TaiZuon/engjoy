package WordStorage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class WordStorageHistory implements WordStorage {
    private final int MAX_LENGTH_LIST_WORD = 20;
    private List<String> wordList;
    private int length;

    private static WordStorageHistory wordStorageHistory;

    private WordStorageHistory() {
        wordList = new ArrayList<String>();
        length = 0;
    }

    public static WordStorageHistory getWordStorageHistory() {
        if (wordStorageHistory == null) {
            return wordStorageHistory = new WordStorageHistory();
        }
        return wordStorageHistory;
    }

    @Override
    public void addWord(String target) {
        if (wordList.contains(target)) {
            return;
        }
        wordList.add(0, target);
        ++length;
        maintainLength();
    }

    @Override
    public void deleteWord(String target) {
        
    }

    private void maintainLength() {
        if (length > MAX_LENGTH_LIST_WORD) {
            wordList.remove(length - 1);
            --length;
        }
    }

    public List<String> getHistoryWordList() {
        return wordList;
    }

    @Override
    public void importFromFileSave() {
        try {
            FileInputStream inputStream = new FileInputStream("Graphical_Interface_version\\src\\WordStorage\\historyWordList.txt");

            InputStreamReader isr = new InputStreamReader(inputStream, "UTF-8");

            BufferedReader br = new BufferedReader(isr);

            String line = "";

            while ((line = br.readLine()) != null) {
                if (!line.isEmpty()) {
                    addWord(line);
                }
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void exportFromFileSave() {
        try {
            FileOutputStream outputStream = new FileOutputStream("Graphical_Interface_version\\src\\WordStorage\\historyWordList.txt");
            OutputStreamWriter writer = new OutputStreamWriter(outputStream, "UTF-8");
            BufferedWriter buffer = new BufferedWriter(writer);
            PrintWriter printWriter = new PrintWriter(buffer);

            for (String word: wordList) {
                printWriter.write(word + "\n");
            }
            printWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
     
}
