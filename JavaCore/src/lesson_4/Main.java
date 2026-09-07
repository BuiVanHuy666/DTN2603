package lesson_4;

import lesson_4.backend.LibraryManager;
import lesson_4.backend.interfaces.Manageable;

import java.util.Scanner;

public class Main {
    private final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Manageable manager = new LibraryManager();
        Main main = new Main();
        main.app(manager);
    }

    private void app(Manageable manager) {
        while (true) {
            this.printMenu();
            String choose = scanner.nextLine();
            switch (choose) {
                case "1":
                    manager.store();
                    break;
                case "2":
                    manager.destroy();
                    break;
                case "3":
                    manager.index();
                    break;
                case "4":
                    manager.show();
                    break;
                case "5":
                    manager.exit();
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }

    private void printMenu()
    {
        System.out.println("====Mời bạn chọn chức năng====");
        System.out.println("1. Thêm mới tài liệu.");
        System.out.println("2. Xoá tài liệu theo mã tài liệu.");
        System.out.println("3. Hiện thị thông tin về tài liệu.");
        System.out.println("4. Tìm kiếm tài liệu theo loại");
        System.out.println("5. Thoát khỏi chương trình.");
    }
}
