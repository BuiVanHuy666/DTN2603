package com.buivanhuy.utils.database;

import lombok.Getter;

import java.sql.*;

/**
 * A Singleton utility class for managing database connections and executing SQL statements.
 * This class encapsulates the JDBC connection to a MySQL database and provides
 * convenient methods for executing queries and updates.
 */
public class DB {

    /**
     * The single instance of the DB class.
     */
    private static DB INSTANCE = new DB();

    /**
     * The active database connection.
     * Accessible via the Lombok-generated getter ({@code getConnection()}).
     */
    @Getter
    private Connection connection;

    /**
     * Private constructor to prevent instantiation from outside the class.
     * Initializes the MySQL JDBC driver and establishes the database connection.
     */
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

    /**
     * Retrieves the singleton instance of the {@link DB} class.
     *
     * @return The single, globally accessible {@link DB} instance.
     */
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

    /**
     * Executes the given SQL statement, which returns a single {@link ResultSet} object.
     * Typically used for {@code SELECT} statements.
     *
     * @param sql An SQL statement to be sent to the database, typically a static SQL {@code SELECT} statement.
     * @return A {@link ResultSet} object that contains the data produced by the given query.
     * @throws RuntimeException if a database access error occurs or the SQL statement is invalid.
     */
    public ResultSet executeQuery(String sql) {
        try {
            Statement statement = connection.createStatement();
            return statement.executeQuery(sql);
        } catch (SQLException e) {
            System.err.println("Lỗi khi chạy lệnh SELECT: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Executes the given SQL Data Manipulation Language (DML) statement,
     * such as an {@code INSERT}, {@code UPDATE}, or {@code DELETE} statement.
     *
     * @param sql An SQL Data Manipulation Language (DML) statement.
     * @return The number of rows affected by the statement. Returns {@code 0} if an error occurs.
     */
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