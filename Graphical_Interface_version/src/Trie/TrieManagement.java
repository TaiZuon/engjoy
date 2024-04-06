package Trie;

import java.util.ArrayList;
import java.util.Hashtable;

public abstract class TrieManagement {
    private Trie trie;

    public TrieManagement() {
        trie = new Trie();
    }

    public ArrayList<String> getAllWordFromTrie() {
        return trie.getAllWord();
    }

    /**
     * hàm trả về danh sách các từ bắt đầu bằng prefix
     * @param prefix
     * @return
     */
    public ArrayList <String> searchAllWordStartWith(String prefix) {
        return trie.getAllWordsStartWithPrefix(prefix);
    }

    protected void resetTrie() {
        trie = new Trie();
    }

    protected void addToTrie(String target) {
        trie.insert(target);
    }

    protected void removeFromTrie(String wordTarget) {
        trie.remove(trie.getRoot(), wordTarget, 0);
    }

    protected boolean isExistInTrie(String target) {
        return trie.search(target);
    }
}
