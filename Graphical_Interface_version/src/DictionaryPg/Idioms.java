package DictionaryPg;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;

public class Idioms implements Cloneable{
    private String idioms;
    private ArrayList<String> listMeaning;

    public Idioms() {
        idioms = "";
        listMeaning = new ArrayList<String>();
    }

    public Idioms(String idioms) {
        this.idioms = idioms;
        listMeaning = new ArrayList<String>();
    }

    public Idioms(String idioms, ArrayList<String> listMeaning) {
        this.idioms = idioms;
        this.listMeaning = listMeaning;
    }

    /**
     * deep clone
     */
    @Override
	public Object clone() {
        Idioms idiomsClone = new Idioms(idioms);
        if (listMeaning != null) {
            for(String meaning: listMeaning) {
                idiomsClone.addMeaning(new String(meaning));
            }
        }
        return idiomsClone;
	}

    public String getIdiom() {
        return idioms;
    }

    public void setIdioms(String idioms) {
        this.idioms = idioms;
    }

    public ArrayList<String> getListMeaning() {
        return listMeaning;
    }

    public void setListMeaning(ArrayList<String> listMeaning) {
        this.listMeaning = listMeaning;
    }

    public void addMeaning(String meaning) {
        listMeaning.add(meaning);
    }

    public void showIdioms() {
        System.out.println("Idioms: " + idioms);
        if (listMeaning != null) {
        if (listMeaning.size() > 0) {
            System.out.println("  Meaning of idioms:");
            for (String mean: listMeaning) {
                System.out.println("    - " + mean);
            }
        } }
    }

    public String toContentForSearchScene() {
        String res = "- " + idioms + '\n';
        if (listMeaning != null) {
            for (String iter : listMeaning) {
                res += "\t" + iter + '\n';
            }
        }
        return res;
    }

    public void exportToFile(PrintWriter printWriter) {
        printWriter.write("!" + idioms + "\n");
        if (listMeaning != null) {
            for (String mean : listMeaning) {
                printWriter.write("=" + mean + "\n");
            }
        }
    }
}
