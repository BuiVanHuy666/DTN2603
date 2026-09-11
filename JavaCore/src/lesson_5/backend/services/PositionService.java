package lesson_5.backend.services;

import lesson_1.enums.PositionName;
import lesson_1.models.Position;
import lesson_5.backend.interfaces.Manageable;
import utils.DB;
import utils.Table;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PositionService implements Manageable<Position> {
    private static final Scanner scanner = new Scanner(System.in);

    @Override
    public void index() {
        List<Position> positions = new ArrayList<>();

        try (ResultSet resultSet = DB.getInstance().executeQuery("SELECT * FROM position")) {
            if (resultSet != null) {
                while (resultSet.next()) {
                    String positionNameStr = resultSet.getString("name");

                    PositionName positionEnum = null;
                    if (positionNameStr != null) {
                        try {
                            positionEnum = PositionName.valueOf(positionNameStr.toUpperCase());
                        } catch (IllegalArgumentException ex) {
                            System.err.println("Không thể parse Enum cho giá trị: " + positionNameStr);
                        }
                    }

                    positions.add(new Position(
                            resultSet.getInt("id"),
                            positionEnum
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
        Object[] headers = {"ID", "Tên vị trí"};

        Table.printTable(positions, border, format, headers, pos -> new Object[]{
                pos.getId(),
                pos.getName() != null ? pos.getName().name() : "NULL"
        });
    }

    @Override
    public void show(String id) {
        try {
            int posId = Integer.parseInt(id);
            Position position = Position.find(posId);

            if (position != null) {
                System.out.println("Position [ID: " + position.getId() + ", Name: " + position.getName() + "]");
            } else {
                System.out.println("❌ Không tìm thấy chức vụ với ID: " + id);
            }
        } catch (NumberFormatException e) {
            System.out.println("❌ ID chức vụ phải là số nguyên!");
        }
    }

    @Override
    public void create() {
        System.out.println("=== TẠO MỚI CHỨC VỤ ===");
        System.out.println("Các vị trí hợp lệ: DEV, TEST, SCRUM_MASTER, PM");
        System.out.print("Nhập tên vị trí: ");
        String posNameStr = scanner.nextLine().trim();

        try {
            PositionName positionEnum = PositionName.valueOf(posNameStr.toUpperCase());
            Position position = new Position(0, positionEnum);
            this.store(position);
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Giá trị chức vụ không hợp lệ! Chỉ chấp nhận: DEV, TEST, SCRUM_MASTER, PM");
        }
    }

    @Override
    public void store(Position position) {
        String[] columns = {"name"};

        boolean isSuccess = DB.buildInsertQuery(
                "position",
                columns,
                position.getName().name()
        );

        if (isSuccess) {
            System.out.println("✅ Thêm chức vụ thành công!");
        } else {
            System.out.println("❌ Thêm chức vụ thất bại!");
        }
    }

    @Override
    public void destroy(String id) {
        String sql = "SELECT * FROM position WHERE id = " + id;

        try (ResultSet result = DB.getInstance().executeQuery(sql)) {
            if (result != null && result.next()) {
                int c = DB.getInstance().executeUpdate("DELETE FROM position WHERE id = " + id);
                if (c > 0) {
                    System.out.println("✅ Xóa chức vụ thành công!");
                } else {
                    System.out.println("❌ Xóa chức vụ thất bại!");
                }
            } else {
                System.out.println("❌ Không tìm thấy thông tin chức vụ với ID: " + id);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi cơ sở dữ liệu khi xóa chức vụ (kiểm tra ràng buộc khóa ngoại): " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Lỗi hệ thống: " + e.getMessage());
        }
    }

    @Override
    public void edit() {
        System.out.println("=== CHỈNH SỬA CHỨC VỤ ===");
        System.out.print("Nhập ID chức vụ cần sửa: ");
        String id = scanner.nextLine().trim();

        try {
            Position position = Position.find(Integer.parseInt(id));
            if (position == null) {
                System.out.println("❌ Không tìm thấy chức vụ với ID: " + id);
                return;
            }

            System.out.println("\nThông tin hiện tại: Position [ID: " + position.getId() + ", Name: " + position.getName() + "]");
            System.out.println("(Nhấn Enter để GIỮ NGUYÊN thông tin cũ)");
            System.out.println("Các vị trí hợp lệ: DEV, TEST, SCRUM_MASTER, PM");

            System.out.print("Tên vị trí mới [" + position.getName() + "]: ");
            String posNameStr = scanner.nextLine().trim();

            if (!posNameStr.isEmpty()) {
                try {
                    position.setName(PositionName.valueOf(posNameStr.toUpperCase()));
                } catch (IllegalArgumentException e) {
                    System.out.println("⚠️ Giá trị chức vụ không hợp lệ, giữ nguyên giá trị cũ!");
                    return;
                }
            }

            update(position);
        } catch (NumberFormatException e) {
            System.out.println("❌ ID chức vụ phải là số nguyên!");
        }
    }

    @Override
    public void update(Position position) {
        String sql = "UPDATE position SET name = '" + position.getName().name() + "' WHERE id = " + position.getId();

        int rowsAffected = DB.getInstance().executeUpdate(sql);
        if (rowsAffected > 0) {
            System.out.println("✅ Cập nhật chức vụ ID " + position.getId() + " thành công!");
        } else {
            System.out.println("❌ Cập nhật chức vụ thất bại!");
        }
    }
}