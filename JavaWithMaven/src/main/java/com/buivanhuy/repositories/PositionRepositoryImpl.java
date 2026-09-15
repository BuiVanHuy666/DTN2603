package com.buivanhuy.repositories;

import com.buivanhuy.entities.Position;
import com.buivanhuy.enums.PositionName;
import com.buivanhuy.utils.database.DB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PositionRepositoryImpl implements ResourceRepository<Position> {

    @Override
    public List<Position> findAll() {
        List<Position> positions = new ArrayList<>();
        String sql = "SELECT * FROM position";
        try (ResultSet rs = DB.getInstance().executeQuery(sql)) {
            while (rs.next()) {
                positions.add(mapResultSetToPosition(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return positions;
    }

    @Override
    public Position findById(int id) {
        String sql = "SELECT * FROM position WHERE id = " + id;
        try (ResultSet rs = DB.getInstance().executeQuery(sql)) {
            if (rs.next()) {
                return mapResultSetToPosition(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Position store(Position entity) {
        String sql = String.format("INSERT INTO position (name) VALUES ('%s')", entity.getName());
        int affectedRows = DB.getInstance().executeUpdate(sql);
        if (affectedRows > 0) {
            try (ResultSet rs = DB.getInstance().executeQuery("SELECT LAST_INSERT_ID() AS id")) {
                if (rs.next()) {
                    entity.setId(rs.getInt("id"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return entity;
        }
        return null;
    }

    @Override
    public Position update(Position entity) {
        String sql = String.format("UPDATE position SET name = '%s' WHERE id = %d", entity.getName(), entity.getId());
        int affectedRows = DB.getInstance().executeUpdate(sql);
        return affectedRows > 0 ? entity : null;
    }

    @Override
    public boolean delete(Position entity) {
        if (entity == null) {
            return false;
        }
        String sql = "DELETE FROM position WHERE id = " + entity.getId();
        return DB.getInstance().executeUpdate(sql) > 0;
    }

    private Position mapResultSetToPosition(ResultSet rs) throws SQLException {
        Position position = new Position();
        position.setId(rs.getInt("id"));
        position.setName(PositionName.valueOf(rs.getString("name")));
        return position;
    }
}