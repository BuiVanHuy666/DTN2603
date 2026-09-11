package lesson_1.models;

import lombok.Getter;
import lombok.Setter;
import utils.DB;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Department extends Model implements Comparable<Department> {
    @Getter @Setter
    private int id;

    @Getter @Setter
    private String name;

    @Getter @Setter
    private String address;

    public Department(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Department(int id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    // Question 1 & 2: Ghi đè toString()
    @Override
    public String toString() {
        return "Department {" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Department that = (Department) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int compareTo(Department other) {
        if (this.name == null || other.name == null) {
            return 0;
        }

        return this.name.compareToIgnoreCase(other.name);
    }

    public static List<Department> all() {
        List<Department> departments = new ArrayList<>();
        String sql = "SELECT * FROM department";

        try (ResultSet result = DB.getInstance().executeQuery(sql)) {
            if (result != null) {
                while (result.next()) {
                    departments.add(new Department(
                            result.getInt("id"),
                            result.getString("name")
                    ));
                }
            }
        } catch (Exception e) {
            System.err.println("Lỗi khi lấy danh sách Department: " + e.getMessage());
        }

        return departments;
    }

    public static Department find(int id) {
        String sql = "SELECT * FROM department WHERE id = " + id;
        try (ResultSet result = DB.getInstance().executeQuery(sql)) {
            if (result != null && result.next()) {
                return new Department(
                        result.getInt("id"),
                        result.getString("name")
                );
            }
        } catch (Exception e) {
            System.err.println("Lỗi khi tìm Department: " + e.getMessage());
        }
        return null;
    }
}