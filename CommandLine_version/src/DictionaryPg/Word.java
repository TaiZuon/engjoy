package DictionaryPg;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Word implements Cloneable{
    private String word;
    private String pronounce;
    private ArrayList<WordType> listWordTypes;
    private ArrayList<Idioms> listIdioms;

    public Word() {
        word = "";
        pronounce = "";
        listWordTypes = new ArrayList<WordType>();
        listIdioms = new ArrayList<Idioms>();
    }

    public Word(String word) {
        this.word = word;
        pronounce = "";
        this.listWordTypes = new ArrayList<WordType>();
        this.listIdioms = new ArrayList<Idioms>();
    }

    public Word(String word, String pronounce) {
        this.word = word;
        this.pronounce = pronounce;
        this.listWordTypes = new ArrayList<WordType>();
        this.listIdioms = new ArrayList<Idioms>();
    }

    public Word(String word, String pronounce, ArrayList<WordType> listWordTypes) {
        this.word = word;
        this.pronounce = pronounce;
        this.listWordTypes = listWordTypes;
        this.listIdioms = new ArrayList<Idioms>();
    }

    public Word(String word, String pronounce, ArrayList<WordType> listWordTypes, ArrayList<Idioms> listIdioms) {
        this.word = word;
        this.pronounce = pronounce;
        this.listWordTypes = listWordTypes;
        this.listIdioms = listIdioms;
    }

    /**
     * deep clone
     */
    @Override
    public Word clone() {
        Word wordClone = new Word(word, pronounce);
        for(WordType wordType: this.listWordTypes) {
            wordClone.addWordType((WordType) wordType.clone());
        }
        for(Idioms idioms: this.listIdioms) {
            wordClone.addIdioms((Idioms) idioms.clone());
        }
        return wordClone;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getPronounce() {
        return pronounce;
    }

    public void setPronounce(String pronounce) {
        this.pronounce = pronounce;
    }

    public ArrayList<WordType> getListWordTypes() {
        return listWordTypes;
    }

    public void setListWordTypes(ArrayList<WordType> listWordTypes) {
        this.listWordTypes = listWordTypes;
    }

    public ArrayList<Idioms> getListIdioms() {
        return listIdioms;
    }

    public void setListIdioms(ArrayList<Idioms> listIdioms) {
        this.listIdioms = listIdioms;
    }
    
    /**
     * thêm wordType vào danh sách listWordTypes
     * @param wordType
     */
    public void addWordType(WordType wordType) {
        listWordTypes.add(wordType);
    }

    public void addIdioms(Idioms idioms) {
        listIdioms.add(idioms);
    }

    /**
     * hiện thị tất cả thuộc tính của từ
     */
    public void showWord(){
        System.out.println("Tu: " + word);

        System.out.println(pronounce);

        for (WordType wordType : listWordTypes) {
            wordType.showWordType();
        }

        for(Idioms idioms : listIdioms) {
            idioms.showIdioms();
        }
    }

    public void exportToFile(PrintWriter printWriter) {
        if (word.equals("")) {
            return;
        }
        
        printWriter.write("@" + word + " ");
        printWriter.write(pronounce + "\n");
        for (WordType wordType: listWordTypes) {
            wordType.exportToFile(printWriter);
        }

        for (Idioms idioms: listIdioms) {
            idioms.exportToFile(printWriter);
        }
    }
}