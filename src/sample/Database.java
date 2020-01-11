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
    static final String PASS = "2201798295Binus";
//    static final String PASS = "";
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

    // Product Queries
    public static void addProductType(String TypeID, String Type){
        try {
            conn = connect();
            stmt = conn.createStatement();

            String sql = "INSERT INTO product_type(TypeID, Type) value('%s', '%s')";
            sql = String.format(sql, TypeID, Type);
            stmt.execute(sql);

            System.out.println(String.format("Added %s to product_type", TypeID));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteProductTypeByID(String TypeID){
        try {
            conn = connect();
            stmt = conn.createStatement();

            String sql = String.format("DELETE FROM product_type where TypeID = '%s'", TypeID);
            stmt.execute(sql);

            System.out.println(String.format("Deleted TypeID = %s from product_type", TypeID));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteProductTypeByType(String Type){
        try {
            conn = connect();
            stmt = conn.createStatement();

            String sql = String.format("DELETE FROM product_type where Type = '%s'", Type);
            stmt.execute(sql);

            System.out.println(String.format("Deleted Type = %s from product_type", Type));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<String> getAllTypes() throws NullPointerException {
        ArrayList<String> listofTypes = new ArrayList<>();
        try {
            conn = connect();
            String sql = "SELECT Type FROM product_type";
            ResultSet rs = conn.createStatement().executeQuery(sql);

            while (rs.next()){
                listofTypes.add(rs.getString("Type"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listofTypes;
    }

    public static boolean isProductTypeExist(String Type) throws SQLException {
        conn = connect();

        String sql = "SELECT * FROM product_type WHERE Type = '%s'";
        sql = String.format(sql, Type);
        ResultSet rs = conn.createStatement().executeQuery(sql);

        // Checks if ProductType exists
        int counter = 0;
        while (rs.next()){
            counter++;
        }

        if (counter != 0) {
            System.out.println(String.format("%s exists in product_type", Type));
            return true;
        } else {
            System.out.println(String.format("%s doesn't exists in product_type", Type));
            return false;
        }
    }

    public static String getTypeID(String Type) throws SQLException{
        conn = connect();

        String sql = "SELECT * FROM product_type WHERE Type = '%s'";
        sql = String.format(sql, Type);
        ResultSet rs = conn.createStatement().executeQuery(sql);

        rs.next();
        return rs.getString("TypeID");
    }

    public static String getType(String TypeID) throws SQLException{
        conn = connect();

        String sql = "SELECT * FROM product_type WHERE TypeID = '%s'";
        sql = String.format(sql, TypeID);
        ResultSet rs = conn.createStatement().executeQuery(sql);

        rs.next();
        return rs.getString("Type");
    }

    public static String getLastTypeID() throws SQLException{
        try {
            conn = connect();

            String sql = "SELECT MAX(TypeID) FROM product_type";
            ResultSet rs = conn.createStatement().executeQuery(sql);
            rs.next();

            // If no data exists yet
            if (rs.getString("MAX(TypeID)") == null){
                System.out.println("Last TypeID = TYP00000");
                return "TYP00000";
            }

            // If data exists
            String LastTypeID = rs.getString("MAX(TypeID)");
            System.out.println(String.format("Last TypeID = %s", LastTypeID));
            return LastTypeID;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void addProduct(String product_id, String product_name, String type_id, int price){
        try {
            conn = connect();
            stmt = conn.createStatement();

            String sql = "INSERT INTO products(product_id, product_name, TypeID, price) VALUE('%s', '%s', '%s', '%d')";
            sql = String.format(sql, product_id, product_name, type_id, price);
            stmt.execute(sql);

            System.out.println(String.format("Added %s to products database", product_id));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void editProduct(String product_id, String product_name, String type_id, int price){
        try {
            conn = connect();
            stmt = conn.createStatement();

            String sql = "UPDATE products SET product_name='%s', TypeID='%s', price='%d' WHERE product_id='%s'";
            sql = String.format(sql, product_name, type_id, price, product_id);
            stmt.execute(sql);

            System.out.println(String.format("Edited %s in products database", product_id));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteProduct(String product_id){
        try {
            conn = connect();
            stmt = conn.createStatement();

            String sql = String.format("DELETE FROM products where product_id = '%s'", product_id);
            stmt.execute(sql);

            System.out.println(String.format("Deleted %s from product", product_id));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String getProductName(String ProductID) throws SQLException{
        conn = connect();

        String sql = "SELECT ProductName FROM products WHERE ProductID = '%s'";
        sql = String.format(sql, ProductID);
        ResultSet rs = conn.createStatement().executeQuery(sql);

        rs.next();
        return rs.getString("ProductName");
    }

    public static int getProductPrice(String ProductID) throws SQLException{
        conn = connect();

        String sql = "SELECT Price FROM products WHERE ProductID = '%s'";
        sql = String.format(sql, ProductID);
        ResultSet rs = conn.createStatement().executeQuery(sql);

        rs.next();
        return rs.getInt("Price");
    }

    public static boolean isProductTypeExistInProduct(String TypeID) throws SQLException{
        conn = connect();

        String sql = "SELECT * FROM products WHERE TypeID = '%s'";
        sql = String.format(sql, TypeID);
        ResultSet rs = conn.createStatement().executeQuery(sql);

        // Checks if ProductType exists
        int counter = 0;
        while (rs.next()){
            counter++;
        }

        if (counter != 0) {
            System.out.println(String.format("TypeID %s exists in products database", TypeID));
            return true;
        } else {
            System.out.println(String.format("TypeID %s doesn't exists in products database", TypeID));
            return false;
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

    // Branch Queries
    public static void addBranch(String branch_id, String branch_name, String address, String phone){
        try {
            conn = connect();
            stmt = conn.createStatement();

            String sql = "INSERT INTO branch(branch_id, branch_name, address, phone) VALUE('%s', '%s', '%s', '%s')";
            sql = String.format(sql, branch_id, branch_name, address, phone);
            stmt.execute(sql);

            System.out.println(String.format("Added %s to branch", branch_id));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteBranch(String branch_id){
        try {
            conn = connect();
            stmt = conn.createStatement();

            String sql = String.format("DELETE FROM branch where branch_id = '%s'", branch_id);
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void editBranch(String branch_id, String branch_name, String address, String phone){
        try {
            conn = connect();
            stmt = conn.createStatement();

            String sql = "UPDATE branch SET branch_name='%s', address='%s', phone='%s' WHERE branch_id='%s'";
            sql = String.format(sql, branch_name, address, phone, branch_id);
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Employee Queries
    public static void addEmployee(String employee_id, String employee_name, String password, String position_id, String branch_id){
        try {
            conn = connect();
            stmt = conn.createStatement();

            String sql = "INSERT INTO employee(employee_id, employee_name, password, position_id, branch_id) VALUE('%s', '%s', '%s', '%s', '%s)";
            sql = String.format(sql, employee_id, employee_name, password, position_id, branch_id);
            stmt.execute(sql);

            System.out.println(String.format("Added %s to branch", branch_id));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteEmployee(String employee_id){
        try {
            conn = connect();
            stmt = conn.createStatement();

            String sql = String.format("DELETE FROM employee where employee_id = '%s'", employee_id);
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void editEmployee(String employee_id, String employee_name, String password, String position_id, String branch_id){
        try {
            conn = connect();
            stmt = conn.createStatement();

            String sql = "UPDATE employee SET employee_name='%s', password='%s', position_id='%s', branch_id='%s' WHERE employee_id='%s'";
            sql = String.format(sql, employee_name, password, position_id, branch_id, employee_id);
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String getEmployeeName(String EmployeeID) throws SQLException{
        conn = connect();

        String sql = "SELECT employee_ name FROM employee WHERE employee_id = '%s'";
        sql = String.format(sql, EmployeeID);
        ResultSet rs = conn.createStatement().executeQuery(sql);

        rs.next();
        return rs.getString("employee_id");
    }
}