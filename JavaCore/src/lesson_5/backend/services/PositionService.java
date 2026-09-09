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

public class PositionService implements Manageable {
    @Override
    public void index() {
        List<Position> positions = new ArrayList<>();
        ResultSet resultSet = DB.getInstance().executeQuery("SELECT * FROM position");

        if (resultSet != null) {
            try {
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
            } catch (SQLException e) {
                System.err.println("Lỗi khi đọc dữ liệu từ ResultSet: " + e.getMessage());
            }
        }

        String border = "+----+---------------------------+";
        String format = "| %-2s | %-25s |%n";
        Object[] headers = {"ID", "Tên vị trí"};

        Table.printTable(positions, border, format, headers, pos -> new Object[]{
                pos.getId(),
                pos.getName() != null ? pos.getName().name() : "NULL"
        });
    }
}