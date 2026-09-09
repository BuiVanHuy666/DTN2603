package lesson_5;
import lesson_5.backend.services.AccountService;
import lesson_5.backend.services.DepartmentService;
import lesson_5.backend.services.PositionService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AccountService accountService = new AccountService();
        DepartmentService departmentService = new DepartmentService();
        PositionService positionService = new PositionService();

        while (true) {
            System.out.println("\n=== QUẢN LÝ HỆ THỐNG (TESTING SYSTEM) ===");
            System.out.println("1. Hiển thị danh sách Account");
            System.out.println("2. Hiển thị danh sách Department");
            System.out.println("3. Hiển thị danh sách Position");
            System.out.println("0. Thoát chương trình");
            System.out.print("Vui lòng chọn chức năng (0-3): ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    System.out.println("\n--- DANH SÁCH ACCOUNT ---");
                    accountService.index();
                    break;
                case "2":
                    System.out.println("\n--- DANH SÁCH DEPARTMENT ---");
                    departmentService.index();
                    break;
                case "3":
                    System.out.println("\n--- DANH SÁCH POSITION ---");
                    positionService.index();
                    break;
                case "0":
                    System.out.println("Đã thoát chương trình.");
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("❌ Lựa chọn không hợp lệ! Vui lòng nhập từ 0 đến 3.");
            }
        }
    }
}