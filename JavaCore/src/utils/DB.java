package utils;

import java.sql.*;
import java.text.SimpleDateFormat;

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

    public static boolean buildInsertQuery(String table, String[] columnNames, Object... args) {
        if (columnNames == null || args == null || columnNames.length != args.length) {
            System.err.println("Lỗi: Số lượng cột và số lượng giá trị không khớp nhau!");
            return false;
        }

        String columns = String.join(", ", columnNames);
        StringBuilder valuesBuilder = new StringBuilder();
        SimpleDateFormat mysqlDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        for (int i = 0; i < args.length; i++) {
            Object arg = args[i];

            if (arg == null) {
                valuesBuilder.append("NULL");
            } else if (arg instanceof java.util.Date) {
                valuesBuilder.append("'").append(mysqlDateFormat.format((java.util.Date) arg)).append("'");
            } else if (arg instanceof String || arg instanceof Enum) {
                valuesBuilder.append("'").append(arg.toString()).append("'");
            } else {
                valuesBuilder.append(arg.toString());
            }

            if (i < args.length - 1) {
                valuesBuilder.append(", ");
            }
        }

        String sql = "INSERT INTO " + table + " (" + columns + ") VALUES (" + valuesBuilder.toString() + ")";
        System.out.println("Executing SQL: " + sql);

        int result = getInstance().executeUpdate(sql);
        return result > 0;
    }
}
