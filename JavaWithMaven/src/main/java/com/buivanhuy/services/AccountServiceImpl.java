package com.buivanhuy.services;

import com.buivanhuy.entities.Account;
import com.buivanhuy.entities.Department;
import com.buivanhuy.entities.Position;
import com.buivanhuy.enums.Gender;
import com.buivanhuy.repositories.AccountRepositoryImpl;
import com.buivanhuy.repositories.ResourceRepository;
import com.buivanhuy.utils.helper.TablePrinter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class AccountServiceImpl implements Manageable {
    private final ResourceRepository<Account> accountRepo = new AccountRepositoryImpl();
    private final Scanner scanner = new Scanner(System.in);
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private final DepartmentService departmentService = new DepartmentService();
    private final PositionService positionService = new PositionService();

    @Override
    public void index() {
        List<Account> accounts = accountRepo.findAll();
        TablePrinter table = new TablePrinter(
                "ID", "Họ và tên", "Username", "Email", "Giới tính", "Department ID", "Position ID", "Ngày tạo"
        );

        for (Account acc : accounts) {
            table.addRow(
                    String.valueOf(acc.getId()),
                    acc.getFullName() != null ? acc.getFullName() : "",
                    acc.getUsername() != null ? acc.getUsername() : "",
                    acc.getEmail() != null ? acc.getEmail() : "",
                    acc.getGender() != null ? acc.getGender().name() : "NULL",
                    (acc.getDepartment() != null)
                            ? departmentService.getDepartmentNameById(acc.getDepartment().getId())
                            : "NULL",
                    (acc.getPosition() != null)
                            ? positionService.getPositionNameById(acc.getPosition().getId())
                            : "NULL",
                    acc.getCreateDate() != null ? dateFormat.format(acc.getCreateDate()) : "NULL"
            );
        }
        table.print();
    }

    @Override
    public void show(String id) {
        try {
            int accountId = Integer.parseInt(id);
            Account acc = accountRepo.findById(accountId);
            if (acc == null) {
                System.out.println("Không tìm thấy tài khoản với ID: " + id);
                return;
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
                    (acc.getDepartment() != null)
                            ? departmentService.getDepartmentNameById(acc.getDepartment().getId())
                            : "NULL",
                    (acc.getPosition() != null)
                            ? positionService.getPositionNameById(acc.getPosition().getId())
                            : "NULL",
                    acc.getCreateDate() != null ? dateFormat.format(acc.getCreateDate()) : "NULL"
            );
            table.print();
        } catch (NumberFormatException e) {
            System.out.println("ID nhập vào phải là số nguyên!");
        }
    }

    @Override
    public void create() {
        System.out.println("\n--- THÊM MỚI TÀI KHOẢN ---");
        store();
    }

    @Override
    public void store() {
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

        Account saved = accountRepo.store(account);
        if (saved != null) {
            System.out.println("Thêm mới thành công! ID mới: " + saved.getId());
        } else {
            System.out.println("Thêm mới thất bại!");
        }
    }

    @Override
    public void edit() {
        update();
    }

    @Override
    public void update() {
        int id = inputInt("Nhập ID tài khoản cần sửa: ");
        Account account = accountRepo.findById(id);
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

        Account updated = accountRepo.update(account);
        if (updated != null) {
            System.out.println("Cập nhật thành công!");
        } else {
            System.out.println("Cập nhật thất bại!");
        }
    }

    @Override
    public void destroy(String id) {
        try {
            int accountId = Integer.parseInt(id);
            Account account = accountRepo.findById(accountId);
            if (account == null) {
                System.out.println("Không tìm thấy tài khoản để xóa!");
                return;
            }

            System.out.print("Bạn có chắc chắn muốn xóa tài khoản '" + account.getUsername() + "'? (y/n): ");
            if ("y".equalsIgnoreCase(scanner.nextLine().trim())) {
                boolean success = accountRepo.delete(account);
                System.out.println(success ? "Xóa thành công!" : "Xóa thất bại!");
            } else {
                System.out.println("Đã hủy thao tác xóa.");
            }
        } catch (NumberFormatException e) {
            System.out.println("ID không hợp lệ!");
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
}