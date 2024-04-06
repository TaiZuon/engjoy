package DictionaryPg;
import java.io.PrintWriter;
import java.util.ArrayList;

public class WordType implements Cloneable{
    private String typeName;
    private ArrayList<Meaning> listMeanings;

    public WordType() {
        typeName = "";
        listMeanings = new ArrayList<Meaning>();
    }

    public WordType(String typeName) {
        this.typeName = typeName;
        this.listMeanings = new ArrayList<Meaning>();
    }

    /**
     * Contruction
     */
    public WordType(String typeName, ArrayList<Meaning> listMeanings) {
        this.typeName = typeName;
        this.listMeanings = listMeanings;
    }

    /**
     * deep clone
     */
    @Override
    public Object clone() {
        WordType wordTypeClone = new WordType(typeName);
        for(Meaning meaning: listMeanings) {
            wordTypeClone.addMeaning((Meaning)meaning.clone());
        }
        return wordTypeClone;
    }

    public String getTypeName() {
        return typeName;
    }

    public ArrayList<Meaning> getListMeanings() {
        return listMeanings;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public void setListMeanings(ArrayList<Meaning> listMeanings) {
        this.listMeanings = listMeanings;
    }

    /**
     * Thêm nghĩa vào danh sách listMeanings
     */
    public void addMeaning(Meaning meaning) {
        listMeanings.add(meaning);
    }

    /**
     * hiên thị từ loại và nghĩa đi kèm
     * */ 
    public void showWordType(){
        //in ra tu loai
        System.out.println("Tu Loai: " + typeName);

        //duyet qua danh sach cac nghia r in ra nghia di kem cua tu do
        for(Meaning meaning : listMeanings) {
            meaning.showMeaning();
        }
    }

    public void exportToFile(PrintWriter printWriter) {
        printWriter.write("*  " + typeName + "\n");
        for (Meaning mean : listMeanings) {
            mean.exportToFile(printWriter);
        }
    }
}