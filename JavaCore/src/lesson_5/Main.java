package lesson_5;

import lesson_5.backend.interfaces.Manageable;
import lesson_5.backend.services.AccountService;
import lesson_5.backend.services.DepartmentService;
import lesson_5.backend.services.PositionService;

import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        AccountService accountService = new AccountService();
        DepartmentService departmentService = new DepartmentService();
        PositionService positionService = new PositionService();

        while (true) {
            System.out.println("\n==========================================");
            System.out.println("   HỆ THỐNG QUẢN LÝ (TESTING SYSTEM)");
            System.out.println("==========================================");
            System.out.println("1. Quản lý Account (Tài khoản)");
            System.out.println("2. Quản lý Department (Phòng ban)");
            System.out.println("3. Quản lý Position (Chức vụ)");
            System.out.println("0. Thoát chương trình");
            System.out.print("👉 Vui lòng chọn thực thể (0-3): ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    handleCrudMenu("ACCOUNT", accountService);
                    break;
                case "2":
                    handleCrudMenu("DEPARTMENT", departmentService);
                    break;
                case "3":
                    handleCrudMenu("POSITION", positionService);
                    break;
                case "0":
                    System.out.println("👋 Đã thoát chương trình. Tạm biệt!");
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("❌ Lựa chọn không hợp lệ! Vui lòng chọn từ 0 đến 3.");
            }
        }
    }

    /**
     * Menu con xử lý toàn bộ các thao tác CRUD dùng chung cho mọi Service implement Manageable
     */
    private static void handleCrudMenu(String entityName, Manageable<?> service) {
        while (true) {
            System.out.println("\n------------------------------------------");
            System.out.println("        QUẢN LÝ " + entityName);
            System.out.println("------------------------------------------");
            System.out.println("1. Xem danh sách");
            System.out.println("2. Xem chi tiết theo ID");
            System.out.println("3. Thêm mới (Create/Store)");
            System.out.println("4. Chỉnh sửa theo ID");
            System.out.println("5. Xóa theo ID");
            System.out.println("0. Quay lại Menu chính");
            System.out.print("👉 Vui lòng chọn chức năng (0-5): ");

            String action = scanner.nextLine().trim();

            switch (action) {
                case "1":
                    System.out.println("\n--- DANH SÁCH " + entityName + " ---");
                    service.index();
                    break;
                case "2":
                    System.out.print("Nhập ID " + entityName + " cần xem: ");
                    String showId = scanner.nextLine().trim();
                    service.show(showId);
                    break;
                case "3":
                    service.create();
                    break;
                case "4":
                    service.edit();
                    break;
                case "5":
                    System.out.print("Nhập ID " + entityName + " cần xóa: ");
                    String deleteId = scanner.nextLine().trim();
                    service.destroy(deleteId);
                    break;
                case "0":
                    return;
                default:
                    System.out.println("❌ Lựa chọn không hợp lệ! Vui lòng chọn từ 0 đến 5.");
            }
        }
    }
}