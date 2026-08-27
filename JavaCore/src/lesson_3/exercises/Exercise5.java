package lesson_3.exercises;

import lesson_1.models.Department;
import lesson_2.DataSeeder;

import java.util.Arrays;

public class Exercise5 {
    static Department[] departments = DataSeeder.seedDepartments();

    // Question 1: In ra thông tin của phòng ban thứ 1 (sử dụng toString())
    public static void question1() {
        // Phòng ban thứ 1 nằm ở index 0
        System.out.println("Thông tin phòng ban thứ 1: " + departments[0].toString());
    }

    // Question 2: In ra thông tin của tất cả phòng ban (sử dụng toString())
    public static void question2() {
        System.out.println("Danh sách tất cả phòng ban:");
        for (Department dept : departments) {
            System.out.println(dept.toString());
        }
    }

    // Question 3: In ra địa chỉ của phòng ban thứ 1
    public static void question3() {
        System.out.println("Địa chỉ phòng ban thứ 1: " + departments[0].getAddress());
    }

    // Question 4: Kiểm tra xem phòng ban thứ 1 có tên là "Phòng A" không?
    public static void question4() {
        String nameToCheck = "Phòng A";
        boolean isMatch = departments[0].getName().equals(nameToCheck);

        System.out.println("Phòng ban 1 có tên là '" + nameToCheck + "' không? -> " + isMatch);
    }

    // Question 5: So sánh 2 phòng ban thứ 1 và phòng ban thứ 2 xem có bằng nhau không (bằng nhau khi tên của 2 phòng ban đó bằng nhau)
    public static void question5() {
        boolean isEquals = departments[0].equals(departments[1]);

        System.out.println("Phòng ban 1 và Phòng ban 2 có bằng nhau không? -> " + isEquals);

        Department testDept = new Department(99, "Marketing", "Tầng 99");
        System.out.println("Phòng ban 1 có bằng phòng ban test (cùng tên) không? -> " + departments[0].equals(testDept));
    }

//    Khởi tạo 1 array phòng ban gồm 5 phòng ban, sau đó in ra danh sách phòng ban theo thứ tự tăng dần theo tên (sắp xếp theo vần ABCD)
//    VD:
//    Accounting
//    Boss of director
//    Marketing
//    Sale
//    Waiting room
    public static void question6() {
        Arrays.sort(departments);

        System.out.println("Danh sách phòng ban sau khi sắp xếp theo vần A-Z:");
        for (Department dept : departments) {
            System.out.println(dept.getName());
        }
    }
}