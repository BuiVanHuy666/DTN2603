package com.buivanhuy.view;

import com.buivanhuy.services.AccountServiceImpl;
import com.buivanhuy.services.Manageable;

import java.util.Scanner;

public class Application {
    private static final Scanner scanner = new Scanner(System.in);
    private static final Manageable accountService = new AccountServiceImpl();

    public static void main(String[] args) {
        while (true) {
            printMenu();
            System.out.print("Chọn chức năng: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> accountService.index();
                case "2" -> {
                    System.out.print("Nhập ID cần xem: ");
                    accountService.show(scanner.nextLine().trim());
                }
                case "3" -> accountService.create();
                case "4" -> accountService.edit();
                case "5" -> {
                    System.out.print("Nhập ID cần xóa: ");
                    accountService.destroy(scanner.nextLine().trim());
                }
                case "0" -> {
                    System.out.println("Tạm biệt! Chương trình kết thúc.");
                    return;
                }
                default -> System.out.println("Lựa chọn không hợp lệ, vui lòng chọn lại!");
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n========== QUẢN LÝ TÀI KHOẢN (ACCOUNT) ==========");
        System.out.println("1. Hiển thị danh sách tài khoản");
        System.out.println("2. Xem chi tiết theo ID");
        System.out.println("3. Thêm mới tài khoản");
        System.out.println("4. Cập nhật tài khoản");
        System.out.println("5. Xóa tài khoản");
        System.out.println("0. Thoát");
        System.out.println("=================================================");
    }
}