package lesson_1.models;

import lesson_1.enums.Gender;
import lombok.Getter;
import lombok.Setter;
import utils.DB;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Account extends Model {
    @Getter @Setter
    private int id;

    @Getter @Setter
    private String email;

    @Getter @Setter
    private String username;

    @Getter @Setter
    private String fullName;

    @Getter @Setter
    private Gender gender;

    @Getter @Setter
    private Department department;

    @Getter @Setter
    private Position position;

    @Getter @Setter
    private Date createdAt;

    public Account(
            int id,
            String email,
            String username,
            String fullName,
            Gender gender,
            Department department,
            Position position,
            Date createdAt
    ) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.fullName = fullName;
        this.gender = gender;
        this.department = department;
        this.position = position;
        this.createdAt = createdAt;
    }

    public Account() {}

    @Override
    public String toString() {
        return "Account \n[\nEmail: " + email +
                "\nUsername: " + username +
                "\nFullName: " + fullName +
                "\nGender: " + gender +
                "\nDepartment: " + department +
                "\nPosition: " + position +
                "\nCreateDate: " + createdAt +
                "\n]";
    }

    public static List<Account> all() {
        List<Account> accounts = new ArrayList<>();

        List<Department> allDepartments = Department.all();
        List<Position> allPositions = Position.all();

        String sql = "SELECT * FROM account";

        try (ResultSet result = DB.getInstance().executeQuery(sql)) {
            if (result != null) {
                while (result.next()) {
                    int targetDeptId = result.getInt("department_id");
                    int targetPosId = result.getInt("position_id");

                    Department dept = allDepartments.stream()
                            .filter(d -> d.getId() == targetDeptId)
                            .findFirst()
                            .orElse(null);

                    Position pos = allPositions.stream()
                            .filter(p -> p.getId() == targetPosId)
                            .findFirst()
                            .orElse(null);

                    Account account = new Account(
                            result.getInt("id"),
                            result.getString("email"),
                            result.getString("username"),
                            result.getString("fullname"),
                            Gender.valueOf(result.getString("gender").toUpperCase()),
                            dept,
                            pos,
                            result.getDate("create_date")
                    );

                    accounts.add(account);
                }
            }
        } catch (Exception e) {
            System.err.println("Lỗi khi lấy danh sách Account: " + e.getMessage());
        }

        return accounts;
    }

    public static Account find(int id) {
        String sql = "SELECT * FROM account WHERE id = " + id;
        try (ResultSet result = DB.getInstance().executeQuery(sql)) {
            if (result != null && result.next()) {
                Department dept = Department.find(result.getInt("department_id"));
                Position pos = Position.find(result.getInt("position_id"));

                return new Account(
                        result.getInt("id"),
                        result.getString("email"),
                        result.getString("username"),
                        result.getString("fullname"),
                        Gender.valueOf(result.getString("gender").toUpperCase()),
                        dept,
                        pos,
                        result.getDate("create_date")
                );
            }
        } catch (Exception e) {
            System.err.println("Lỗi khi tìm Account: " + e.getMessage());
        }
        return null;
    }
}
