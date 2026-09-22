package com.buivanhuy.app.repositories;

import com.buivanhuy.app.enums.PositionName;
import com.buivanhuy.entities.Position;
import com.buivanhuy.utils.database.DB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PositionRepositoryImpl implements ResourceRepository<Position> {

    @Override
    public List<Position> findAll() {
        List<Position> positions = new ArrayList<>();
        String sql = "SELECT * FROM position";

        try (PreparedStatement ps = DB.getInstance().getConnection().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                positions.add(mapResultSetToPosition(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return positions;
    }

    @Override
    public Position findById(int id) {
        String sql = "SELECT * FROM position WHERE id = ?";

        try (PreparedStatement ps = DB.getInstance().getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToPosition(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean store(Position entity) {
        String sql = "INSERT INTO position (name) VALUES (?)";

        try (PreparedStatement ps = DB.getInstance().getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            if (entity.getName() != null) {
                ps.setString(1, entity.getName().name());
            } else {
                ps.setNull(1, Types.VARCHAR);
            }

            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        entity.setId(generatedKeys.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Position entity) {
        if (entity == null) {
            return false;
        }
        String sql = "UPDATE position SET name = ? WHERE id = ?";

        try (PreparedStatement ps = DB.getInstance().getConnection().prepareStatement(sql)) {
            if (entity.getName() != null) {
                ps.setString(1, entity.getName().name());
            } else {
                ps.setNull(1, Types.VARCHAR);
            }
            ps.setInt(2, entity.getId());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(Position entity) {
        if (entity == null) {
            return false;
        }
        String sql = "DELETE FROM position WHERE id = ?";

        try (PreparedStatement ps = DB.getInstance().getConnection().prepareStatement(sql)) {
            ps.setInt(1, entity.getId());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private Position mapResultSetToPosition(ResultSet rs) throws SQLException {
        Position position = new Position();
        position.setId(rs.getInt("id"));
        String nameStr = rs.getString("name");
        if (nameStr != null) {
            position.setName(PositionName.valueOf(nameStr));
        }
        return position;
    }
}