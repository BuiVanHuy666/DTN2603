package lesson_1.models;

import lesson_1.enums.PositionName;
import lombok.Getter;
import lombok.Setter;
import utils.DB;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Position extends Model {
    @Getter @Setter
    private int id;

    @Getter @Setter
    private PositionName name;

    public Position(int id, PositionName positionName) {
        this.id = id;
        this.name = positionName;
    }

    @Override
    public String toString() {
        return "Position {" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public static List<Position> all() {
        List<Position> positions = new ArrayList<>();
        String sql = "SELECT * FROM position";

        try (ResultSet result = DB.getInstance().executeQuery(sql)) {
            if (result != null) {
                while (result.next()) {
                    String posNameStr = result.getString("name");
                    PositionName positionEnum = (posNameStr != null) ? PositionName.valueOf(posNameStr.toUpperCase()) : null;

                    positions.add(new Position(
                            result.getInt("id"),
                            positionEnum
                    ));
                }
            }
        } catch (Exception e) {
            System.err.println("Lỗi khi lấy danh sách Position: " + e.getMessage());
        }

        return positions;
    }

    public static Position find(int id) {
        String sql = "SELECT * FROM position WHERE id = " + id;
        try (ResultSet result = DB.getInstance().executeQuery(sql)) {
            if (result != null && result.next()) {
                String posNameStr = result.getString("name");
                PositionName positionEnum = (posNameStr != null) ? PositionName.valueOf(posNameStr.toUpperCase()) : null;

                return new Position(
                        result.getInt("id"),
                        positionEnum
                );
            }
        } catch (Exception e) {
            System.err.println("Lỗi khi tìm Position: " + e.getMessage());
        }
        return null;
    }
}
