package com.buivanhuy.app.repositories;

import com.buivanhuy.app.enums.PositionName;
import com.buivanhuy.entities.Account;
import com.buivanhuy.entities.Department;
import com.buivanhuy.entities.Position;
import com.buivanhuy.app.enums.Gender;
import com.buivanhuy.utils.database.DB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountRepositoryImpl implements ResourceRepository<Account> {
    @Override
    public List<Account> findAll() {
        List<Account> accounts = new ArrayList<>();
        String sql = """
                SELECT a.*, d.name AS dept_name, p.name AS pos_name
                FROM account a
                LEFT JOIN department d ON a.department_id = d.id
                LEFT JOIN position p ON a.position_id = p.id""";
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
        String sql = """
            SELECT a.*, d.name AS dept_name, p.name AS pos_name
            FROM account a
            LEFT JOIN department d ON a.department_id = d.id
            LEFT JOIN position p ON a.position_id = p.id
            WHERE a.id = ?""";

        try (PreparedStatement ps = DB.getInstance().getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToAccount(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean store(Account entity) {
        String sql = "INSERT INTO account (fullname, username, email, gender, department_id, position_id, create_date) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = DB.getInstance().getConnection()
                .prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, entity.getFullName());
            ps.setString(2, entity.getUsername());
            ps.setString(3, entity.getEmail());

            ps.setString(4, entity.getGender() != null ? entity.getGender().name() : Gender.OTHER.name());

            if (entity.getDepartment() != null) {
                ps.setInt(5, entity.getDepartment().getId());
            } else {
                ps.setNull(5, java.sql.Types.INTEGER);
            }

            if (entity.getPosition() != null) {
                ps.setInt(6, entity.getPosition().getId());
            } else {
                ps.setNull(6, java.sql.Types.INTEGER);
            }

            if (entity.getCreateDate() != null) {
                ps.setTimestamp(7, new java.sql.Timestamp(entity.getCreateDate().getTime()));
            } else {
                ps.setNull(7, java.sql.Types.TIMESTAMP);
            }

            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Account entity) {
        String sql = "UPDATE account SET fullname = ?, username = ?, email = ?, gender = ?, department_id = ?, position_id = ? WHERE id = ?";

        try (PreparedStatement ps = DB.getInstance().getConnection().prepareStatement(sql)) {
            ps.setString(1, entity.getFullName());
            ps.setString(2, entity.getUsername());
            ps.setString(3, entity.getEmail());
            ps.setString(4, entity.getGender() != null ? entity.getGender().name() : Gender.OTHER.name());

            if (entity.getDepartment() != null && entity.getDepartment().getId() > 0) {
                ps.setInt(5, entity.getDepartment().getId());
            } else {
                ps.setNull(5, Types.INTEGER);
            }

            if (entity.getPosition() != null && entity.getPosition().getId() > 0) {
                ps.setInt(6, entity.getPosition().getId());
            } else {
                ps.setNull(6, Types.INTEGER);
            }

            ps.setInt(7, entity.getId());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(Account entity) {
        if (entity == null) {
            return false;
        }
        String sql = "DELETE FROM account WHERE id = ?";

        try (PreparedStatement ps = DB.getInstance().getConnection().prepareStatement(sql)) {
            ps.setInt(1, entity.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private Account mapResultSetToAccount(ResultSet rs) throws SQLException {
        Account account = new Account();
        account.setId(rs.getInt("id"));
        account.setFullName(rs.getString("fullname"));
        account.setUsername(rs.getString("username"));
        account.setEmail(rs.getString("email"));
        account.setCreateDate(rs.getTimestamp("create_date"));

        String genderStr = rs.getString("gender");
        if (genderStr != null) {
            try {
                account.setGender(Gender.valueOf(genderStr));
            } catch (IllegalArgumentException ignored) {}
        }

        int departmentId = rs.getInt("department_id");
        if (!rs.wasNull()) {
            String deptName = rs.getString("dept_name");
            account.setDepartment(new Department(departmentId, deptName));
        }

        int positionId = rs.getInt("position_id");
        if (!rs.wasNull()) {
            String posNameStr = rs.getString("pos_name");
            PositionName posNameEnum = null;
            if (posNameStr != null) {
                try {
                    posNameEnum = PositionName.valueOf(posNameStr);
                } catch (IllegalArgumentException ignored) {}
            }
            account.setPosition(new Position(positionId, posNameEnum));
        }

        return account;
    }
}