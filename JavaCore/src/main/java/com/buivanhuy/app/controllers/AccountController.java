package com.buivanhuy.app.controllers;

import com.buivanhuy.app.enums.Gender;
import com.buivanhuy.app.enums.PositionName;
import com.buivanhuy.app.services.Manageable;
import com.buivanhuy.entities.Account;
import com.buivanhuy.entities.Department;
import com.buivanhuy.entities.Position;
import com.buivanhuy.utils.helper.TablePrinter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class AccountController {
    private final Scanner scanner = new Scanner(System.in);
    private final Manageable<Account> accountService;
    private final Manageable<Department> departmentService;
    private final Manageable<Position> positionService;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public AccountController(
            Manageable<Account> accountService,
            Manageable<Department> departmentService,
            Manageable<Position> positionService
    ) {
        this.accountService = accountService;
        this.departmentService = departmentService;
        this.positionService = positionService;
    }

    public void index() {
        List<Account> accounts = accountService.index();

        TablePrinter table = new TablePrinter(
                "ID", "Họ và tên", "Username", "Email", "Giới tính", "Phòng ban", "Chức vụ", "Ngày tạo"
        );

        for (Account acc : accounts) {
            String deptName = (acc.getDepartment() != null && acc.getDepartment().getName() != null)
                    ? acc.getDepartment().getName()
                    : "NULL";

            String posName = (acc.getPosition() != null && acc.getPosition().getName() != null)
                    ? String.valueOf(acc.getPosition().getName())
                    : "NULL";

            table.addRow(
                    String.valueOf(acc.getId()),
                    acc.getFullName() != null ? acc.getFullName() : "",
                    acc.getUsername() != null ? acc.getUsername() : "",
                    acc.getEmail() != null ? acc.getEmail() : "",
                    acc.getGender() != null ? acc.getGender().name() : "NULL",
                    deptName,
                    posName,
                    acc.getCreateDate() != null ? dateFormat.format(acc.getCreateDate()) : "NULL"
            );
        }
        table.print();
    }

    public void show() {
        int id = inputInt("Nhập ID cần xem: ");
        Account acc = accountService.show(id);

        if (acc != null) {
            String deptName = "NULL";
            if (acc.getDepartment() != null) {
                Department dept = departmentService.show(acc.getDepartment().getId());
                if (dept != null && dept.getName() != null) {
                    deptName = dept.getName();
                }
            }

            String posName = "NULL";
            if (acc.getPosition() != null) {
                Position pos = positionService.show(acc.getPosition().getId());
                if (pos != null && pos.getName() != null) {
                    posName = String.valueOf(pos.getName());
                }
            }
            TablePrinter table = new TablePrinter(
                    "ID", "Họ và tên", "Username", "Email", "Giới tính", "Department ID", "Position ID", "Ngày tạo"
            );

            table.addRow(
                    String.valueOf(acc.getId()),
                    acc.getFullName() != null ? acc.getFullName() : "",
                    acc.getUsername() != null ? acc.getUsername() : "",
                    acc.getEmail() != null ? acc.getEmail() : "",
                    acc.getGender() != null ? acc.getGender().name() : "NULL",
                    deptName,
                    posName,
                    acc.getCreateDate() != null ? dateFormat.format(acc.getCreateDate()) : "NULL"
            );
            table.print();
        } else {
            System.out.println("Không tìm thấy tài khoản.");
        }

    }

    public void create() {
        System.out.println("\n--- THÊM MỚI TÀI KHOẢN ---");

        System.out.print("Họ và tên: ");
        String fullName = scanner.nextLine().trim();

        System.out.print("Username: ");
        String username = scanner.nextLine().trim();

        System.out.print("Email: ");
        String email = scanner.nextLine().trim();

        Gender gender = inputGender();
        int departmentId = inputInt("Department ID: ");
        int positionId = inputInt("Position ID: ");

        Account account = new Account();
        account.setFullName(fullName);
        account.setUsername(username);
        account.setEmail(email);
        account.setCreateDate(new Date());
        account.setGender(gender);

        Department dept = new Department();
        dept.setId(departmentId);
        account.setDepartment(dept);

        Position pos = new Position();
        pos.setId(positionId);
        account.setPosition(pos);
        try {
            boolean isStored = accountService.store(account);
            System.out.println(isStored ? "Thêm mới thành công!" : "Thêm mới thất bại!");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public void edit() {
        int id = inputInt("Nhập ID tài khoản cần sửa: ");
        Account account = accountService.show(id);

        if (account == null) {
            System.out.println("Không tìm thấy tài khoản có ID: " + id);
            return;
        }

        System.out.println("Nhập thông tin mới (bấm Enter để giữ nguyên):");
        System.out.print("Họ và tên (" + account.getFullName() + "): ");
        String fullName = scanner.nextLine().trim();
        if (!fullName.isEmpty()) account.setFullName(fullName);

        System.out.print("Username (" + account.getUsername() + "): ");
        String username = scanner.nextLine().trim();
        if (!username.isEmpty()) account.setUsername(username);

        System.out.print("Email (" + account.getEmail() + "): ");
        String email = scanner.nextLine().trim();
        if (!email.isEmpty()) account.setEmail(email);

        System.out.print("Đổi giới tính? (y/n): ");
        if ("y".equalsIgnoreCase(scanner.nextLine().trim())) {
            account.setGender(inputGender());
        }

        System.out.print("Đổi Department ID? (y/n): ");
        if ("y".equalsIgnoreCase(scanner.nextLine().trim())) {
            Department d = new Department();
            d.setId(inputInt("Department ID mới: "));
            account.setDepartment(d);
        }

        System.out.print("Đổi Position ID? (y/n): ");
        if ("y".equalsIgnoreCase(scanner.nextLine().trim())) {
            Position p = new Position();
            p.setId(inputInt("Position ID mới: "));
            account.setPosition(p);
        }
        boolean updated = accountService.update(account);

        System.out.println(updated ? "Cập nhật thành công!" : "Cập nhật thất bại!");
    }

    public void destroy() {
        int id = inputInt("Nhập ID cần xóa: ");

        try {
            boolean isDeleted = accountService.destroy(id);
            if (isDeleted) {
                System.out.println("Xóa tài khoản thành công!");
            } else {
                System.out.println("Xóa thất bại do lỗi hệ thống cơ sở dữ liệu!");
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public void importFromCSV() {
        System.out.println("\n========== NHẬP DỮ LIỆU TỪ CSV ==========");
        System.out.print("Vui lòng nhập đường dẫn tuyệt đối của file CSV: ");
        String filePath = scanner.nextLine().trim();

        if (filePath.isEmpty()) {
            System.out.println("Đường dẫn file không được để trống!");
            return;
        }

        try {
            accountService.importFromCSV(filePath);
        } catch (Exception e) {
            System.out.println("Có lỗi xảy ra trong quá trình nhập dữ liệu: " + e.getMessage());
        }
    }

    private Gender inputGender() {
        while (true) {
            System.out.print("Giới tính (MALE, FEMALE, OTHER): ");
            try {
                return Gender.valueOf(scanner.nextLine().trim().toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("Giá trị không hợp lệ! Vui lòng nhập lại.");
            }
        }
    }

    private int inputInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập số nguyên hợp lệ!");
            }
        }
    }
}
