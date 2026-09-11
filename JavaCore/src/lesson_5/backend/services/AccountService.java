package lesson_5.backend.services;

import lesson_1.enums.Gender;
import lesson_1.enums.PositionName;
import lesson_1.models.Account;
import lesson_1.models.Department;
import lesson_1.models.Position;
import lesson_5.backend.interfaces.Manageable;
import utils.DB;
import utils.Table;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class AccountService implements Manageable<Account> {
    private static final Scanner scanner = new Scanner(System.in);

    @Override
    public void index() {
        List<Account> accounts = new ArrayList<>();

        try (ResultSet result = DB.getInstance().executeQuery("SELECT * FROM account")) {
            if (result != null) {
                while (result.next()) {
                    Account account = new Account();

                    account.setId(result.getInt("id"));
                    account.setUsername(result.getString("username"));
                    account.setFullName(result.getString("fullname"));

                    Department dept = new Department(result.getInt("department_id"), null);
                    account.setDepartment(dept);

                    Position pos = new Position(result.getInt("position_id"), null);
                    account.setPosition(pos);

                    accounts.add(account);
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi đọc dữ liệu từ ResultSet: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Lỗi hệ thống: " + e.getMessage());
        }

        String border = "+----+----------------------+-------------------------+---------+---------+";
        String format = "| %-2s | %-20s | %-23s | %-7s | %-7s |%n";
        Object[] headers = {"ID", "Username", "Full Name", "Dept ID", "Pos ID"};

        Table.printTable(
                accounts, border, format, headers, acc -> new Object[]{
                        acc.getId(),
                        acc.getUsername(),
                        acc.getFullName(),
                        acc.getDepartment() != null ? acc.getDepartment().getId() : null,
                        acc.getPosition() != null ? acc.getPosition().getId() : null
                }
        );
    }

    @Override
    public void show(String id) {
        String sql = "SELECT a.*, d.name as department_name, p.name as position_name " +
                "FROM account a " +
                "JOIN department d ON a.department_id = d.id " +
                "JOIN position p ON a.position_id = p.id " +
                "WHERE a.id = " + id;
        try (ResultSet result = DB.getInstance().executeQuery(sql)) {
            if (result != null && result.next()) {
                Department department = new Department(
                        result.getInt("department_id"),
                        result.getString("department_name")
                );

                String posNameStr = result.getString("position_name");
                PositionName positionEnum =
                        (posNameStr != null) ? PositionName.valueOf(posNameStr.toUpperCase()) : null;
                Position position = new Position(
                        result.getInt("position_id"),
                        positionEnum
                );
                Account account = new Account(
                        result.getInt("id"),
                        result.getString("email"),
                        result.getString("username"),
                        result.getString("fullname"),
                        Gender.valueOf(result.getString("gender")),
                        department,
                        position,
                        result.getDate("create_date")
                );
                System.out.println(account.toString());
            } else {
                System.out.println("Không tìm thấy thông tin tài khoản");
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi đọc dữ liệu từ ResultSet: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Lỗi hệ thống: " + e.getMessage());
        }
    }

    @Override
    public void create() {
        System.out.println("=== Tạo tài khoản ===");
        System.out.println("Nhập thông tin tài khoản mới:");
        System.out.println("Nhập id");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Nhập email");
        String email = scanner.nextLine();

        System.out.println("Nhập username");
        String username = scanner.nextLine();

        System.out.println("Nhập fullname");
        String fullname = scanner.nextLine();

        System.out.println("Nhập giới tính");
        Gender gender = Gender.valueOf(scanner.nextLine().toUpperCase());

        System.out.println("Nhập id phòng ban");
        int departmentId = scanner.nextInt();

        System.out.println("Nhập id chức vụ");
        int positionId = scanner.nextInt();

        Department department = Department.find(departmentId);
        Position position = Position.find(positionId);

        Account newAccount = new Account(id, email, username, fullname, gender, department, position, new Date());
        this.store(newAccount);
    }

    @Override
    public void store(Account account) {
        String[] columns = {"email", "username", "fullname", "gender", "department_id", "position_id", "create_date"};

        boolean isSuccess = DB.buildInsertQuery(
                "account",
                columns,
                account.getEmail(),
                account.getUsername(),
                account.getFullName(),
                account.getGender().name(),
                account.getDepartment().getId(),
                account.getPosition().getId(),
                new Date()
        );

        if (isSuccess) {
            System.out.println("Thêm tài khoản thành công!");
        } else {
            System.out.println("Thêm tài khoản thất bại!");
        }
    }

    @Override
    public void destroy(String id) {
        String sql = "SELECT * FROM account WHERE id = " + id;

        try (ResultSet result = DB.getInstance().executeQuery(sql)) {
            if (result != null && result.next()) {
                int c = DB.getInstance().executeUpdate("DELETE FROM account WHERE id = " + id);

                if (c > 0) {
                    System.out.println("Xóa tài khoản thành công");
                } else {
                    System.out.println("Xóa tài khoản thất bại");
                }
            } else {
                System.out.println("Không tìm thấy thông tin tài khoản");
            }
        } catch (SQLException e) {
            System.err.println("Lỗi cơ sở dữ liệu khi xóa tài khoản: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Lỗi hệ thống: " + e.getMessage());
        }
    }

    @Override
    public void edit() {
        System.out.println("=== CHỈNH SỬA TÀI KHOẢN ===");
        System.out.print("Nhập ID tài khoản cần sửa: ");
        String id = scanner.nextLine().trim();

        Account account = Account.find(Integer.valueOf(id));
        if (account == null) {
            System.out.println("❌ Không tìm thấy tài khoản với ID: " + id);
            return;
        }

        System.out.println("\nThông tin hiện tại của tài khoản:");
        System.out.println(account);
        System.out.println("--------------------------------------------------");
        System.out.println("(Nhấn Enter để GIỮ NGUYÊN thông tin cũ)");

        System.out.print("Email mới [" + account.getEmail() + "]: ");
        String email = scanner.nextLine().trim();
        if (!email.isEmpty()) account.setEmail(email);

        System.out.print("Username mới [" + account.getUsername() + "]: ");
        String username = scanner.nextLine().trim();
        if (!username.isEmpty()) account.setUsername(username);

        System.out.print("Fullname mới [" + account.getFullName() + "]: ");
        String fullname = scanner.nextLine().trim();
        if (!fullname.isEmpty()) account.setFullName(fullname);

        System.out.print("Giới tính mới (MALE/FEMALE/UNKNOWN) [" + account.getGender() + "]: ");
        String genderStr = scanner.nextLine().trim();
        if (!genderStr.isEmpty()) {
            try {
                account.setGender(Gender.valueOf(genderStr.toUpperCase()));
            } catch (IllegalArgumentException e) {
                System.out.println("⚠️ Giới tính không hợp lệ, giữ nguyên giá trị cũ!");
            }
        }

        System.out.print("ID phòng ban mới [" + (account.getDepartment() != null ? account.getDepartment()
                .getId() : "null") + "]: ");
        String deptIdStr = scanner.nextLine().trim();
        if (!deptIdStr.isEmpty()) {
            Department dept = Department.find(Integer.parseInt(deptIdStr));
            if (dept != null) {
                account.setDepartment(dept);
            } else {
                System.out.println("⚠️ Phòng ban không tồn tại, giữ nguyên phòng ban cũ!");
            }
        }

        System.out.print("ID chức vụ mới [" + (account.getPosition() != null ? account.getPosition()
                .getId() : "null") + "]: ");
        String posIdStr = scanner.nextLine().trim();
        if (!posIdStr.isEmpty()) {
            Position pos = Position.find(Integer.parseInt(posIdStr));
            if (pos != null) {
                account.setPosition(pos);
            } else {
                System.out.println("⚠️ Chức vụ không tồn tại, giữ nguyên chức vụ cũ!");
            }
        }

        update(account);
    }

    @Override
    public void update(Account account) {
        String sql = "UPDATE account SET " +
                "email = '" + account.getEmail() + "', " +
                "username = '" + account.getUsername() + "', " +
                "fullname = '" + account.getFullName() + "', " +
                "gender = '" + account.getGender().name() + "', " +
                "department_id = " + (account.getDepartment() != null ? account.getDepartment()
                .getId() : "NULL") + ", " +
                "position_id = " + (account.getPosition() != null ? account.getPosition().getId() : "NULL") + " " +
                "WHERE id = " + account.getId();

        int rowsAffected = DB.getInstance().executeUpdate(sql);
        if (rowsAffected > 0) {
            System.out.println("✅ Cập nhật tài khoản ID " + account.getId() + " thành công!");
        } else {
            System.out.println("❌ Cập nhật tài khoản thất bại!");
        }
    }

    public static void main(String[] args) {
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(sdf.format(now));
    }
}