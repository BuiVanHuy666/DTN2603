package lesson_5.backend.services;

import lesson_1.models.Department;
import lesson_5.backend.interfaces.Manageable;
import utils.DB;
import utils.Table;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DepartmentService implements Manageable<Department> {
    private static final Scanner scanner = new Scanner(System.in);

    @Override
    public void index() {
        List<Department> departments = new ArrayList<>();

        try (ResultSet resultSet = DB.getInstance().executeQuery("SELECT * FROM department")) {
            if (resultSet != null) {
                while (resultSet.next()) {
                    departments.add(new Department(
                            resultSet.getInt("id"),
                            resultSet.getString("name")
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi đọc dữ liệu từ ResultSet: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Lỗi hệ thống: " + e.getMessage());
        }

        String border = "+----+---------------------------+";
        String format = "| %-2s | %-25s |%n";
        Object[] headers = {"ID", "Tên phòng ban"};

        Table.printTable(departments, border, format, headers, dept -> new Object[]{
                dept.getId(),
                dept.getName()
        });
    }

    @Override
    public void show(String id) {
        try {
            int deptId = Integer.parseInt(id);
            Department department = Department.find(deptId);

            if (department != null) {
                System.out.println(department);
            } else {
                System.out.println("❌ Không tìm thấy thông tin phòng ban với ID: " + id);
            }
        } catch (NumberFormatException e) {
            System.out.println("❌ ID phòng ban phải là số nguyên!");
        }
    }

    @Override
    public void create() {
        System.out.println("=== TẠO MỚI PHÒNG BAN ===");
        System.out.print("Nhập tên phòng ban: ");
        String name = scanner.nextLine().trim();

        if (name.isEmpty()) {
            System.out.println("⚠️ Tên phòng ban không được để trống!");
            return;
        }

        Department department = new Department(0, name);
        this.store(department);
    }

    @Override
    public void store(Department department) {
        String[] columns = {"name"};

        boolean isSuccess = DB.buildInsertQuery(
                "department",
                columns,
                department.getName()
        );

        if (isSuccess) {
            System.out.println("✅ Thêm phòng ban thành công!");
        } else {
            System.out.println("❌ Thêm phòng ban thất bại!");
        }
    }

    @Override
    public void destroy(String id) {
        String sql = "SELECT * FROM department WHERE id = " + id;

        try (ResultSet result = DB.getInstance().executeQuery(sql)) {
            if (result != null && result.next()) {
                int c = DB.getInstance().executeUpdate("DELETE FROM department WHERE id = " + id);
                if (c > 0) {
                    System.out.println("✅ Xóa phòng ban thành công!");
                } else {
                    System.out.println("❌ Xóa phòng ban thất bại!");
                }
            } else {
                System.out.println("❌ Không tìm thấy thông tin phòng ban với ID: " + id);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi cơ sở dữ liệu khi xóa phòng ban (kiểm tra ràng buộc khóa ngoại): " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Lỗi hệ thống: " + e.getMessage());
        }
    }

    @Override
    public void edit() {
        System.out.println("=== CHỈNH SỬA PHÒNG BAN ===");
        System.out.print("Nhập ID phòng ban cần sửa: ");
        String id = scanner.nextLine().trim();

        try {
            Department department = Department.find(Integer.parseInt(id));
            if (department == null) {
                System.out.println("❌ Không tìm thấy phòng ban với ID: " + id);
                return;
            }

            System.out.println("\nThông tin hiện tại: " + department);
            System.out.println("(Nhấn Enter để GIỮ NGUYÊN thông tin cũ)");

            System.out.print("Tên phòng ban mới [" + department.getName() + "]: ");
            String name = scanner.nextLine().trim();
            if (!name.isEmpty()) {
                department.setName(name);
            }

            update(department);
        } catch (NumberFormatException e) {
            System.out.println("❌ ID phòng ban phải là số nguyên!");
        }
    }

    @Override
    public void update(Department department) {
        String sql = "UPDATE department SET name = '" + department.getName() + "' WHERE id = " + department.getId();

        int rowsAffected = DB.getInstance().executeUpdate(sql);
        if (rowsAffected > 0) {
            System.out.println("✅ Cập nhật phòng ban ID " + department.getId() + " thành công!");
        } else {
            System.out.println("❌ Cập nhật phòng ban thất bại!");
        }
    }
}