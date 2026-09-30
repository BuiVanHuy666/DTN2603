package com.buivanhuy.view;

import com.buivanhuy.app.controllers.AccountController;
import com.buivanhuy.app.repositories.*;
import com.buivanhuy.app.services.*;
import com.buivanhuy.entities.Account;
import com.buivanhuy.entities.Department;
import com.buivanhuy.entities.Position;

import java.util.Scanner;

public class Application {
    private static Application INSTANCE;

    private final Scanner scanner;
    private final AccountController accountController;

    private Account currentUser = null;

    private Application() {
        this.scanner = new Scanner(System.in);

        AccountRepository accountRepository = new AccountRepositoryImpl();
        ResourceRepository<Department> departmentRepository = new DepartmentRepositoryImpl();
        ResourceRepository<Position> positionRepository = new PositionRepositoryImpl();

        AccountService accountService =
                new AccountServiceImpl(
                        accountRepository,
                        departmentRepository,
                        positionRepository
                );
        DepartmentServiceImpl departmentService = new DepartmentServiceImpl(departmentRepository);
        PositionServiceImpl positionService = new PositionServiceImpl(positionRepository);

        this.accountController = new AccountController(
                accountService,
                departmentService,
                positionService
        );
    }

    public static Application getInstance() {
        if (INSTANCE == null) {
            synchronized (Application.class) {
                if (INSTANCE == null) {
                    INSTANCE = new Application();
                }
            }
        }
        return INSTANCE;
    }

    public void run() {
        while (true) {
            if (currentUser == null) {
                showLoginMenu();
            } else {
                showMainMenu();
            }
        }
    }

    private void showLoginMenu() {
        System.out.println("\n========== HỆ THỐNG ĐĂNG NHẬP ==========");
        System.out.println("1. Đăng nhập");
        System.out.println("0. Thoát chương trình");
        System.out.print("Chọn chức năng: ");
        String choice = scanner.nextLine().trim();

        switch (choice) {
            case "1" -> processLogin();
            case "0" -> {
                System.out.println("Tạm biệt! Chương trình kết thúc.");
                System.exit(0);
            }
            default -> System.out.println("Lựa chọn không hợp lệ, vui lòng chọn lại!");
        }
    }

    private void processLogin() {
        System.out.print("Nhập tên tài khoản (Username): ");
        String username = scanner.nextLine().trim();
        System.out.print("Nhập mật khẩu (Password): ");
        String password = scanner.nextLine().trim();

        Account account = accountController.login(username, password);

        if (account != null) {
            System.out.println("✅ Đăng nhập thành công! Chào mừng " + account.getFullName());
            this.currentUser = account;
        }
    }

    private void showMainMenu() {
        printMenu();
        System.out.print("Chọn chức năng: ");
        String choice = scanner.nextLine().trim();

        switch (choice) {
            case "1" -> accountController.index();
            case "2" -> accountController.show();
            case "3" -> accountController.create();
            case "4" -> accountController.edit();
            case "5" -> accountController.destroy();
            case "6" -> accountController.importFromCSV();
            case "0" -> {
                System.out.println("Đã đăng xuất thành công!");
                this.currentUser = null;
            }
            default -> System.out.println("Lựa chọn không hợp lệ, vui lòng chọn lại!");
        }
    }

    private void printMenu() {
        System.out.println("\n========== QUẢN LÝ TÀI KHOẢN (ACCOUNT) ==========");
        System.out.println("1. Hiển thị danh sách tài khoản");
        System.out.println("2. Xem chi tiết theo ID");
        System.out.println("3. Thêm mới tài khoản");
        System.out.println("4. Cập nhật tài khoản");
        System.out.println("5. Xóa tài khoản");
        System.out.println("6. Nhập tài khoản từ file");
        System.out.println("0. Đăng xuất");
        System.out.println("=================================================");
    }

    public static void main(String[] args) {
        Application.getInstance().run();
    }
}