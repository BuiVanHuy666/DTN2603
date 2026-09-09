package lesson_5.backend.services;

import lesson_1.models.Account;
import lesson_1.models.Department;
import lesson_1.models.Position;
import lesson_5.backend.interfaces.Manageable;
import utils.DB;
import utils.Table;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountService implements Manageable {
    @Override
    public void index() {
        List<Account> accounts = new ArrayList<>();
        ResultSet resultSet = DB.getInstance().executeQuery("SELECT * FROM account");

        if (resultSet != null) {
            try {
                while (resultSet.next()) {
                    Account account = new Account();

                    account.setId(resultSet.getInt("id"));
                    account.setUsername(resultSet.getString("username"));
                    account.setFullName(resultSet.getString("fullname"));

                    Department dept = new Department(resultSet.getInt("department_id"), null);
                    account.setDepartment(dept);

                    Position pos = new Position(resultSet.getInt("position_id"), null);
                    account.setPosition(pos);

                    accounts.add(account);
                }
            } catch (SQLException e) {
                System.err.println("Lỗi khi đọc dữ liệu từ ResultSet: " + e.getMessage());
            }
        }

        String border = "+----+----------------------+-------------------------+---------+---------+";
        String format = "| %-2s | %-20s | %-23s | %-7s | %-7s |%n";
        Object[] headers = {"ID", "Username", "Full Name", "Dept ID", "Pos ID"};

        Table.printTable(accounts, border, format, headers, acc -> new Object[]{
                acc.getId(),
                acc.getUsername(),
                acc.getFullName(),
                acc.getDepartment() != null ? acc.getDepartment().getId() : null,
                acc.getPosition() != null ? acc.getPosition().getId() : null
        });
    }
}