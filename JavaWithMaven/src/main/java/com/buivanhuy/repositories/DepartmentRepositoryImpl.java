package com.buivanhuy.repositories;

import com.buivanhuy.entities.Department;
import com.buivanhuy.utils.database.DB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DepartmentRepositoryImpl implements ResourceRepository<Department> {
    @Override
    public List<Department> findAll() {
        List<Department> departments = new ArrayList<>();
        String sql = "SELECT * FROM department";
        try (ResultSet rs = DB.getInstance().executeQuery(sql)) {
            while (rs.next()) {
                departments.add(mapResultSetToDepartment(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return departments;
    }

    @Override
    public Department findById(int id) {
        String sql = "SELECT * FROM department WHERE id = " + id;
        try (ResultSet rs = DB.getInstance().executeQuery(sql)) {
            if (rs.next()) {
                return mapResultSetToDepartment(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Department store(Department entity) {
        String sql = String.format("INSERT INTO department (name) VALUES ('%s')", entity.getName());
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
    public Department update(Department entity) {
        String sql = String.format("UPDATE department SET name = '%s' WHERE id = %d", entity.getName(), entity.getId());
        int affectedRows = DB.getInstance().executeUpdate(sql);
        return affectedRows > 0 ? entity : null;
    }

    @Override
    public boolean delete(Department entity) {
        if (entity == null) {
            return false;
        }
        String sql = "DELETE FROM department WHERE id = " + entity.getId();
        return DB.getInstance().executeUpdate(sql) > 0;
    }

    private Department mapResultSetToDepartment(ResultSet rs) throws SQLException {
        Department department = new Department();
        department.setId(rs.getInt("id"));
        department.setName(rs.getString("name"));
        return department;
    }
}