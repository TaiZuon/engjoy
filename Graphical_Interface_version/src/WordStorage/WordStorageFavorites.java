package WordStorage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import Trie.TrieManagement;

/**
 * WordStorageFavorites
 */
public class WordStorageFavorites extends TrieManagement implements WordStorage {

    private static WordStorageFavorites wordStorageFavorites;

    private WordStorageFavorites() {
        super();
    }

    public static WordStorageFavorites getWordStorageFavorites() {
        if (wordStorageFavorites == null) {
            wordStorageFavorites = new WordStorageFavorites();
        }
        return wordStorageFavorites;
    }

    @Override
    public void addWord(String target) {
        addToTrie(target);
    }

    @Override
    public void deleteWord(String target) {
        removeFromTrie(target);
    }

    public boolean isExistInFavorites(String target) {
        return isExistInTrie(target);
    }

    @Override
    public void importFromFileSave() {
        try {
            FileInputStream inputStream = new FileInputStream("Graphical_Interface_version\\src\\WordStorage\\favoritesWordList.txt");

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
            FileOutputStream outputStream = new FileOutputStream("Graphical_Interface_version\\src\\WordStorage\\favoritesWordList.txt");
            OutputStreamWriter writer = new OutputStreamWriter(outputStream, "UTF-8");
            BufferedWriter buffer = new BufferedWriter(writer);
            PrintWriter printWriter = new PrintWriter(buffer);

            List<String> listWord = getAllWordFromTrie();
            for (String word: listWord) {
                printWriter.write(word + "\n");
            }
            printWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}