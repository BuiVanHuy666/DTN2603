package lesson_5.backend.services;

import lesson_1.models.Department;
import lesson_5.backend.interfaces.Manageable;
import utils.DB;
import utils.Table;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DepartmentService implements Manageable {
    @Override
    public void index() {
        List<Department> departments = new ArrayList<>();
        ResultSet resultSet = DB.getInstance().executeQuery("SELECT * FROM department");

        if (resultSet != null) {
            try {
                while (resultSet.next()) {
                    departments.add(new Department(
                            resultSet.getInt("id"),
                            resultSet.getString("name")
                    ));
                }
            } catch (SQLException e) {
                System.err.println("Lỗi khi đọc dữ liệu từ ResultSet: " + e.getMessage());
            }
        }

        String border = "+----+---------------------------+";
        String format = "| %-2s | %-25s |%n";
        Object[] headers = {"ID", "Tên phòng ban"};

        Table.printTable(departments, border, format, headers, dept -> new Object[]{
                dept.getId(),
                dept.getName()
        });
    }
}