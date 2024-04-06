package DictionaryPg;
import java.util.ArrayList;
import java.util.Scanner;

public class ModifyWord {
    private static ModifyWord modifyWord;

    private ModifyWord() {
    }

    public static ModifyWord getModifyWord() {
        return (modifyWord != null) ? modifyWord : new ModifyWord();
    }

    /**
     * kiểm tra ký tự có hợp lệ không
     * @param c
     * @return
     */
    private boolean isValid(char c) {
        switch (c) {
            case '-':
                return true;
            case ' ':
                return true;
            case '\'':
                return true;
            case '.':
                return true;
        }
        return ('a' <= c && c <= 'z');
    }

    /**
     * kiểm tra từ có thỏa mãn trong từ điển
     * @param target
     * @return
     */
    private boolean checkTarget(String target) {
        for(int i = 0; i< target.length(); ++i) {
            if (!isValid(target.charAt(i))) return false;
        }
        return true;
    }

    /**
     * trả về từ target mà người dùng cần sửa
     * @return
     */
    private String getTargetToModify() {
        String target = "";
        do {
            System.out.print("Xin moi nhap tu ban can sua [0 de quay lai]: ");
            Scanner scanner = new Scanner(System.in);
                try {
                    target = scanner.nextLine();
                    target.toLowerCase();
                } catch (Exception e) {
                    // TODO: handle exception
                    System.out.println("Tu nhap khong hop le");
                    continue;
                }
            if (target.equals("0")) break;
            target = target.toLowerCase();

            if(!checkTarget(target)) {
                System.out.println("Tu nhap khong hop le");
            } else if (Dictionary.getDictionary().searchWord(target) == null) {
                System.out.println("Tu nhap khong co trong tu dien");
            } else break;
        } while (true);
        return target;
    }

    /**
     * hiện thị menu
     */
    private void showMenuModify() {
        System.out.println("[0] - Quay lai");
        System.out.println("[1] - Sua tu");
        System.out.println("[2] - Sua loai tu");
        System.out.println("[3] - Sua nghia theo loai tu");
        System.out.println("[4] - Sua cau truc cau theo nghia");
        System.out.println("[5] - Sua idioms");
        System.out.println("[6] - Sua nghia theo idioms");
    }

    /**
     * trả về yêu cầu sửa của user
     * @return
     */
    private int getUserAction() {
        int userAction = -1;
        do {
            System.out.print("Xin moi nhap lua chon cua ban: ");
            Scanner scanner = new Scanner(System.in);
                try {
                    userAction = scanner.nextInt();
                } catch (Exception e) {
                    // TODO: handle exception
                    System.out.println("Yeu cau khong hop le!");
                    continue;
                }
            

            if (0 > userAction || userAction > 6) {
                System.out.println("Yeu cau khong hop le!");
            }
            else {
                return userAction;
            }
        } while (true);
    }

    /**
     *  [0] - Quay lai
        [1] - Sua tu
        [2] - Sua loai tu
        [3] - Sua nghia theo loai tu
        [4] - Sua cau truc cau theo nghia
        [5] - Sua idioms
        [6] - Sua nghia theo di idioms
     */
    public void modifyCommandline() {
        System.out.println("Ban dang trong menu sua tu!");
        String targetWord = getTargetToModify();
        if (targetWord.equals("0")) return;
        Word target = Dictionary.getDictionary().getWordExplainOf(targetWord);
        do {
            target.showWord();
            showMenuModify();
            int userAction = getUserAction();
            switch (userAction) {
                case 1:
                    modifyTarget(target);
                    break;
                case 2:
                    modifyWordType(target);
                    break;
                case 3:
                    modifyMeaningInWordType(target);
                    break;
                case 4:
                    modifyStructureInMeaning(target);
                    break;
                case 5:
                    modifyIdioms(target);
                    break;
                case 6:
                    modifyMeaningInIdioms(target);
                    break;
                default:
                    return;
            }
        } while (true);
    }

    /**
     * trả về kiểu sửa(quay lai, thay đổi ký tự, thêm, xóa)
     * @return
     */
    private int getTypeModify() {
        int userAction = -1;
        do {
            System.out.println("[0] - quay lai ");
            System.out.println("[1] - thay doi ");
            System.out.println("[2] - them ");
            System.out.println("[3] - xóa ");
            System.out.print("Xin moi nhap lua chon cua ban: ");
            Scanner scanner = new Scanner(System.in);
                try {
                    userAction = scanner.nextInt();
                } catch (Exception e) {
                    // TODO: handle exception
                    System.out.println("Yeu cau khong hop le!");
                    continue;
                }
            

            if (0 > userAction || userAction > 3) {
                System.out.println("Yeu cau khong hop le!");
            }
            else {
                return userAction;
            }
        } while (true);
    }

    /*
     * hàm sửa từ:
     * nhập từ đúng
     * thêm từ đúng vào trie
     * xóa từ cũ khỏi dictionary
     */
    private void modifyTarget(Word target) {
        String oldTarget = target.getWord();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Replace \"" + target.getWord() + "\" into: ");
        String newTarget = scanner.nextLine();
        newTarget.toLowerCase();
        target.setWord(newTarget);
        Dictionary.getDictionary().add(newTarget,target);
        Dictionary.getDictionary().remove(oldTarget);
    }

    /**
     * Replace:
     *  - nhập chỉ số tư loại
     *  - nhập từ thay thế
     *  - thay thế
     * Add: như insertCommandline
     * Remove:
     *  - nhập chỉ số từ loại
     *  - xóa
     * @param target
     */
    private void modifyWordType(Word target) {

        int typeModify = getTypeModify();

        ArrayList<WordType> listWordTypes = target.getListWordTypes();
        for (WordType wordType : listWordTypes) {
            wordType.showWordType();
        }

        Scanner scanner = new Scanner(System.in);
        int index;

        switch (typeModify) {
            case 1:
                System.out.print("Index of wordType [0.." + (listWordTypes.size() - 1) + "] : ");
                index = scanner.nextInt();
                scanner.nextLine();
                System.out.print("Replace \"" + listWordTypes.get(index).getTypeName() + "\" into: ");
                String newType = scanner.nextLine();
                newType.toLowerCase();
                listWordTypes.get(index).setTypeName(newType);
                break;
            case 2:
                //get a list of types
                System.out.print("Number of types: ");
                int numOfTypes = scanner.nextInt();
                scanner.nextLine();

                for (int j = 1; j <= numOfTypes; j++) {
                    System.out.print("Type " + j + ": ");
                    String typeName = scanner.nextLine();
                    typeName.toLowerCase();

                    //get list of meaning
                    System.out.print("Number of Meanings: ");
                    int numOfMeanings = scanner.nextInt();
                    scanner.nextLine();
                    ArrayList<Meaning> listMeanings = new ArrayList<>();
                    for (int k = 1; k <= numOfMeanings; k++) {
                        System.out.print("Meaning " + k + ": ");
                        String meaning = scanner.nextLine();
                        meaning.toLowerCase();

                        //get list of Exs
                        System.out.print("Number of Example: ");
                        int numOfExamples = scanner.nextInt();
                        scanner.nextLine();
                        ArrayList<String> listExamples = new ArrayList<>();
                        for (int l = 0; l < numOfExamples; l++) {
                            System.out.print("Example " + l + ": ");
                            String ex = scanner.nextLine();
                            ex.toLowerCase();
                            listExamples.add(ex);
                        }
                        Meaning m = new Meaning(meaning, listExamples);
                        listMeanings.add(m);
                    }
                    WordType wt = new WordType(typeName, listMeanings);
                    listWordTypes.add(wt);
                }
                break;
            case 3:
                System.out.print("Index of wordType [0.." + (listWordTypes.size() - 1) + "]: ");
                index = scanner.nextInt();
                scanner.nextLine();
                System.out.print("Removed: " + listWordTypes.get(index).getTypeName());
                listWordTypes.remove(index);
                break;
            default:
                return;
        }
    }

    /**
     * Nhập chỉ só từ loại
     * Replace:
     *  - nhập chỉ số meaning
     *  - nhập từ thay thế
     *  - thay thế
     * Add: như insertCommandline
     * Remove:
     *  - nhập chỉ số meaning
     *  - xóa
     * @param target
     */
    private void modifyMeaningInWordType(Word target) {
        int typeModify = getTypeModify();

        ArrayList<WordType> listWordTypes = target.getListWordTypes();
        for (WordType wordType : listWordTypes) {
            wordType.showWordType();
        }

        Scanner scanner = new Scanner(System.in);
        int index;

        System.out.print("Index of wordType [0.." + (listWordTypes.size() - 1) + "]: ");
        index = scanner.nextInt();
        scanner.nextLine();
        WordType wordTypeTarget = listWordTypes.get(index);

        wordTypeTarget.showWordType();
        ArrayList<Meaning> listMeaning = wordTypeTarget.getListMeanings();

        
        switch (typeModify) {
            case 1:
                System.out.print("Index of meaning [0.." + (listMeaning.size() - 1) + "]: ");
                index = scanner.nextInt();
                scanner.nextLine();
                System.out.print("Replace \"" + listMeaning.get(index).getExplain() + "\" into: ");
                String newExplain = scanner.nextLine();
                newExplain.toLowerCase();
                listMeaning.get(index).setExplain(newExplain);
                break;
            case 2:
                //get list of meaning
                System.out.print("Number of Meanings: ");
                int numOfMeanings = scanner.nextInt();
                scanner.nextLine();
                for (int k = 1; k <= numOfMeanings; k++) {
                    System.out.print("Meaning " + k + ": ");
                    String meaning = scanner.nextLine();
                    meaning.toLowerCase();
                    //get list of Exs
                    System.out.print("Number of Example: ");
                    int numOfExamples = scanner.nextInt();
                    scanner.nextLine();
                    ArrayList<String> listExamples = new ArrayList<>();
                    for (int l = 0; l < numOfExamples; l++) {
                        System.out.print("Example " + l + ": ");
                        String ex = scanner.nextLine();
                        ex.toLowerCase();
                        listExamples.add(ex);
                    }
                    Meaning m = new Meaning(meaning, listExamples);
                    listMeaning.add(m);
                }
                break;
            case 3:
                System.out.print("Index of meaning [0.." + (listMeaning.size() - 1) + "]: ");
                index = scanner.nextInt();
                scanner.nextLine();
                System.out.print("Removed: " + listMeaning.get(index).getExplain());
                listMeaning.remove(index);
                break;
            default:
                return;
        }
    }


    /**
     * Nhập chỉ só từ loại
     * Nhập chỉ số meaning
     * Replace:
     *  - nhập chỉ số structure
     *  - nhập từ thay thế
     *  - thay thế
     * Add: như insertCommandline
     * Remove:
     *  - nhập chỉ số structure
     *  - xóa
     * @param target
     */
    private void modifyStructureInMeaning(Word target) {
        int typeModify = getTypeModify();

        ArrayList<WordType> listWordTypes = target.getListWordTypes();

        //show danh sách từ loại
        for (WordType wordType : listWordTypes) {
            wordType.showWordType();
        }

        Scanner scanner = new Scanner(System.in);
        int index;

        //Nhập chỉ só từ loại
        System.out.print("Index of wordtype [0.." + (listWordTypes.size() - 1) + "]: ");
        index = scanner.nextInt();
        scanner.nextLine();
        WordType wordTypeTarget = listWordTypes.get(index);

        //Show danh sách meaning
        wordTypeTarget.showWordType();
        ArrayList<Meaning> listMeaningTarget = wordTypeTarget.getListMeanings();
        for (Meaning meaning : listMeaningTarget) {
            meaning.showMeaning();
        }

        //Nhập chỉ số meaning
        System.out.print("Index of meaning [0.." + (listMeaningTarget.size() - 1) + "]: ");
        index = scanner.nextInt();
        scanner.nextLine();
        ArrayList<String> listStructure = listMeaningTarget.get(index).getListStructure(); 
        for(String structure : listStructure) {
            System.out.println(structure);
        }

        switch (typeModify) {
            case 1:
                System.out.print("Index of structure [0.." + (listStructure.size() - 1) + "]: ");
                index = scanner.nextInt();
                scanner.nextLine();
                System.out.print("Replace \"" + listStructure.get(index) + "\" into: ");
                String newStructure = scanner.nextLine();
                newStructure.toLowerCase();
                listStructure.get(index).replace(listStructure.get(index), newStructure);
                break;
            case 2:
                //get list of Exs
                System.out.print("Number of Example: ");
                int numOfExamples = scanner.nextInt();
                scanner.nextLine();
                for (int l = 0; l < numOfExamples; l++) {
                    System.out.print("Example " + l + ": ");
                    String ex = scanner.nextLine();
                    ex.toLowerCase();
                    listStructure.add(ex);
                }
                break;
            case 3:
                System.out.print("Index of structure [0.." + (listStructure.size() - 1) + "]: ");
                index = scanner.nextInt();
                scanner.nextLine();
                System.out.print("Removed: " + listStructure.get(index));
                listStructure.remove(index);
                break;
            default:
                return;
        }
    }

    /**
     * Replace:
     *  - nhập chỉ số idioms
     *  - nhập từ thay thế
     *  - thay thế
     * Add: như insertCommandline
     * Remove:
     *  - nhập chỉ số idioms
     *  - xóa
     * @param target
     */
    private void modifyIdioms(Word target) {
        int typeModify = getTypeModify();

        ArrayList<Idioms> listIdioms = target.getListIdioms();
        for (Idioms idioms : listIdioms) {
            idioms.showIdioms();
        }

        Scanner scanner = new Scanner(System.in);
        int index;

        switch (typeModify) {
            case 1:
                System.out.print("Index of idioms [0.." + (listIdioms.size() - 1) + "]: ");
                index = scanner.nextInt();
                scanner.nextLine();
                System.out.print("Replace \"" + listIdioms.get(index).getIdiom() + "\" into: ");
                String newIdioms = scanner.nextLine();
                listIdioms.get(index).setIdioms(newIdioms);
                break;
            case 2:
                //get a list of idioms
                System.out.print("Number of idioms: ");
                int numOfIdioms = scanner.nextInt();
                scanner.nextLine();
                for (int j = 1; j <= numOfIdioms; j++) {
                    System.out.print("Idiom " + j + ": ");
                    String id = scanner.nextLine();
                    id.toLowerCase();
                    //get list of meaning
                    System.out.print("Number of Meanings: ");
                    int numOfMeanings = scanner.nextInt();
                    scanner.nextLine();
                    ArrayList<String> listM = new ArrayList<>();
                    for (int k = 1; k <= numOfMeanings; k++) {
                        System.out.print("Meaning " + k + ": ");
                        String temp = scanner.nextLine();
                        temp.toLowerCase();
                        listM.add(temp);
                    }
                    Idioms idiom = new Idioms(id, listM);
                    listIdioms.add(idiom);
                }
                break;
            case 3:
                System.out.print("Index of idioms [0.." + (listIdioms.size() - 1) + "]: ");
                index = scanner.nextInt();
                scanner.nextLine();
                System.out.print("Removed: " + listIdioms.get(index).getIdiom());
                listIdioms.remove(index);
                break;
            default:
                return;
        }
    }

    /**
     * Nhập chỉ só idioms
     * Replace:
     *  - nhập chỉ số meaning
     *  - nhập từ thay thế
     *  - thay thế
     * Add: như insertCommandline
     * Remove:
     *  - nhập chỉ số meaning
     *  - xóa
     * @param target
     */
    private void modifyMeaningInIdioms(Word target) {
        int typeModify = getTypeModify();

        ArrayList<Idioms> listIdioms = target.getListIdioms();
        for (Idioms idioms : listIdioms) {
            idioms.showIdioms();
        }

        Scanner scanner = new Scanner(System.in);
        int index;

        System.out.print("Index of idioms [0.." + (listIdioms.size() - 1) + "]: ");
        index = scanner.nextInt();
        scanner.nextLine();
        Idioms idiomsTarget = listIdioms.get(index);
        idiomsTarget.showIdioms();
        ArrayList<String> listMeaning = idiomsTarget.getListMeaning();

        switch (typeModify) {
            case 1:
                System.out.print("Index of meaning [0.." + (listMeaning.size() - 1) + "]: ");
                index = scanner.nextInt();
                scanner.nextLine();
                System.out.print("Replace \"" + listMeaning.get(index) + "\" into: ");
                String newMeaning = scanner.nextLine();
                newMeaning.toLowerCase();
                listMeaning.get(index).replace(listMeaning.get(index), newMeaning);
                break;
            case 2:
                //get list of meaning
                System.out.print("Number of Meanings: ");
                int numOfMeanings = scanner.nextInt();
                scanner.nextLine();
                for (int k = 1; k <= numOfMeanings; k++) {
                    System.out.print("Meaning " + k + ": ");
                    String temp = scanner.nextLine();
                    temp.toLowerCase();
                    idiomsTarget.addMeaning(temp); 
                }
                break;
            case 3:
                System.out.print("Index of meaning [0.." + (listMeaning.size() - 1) + "]: ");
                index = scanner.nextInt();
                scanner.nextLine();
                System.out.print("Removed: " + listMeaning.get(index));
                listMeaning.remove(index);
                break;
            default:
                return;
        }
    }
}
