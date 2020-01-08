package sample;

import java.lang.reflect.Type;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Database {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/21_dbta";
    static final String USER = "root";
//    static final String PASS = "2201798295Binus";
    static final String PASS = "";
    static Connection conn;
    static Statement stmt;
    static ResultSet rs;

    public static Connection connect(){
        try {
            Class.forName(JDBC_DRIVER);
            return DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Employee Queries
    public static String getEmployeeName(String EmployeeID) throws SQLException{
        conn = connect();

        String sql = "SELECT employee_ name FROM employee WHERE employee_id = '%s'";
        sql = String.format(sql, EmployeeID);
        ResultSet rs = conn.createStatement().executeQuery(sql);

        rs.next();
        return rs.getString("employee_id");
    }


    // Product Queries
    public static void addProduct(String ProductID, String ProductName, int Price){
        try {
            conn = connect();
            stmt = conn.createStatement();

            String sql = "INSERT INTO products(product_id, product_name, price) VALUE('%s', '%s', '%d')";
            sql = String.format(sql, ProductID, ProductName, Price);
            stmt.execute(sql);

            System.out.println(String.format("Added %s to product", ProductID));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void editProduct(String ProductID, String ProductName, int Price){
        try {
            conn = connect();
            stmt = conn.createStatement();

            String sql = "UPDATE products SET product_name='%s', price='%d' WHERE product_id='%s'";
            sql = String.format(sql, ProductName, Price, ProductID);
            stmt.execute(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteProduct(String ProductID){
        try {
            conn = connect();
            stmt = conn.createStatement();

            String sql = String.format("DELETE FROM products where product_id = '%s'", ProductID);
            stmt.execute(sql);

            System.out.println(String.format("Deleted %s from product", ProductID));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Order Queries
    public static void addOrder(String order_id, String employee_id, LocalDateTime dateTime, int total, int cash, String status){
        try {
            conn = connect();
            stmt = conn.createStatement();

            String sql = "INSERT INTO orders(order_id, employee_id, dateTime, total, cash, status) VALUE('%s', '%s', '%s', '%d', '%d', '%d', '%s')";
            sql = String.format(sql, order_id, employee_id, dateTime, total, cash, status);
            stmt.execute(sql);

            System.out.println(String.format("Added %s to orders", order_id));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addSubOrder(String order_id, String product_id, int qty, String description){
        try {
            conn = connect();
            stmt = conn.createStatement();

            String sql = "INSERT INTO orders_details(order_id, product_id, qty, description) VALUE('%s', '%s', '%d', '%s')";
            sql = String.format(sql, order_id, product_id, qty, description);
            stmt.execute(sql);

            System.out.println(String.format("Added %s to sub-orders", order_id));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteOrder(String id){
        try {
            conn = connect();
            stmt = conn.createStatement();

            String sql = String.format("DELETE FROM orders where order_id = '%s'", id);
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void editOrder(String order_id, String employee_id, LocalDate date, int total, int cash, int changes, String status){
        try {
            conn = connect();
            stmt = conn.createStatement();

            String sql = "UPDATE orders SET employee_id='%s', date='%s', total='%d', cash='%d', changes='%d', status='%s' WHERE order_id='%s'";
            sql = String.format(sql, employee_id, date, total, cash, changes, status, order_id);
            stmt.execute(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}