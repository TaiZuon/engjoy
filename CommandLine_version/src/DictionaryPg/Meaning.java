package DictionaryPg;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class Meaning implements Cloneable{
        private String explain;
        private ArrayList<String> listStructure;

        public Meaning() {
            explain = "";
            listStructure = new ArrayList<String>();
        }

        public Meaning(String explain) {
            this.explain = explain;
            this.listStructure =  new ArrayList<String>();
        }

        public Meaning(String explain, ArrayList<String> listStructure) {
            this.explain = explain;
            this.listStructure = listStructure;
        }

        /**
         * deep clone
         */
        public Object clone() {
            Meaning meaningClone = new Meaning(this.explain);
            for (String structure : this.listStructure) {
                meaningClone.addStructure(new String(structure));
            }
            return meaningClone;
        }
        
        public String getExplain() {
            return explain;
        }

        public ArrayList<String> getListStructure() {
            return listStructure;
        }

        public void setExplain (String explain) {
            this.explain = explain;
        }

        public void setListStructure ( ArrayList<String> listStructure) {
            this.listStructure = listStructure;
        }

        /**
         * thêm 1 cấu trúc câu của từ vào danh sách các cấu trúc
         * @param stucture
         */
        public void addStructure(String stucture) {
            listStructure.add(stucture);
        }

        /**
         * hiện thị nghĩa và cấu trúc câu của từ.
         */
        public void showMeaning() {
            System.out.println("  Nghia: " +explain);

            if (listStructure.size() > 0) {
                System.out.println("    Cau truc: ");
                for (String structure : listStructure) {
                    System.out.println("      - " + structure);
                }
            }
        }

        public void exportToFile(PrintWriter printWriter) {
            printWriter.write("- " + explain + "\n");
            for (String struc : listStructure) {
                printWriter.write("=" + struc + "\n");
            }
        }
    }