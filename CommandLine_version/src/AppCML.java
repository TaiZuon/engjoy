import DictionaryPg.DictionaryCommandline;
import DictionaryPg.DictionaryManagement;

public class AppCML {
    public static void main(String[] args) throws Exception {
        DictionaryManagement.getDictionaryManagement().insertFromFile("src\\DictionaryPg\\d.txt");
        DictionaryCommandline.getDictionaryCommandline().dictionaryAdvanced(); 
    }
}