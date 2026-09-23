package com.buivanhuy.view;

import com.buivanhuy.app.controllers.AccountController;
import com.buivanhuy.app.repositories.AccountRepositoryImpl;
import com.buivanhuy.app.repositories.DepartmentRepositoryImpl;
import com.buivanhuy.app.repositories.PositionRepositoryImpl;
import com.buivanhuy.app.repositories.ResourceRepository;
import com.buivanhuy.app.services.*;
import com.buivanhuy.entities.Account;
import com.buivanhuy.entities.Department;
import com.buivanhuy.entities.Position;

import java.util.Scanner;

public class Application {
    private static Application INSTANCE;

    private final Scanner scanner;
    private final AccountController accountController;

    private Application() {
        this.scanner = new Scanner(System.in);

        ResourceRepository<Account> accountRepository = new AccountRepositoryImpl();
        ResourceRepository<Department> departmentRepository = new DepartmentRepositoryImpl();
        ResourceRepository<Position> positionRepository = new PositionRepositoryImpl();

        Manageable<Account> accountService =
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
                    System.out.println("Tạm biệt! Chương trình kết thúc.");
                    return;
                }
                default -> System.out.println("Lựa chọn không hợp lệ, vui lòng chọn lại!");
            }
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
        System.out.println("0. Thoát");
        System.out.println("=================================================");
    }

    public static void main(String[] args) {
        Application.getInstance().run();
    }
}