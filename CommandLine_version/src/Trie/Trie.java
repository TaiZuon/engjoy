package Trie;

import java.util.ArrayList;

public class Trie {

    // CHARACTER size (# of symbols)
    // character -,  , ' , . , a, b, ... , z
    static final int CHARACTER_SIZE = 30;
     
    // trie node
    private static class TrieNode
    {
        TrieNode[] children = new TrieNode[CHARACTER_SIZE];
      
        // isEndOfWord is true if the node represents
        // end of a word
        boolean isEndOfWord;
         
        TrieNode(){
            isEndOfWord = false;
            for (int i = 0; i < CHARACTER_SIZE; i++)
                children[i] = null;
        }
    };
      
    static TrieNode root = new TrieNode();

    public TrieNode getRoot() {
        return root;
    }

    /**
     * chuyển ký sang số tương ứng cho danh sách trienode
     * @param c
     * @return
     */
    private int charToInt(char c) {
        switch (c) {
            case '-':
                return 0;
            case ' ':
                return 1;
            case '\'':
                return 2;
            case '.':
                return 3;
        }
        return c - 'a' + 4;
    }

    /**
     * chuyển số sang ký tự tương ứng
     * @param c
     * @return
     */
    private char intToChar(int c) {
        switch (c) {
            case 0:
                return '-';
            case 1:
                return ' ';
            case 2:
                return '\'';
            case 3:
                return '.';
        }
        return (char) (c + 'a' - 4);
    }
     
    // If not present, inserts key into trie
    // If the key is prefix of trie node,
    // just marks leaf node
    public void insert(String key)
    {
        int level;
        int length = key.length();
        int index;
      
        TrieNode pCrawl = root;
      
        for (level = 0; level < length; level++)
        {
            index = charToInt(key.charAt(level));
            if (pCrawl.children[index] == null)
                pCrawl.children[index] = new TrieNode();
      
            pCrawl = pCrawl.children[index];
        }
      
        // mark last node as leaf
        pCrawl.isEndOfWord = true;
    }

    // Returns true if root has no children, else false
    public boolean isEmpty(TrieNode root)
    {
        for (int i = 0; i < CHARACTER_SIZE; i++)
            if (root.children[i] != null)
                return false;
        return true;
    }
 
    // Recursive function to delete a key from given Trie
    public TrieNode remove(TrieNode root, String key, int depth)
    {
        // If tree is empty
        if (root == null)
            return null;
 
        // If last character of key is being processed
        if (depth == key.length()) {
 
            // This node is no more end of word after
            // removal of given key
            if (root.isEndOfWord)
                root.isEndOfWord = false;
 
            // If given is not prefix of any other word
            if (isEmpty(root)) {
                root = null;
            }
 
            return root;
        }
 
        // If not last character, recur for the child
        // obtained using ASCII value
        int index = charToInt(key.charAt(depth));
        root.children[index] = remove(root.children[index], key, depth + 1);
 
        // If root does not have any child (its only child got
        // deleted), and it is not end of another word.
        if (isEmpty(root) && root.isEndOfWord == false){
            root = null;
        }
 
        return root;
    }
      
    // Returns true if key presents in trie, else false
    public boolean search(String key)
    {
        int level;
        int length = key.length();
        int index;
        TrieNode pCrawl = root;
      
        for (level = 0; level < length; level++)
        {
            index = charToInt(key.charAt(level));
      
            if (pCrawl.children[index] == null)
                return false;
      
            pCrawl = pCrawl.children[index];
        }
      
        return (pCrawl.isEndOfWord);
    }
    
    /**
     * 
     * @param node
     * @param word
     * @param wordList
     */
    public void findWord(TrieNode node, String word, ArrayList <String> wordList) {
        // push word when node is end Of Word
        if (node.isEndOfWord) {
            wordList.add(word);
        }
        
        for (int i = 0; i < CHARACTER_SIZE ; i++) {
            // if NON NULL child is found
            // add parent key to str and
            // call the display function recursively
            // for child node
            if (node.children[i] != null) {
                findWord(node.children[i], word + intToChar(i), wordList);
            }
        }
    }

    /**
     * lấy ra node của chữ cái cuối cùng của prefix
     * @param prefix
     * @return
     */
    private TrieNode getLastTrieNodeOfPrefix(String prefix) {
        int level;
        int length = prefix.length();
        int index;
        TrieNode pCrawl = root;
      
        for (level = 0; level < length; level++)
        {
            index = charToInt(prefix.charAt(level));

            if (pCrawl.children[index] == null)
                return null;

            pCrawl = pCrawl.children[index];
        }
        return pCrawl;
    }

    /**
     * hàm trả về danh sách tất cả các từ bắt đầu bằng prefix
     * @param prefix
     * @return
     */
    public ArrayList <String> getAllWordsStartWithPrefix(String prefix) {
        TrieNode nodePrefix = getLastTrieNodeOfPrefix(prefix);
        if (nodePrefix != null) {
            ArrayList <String> res = new ArrayList<>();
            findWord(nodePrefix, prefix, res);
            return res;
        }
        return null;
    }

    /**
     * hàm trả về danh sách các từ trong Trie
     * @return
     */
    public ArrayList <String> getAllWord() {
        ArrayList <String> res = new ArrayList<>();
        findWord(root, "", res);
        return res;
    }
}
// This code is contributed by Sumit Ghosh