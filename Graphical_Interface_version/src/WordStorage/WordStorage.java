package WordStorage;

public interface WordStorage {
    public void addWord(String target);

    public void deleteWord(String target);

    public void importFromFileSave(); 

    public void exportFromFileSave(); 
}
