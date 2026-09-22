package com.buivanhuy.utils.database;

import lombok.Getter;

import java.sql.*;

public class DB {
    private static DB INSTANCE = new DB();

    @Getter
    private Connection connection;

    private DB() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            String URL = "jdbc:mysql://localhost:3306/testing_system";
            String USER = "root";
            String PASSWORD = "";
            this.connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception e) {
            System.err.println("Kết nối Database thất bại: " + e.getMessage());
        }
    }

    public static DB getInstance() {
        if (INSTANCE == null) {
            synchronized (DB.class) {
                if (INSTANCE == null) {
                    INSTANCE = new DB();
                }
            }
        }
        return INSTANCE;
    }

    public ResultSet executeQuery(String sql) {
        try {
            Statement statement = connection.createStatement();
            return statement.executeQuery(sql);
        } catch (SQLException e) {
            System.err.println("Lỗi khi chạy lệnh SELECT: " + e.getMessage());
            throw  new RuntimeException(e);
        }
    }

    public int executeUpdate(String sql) {
        try {
            Statement statement = connection.createStatement();
            return statement.executeUpdate(sql);
        } catch (SQLException e) {
            System.err.println("Lỗi khi chạy lệnh MUTATION: " + e.getMessage());
            return 0;
        }
    }
}
