package utils;

import java.sql.*;

public final class DB {
    private static DB INSTANCE = new DB();

    private Connection connection;

    private final String URL = "jdbc:mysql://localhost:3306/testing_system";
    private final String USER = "root";
    private final String PASSWORD = "";

    private DB() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            this.connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Kết nối Database thành công!");
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
            return null;
        }
    }
}
