package com.buivanhuy.app.services;

import com.buivanhuy.app.enums.Gender;
import com.buivanhuy.app.repositories.ResourceRepository;
import com.buivanhuy.entities.Account;
import com.buivanhuy.entities.Department;
import com.buivanhuy.entities.Position;
import com.buivanhuy.utils.helper.Validation;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class AccountServiceImpl implements Manageable<Account> {
    private final ResourceRepository<Account> accountRepo;
    private final ResourceRepository<Department> departmentRepo;
    private final ResourceRepository<Position> positionRepo;

    public AccountServiceImpl(
            ResourceRepository<Account> accountRepo,
            ResourceRepository<Department> departmentRepo,
            ResourceRepository<Position> positionRepo
    ) {
        this.accountRepo = accountRepo;
        this.departmentRepo = departmentRepo;
        this.positionRepo = positionRepo;
    }

    @Override
    public List<Account> index() {
        return accountRepo.findAll();
    }

    @Override
    public Account show(int id) {
        return accountRepo.findById(id);
    }

    @Override
    public boolean store(Account account) {
        validateAccount(account);
        return accountRepo.store(account);
    }

    @Override
    public boolean update(Account account) {
        Validation.requireNotNull(account, "Dữ liệu tài khoản không được để trống!");

        Validation.validateEntityExists(account.getId(), accountRepo, "Tài khoản cần cập nhật");

        validateAccount(account);

        return accountRepo.update(account);
    }

    @Override
    public void importFromCSV(String filePath) {
        int successCount = 0;
        int failCount = 0;

        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String errorFilePath = "src/main/java/com/buivanhuy/csv/input-errors-" + timestamp + ".csv";

        List<Account> existingAccounts = accountRepo.findAll();
        Set<String> existingUsernames = new HashSet<>();
        Set<String> existingEmails = new HashSet<>();

        for (Account acc : existingAccounts) {
            if (acc.getUsername() != null) existingUsernames.add(acc.getUsername().toLowerCase());
            if (acc.getEmail() != null) existingEmails.add(acc.getEmail().toLowerCase());
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);

        try (
                BufferedReader br = new BufferedReader(new InputStreamReader(
                        new FileInputStream(filePath),
                        java.nio.charset.StandardCharsets.UTF_8
                ));
                BufferedWriter errorWriter = new BufferedWriter(new OutputStreamWriter(
                        new FileOutputStream(errorFilePath), java.nio.charset.StandardCharsets.UTF_8))
        ) {
            String line;
            boolean isFirstLine = true;

            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    errorWriter.write(line + ",error-message");
                    errorWriter.newLine();
                    continue;
                }

                String[] data = line.split(",");

                if (data.length < 6) {
                    errorWriter.write(line + ",Thiếu dữ liệu (không đủ 6 cột cơ bản)");
                    errorWriter.newLine();
                    failCount++;
                    continue;
                }

                List<String> rowErrors = new ArrayList<>();
                Account account = new Account();

                String fullName = data[0].trim();
                account.setFullName(fullName);
                try {
                    Validation.validateStringLength(fullName, 3, 100, "Họ và tên");
                } catch (Exception e) {
                    rowErrors.add(e.getMessage());
                }

                String username = data[1].trim();
                account.setUsername(username);
                try {
                    Validation.validateStringLength(username, 3, 100, "Username");
                } catch (Exception e) {
                    rowErrors.add(e.getMessage());
                }

                if (existingUsernames.contains(username.toLowerCase())) {
                    rowErrors.add("Username đã tồn tại trong hệ thống");
                }

                String email = data[2].trim();
                account.setEmail(email);
                try {
                    Validation.validateEmail(email);
                } catch (Exception e) {
                    rowErrors.add(e.getMessage());
                }

                if (existingEmails.contains(email.toLowerCase())) {
                    rowErrors.add("Email đã tồn tại trong hệ thống");
                }

                try {
                    account.setGender(Gender.valueOf(data[3].trim().toUpperCase()));
                } catch (IllegalArgumentException e) {
                    rowErrors.add("Giới tính không hợp lệ!");
                }

                try {
                    int deptId = Integer.parseInt(data[4].trim());
                    Validation.validateEntityExists(deptId, departmentRepo, "Phòng ban");
                    Department dept = new Department();
                    dept.setId(deptId);
                    account.setDepartment(dept);
                } catch (NumberFormatException e) {
                    rowErrors.add("ID Phòng ban phải là số nguyên!");
                } catch (Exception e) {
                    rowErrors.add(e.getMessage());
                }

                try {
                    int posId = Integer.parseInt(data[5].trim());
                    Validation.validateEntityExists(posId, positionRepo, "Chức vụ");
                    Position pos = new Position();
                    pos.setId(posId);
                    account.setPosition(pos);
                } catch (NumberFormatException e) {
                    rowErrors.add("ID Chức vụ phải là số nguyên!");
                } catch (Exception e) {
                    rowErrors.add(e.getMessage());
                }

                if (data.length > 6 && !data[6].trim().isEmpty()) {
                    try {
                        Date parsedDate = dateFormat.parse(data[6].trim());
                        Validation.validatePastOrPresent(parsedDate, "Ngày tạo");
                        account.setCreateDate(parsedDate);
                    } catch (java.text.ParseException e) {
                        rowErrors.add("Ngày tạo không đúng định dạng (dd/MM/yyyy)");
                    } catch (Exception e) {
                        rowErrors.add(e.getMessage());
                    }
                } else {
                    account.setCreateDate(new Date());
                }

                if (rowErrors.isEmpty()) {
                    try {
                        if (accountRepo.store(account)) {
                            successCount++;
                            existingUsernames.add(username.toLowerCase());
                            existingEmails.add(email.toLowerCase());
                        } else {
                            rowErrors.add("Lỗi không xác định khi lưu vào cơ sở dữ liệu");
                        }
                    } catch (Exception e) {
                        rowErrors.add("Lỗi hệ thống: " + e.getMessage());
                    }
                }

                if (!rowErrors.isEmpty()) {
                    String errorMsg = String.join(" - ", rowErrors);
                    errorMsg = errorMsg.replace(",", " ");

                    errorWriter.write(line + "," + errorMsg);
                    errorWriter.newLine();
                    failCount++;
                }
            }

            System.out.println("\n--- KẾT QUẢ NHẬP CSV ---");
            System.out.println("Thành công: " + successCount + " tài khoản.");
            if (failCount > 0) {
                System.out.println("Thất bại: " + failCount + " tài khoản. (Xem chi tiết lỗi tại file: " + errorFilePath + ")");
            }

        } catch (IOException e) {
            System.out.println("Lỗi đọc/ghi file CSV. Vui lòng kiểm tra lại đường dẫn: " + filePath);
            System.out.println("Chi tiết lỗi: " + e.getMessage());
        }
    }

    @Override
    public boolean destroy(int id) {
        Account account = accountRepo.findById(id);
        if (account == null) {
            throw new IllegalArgumentException("Không tìm thấy tài khoản với ID: " + id);
        }

        return accountRepo.delete(account);
    }

    private void validateAccount(Account account) {
        Validation.requireNotNull(account, "Account không được để trống!");

        Validation.validateStringLength(account.getFullName(), 3, 100, "Họ và tên");

        Validation.validateStringLength(account.getUsername(), 3, 100, "Username");

        Validation.validateEmail(account.getEmail());

        Validation.requireNotNull(account.getGender(), "Giới tính không hợp lệ hoặc để trống!");

        Validation.validatePastOrPresent(account.getCreateDate(), "Ngày tạo");

        Validation.requireNotNull(account.getDepartment(), "Phòng ban không được để trống!");
        Validation.validateEntityExists(account.getDepartment().getId(), departmentRepo, "Phòng ban");

        Validation.requireNotNull(account.getPosition(), "Chức vụ không được để trống!");
        Validation.validateEntityExists(account.getPosition().getId(), positionRepo, "Chức vụ");
    }
}