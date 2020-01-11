package sample;

import animatefx.animation.FadeIn;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller implements Initializable {
    // Settins Options
    int Settings;

    // Sidebar Pane Members
    @FXML private Label OrderLabel;
    @FXML private Label ProductLabel;
    @FXML private Label EmployeeLabel;
    @FXML private Label BranchLabel;
    @FXML private Rectangle OrderRectangle;
    @FXML private Rectangle ProductRectangle;
    @FXML private Rectangle EmployeeRectangle;
    @FXML private Rectangle BranchRectangle;

    // Order Pane Members
    @FXML private AnchorPane OrderPane;
    @FXML private Label NewOrderLabel;
    @FXML private Label DeleteOrderLabel;
    @FXML private Label EditOrderLabel;
    @FXML private ComboBox OrderFilter;
    @FXML private DatePicker OrderDateFilter;
    @FXML private Label OrderFilterLabel;
    @FXML private Label OrderDateFilterLabel;
    @FXML private TableView<Order> OrderTable;
    @FXML private TableColumn<Order, String> OrdIDCol;
    @FXML private TableColumn<Order, String> EmpIDCol;
    @FXML private TableColumn<Order, String> OrdDateCol;
    @FXML private TableColumn<Order, String> BranchCol;
    @FXML private TableColumn<Order, Integer> OrdTotalCol;
    ObservableList<Order> OrderList = FXCollections.observableArrayList();

    // Product Pane Members
    @FXML private AnchorPane ProductPane;
    @FXML private Label NewProductLabel;
    @FXML private Label DeleteProductLabel;
    @FXML private Label EditProductLabel;
    @FXML private ComboBox FilterProduct;
    @FXML private Label ProductFilterLabel;
    @FXML private TableView<Product> ProductTable;
    @FXML private TableColumn<Product, Integer> ProdNoCol;
    @FXML private TableColumn<Product, String> ProdIDCol;
    @FXML private TableColumn<Product, String> ProdNameCol;
    @FXML private TableColumn<Product, Integer> ProdTypeCol;
    @FXML private TableColumn<Product, Integer> ProdPriceCol;
    ObservableList<Product> ProductList = FXCollections.observableArrayList();

    // Employee Pane Members
    @FXML private AnchorPane EmployeePane;
    @FXML private Label NewEmployeeLabel;
    @FXML private Label DeleteEmployeeLabel;
    @FXML private Label EditEmployeeLabel;
    @FXML private ComboBox EmployeeFilter;
    @FXML private Label EmployeeFilterLabel;
    @FXML private TableView<Order> EmployeeTable;
    @FXML private TableColumn<Order, String> EmpIDCol2;
    @FXML private TableColumn<Order, String> EmpNameCol;
    @FXML private TableColumn<Order, String> EmpPositionCol;
    @FXML private TableColumn<Order, String> EmpPasswordCol;
    ObservableList<Order> EmployeeList = FXCollections.observableArrayList();

    // Branch Pane Members
    @FXML private AnchorPane BranchPane;
    @FXML private Label NewBranchLabel;
    @FXML private Label DeleteBranchLabel;
    @FXML private Label EditBranchLabel;
    @FXML private TableView<Order> BranchTable;
    @FXML private TableColumn<Order, String> BranchIDCol;
    @FXML private TableColumn<Order, String> BranchNameCol;
    @FXML private TableColumn<Order, String> BranchAddCol;
    @FXML private TableColumn<Order, String> BranchPhoneCol;
    ObservableList<Order> BranchList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb){
        // Initialize Order Pane
        NewOrderLabel.setFont(Font.loadFont("file:src/fonts/cocoregular.ttf", 18));
        DeleteOrderLabel.setFont(Font.loadFont("file:src/fonts/cocoregular.ttf", 18));
        EditOrderLabel.setFont(Font.loadFont("file:src/fonts/cocoregular.ttf", 18));
        OrderFilterLabel.setFont(Font.loadFont("file:src/fonts/cocoregular.ttf", 12));
        OrderDateFilterLabel.setFont(Font.loadFont("file:src/fonts/cocoregular.ttf", 12));

        // Initialize Product Pane
        NewProductLabel.setFont(Font.loadFont("file:src/fonts/cocoregular.ttf", 18));
        DeleteProductLabel.setFont(Font.loadFont("file:src/fonts/cocoregular.ttf", 18));
        EditProductLabel.setFont(Font.loadFont("file:src/fonts/cocoregular.ttf", 18));
        ProductFilterLabel.setFont(Font.loadFont("file:src/fonts/cocolight.ttf", 12));

        // Initialize Employee Pane
        NewEmployeeLabel.setFont(Font.loadFont("file:src/fonts/cocoregular.ttf", 18));
        DeleteEmployeeLabel.setFont(Font.loadFont("file:src/fonts/cocoregular.ttf", 18));
        EditEmployeeLabel.setFont(Font.loadFont("file:src/fonts/cocoregular.ttf", 18));
//        EmployeeFilterLabel.setFont(Font.loadFont("file:src/fonts/cocolight.ttf", 12));

        // Initialize Branch Pane
        NewBranchLabel.setFont(Font.loadFont("file:src/fonts/cocoregular.ttf", 18));
        DeleteBranchLabel.setFont(Font.loadFont("file:src/fonts/cocoregular.ttf", 18));
        EditBranchLabel.setFont(Font.loadFont("file:src/fonts/cocoregular.ttf", 18));
    }

    public void initData(int settings){
        Settings = settings;
        OrderLabelClicked();
    }

    private void LabelDefault(){
        // Set Label Fonts
        OrderLabel.setFont(Font.loadFont("file:src/fonts/expressway.ttf", 20));
        ProductLabel.setFont(Font.loadFont("file:src/fonts/expressway.ttf", 20));
        EmployeeLabel.setFont(Font.loadFont("file:src/fonts/expressway.ttf", 20));
        BranchLabel.setFont(Font.loadFont("file:src/fonts/expressway.ttf", 20));
        // Set Label Colors
        OrderLabel.setTextFill(Paint.valueOf("9a9a9a"));
        ProductLabel.setTextFill(Paint.valueOf("9a9a9a"));
        EmployeeLabel.setTextFill(Paint.valueOf("9a9a9a"));
        BranchLabel.setTextFill(Paint.valueOf("9a9a9a"));
        // Set Rectangles Off
        OrderRectangle.setVisible(false);
        ProductRectangle.setVisible(false);
        EmployeeRectangle.setVisible(false);
        BranchRectangle.setVisible(false);
        // Set Panes Off
        OrderPane.setDisable(true);
        OrderPane.setVisible(false);
        ProductPane.setDisable(true);
        ProductPane.setVisible(false);
        EmployeePane.setDisable(true);
        EmployeePane.setVisible(false);
        BranchPane.setDisable(true);
        BranchPane.setVisible(false);

        // Area Manager Settings
        if (Settings == 1){
        }
        // Branch Manager Settings
        else if (Settings == 2){
            DisableBranchOptions();
        }
        // Cashier Options
        else if (Settings == 3){
            ProductOptionforCashiers();
            DisableEmployeeOptions();
            DisableBranchOptions();
        }
    }

    private void ProductOptionforCashiers(){
        NewProductLabel.setDisable(true);
        NewProductLabel.setVisible(false);
        DeleteProductLabel.setDisable(true);
        DeleteProductLabel.setVisible(false);
        EditProductLabel.setDisable(true);
        EditProductLabel.setVisible(false);
    }

    private void DisableEmployeeOptions(){
        EmployeeRectangle.setDisable(true);
        EmployeeRectangle.setVisible(false);
        EmployeeLabel.setDisable(true);
        EmployeeLabel.setVisible(false);
        EmployeePane.setDisable(true);
        EmployeePane.setVisible(false);
    }

    private void DisableBranchOptions(){
        BranchRectangle.setDisable(true);
        BranchRectangle.setVisible(false);
        BranchLabel.setDisable(true);
        BranchLabel.setVisible(false);
        BranchPane.setDisable(true);
        BranchPane.setVisible(false);
    }

    @FXML
    public void OrderLabelClicked(){
        LabelDefault();
        OrderLabel.setTextFill(Paint.valueOf("#640e19"));
        OrderRectangle.setVisible(true);
        new FadeIn(OrderRectangle).play();
        OrderPane.setDisable(false);
        OrderPane.setVisible(true);
        new FadeIn(OrderPane).play();
        RefreshOrderTable();
    }

    @FXML
    public void ProductLabelClicked(){
        LabelDefault();
        ProductLabel.setTextFill(Paint.valueOf("#640e19"));
        ProductRectangle.setVisible(true);
        new FadeIn(ProductRectangle).play();
        ProductPane.setDisable(false);
        ProductPane.setVisible(true);
        new FadeIn(ProductPane).play();
        RefreshProductTable();
    }

    @FXML
    public void EmployeeLabelClicked(){
        LabelDefault();
        EmployeeLabel.setTextFill(Paint.valueOf("#640e19"));
        EmployeeRectangle.setVisible(true);
        new FadeIn(EmployeeRectangle).play();
        EmployeePane.setDisable(false);
        EmployeePane.setVisible(true);
        new FadeIn(EmployeePane).play();
//        RefreshEmployeeTable();
    }

    @FXML
    public void BranchLabelClicked(){
        LabelDefault();
        BranchLabel.setTextFill(Paint.valueOf("#640e19"));
        BranchRectangle.setVisible(true);
        new FadeIn(BranchRectangle).play();
        BranchPane.setDisable(false);
        BranchPane.setVisible(true);
        new FadeIn(BranchPane).play();
//        RefreshBranchTable();
    }

    // Order Pane Functions
    @FXML
    public void NewOrderClicked() throws IOException {
        System.out.println("New Order Clicked");
        new FadeIn(NewOrderLabel).setSpeed(5).play();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("OrderForm.fxml"));
        Parent OrderFormParent = loader.load();

        Stage stage = new Stage(); // New stage (window)

        // Get CurrentProductID; if no Product exist yet prevProductID set to 0
        String prevOrderID = "ORD00000";
        if (!OrderList.isEmpty()){
            prevOrderID = OrderList.get(OrderList.size()-1).getOrderID();
        }

        // Passing data to ProductFormController
        OrderFormController controller = loader.getController();
        controller.initData(this, prevOrderID, ProductList);

        // Setting the stage up
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.setTitle("New Order Form");
        stage.setScene(new Scene(OrderFormParent));
        stage.showAndWait();
    }

    @FXML
    public void DeleteOrderClicked(){
        System.out.println("Delete Order Clicked");
        new FadeIn(DeleteOrderLabel).setSpeed(5).play();

        // Gets Selected Row
        Order selectedOrder = OrderTable.getSelectionModel().getSelectedItem();
        if(!(selectedOrder == null)){
            String id = selectedOrder.getOrderID();
            Database.deleteOrder(id);
            RefreshOrderList();
        }
    }

    @FXML
    public void EditOrderClicked() throws IOException {
        System.out.println("Edit Order Clicked");
        new FadeIn(EditOrderLabel).setSpeed(5).play();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("EditOrderForm.fxml"));
        Parent EditOrderFormParent = loader.load();

        Stage stage = new Stage(); // New stage (window)

        // Passing data to CustomerFormController
        EditOrderFormController controller = loader.getController();
        Order selectedOrder = OrderTable.getSelectionModel().getSelectedItem();
        controller.initData(this, selectedOrder, ProductList);

        // Setting the stage up
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.setTitle("Edit Order Form");
        stage.setScene(new Scene(EditOrderFormParent));
        stage.showAndWait();
    }

    @FXML
    public void RefreshOrderList() throws NullPointerException{
        OrderList.clear();
        String filter;
        try {
            // Checks if ComboBox is empty
            try {
                filter = OrderFilter.getValue().toString();
            } catch (NullPointerException e) {
                filter = "All";
            }

            Connection conn = Database.connect();
            String sql = "SELECT * FROM orders";
            ResultSet rs = conn.createStatement().executeQuery(sql);

            int colNo = 1;
            while(rs.next()) {
                OrderList.add(new Order(rs.getString("order_id"), Database.getEmployeeName(rs.getString("employee_id")), rs.getTimestamp("datetime").toLocalDateTime(), rs.getInt("total"), rs.getString("status")));
                colNo++;
            }

            rs.close();
            conn.close();
        } catch (SQLException e) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, e);
        }
        OrdIDCol.setCellValueFactory(new PropertyValueFactory<>("order_id"));
        EmpIDCol.setCellValueFactory(new PropertyValueFactory<>("employee_id"));
        OrdDateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        BranchCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        OrdTotalCol.setCellValueFactory(new PropertyValueFactory<>("total"));
        OrderTable.setItems(OrderList);
    }

    @FXML
    public void RefreshOrderTable() throws NullPointerException{
        RefreshOrderList();
        OrdIDCol.setCellValueFactory(new PropertyValueFactory<>("order_id"));
        EmpIDCol.setCellValueFactory(new PropertyValueFactory<>("employee_id"));
        OrdDateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        BranchCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        OrdTotalCol.setCellValueFactory(new PropertyValueFactory<>("total"));
        OrderTable.setItems(OrderList);
    }

    // Product Pane Functions
    @FXML
    public void NewProductClicked() throws IOException {
        System.out.println("New_Product_Label clicked in MainScreen.fxml");
        new FadeIn(NewProductLabel).setSpeed(5).play();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ProductForm.fxml"));
        Parent ProductFormParent = loader.load();

        Stage stage = new Stage(); // New stage (window)

        // Get CurrentProductID; if no Product exist yet prevProductID set to 0
        String prevProductID = "PRO00000";
        if (!ProductList.isEmpty()){
            prevProductID = ProductList.get(ProductList.size()-1).getProductID();
        }

        // Passing data to ProductFormController
        ProductFormController controller = loader.getController();
        controller.initData(this, prevProductID);

        // Setting the stage up
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.setTitle("New Product Form");
        stage.setScene(new Scene(ProductFormParent));
        stage.showAndWait();
    }

    @FXML
    public void DeleteProductClicked(){
        System.out.println("Delete_Product_Label clicked on MainScreen.fxml");
        new FadeIn(DeleteProductLabel).setSpeed(5).play();

        // Gets Selected Row
        Product selectedItem = ProductTable.getSelectionModel().getSelectedItem();
        String id = selectedItem.getProductID();
        if(!(selectedItem == null)){
            Database.deleteProduct(id);
            RefreshProductList();
        }
    }

    @FXML
    public void EditProductClicked() throws IOException {
        System.out.println("Edit_Product_Label clicked on MainScreen.fxml");
        new FadeIn(EditProductLabel).setSpeed(5).play();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("EditProductForm.fxml"));
        Parent ProductFormParent = loader.load();

        Stage stage = new Stage(); // New stage (window)

        // Passing data to EditProductFormController
        EditProductFormController controller = loader.getController();
        Product selectedProduct = ProductTable.getSelectionModel().getSelectedItem();
        controller.initData(this, selectedProduct);

        // Setting the stage up
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.setTitle("Edit Product Form");
        stage.setScene(new Scene(ProductFormParent));
        stage.showAndWait();
    }

    public void RefreshProductList(){
        ProductList.clear();
        String filter, sql;
        try {
            try {
                filter = FilterProduct.getValue().toString();
                // If user choose a specific filter
                if (filter.equals("Descending")) {
                    sql = String.format("SELECT * FROM products ORDER BY product_name DESC");
                } else {
                    sql = "SELECT * FROM products";
                }
            } catch (NullPointerException e) {
                sql = "SELECT * FROM products";
            }

            Connection conn = Database.connect();
            ResultSet rs = conn.createStatement().executeQuery(sql);

            int colNo = 1;
            while (rs.next()) {
                ProductList.add(new Product(colNo, rs.getString("product_id"), rs.getString("product_name"), Database.getType(rs.getString("TypeID")), rs.getInt("price")));
                colNo++;
            }

            rs.close();
            conn.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
        ProdNoCol.setCellValueFactory(new PropertyValueFactory<>("columnNo"));
        ProdIDCol.setCellValueFactory(new PropertyValueFactory<>("ProductID"));
        ProdNameCol.setCellValueFactory(new PropertyValueFactory<>("ProductName"));
        ProdTypeCol.setCellValueFactory(new PropertyValueFactory<>("ProductType"));
        ProdPriceCol.setCellValueFactory(new PropertyValueFactory<>("Price"));
        ProductTable.setItems(ProductList);
    }

    @FXML
    public void RefreshProductTable() throws NullPointerException{
        RefreshProductList();
        ProdNoCol.setCellValueFactory(new PropertyValueFactory<>("columnNo"));
        ProdIDCol.setCellValueFactory(new PropertyValueFactory<>("ProductID"));
        ProdNameCol.setCellValueFactory(new PropertyValueFactory<>("ProductName"));
        ProdTypeCol.setCellValueFactory(new PropertyValueFactory<>("ProductType"));
        ProdPriceCol.setCellValueFactory(new PropertyValueFactory<>("Price"));
        ProductTable.setItems(ProductList);
    }
}