// package DictionaryPg;
// import java.util.Scanner;

// import Game.HangMan.*;

// public class DictionaryCommandline {
//     /**
//      * Obj DCL duy nhất 
//      */
//     private static DictionaryCommandline dictionaryCommandline;
//     private DictionaryCommandline() {}
    
//     /**
//      * hàm trả về dictionaryCommandline duy nhất, không cần tạo object
//      */
//     public static DictionaryCommandline getDictionaryCommandline() {
//         return (dictionaryCommandline != null)? dictionaryCommandline : new DictionaryCommandline();
//     }

//     /**
//      * Có chức năng:
//      *  - showAllWords
//      *  - insertFromCommandline
//      */ 
//     public void dictionaryBasic() {
//         System.out.println("Welcome to Dictionary");
//         int userAction;
//         do {
//             System.out.println("[1] Add words");
//             System.out.println("[2] Show all words");
//             System.out.println("[3] Exit");
//             System.out.println("Your action: ");
//             try (Scanner scanner = new Scanner(System.in)) {
//                 userAction = scanner.nextInt();
//             }
            
//             switch (userAction) {
//                 case 1:
//                     DictionaryManagement.getDictionaryManagement().insertFromCommandline();
//                     break;
//                 case 2:
//                     DictionaryManagement.getDictionaryManagement().showAllWords();
//                     break;
//                 default:
//                     break;
//             }
//         } while (userAction != 3);
//         System.out.println("Exited!");
//     }

//     /**
//      * hien thi menu
//      */
//     private void showMenu(){
//         System.out.println("Welcome to EngJoy");
//         System.out.println("[0] - Thoat ung dung");
//         System.out.println("[1] - Them tu vung");
//         System.out.println("[2] - Xoa tu vung");
//         System.out.println("[3] - Sua tu vung");
//         System.out.println("[4] - Hien thi danh sach tu vung");
//         System.out.println("[5] - Tra cuu");
//         System.out.println("[6] - Tim kiem");
//         System.out.println("[7] - Game");
//         System.out.println("[8] - Nhap danh sach tu vung tu tep"); 
//         System.out.println("[9] - Xuat danh sach tu vung ra tep");
//     }

//     /**
//      * hàm để nhập yêu cầu của người dùng 
//      * trả về stt của yêu cầu
//      * @return
//      */
//     private int getUserAction() {
//         int userAction = -1;
//         do {
//             System.out.print("Xin moi nhap lua chon cua ban: ");
//             Scanner scanner = new Scanner(System.in);
//                 try {
//                     userAction = scanner.nextInt();
//                 } catch (Exception e) {
//                     // TODO: handle exception
//                     System.out.println("Yeu cau khong hop le!");
//                     continue;
//                 }
            

//             if (0 > userAction || userAction > 9) {
//                 System.out.println("Yeu cau khong hop le!");
//             }
//             else {
//                 return userAction;
//             }
//         } while (true);
        
//     }

//     /**
//      * 0 - thoát ứng dụng, 
//      * 1 - thêm từ, 
//      * 2 - xóa từ,
//      * 3 - sửa từ, chưa có 
//      * 4 - hiển thị danh sách các từ, 
//      * 5 - tra cứu, 
//      * 6 - tìm kiếm, 
//      * 7  - Truy cập phần Game, chưa có
//      * 8 - nhập danh sách từ vựng từ tệp
//      * 9 - xuất dữ liệu danh sách từ vựng ra tệp.
//      */
//     public void dictionaryAdvanced() {
//         DictionaryManagement.getDictionaryManagement().insertFromFile("./src/DictionaryPg/tudien.txt");
//         do {
//             showMenu();
//             int userAction = getUserAction();
//             switch (userAction) {
//                 case 1:
//                     DictionaryManagement.getDictionaryManagement().insertFromCommandline();
//                     break;
//                 case 2:
//                     DictionaryManagement.getDictionaryManagement().deleteFromDictionary();
//                     break;
//                 case 3:
//                     ModifyWord.getModifyWord().modifyCommandline();
//                     break;
//                 case 4:
//                     DictionaryManagement.getDictionaryManagement().showAllWords();
//                     break;
//                 case 5:
//                     DictionaryManagement.getDictionaryManagement().dictionaryLookup();
//                     break;
//                 case 6:
//                     DictionaryManagement.getDictionaryManagement().dictionarySearcher();
//                     break;
//                 case 7:
//                     HangMan hangman = new HangMan(Dictionary.getDictionary());
//                     hangman.startGame();
//                     break;
//                 case 8:
//                     DictionaryManagement.getDictionaryManagement().insertFromFile("./CommandLine_version/src/testout.txt");
//                     break;
//                 case 9:
//                     DictionaryManagement.getDictionaryManagement().ExportToFile();
//                     break;
//                 default:
//                     return;
//             }
//         } while (true);
//     }
// }
