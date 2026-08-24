package lesson_2.exercises;

import lesson_1.models.Account;
import lesson_2.DataSeeder;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Exercise2 {
    public static void question1() {
        // Khai báo 1 số nguyên = 5 và sử dụng lệnh System out printf để in ra số nguyên đó
        int x = 5;
        System.out.printf("%d%n", x);
    }

    public static void question2() {
        // Khai báo 1 số nguyên = 100 000 000 và sử dụng lệnh System out printf để in ra số nguyên đó thành định dạng như sau: 100,000,000
        int x = 100000000;
        System.out.printf("%,d%n", x);
    }

    public static void question3() {
        // Khai báo 1 số thực = 5,567098 và sử dụng lệnh System out printf để in ra số thực đó chỉ bao gồm 4 số đằng sau
        float f = 5.567098f;
        System.out.printf("%.4f%n", f);
    }

    public static void question4() {
        // Khai báo Họ và tên của 1 học sinh và in ra họ và tên học sinh đó theo định dạng như sau:
        // Họ và tên: "Nguyễn Văn A" thì sẽ in ra trên console như sau:
        // Tên tôi là "Nguyễn Văn A" và tôi đang độc thân.
        String name = "Nguyễn Văn A";
        System.out.printf("Tên tôi là \"%s\" và tôi đang độc thân.%n", name);
    }

    public static void question5() {
        // Lấy thời gian bây giờ và in ra theo định dạng sau: 24/04/2020 11h:16p:20s
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH'h':mm'p':ss's'");
        System.out.println(sdf.format(now));
    }

    public static void question6() {
        // In ra thông tin account (như Question 8 phần FOREACH) theo định dạng table (giống trong Database)
        Account[] accounts = DataSeeder.seedAccounts(DataSeeder.seedDepartments(), DataSeeder.seedPositions());
        System.out.printf("%-30s | %-20s | %-20s%n", "Email", "Full Name", "Department");
        System.out.println("---------------------------------------------------------------------------");
        for (Account acc : accounts) {
            String depName = (acc.getDepartment() != null) ? acc.getDepartment().getName() : "Chưa có";
            System.out.printf("%-30s | %-20s | %-20s%n", acc.getEmail(), acc.getFullName(), depName);
        }
    }
}
