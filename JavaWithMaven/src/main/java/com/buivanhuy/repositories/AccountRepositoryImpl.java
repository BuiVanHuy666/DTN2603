package com.buivanhuy.repositories;

import com.buivanhuy.entities.Account;
import com.buivanhuy.entities.Department;
import com.buivanhuy.entities.Position;
import com.buivanhuy.enums.Gender;
import com.buivanhuy.utils.database.DB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class AccountRepositoryImpl implements ResourceRepository<Account> {

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public List<Account> findAll() {
        List<Account> accounts = new ArrayList<>();
        String sql = "SELECT * FROM account";
        try (ResultSet rs = DB.getInstance().executeQuery(sql)) {
            while (rs.next()) {
                accounts.add(mapResultSetToAccount(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return accounts;
    }

    @Override
    public Account findById(int id) {
        String sql = "SELECT * FROM account WHERE id = " + id;
        try (ResultSet rs = DB.getInstance().executeQuery(sql)) {
            if (rs.next()) {
                return mapResultSetToAccount(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Account store(Account entity) {
        String createDateStr = entity.getCreateDate() != null
                ? "'" + dateFormat.format(entity.getCreateDate()) + "'"
                : "NULL";
        String genderStr = entity.getGender() != null
                ? "'" + entity.getGender().name() + "'"
                : "NULL";
        String departmentId = (entity.getDepartment() != null)
                ? String.valueOf(entity.getDepartment().getId())
                : "NULL";
        String positionId = (entity.getPosition() != null)
                ? String.valueOf(entity.getPosition().getId())
                : "NULL";

        String sql = String.format(
                "INSERT INTO account (fullname, username, email, create_date, gender, department_id, position_id) " +
                        "VALUES ('%s', '%s', '%s', %s, %s, %s, %s)",
                entity.getFullName(),
                entity.getUsername(),
                entity.getEmail(),
                createDateStr,
                genderStr,
                departmentId,
                positionId
        );

        int affectedRows = DB.getInstance().executeUpdate(sql);
        if (affectedRows > 0) {
            // Lấy ID tự tăng vừa được chèn gần nhất trong MySQL
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
    public Account update(Account entity) {
        String genderStr = entity.getGender() != null
                ? "'" + entity.getGender().name() + "'"
                : "NULL";
        String departmentId = (entity.getDepartment() != null)
                ? String.valueOf(entity.getDepartment().getId())
                : "NULL";
        String positionId = (entity.getPosition() != null)
                ? String.valueOf(entity.getPosition().getId())
                : "NULL";

        String sql = String.format(
                "UPDATE account SET fullname = '%s', username = '%s', email = '%s', " +
                        "gender = %s, department_id = %s, position_id = %s WHERE id = %d",
                entity.getFullName(),
                entity.getUsername(),
                entity.getEmail(),
                genderStr,
                departmentId,
                positionId,
                entity.getId()
        );

        int affectedRows = DB.getInstance().executeUpdate(sql);
        return affectedRows > 0 ? entity : null;
    }

    @Override
    public boolean delete(Account entity) {
        if (entity == null) {
            return false;
        }
        String sql = "DELETE FROM account WHERE id = " + entity.getId();
        return DB.getInstance().executeUpdate(sql) > 0;
    }

    private Account mapResultSetToAccount(ResultSet rs) throws SQLException {
        Account account = new Account();
        account.setId(rs.getInt("id"));
        account.setFullName(rs.getString("fullname"));
        account.setUsername(rs.getString("username"));
        account.setEmail(rs.getString("email"));
        account.setCreateDate(rs.getDate("create_date"));

        String genderStr = rs.getString("gender");
        if (genderStr != null) {
            account.setGender(Gender.valueOf(genderStr));
        }

        int departmentId = rs.getInt("department_id");
        if (!rs.wasNull()) {
            Department department = new Department();
            department.setId(departmentId);
            account.setDepartment(department);
        }

        int positionId = rs.getInt("position_id");
        if (!rs.wasNull()) {
            Position position = new Position();
            position.setId(positionId);
            account.setPosition(position);
        }

        return account;
    }
}