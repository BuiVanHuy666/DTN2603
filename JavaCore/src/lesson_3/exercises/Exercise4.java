package lesson_3.exercises;

public class Exercise4 {
    // Nhập một xâu kí tự, đếm số lượng các từ trong xâu kí tự đó (các từ có thể cách nhau bằng nhiều khoảng trắng );
    public static void question1() {
        String str = "   Học   Java   rất vui   ";

        String[] words = str.trim().split("\\s+");
        System.out.println("Chuỗi ban đầu: '" + str + "'");
        System.out.println("Số từ trong chuỗi: " + words.length);
    }

    // Question 2: Nhập hai xâu kí tự s1, s2 nối xâu kí tự s2 vào sau xâu s1;
    public static void question2() {
        String s1 = "Hello ";
        String s2 = "World!";
        String result = new StringBuilder().append(s1).append(s2).toString();
        System.out.println("Chuỗi s1: '" + s1 + "', Chuỗi s2: '" + s2 + "'");
        System.out.println("Sau khi nối: " + result);
    }

    // Question 3: Viết chương trình để người dùng nhập vào tên và kiểm tra, nếu tên chữ viết hoa chữ cái đầu thì viết hoa lên.
    public static void question3() {
        String name = "nam";

        String firstLetter = name.substring(0, 1).toUpperCase();
        String restOfName = name.substring(1).toLowerCase();
        String result = firstLetter + restOfName;

        System.out.println("Tên ban đầu: " + name);
        System.out.println("Tên sau khi viết hoa: " + result);
    }

    // Question 4: Viết chương trình để người dùng nhập vào tên in từng ký tự trong tên của người dùng ra
    //    VD: Người dùng nhập vào "Nam", hệ thống sẽ in ra
    //        "Ký tự thứ 1 là: N"
    //        "Ký tự thứ 1 là: A"
    //        "Ký tự thứ 1 là: M"
    public static void question4() {
        String name = "Nam";
        System.out.println("Tên ban đầu: " + name);
        char[] chars = name.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            System.out.println("Ký tự thứ " + (i + 1) + " là: " + String.valueOf(chars[i]).toUpperCase());
        }
    }

    // Question 5: Viết chương trình để người dùng nhập vào họ, sau đó yêu cầu người dùng nhập vào tên và hệ thống sẽ in ra họ và tên đầy đủ.
    public static void question5() {
        String firstName = "Nguyễn";
        String lastName = "Nam";
        String fullName = firstName + " " + lastName;
        System.out.println("Họ: " + firstName + ", Tên: " + lastName);
        System.out.println("Họ và tên đầy đủ: " + fullName);
    }

    // Question 6: Viết chương trình yêu cầu người dùng nhập vào họ và tên đầy đủ và sau đó hệ thống sẽ tách ra họ, tên , tên đệm
    //   VD: Người dùng nhập vào "Nguyễn Văn Nam"
    //      Hệ thống sẽ in ra
    //      "Họ là: Nguyễn"
    //      "Tên đệm là: Văn"
    //      "Tên là: Nam"
    public static void question6() {
        String fullName = "Nguyễn Văn Nam";
        System.out.println("Họ và tên ban đầu: " + fullName);

        String[] words = fullName.trim().split("\\s+");
        if (words.length == 0) return;

        System.out.println("Họ là: " + words[0]);
        if (words.length > 1) {
            System.out.println("Tên là: " + words[words.length - 1]);
            // Nếu có từ 3 chữ trở lên mới có tên đệm
            if (words.length > 2) {
                StringBuilder middleName = new StringBuilder();
                for (int i = 1; i < words.length - 1; i++) {
                    middleName.append(words[i]).append(" ");
                }
                System.out.println("Tên đệm là: " + middleName.toString().trim());
            }
        }
    }

//    Viết chương trình yêu cầu người dùng nhập vào họ và tên đầy đủ và chuẩn hóa họ và tên của họ như sau:
//    a) Xóa dấu cách ở đầu và cuối và giữa của chuỗi người dùng nhập vào
//    VD: Nếu người dùng nhập vào " nguyễn văn nam " thì sẽ chuẩn hóa thành "nguyễn văn nam"
//    b) Viết hoa chữ cái mỗi từ của người dùng
//    VD: Nếu người dùng nhập vào " nguyễn văn nam " thì sẽ chuẩn hóa thành "Nguyễn Văn Nam"
    public static void question7() {
        String fullName = "  nguyễn   vĂn   nAm  ";
        System.out.println("Tên ban đầu: '" + fullName + "'");

        String[] words = fullName.trim().split("\\s+");
        StringBuilder normalized = new StringBuilder();

        for (String word : words) {
            normalized.append(capitalizeFirstLetter(word)).append(" ");
        }
        System.out.println("Tên sau khi chuẩn hóa: '" + normalized.toString().trim() + "'");
    }

    // Hàm helper riêng phục vụ cho question7
    private static String capitalizeFirstLetter(String word) {
        if (word == null || word.isEmpty()) return word;
        return word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase();
    }

    // Question 8: In ra tất cả các group có chứa chữ "Java"
    public static void question8() {
        String[] groups = {"Java Core", "C# Basics", "Advanced Java", "Python"};
        System.out.println("Các group có chứa 'Java':");
        for (String group : groups) {
            if (group.contains("Java")) {
                System.out.println("- " + group);
            }
        }
    }

    // Question 9: In ra tất cả các group "Java"
    public static void question9() {
        String[] groups = {"Java Core", "Java", "Advanced Java", "java"};
        System.out.println("Các group chính xác là 'Java':");
        for (String group : groups) {
            if (group.equals("Java")) {
                System.out.println("- " + group);
            }
        }
    }

    // Question 10: Kiểm tra 2 chuỗi có là đảo ngược của nhau hay không.
    public static void question10() {
        String s1 = "word";
        String s2 = "drow";
        String reversedS2 = new StringBuilder(s2).reverse().toString();

        System.out.print("So sánh '" + s1 + "' và '" + s2 + "': ");
        System.out.println(s1.equals(reversedS2) ? "OK" : "KO");
    }

    // Question 11: Count special Character. Tìm số lần xuất hiện ký tự "a" trong chuỗi
    public static void question11() {
        String str = "Java Academy";
        int count = 0;
        for (char c : str.toCharArray()) {
            if (c == 'a' || c == 'A') count++;
        }
        System.out.println("Chuỗi: " + str);
        System.out.println("Số lần xuất hiện chữ 'a': " + count);
    }

    // Question 12: Reverse String. Đảo ngược chuỗi sử dụng vòng lặp
    public static void question12() {
        String str = "Hello";
        StringBuilder reversed = new StringBuilder();
        for (int i = str.length() - 1; i >= 0; i--) {
            reversed.append(str.charAt(i));
        }
        System.out.println("Chuỗi ban đầu: " + str);
        System.out.println("Chuỗi đảo ngược: " + reversed.toString());
    }

    // Question 13: String not contains digit
    //     Kiểm tra một chuỗi có chứa chữ số hay không, nếu có in ra false ngược lại true.
    //     Ví dụ: "abc" => true
    //            "1abc", "abc1", "123", "a1bc", null => false
    public static void question13() {
        String str1 = "abc";
        String str2 = "1abc";

        System.out.println("'" + str1 + "' không chứa số: " + (!str1.matches(".*\\d.*")));
        System.out.println("'" + str2 + "' không chứa số: " + (!str2.matches(".*\\d.*")));
    }

    // Question 14: Replace character
    //    Cho một chuỗi str, chuyển các ký tự được chỉ định sang một ký tự khác cho trước.
    //    Ví dụ:
    //       "VTI Academy" chuyển ký tự 'e' sang '*' kết quả " VTI Acad*my"
    public static void question14() {
        String str = "VTI Academy";
        String result = str.replace('e', '*');
        System.out.println("Chuỗi ban đầu: " + str);
        System.out.println("Sau khi thay 'e' bằng '*': " + result);
    }

    // Question 15: Revert string by word
    //    Đảo ngược các ký tự của chuỗi cách nhau bởi dấu cách mà không dùng thư viện.
    //    Ví dụ: " I am developer " => "developer am I".
    //    Các ký tự bên trong chỉ cách nhau đúng một dấu khoảng cách.
    //    Gợi ý: Các bạn cần loại bỏ dấu cách ở đầu và cuối câu, thao tác cắt chuỗi theo dấu cách
    public static void question15() {
        String str = " I am developer ";
        String[] words = str.trim().split("\\s+");
        StringBuilder reversed = new StringBuilder();

        for (int i = words.length - 1; i >= 0; i--) {
            reversed.append(words[i]).append(" ");
        }
        System.out.println("Chuỗi ban đầu: '" + str + "'");
        System.out.println("Đảo ngược theo từ: '" + reversed.toString().trim() + "'");
    }

    // Question 16: Cho một chuỗi str và số nguyên n >= 0. Chia chuỗi str ra làm các phần bằng nhau với n ký tự. Nếu chuỗi không chia được thì xuất ra màn hình “KO”.
    public static void question16() {
        String str = "abcdef";
        int n = 2;
        System.out.println("Chia chuỗi '" + str + "' thành các phần " + n + " ký tự:");

        if (str == null || n <= 0 || str.length() % n != 0) {
            System.out.println("KO");
            return;
        }

        for (int i = 0; i < str.length(); i += n) {
            System.out.println(str.substring(i, i + n));
        }
    }
}