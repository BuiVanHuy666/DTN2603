package com.buivanhuy.app.repositories;

import com.buivanhuy.entities.Department;
import com.buivanhuy.utils.database.DB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentRepositoryImpl implements ResourceRepository<Department> {

    @Override
    public List<Department> findAll() {
        List<Department> departments = new ArrayList<>();
        String sql = "SELECT * FROM department";

        try (PreparedStatement ps = DB.getInstance().getConnection().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                departments.add(mapResultSetToDepartment(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return departments;
    }

    @Override
    public Department findById(int id) {
        String sql = "SELECT * FROM department WHERE id = ?";

        try (PreparedStatement ps = DB.getInstance().getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToDepartment(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean store(Department entity) {
        String sql = "INSERT INTO department (name) VALUES (?)";

        try (PreparedStatement ps = DB.getInstance().getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, entity.getName());

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
    public boolean update(Department entity) {
        if (entity == null) {
            return false;
        }
        String sql = "UPDATE department SET name = ? WHERE id = ?";

        try (PreparedStatement ps = DB.getInstance().getConnection().prepareStatement(sql)) {
            ps.setString(1, entity.getName());
            ps.setInt(2, entity.getId());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(Department entity) {
        if (entity == null) {
            return false;
        }
        String sql = "DELETE FROM department WHERE id = ?";

        try (PreparedStatement ps = DB.getInstance().getConnection().prepareStatement(sql)) {
            ps.setInt(1, entity.getId());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private Department mapResultSetToDepartment(ResultSet rs) throws SQLException {
        Department department = new Department();
        department.setId(rs.getInt("id"));
        department.setName(rs.getString("name"));
        return department;
    }
}