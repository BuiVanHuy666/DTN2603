package lesson_3.exercises;

import lesson_1.models.Account;

import java.util.Date;

public class Exercise2 {
    //    Question 1:
//    Không sử dụng data đã insert từ bài trước, tạo 1 array Account và khởi tạo 5 phần tử theo cú pháp (sử dụng vòng for để khởi tạo):
//      ∙ Email: "Email 1"
//      ∙ Username: "User name 1"
//      ∙ FullName: "Full name 1"
//      ∙ CreateDate: now
    public static void question1() {
        Account[] accounts = new Account[5];

        for (int i = 0; i < accounts.length; i++) {
            int index = i + 1;
            String email = "Email " + index;
            String username = "User name " + index;
            String fullName = "Full name " + index;
            Date createDate = new Date(); // Lấy thời gian hiện tại (now)

            // Khởi tạo đối tượng Account và gán vào mảng
            accounts[i] = new Account(
                    index, // id
                    email,
                    username,
                    fullName,
                    null,
                    null,
                    createDate
            );
        }

        System.out.println("--- Danh sách 5 Account được khởi tạo ---");
        for (Account account : accounts) {
            System.out.println(account);
        }
    }
}
