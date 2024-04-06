package DictionaryPg;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Random;

import Trie.Trie;

/**
 * Dictionary
 */
public class Dictionary {

    private static Hashtable<String, Word> listWord;
    private static Trie trie;

    /** 
     * Obj Dictionary duy nhất
     */
    private static Dictionary dictionary;

    private Dictionary() {
        listWord = new Hashtable<String, Word>();
        trie = new Trie();
    }
    
    /**
     * hàm trả về dictionary duy nhất, không cần tạo object
     */
    public static Dictionary getDictionary() { 
        if (dictionary == null) {
            dictionary = new Dictionary();
        }
        return dictionary;
    }

    /**
     * insert từ vào trie
     * thêm key wordTarget và value wordExplain
     */
    public void add(String wordTarget, Word wordExplain) {
        trie.insert(wordTarget);
        listWord.put(wordTarget, wordExplain);
    }

    /**
     * xóa từ khỏi trie và listWord
     * @param wordTarget
     */
    public void remove(String wordTarget) {
        trie.remove(trie.getRoot(), wordTarget, 0);
        listWord.remove(wordTarget);
    }

    /**
     * xóa từ khỏi Trie
     * @param wordTarget
     */
    public void removeFromTrie(String wordTarget) {
        listWord.remove(wordTarget);
    }

    /**
     * Thêm từ vào Trie
     * @param wordTarget
     */
    public void addToTrie(String wordTarget) {
        trie.insert(wordTarget);
    }

    /**
     * xóa từ khỏi hashtable
     * @param wordTarget
     */
    public void removeHashTable(String wordTarget) {
        trie.remove(trie.getRoot(), wordTarget, 0);
    }

    /**
     * show ra tất cả từ vựng và explain
     * theo thứ tự alphabet
     */
    public void showAllWordAlphabet() {
        ArrayList<String> allWords = trie.getAllWord();
        
        for (String word : allWords) {
            Word wordExplain = getWordExplainOf(word);

            //bo qua tu null sau khi xoa tu do.
            if (wordExplain == null) {
                continue;
            }

            wordExplain.showWord();
        }
    }

    /**
     * hàm trả về danh sách các từ bắt đầu bằng prefix
     * @param prefix
     * @return
     */
    public ArrayList <String> searchAllWordStartWith(String prefix) {
        return trie.getAllWordsStartWithPrefix(prefix);
    }

    /**
     * trả về explain của word
     * @param word
     * @return
     */
    public Word getWordExplainOf(String target) {
        return listWord.get(target);
    } 

    /**
     * search từ có trong dictionary không
     * nếu không trả về null
     * @param word
     * @return
     */
    public Word searchWord(String target) {
        if (trie.search(target)) {
            return listWord.get(target);
        }
        return null;
    }

    /**
     * hiện thông tin 1 từ
     * @param target
     */
    public void showWordExplainOf(String target) {
        Word wordExplain = getWordExplainOf(target);
        
        if (wordExplain != null) {
            wordExplain.showWord();
        }
        else {
            System.out.println("Khong co tu " + target + " trong tu dien");
        }
    }
    
    /**
     * lay random tu trong tu dien cho game, bo cac ky tu dac biet.
     */
    public String getRandomWord() {
        Random random = new Random();
        ArrayList<String> filteredWords = new ArrayList<>();

        //duyet qua cac tu va chi lay nhung tu ma khong co ky tu dac biet cho vao fillteredWords :v
        for (String word : listWord.keySet()) {
            if (!word.contains("[^a-zA-Z]") && !word.contains("-") && !word.contains(" ")) {
                filteredWords.add(word);
            }
        }

        if (filteredWords.isEmpty()) {
            return null;
        }

        //chon tu tu danh sach day
        int randomIndex = random.nextInt(filteredWords.size());
        return filteredWords.get(randomIndex);
    }

    public void exportToFile(PrintWriter printWriter) {
        ArrayList<String> list = trie.getAllWord();
        for (String target : list) {
            listWord.get(target).exportToFile(printWriter);
        } 
    }

    public ArrayList<String> getAllWord() {
        return trie.getAllWord();
    }

    public void resetToNone() {
        listWord = new Hashtable<String, Word>();
        trie = new Trie();
    }
}
