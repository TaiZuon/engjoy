package DictionaryPg;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import Trie.Trie;

public class DictionaryManagement {
    /**
     * Obj DictionaryManagement duy nhất
     */
    private static DictionaryManagement dictionaryManagement;
    private DictionaryManagement() {}
    /**
     * hàm trả về DM duy nhất, không cần tạo object
     */
    public static DictionaryManagement getDictionaryManagement() {
        return (dictionaryManagement != null)? dictionaryManagement : new DictionaryManagement();
    }

    /**
     * 1. Nhập numOfWords (số lượng từ cần insert)
     * 2. Định dạng nhập
     */
    public void insertFromCommandline() {
        Scanner scanner = new Scanner(System.in);
            System.out.print("Enter number of words: ");
            int numOfWords = scanner.nextInt();
            scanner.nextLine();
            for (int i = 1; i <= numOfWords; i++) {
                //get the word
                System.out.print("Word " + i + ": ");
                String target = scanner.nextLine();
                
                //get the pronounce
                System.out.print("Pronounce:");
                String pronounce = "/";
                pronounce += scanner.nextLine() + "/";

                //get a list of types
                System.out.print("Number of types: ");
                int numOfTypes = scanner.nextInt();
                scanner.nextLine();
                ArrayList<WordType> listWordTypes = new ArrayList<>();

                for (int j = 1; j <= numOfTypes; j++) {
                    System.out.print("Type " + j + ": ");
                    String typeName = scanner.nextLine();

                    //get list of meaning
                    System.out.print("Number of Meanings: ");
                    int numOfMeanings = scanner.nextInt();
                    scanner.nextLine();
                    ArrayList<Meaning> listMeanings = new ArrayList<>();
                    for (int k = 1; k <= numOfMeanings; k++) {
                        System.out.print("Meaning " + k + ": ");
                        String meaning = scanner.nextLine();

                        //get list of Exs
                        System.out.print("Number of Example: ");
                        int numOfExamples = scanner.nextInt();
                        scanner.nextLine();
                        ArrayList<String> listExamples = new ArrayList<>();
                        for (int l = 0; l < numOfExamples; l++) {
                            System.out.print("Example " + l + ": ");
                            String ex = scanner.nextLine();
                            listExamples.add(ex);
                        }
                        Meaning m = new Meaning(meaning, listExamples);
                        listMeanings.add(m);
                    }
                    WordType wt = new WordType(typeName, listMeanings);
                    listWordTypes.add(wt);
                }
                
                //get a list of idioms
                System.out.print("Number of idioms: ");
                int numOfIdioms = scanner.nextInt();
                scanner.nextLine();
                ArrayList<Idioms> listIdioms = new ArrayList<>();
                for (int j = 1; j <= numOfIdioms; j++) {
                    System.out.print("Idiom " + j + ": ");
                    String id = scanner.nextLine();

                    //get list of meaning
                    System.out.print("Number of Meanings: ");
                    int numOfMeanings = scanner.nextInt();
                    scanner.nextLine();
                    ArrayList<String> listM = new ArrayList<>();
                    for (int k = 1; k <= numOfMeanings; k++) {
                        System.out.print("Meaning " + k + ": ");
                        String temp = scanner.nextLine();
                        listM.add(temp);
                    }
                    Idioms idiom = new Idioms(id, listM);
                    listIdioms.add(idiom);
                }
                Dictionary.getDictionary().add(target, new Word(target, pronounce, listWordTypes, listIdioms));
            }
    }

    /**
     * khởi tạo dữ liệu từ file txt
     */
    public void insertFromFile(String path){
        try {
            System.out.println("Start Reading!");

            FileInputStream inputStream = new FileInputStream(path);

            InputStreamReader isr = new InputStreamReader(inputStream, "UTF-8");

            BufferedReader br = new BufferedReader(isr);

            String line = "";
            boolean isWord = false;

            while (true) {
                if (!isWord) {
                    line = br.readLine();
                    isWord = true;
                }
                
                if (line == null) {
                    break;
                }
                if (line == "") continue;

                if (line.charAt(0) == '@') {
                    //start a new word
                    String target = new String();
                    String pronounce = new String();
                    int n = line.indexOf('/');
                    if (n >= 0) { 
                    target = line.substring(1, line.indexOf('/') - 1);
                    pronounce = line.substring(target.length() + 2, line.length());
                    } else {
                        target = line.substring(1);
                        pronounce = "/???/";
                        line = br.readLine();
                        line = br.readLine();
                    }
                    //loop to get all the wordtypes
                    ArrayList<WordType> listWordTypes = new ArrayList<>();
                    ArrayList<Idioms> listIdioms = new ArrayList<>();
                    boolean isWordType = false;
                    boolean isIdiom = false;
                    while(true) {
                        if (!isWordType && !isIdiom) {
                            line = br.readLine();
                            isWordType = false;
                            isIdiom = false;
                        }
                        // start a new wordtype    
                        if (line == null || line.equals("")) {
                            break;
                        } else if (line.charAt(0) == '*') {
                            String typeName = line.substring(2);
                            // loop to get all meanings of the wordtype
                            ArrayList<Meaning> listMeanings = new ArrayList<>();
                            boolean isMeaning = false;
                            while(true) {
                                if (!isMeaning) {
                                    line = br.readLine();
                                    isMeaning = true;
                                }
                                
                                if (line == null ||line.equals("")) break;
                                if  (line.charAt(0) != '-') {
                                    break;
                                }
                                if (line.charAt(0) == '-') {
                                    String meaning = line.substring(2);
                                    ArrayList<String> ex = new ArrayList<>();
                                    while(true) {
                                        line = br.readLine();
                                        if (line == null || line.equals("") || line.charAt(0) != '=') {
                                            if (line == null) break;
                                            if (line.equals("")) break;
                                            else {
                                                if (line.charAt(0) == '-') isMeaning = true;
                                                if (line.charAt(0) == '*') isWordType = true;
                                                break;
                                            }
                                        }
                                        if (line.charAt(0) == '=') {
                                            ex.add(line.substring(1));
                                        }
                                    }
                                    Meaning m = new Meaning(meaning, ex);
                                    listMeanings.add(m);
                                }
                            }
                            WordType wordType = new WordType(typeName, listMeanings);
                            listWordTypes.add(wordType);
                        } else if (line.charAt(0) == '!') {
                            String idiom = line.substring(1);
                            ArrayList<String> listMeaning = new ArrayList<>();
                            while (true) {
                                line = br.readLine();
                                if (line == null || line.equals("") || line.charAt(0) == '!' || (line.charAt(0) != '-' && line.charAt(0) != '=')) {
                                    if (line.equals("")) break;
                                    else {
                                        if (line.charAt(0) == '!') isIdiom = true;
                                        break;
                                    }
                                }
                                if (line.charAt(0) == '=') {
                                    listMeaning.add(line.substring(1));
                                }
                            }
                            Idioms idi = new Idioms(idiom, listMeaning);
                            listIdioms.add(idi);
                        } else if (line.charAt(0) == '@') {
                            isWord = true;
                            break;
                        }
                    }
                    Word word = new Word(target, pronounce, listWordTypes, listIdioms);
                    if (line == null) break;
                    if (line.equals("")) {
                        isWord = false;
                    }
                    //word.showWord();
                    Dictionary.getDictionary().add(target, word);
                    //System.out.println(target);
                }    
            }
            inputStream.close();
            isr.close();
            System.out.println("Reading ended!");;
        }
        catch (Exception e) {
            System.out.println("Something went wrong :((");
            System.out.println(e.getMessage());
        }
    }

    /**
     * yeu cau nguoi dung nhap tu can xoa
     * xóa từ khỏi từ điển
     * @param target
     */
    public void deleteFromDictionary() {
        Scanner nhap = new Scanner(System.in);
        System.out.println("Nhap tu can xoa: ");
        String target = nhap.nextLine();


        Word word = Dictionary.getDictionary().searchWord(target);

        //neu co tu thi xoa tu day, khong thi in ra khong tim thay tu. 
        if (word != null) {
            Dictionary.getDictionary().remove(target);
            System.out.println("Ban vua xoa: " + target);
        }
        else {
            System.out.println("Trong tu dien khong co tu: " + target);
        }
    }

    /**
     * Hiện thị danh sách từ vựng theo thứ tự alphabet
     * phiên bản không đẹp 
     */
    public void showAllWords() {
        Dictionary.getDictionary().showAllWordAlphabet();
    }

    /**
     * yeu cau nguoi dung nhap tu
     * tìm 1 từ trong từ điển 
     * hiện thông tin từ đó
     */
    public void dictionaryLookup() {
        //yeu cau nguoi dung nhap tu.
        Scanner nhap = new Scanner(System.in);
        System.out.println("Nhap tu tra cuu: ");
        String target = nhap.nextLine();

        //tim tu vua nhap.
        Word word = Dictionary.getDictionary().searchWord(target);

        //neu thay thi in ra tu, neu k thi in ra k tim thay.
        if (word != null) {
            word.showWord();
        } else {
            System.out.println("Khong tim thay tu: " + target);
        }
    }

    /**
     * hiện tất cả các từ bắt đầu bằng prefix
     * yeu cau nguoi dung nhap prefix
     * @param prefix
     */
    public void dictionarySearcher() {
        //nhap tien to.
        Scanner nhap = new Scanner(System.in);
        System.out.println("Nhap tien to: ");
        String prefix = nhap.nextLine();

        //tim tu voi tien to da cho.
        ArrayList<String> words = Dictionary.getDictionary().searchAllWordStartWith(prefix);

        //neu khong co tien to day.
        if (words.isEmpty()) {
            System.out.println("Khong tim thay tien to: " + prefix);
            return;
        }
        else {
            //neu tim thay tien to thi in ra.
            System.out.println("Theo tien to da cho, ta co cac tu sau trong tu dien:");
            for (String word : words) {
                System.out.println(word);
            }
        }
    }

    /**
     * yêu cầu người dùng nhập tên tệp cần xuất
     * xuất dữ liệu từ điển hiện tại ra tệp
     * @param path
     */
    public void ExportToFile(String path) {
        try {
            FileOutputStream outputStream = new FileOutputStream(path);
            OutputStreamWriter writer = new OutputStreamWriter(outputStream, "UTF-8");
            BufferedWriter br = new BufferedWriter(writer);
            PrintWriter printWriter = new PrintWriter(br);

            Dictionary.getDictionary().exportToFile(printWriter);

            printWriter.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("smth went wrong!");
        }
    } 

    public void resetDictonaryToNone() {
        Dictionary.getDictionary().resetToNone();
    }
}
