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
import java.time.LocalDate;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller implements Initializable {
    // Employee & Settings
    Employee user;
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
    @FXML private Label LogOutLabel;

    // Order Pane Members
    @FXML private AnchorPane OrderPane;
    @FXML private Label NewOrderLabel;
    @FXML private Label DeleteOrderLabel;
    @FXML private Label EditOrderLabel;
    @FXML private ComboBox OrderFilter;
    @FXML private Label OrderFilterLabel;
    @FXML private DatePicker OrderDateFilterStart;
    @FXML private DatePicker OrderDateFilterEnd;
    @FXML private Label OrderDateFilterLabel;
    @FXML private TableView<Order> OrderTable;
    @FXML private TableColumn<Order, String> OrdIDCol;
    @FXML private TableColumn<Order, String> OrdEmpIDCol;
    @FXML private TableColumn<Order, String> OrdDateCol;
    @FXML private TableColumn<Order, String> BranchCol;
    @FXML private TableColumn<Order, Integer> OrdTotalCol;
    ObservableList<Order> OrderList = FXCollections.observableArrayList();
    public HashMap<String, String> AllBranch;

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
    public ArrayList<String> ProductTypeList = new ArrayList<>();

    // Employee Pane Members
    @FXML private AnchorPane EmployeePane;
    @FXML private Label NewEmployeeLabel;
    @FXML private Label DeleteEmployeeLabel;
    @FXML private Label EditEmployeeLabel;
    @FXML private ComboBox EmployeeFilter;
    @FXML private Label EmployeeFilterLabel;
    @FXML private TableView<Employee> EmployeeTable;
    @FXML private TableColumn<Order, Integer> EmpNoCol;
    @FXML private TableColumn<Order, String> EmpIDCol;
    @FXML private TableColumn<Order, String> EmpNameCol;
    @FXML private TableColumn<Order, String> EmpPositionCol;
    @FXML private TableColumn<Order, String> EmpBranchCol;
    ObservableList<Employee> EmployeeList = FXCollections.observableArrayList();
    public HashMap<String, String> AllPosition;

    // Branch Pane Members
    @FXML private AnchorPane BranchPane;
    @FXML private Label NewBranchLabel;
    @FXML private Label DeleteBranchLabel;
    @FXML private Label EditBranchLabel;
    @FXML private TableView<Branch> BranchTable;
    @FXML private TableColumn<Order, String> BranchNoCol;
    @FXML private TableColumn<Order, String> BranchIDCol;
    @FXML private TableColumn<Order, String> BranchNameCol;
    @FXML private TableColumn<Order, String> BranchAddCol;
    @FXML private TableColumn<Order, String> BranchPhoneCol;
    ObservableList<Branch> BranchList = FXCollections.observableArrayList();

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

        // Initialize Branch Pane
        NewBranchLabel.setFont(Font.loadFont("file:src/fonts/cocoregular.ttf", 18));
        DeleteBranchLabel.setFont(Font.loadFont("file:src/fonts/cocoregular.ttf", 18));
        EditBranchLabel.setFont(Font.loadFont("file:src/fonts/cocoregular.ttf", 18));

        LogOutLabel.setFont(Font.loadFont("file:src/fonts/cocoregular.ttf", 18));
    }

    public void initData(Employee employee) throws SQLException {
        user = employee;

        // Set Settings
        Settings = Integer.parseInt(user.getPositionID().replaceAll("[^\\d.]", ""));

        RefreshProductFilter();
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
            OrderOptionforAreaManagers();
        }
        // Branch Manager Settings
        else if (Settings == 2){
            OrderOptionforBranchManagers();
            ProductOptionforBranchManagersAndCashiers();
        }
        // Cashier Options
        else if (Settings == 3){
            OrderOptionforCashiers();
            ProductOptionforBranchManagersAndCashiers();
            EmployeeOptionforCashiers();
            BranchOptionforCashiers();
        }
    }

    // OrderPane Options
    private void OrderOptionforAreaManagers(){
        System.out.println("Enable Order Option for Area Managers");
        NewOrderLabel.setDisable(true);
        NewOrderLabel.setVisible(false);
        EditOrderLabel.setDisable(true);
        EditOrderLabel.setVisible(false);
        DeleteOrderLabel.setDisable(true);
        DeleteOrderLabel.setVisible(false);
    }

    private void OrderOptionforBranchManagers(){
        System.out.println("Enable Order Option for Branch Managers");
        OrderFilterLabel.setDisable(true);
        OrderFilterLabel.setVisible(false);
        OrderFilter.setDisable(true);
        OrderFilter.setVisible(false);
    }

    private void OrderOptionforCashiers(){
        System.out.println("Enable Order Option for Cashiers");
        DeleteOrderLabel.setDisable(true);
        DeleteOrderLabel.setVisible(false);
        OrderFilterLabel.setDisable(true);
        OrderFilterLabel.setVisible(false);
        OrderFilter.setDisable(true);
        OrderFilter.setVisible(false);
    }

    // ProductPane Options
    private void ProductOptionforBranchManagersAndCashiers(){
        System.out.println("Enable Product Option for Branch Managers & Cashiers");
        NewProductLabel.setDisable(true);
        NewProductLabel.setVisible(false);
        DeleteProductLabel.setDisable(true);
        DeleteProductLabel.setVisible(false);
        EditProductLabel.setDisable(true);
        EditProductLabel.setVisible(false);
    }

    // EmployeePane Options
    private void EmployeeOptionforCashiers(){
        System.out.println("Enable Employee Option for Cashiers");
        NewEmployeeLabel.setDisable(true);
        NewEmployeeLabel.setVisible(false);
        DeleteEmployeeLabel.setDisable(true);
        DeleteEmployeeLabel.setVisible(false);
        EditEmployeeLabel.setDisable(true);
        EditEmployeeLabel.setVisible(false);
    }

    // BranchPane Options
    private void BranchOptionforCashiers(){
        System.out.println("Enable Branch Option for Cashiers");
        NewBranchLabel.setDisable(true);
        NewBranchLabel.setVisible(false);
        DeleteBranchLabel.setDisable(true);
        DeleteBranchLabel.setVisible(false);
        EditBranchLabel.setDisable(true);
        EditBranchLabel.setVisible(false);
    }

    @FXML
    public void OrderLabelClicked() throws SQLException {
        System.out.println("OrderLabel clicked on MainScreen");
        LabelDefault();
        OrderDateFilterStart.setValue(null);
        OrderDateFilterEnd.setValue(null);
        OrderFilter.setPromptText("Branch: All");
        OrderLabel.setTextFill(Paint.valueOf("#640e19"));
        OrderRectangle.setVisible(true);
        new FadeIn(OrderRectangle).play();
        OrderPane.setDisable(false);
        OrderPane.setVisible(true);
        new FadeIn(OrderPane).play();
        RefreshOrderTable();
        RefreshOrderFilter(1);
    }

    @FXML
    public void ProductLabelClicked(){
        System.out.println("ProductLabel clicked on MainScreen");
        LabelDefault();
        FilterProduct.setPromptText("Type: All");
        ProductLabel.setTextFill(Paint.valueOf("#640e19"));
        ProductRectangle.setVisible(true);
        new FadeIn(ProductRectangle).play();
        ProductPane.setDisable(false);
        ProductPane.setVisible(true);
        new FadeIn(ProductPane).play();
        RefreshProductFilter();
        RefreshProductTable();
    }

    @FXML
    public void EmployeeLabelClicked(){
        System.out.println("EmployeeLabel clicked on MainScreen");
        LabelDefault();
        EmployeeFilter.setPromptText("Position: All");
        EmployeeLabel.setTextFill(Paint.valueOf("#640e19"));
        EmployeeRectangle.setVisible(true);
        new FadeIn(EmployeeRectangle).play();
        EmployeePane.setDisable(false);
        EmployeePane.setVisible(true);
        new FadeIn(EmployeePane).play();
        RefreshEmployeeTable();
        RefreshEmployeeFilter(1);
    }

    @FXML
    public void BranchLabelClicked(){
        System.out.println("BranchLabel clicked on MainScreen");
        LabelDefault();
        BranchLabel.setTextFill(Paint.valueOf("#640e19"));
        BranchRectangle.setVisible(true);
        new FadeIn(BranchRectangle).play();
        BranchPane.setDisable(false);
        BranchPane.setVisible(true);
        new FadeIn(BranchPane).play();
        RefreshBranchTable();
    }

    @FXML
    public void LogOutLabelClicked() throws IOException {
        System.out.println("LogOutLabel clicked on MainScreen");

        // Loads the main page
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("LoginPage.fxml"));
        Parent parent = loader.load();

        // Gets stage's info and setting it up
        Stage stage = (Stage) LogOutLabel.getScene().getWindow();
        stage.setResizable(false);
        stage.setTitle("Login Page");
        stage.setScene(new Scene(parent));
        stage.show();

        System.out.println(user.getEmployeeName() + " has Logged Out");
    }

    // Order Pane Functions
    @FXML
    public void NewOrderClicked() throws IOException, SQLException {
        System.out.println("New Order Clicked");
        new FadeIn(NewOrderLabel).setSpeed(5).play();

        RefreshProductList();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("OrderForm.fxml"));
        Parent OrderFormParent = loader.load();

        Stage stage = new Stage(); // New stage (window)

        // Get CurrentProductID; if no Product exist yet prevProductID set to 0
        String prevOrderID = "ORD00000";
        if (!OrderList.isEmpty()){
            prevOrderID = Database.getLastOrderID();
        }

        // Passing data to ProductFormController
        OrderFormController controller = loader.getController();
        controller.initData(this, prevOrderID, ProductList, user);

        // Setting the stage up
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.setTitle("New Order Form");
        stage.setScene(new Scene(OrderFormParent));
        stage.showAndWait();
    }

    @FXML
    public void DeleteOrderClicked() throws SQLException {
        System.out.println("Delete Order Clicked");
        new FadeIn(DeleteOrderLabel).setSpeed(5).play();

        // Gets Selected Row
        Order selectedOrder = OrderTable.getSelectionModel().getSelectedItem();
        if (!(selectedOrder == null)) {
            String id = selectedOrder.getOrderID();
            Database.deleteOrder(id);
            RefreshOrderList();
        } else {
            // Validation with alert box
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("No Data Selected!");
            alert.setContentText("Please select correct row before click delete order!");

            alert.showAndWait();
        }
    }

    @FXML
    public void EditOrderClicked() throws IOException {
        System.out.println("Edit Order Clicked");
        new FadeIn(EditOrderLabel).setSpeed(5).play();

        RefreshProductList();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("EditOrderForm.fxml"));
        Parent EditOrderFormParent = loader.load();

        Stage stage = new Stage(); // New stage (window)

        // Passing data to CustomerFormController
        EditOrderFormController controller = loader.getController();
        Order selectedOrder = OrderTable.getSelectionModel().getSelectedItem();
        try {
            controller.initData(this, selectedOrder, ProductList);
        } catch (NullPointerException e){
            // Validation with alert box
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("No Data Selected!");
            alert.setContentText("Please select correct row before click edit order!");

            alert.showAndWait();
            return;
        }

        // Setting the stage up
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.setTitle("Edit Order Form");
        stage.setScene(new Scene(EditOrderFormParent));
        stage.showAndWait();
    }

    @FXML
    public void RefreshOrderList() throws NullPointerException, SQLException {
        OrderList.clear();

        String filter;
        String sql = "SELECT * FROM orders";
        boolean option = false;

        // Area Manager filter
        if(Settings == 1){
            // Checks if ComboBox is empty
            try {
                filter = OrderFilter.getValue().toString();
                if (!filter.equals("All")){
                    sql = sql + " WHERE branch_id = '%s'";
                    sql = String.format(sql, Database.getBranchID(filter));
                    option = true;
                }
            } catch (NullPointerException ignored) {}
        }
        // Other employee filter
        else {
            sql = sql + " WHERE branch_id = '%s'";
            sql = String.format(sql, user.getBranchID());
            option = true;
        }

        // Checks if DateFilter is Empty
        if (!((OrderDateFilterStart.getValue() == null) || (OrderDateFilterEnd.getValue() == null))){
            LocalDate startDate = OrderDateFilterStart.getValue();
            LocalDate endDate = OrderDateFilterEnd.getValue();
            if (option) {
                sql = sql + " AND ";
            } else {
                sql = sql + " WHERE ";
            }
            sql = sql + "datetime BETWEEN '%s 00:00:00' AND '%s 23:59:59'";
            sql = String.format(sql, startDate, endDate);
        }

        Connection conn = Database.connect();
        ResultSet rs = conn.createStatement().executeQuery(sql);

        while(rs.next()) {
            OrderList.add(new Order(rs.getString("order_id"), Database.getEmployeeName(rs.getString("employee_id")), rs.getTimestamp("datetime").toLocalDateTime(), Database.getBranchName(rs.getString("branch_id")), rs.getInt("total"), rs.getInt("cash")));
        }

        rs.close();
        conn.close();
    }

    @FXML
    public void RefreshOrderTable() throws NullPointerException, SQLException {
        RefreshOrderList();
        OrdIDCol.setCellValueFactory(new PropertyValueFactory<>("OrderID"));
        OrdEmpIDCol.setCellValueFactory(new PropertyValueFactory<>("CashierName"));
        OrdDateCol.setCellValueFactory(new PropertyValueFactory<>("DateTime"));
        BranchCol.setCellValueFactory(new PropertyValueFactory<>("Branch"));
        OrdTotalCol.setCellValueFactory(new PropertyValueFactory<>("Total"));
        OrderTable.setItems(OrderList);
    }

    public void RefreshOrderFilter(int settings){
        OrderFilter.getItems().clear();
        AllBranch = Database.getAllBranch();
        // Setting == 1 is for Filter Settings
        if(settings == 1){
            AllBranch.put("All", "0");
        }
        OrderFilter.setItems(FXCollections.observableArrayList(AllBranch.keySet()));
        System.out.println("Refreshed OrderFilter in Mainscreen.fxml");
    }

    // Product Pane Functions
    @FXML
    public void NewProductClicked() throws IOException, SQLException {
        System.out.println("NewProductLabel clicked on MainScreen >> Product");
        new FadeIn(NewProductLabel).setSpeed(5).play();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ProductForm.fxml"));
        Parent ProductFormParent = loader.load();

        Stage stage = new Stage(); // New stage (window)

        // Get CurrentProductID; if no Product exist yet prevProductID set to 0
        String prevProductID = "PRO00000";
        if (!ProductList.isEmpty()){
            prevProductID = Database.getLastProductID();
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
        System.out.println("DeleteProductLabel clicked on MainScreen >> Product");
        new FadeIn(DeleteProductLabel).setSpeed(5).play();

        // Gets Selected Row
        Product selectedItem = ProductTable.getSelectionModel().getSelectedItem();
        String id = "";

        // Checks if ProductFilter is selected
        try {
            id = selectedItem.getProductID();
        } catch (NullPointerException e) {
            // Validation with alert box
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("No Data Selected!");
            alert.setContentText("Please select correct row before click delete product!");

            alert.showAndWait();
        }

        if(!id.equals("")){
            try {
                Database.deleteProduct(id);
                // If TypeId exists in products
                if (!(Database.isProductTypeExistInProduct(Database.getTypeID(selectedItem.getProductType())))){
                    Database.deleteProductTypeByType(selectedItem.getProductType());
                    RefreshProductFilter();
                }
                RefreshProductTable();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void EditProductClicked() throws IOException, SQLException {
        System.out.println("EditProductLabel clicked on MainScreen >> Product");
        new FadeIn(EditProductLabel).setSpeed(5).play();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("EditProductForm.fxml"));
        Parent ProductFormParent = loader.load();

        Stage stage = new Stage(); // New stage (window)

        // Passing data to EditProductFormController
        EditProductFormController controller = loader.getController();
        Product selectedProduct = ProductTable.getSelectionModel().getSelectedItem();
        try {
            controller.initData(this, selectedProduct);
        } catch (NullPointerException e){
            // Validation with alert box
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("No Data Selected!");
            alert.setContentText("Please select correct row before click edit product!");

            alert.showAndWait();
            return;
        }

        // Setting the stage up
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.setTitle("Edit Product Form");
        stage.setScene(new Scene(ProductFormParent));
        stage.showAndWait();
    }

    public void RefreshProductFilter(){
        FilterProduct.getItems().clear();
        ProductTypeList = Database.getAllTypes();
        ProductTypeList.add(0, "All");
        FilterProduct.setItems(FXCollections.observableArrayList(ProductTypeList));
        System.out.println("ProductFilter refreshed in MainScreen >> Product");
    }

    public void RefreshProductList() {
        ProductList.clear();
        try {
            String filter, sql;
            try {
                filter = FilterProduct.getValue().toString();
                // If user choose a specific filter
                if (filter.equals("All")) {
                    sql = String.format("SELECT * FROM products");
                } else {
                    String TypeID = Database.getTypeID(filter);
                    sql = String.format("SELECT * FROM products WHERE TypeID = '%s'", TypeID);
                }
            } catch (NullPointerException e) {
                sql = "SELECT * FROM products";
            }

            Connection conn = Database.connect();
            ResultSet rs = conn.createStatement().executeQuery(sql);

            int colNo = 1;
            while (rs.next()) {
                ProductList.add(new Product(colNo, rs.getString("product_id"), rs.getString("product_name"),
                        Database.getType(rs.getString("TypeID")), rs.getInt("price")));
                colNo++;
            }

            rs.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void RefreshProductTable() throws NullPointerException{
        RefreshProductList();
        ProdNoCol.setCellValueFactory(new PropertyValueFactory<>("columnNo"));
        ProdIDCol.setCellValueFactory(new PropertyValueFactory<>("ProductID"));
        ProdNameCol.setCellValueFactory(new PropertyValueFactory<>("ProductName"));
        ProdTypeCol.setCellValueFactory(new PropertyValueFactory<>("ProductType"));
        ProdPriceCol.setCellValueFactory(new PropertyValueFactory<>("Price"));
        ProductTable.setItems(ProductList);
        System.out.println("ProductTable refreshed on MainScreen >> Product");
    }

    // Employee Pane Functions
    @FXML
    public void NewEmployeeClicked() throws IOException, SQLException {
        System.out.println("New_Employee_Label clicked in MainScreen.fxml");
        new FadeIn(NewEmployeeLabel).setSpeed(5).play();

        // For removing "All" value in Position ComboBox
        RefreshEmployeeFilter(0);
        RefreshOrderFilter(0);

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("EmployeeForm.fxml"));
        Parent EmployeeFormParent = loader.load();

        Stage stage = new Stage(); // New stage (window)

        // Get CurrentEmployeeID; if no Employee exist yet prevEmployeeID set to 0
        String prevEmployeeID = "EMP00000";
        if (!EmployeeList.isEmpty()){
            prevEmployeeID = Database.getLastEmployeeID();
        }

        // Passing data to EmployeeFormController
        EmployeeFormController controller = loader.getController();
        controller.initData(this, prevEmployeeID, AllPosition, AllBranch);

        // Setting the stage up
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.setTitle("New Employee Form");
        stage.setScene(new Scene(EmployeeFormParent));
        stage.showAndWait();
    }

    @FXML
    public void DeleteEmployeeClicked(){
        System.out.println("Delete_Employee_Label clicked on MainScreen.fxml");
        new FadeIn(DeleteEmployeeLabel).setSpeed(5).play();

        // Gets Selected Row
        try{
            Employee selectedItem = EmployeeTable.getSelectionModel().getSelectedItem();
            String id = selectedItem.getEmployeeID();
            if(!(selectedItem == null)){
                Database.deleteEmployee(id);
                RefreshEmployeeList();
            }
        } catch (NullPointerException e){
            // Validation with alert box
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("No Data Selected!");
            alert.setContentText("Please select correct row before click delete employee!");

            alert.showAndWait();
        }
    }

    @FXML
    public void EditEmployeeClicked() throws IOException {
        System.out.println("Edit_Employee_Label clicked on MainScreen.fxml");
        new FadeIn(EditEmployeeLabel).setSpeed(5).play();

        // For removing "All" value in Position and Branch ComboBox
        RefreshEmployeeFilter(0);
        RefreshOrderFilter(0);

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("EditEmployeeForm.fxml"));
        Parent EmployeeFormParent = loader.load();

        Stage stage = new Stage(); // New stage (window)

        // Passing data to EditEmployeeFormController
        EditEmployeeFormController controller = loader.getController();
        Employee selectedEmployee = EmployeeTable.getSelectionModel().getSelectedItem();
        try {
            controller.initData(this, selectedEmployee, AllPosition, AllBranch);
        } catch (NullPointerException e){
            // Validation with alert box
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("No Data Selected!");
            alert.setContentText("Please select correct row before click edit employee!");

            alert.showAndWait();
            return;
        }

        // Setting the stage up
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.setTitle("Edit Employee Form");
        stage.setScene(new Scene(EmployeeFormParent));
        stage.showAndWait();
    }

    public void RefreshEmployeeFilter(int settings){
        EmployeeFilter.getItems().clear();
        AllPosition = Database.getAllPosition();
        if(settings == 1){
            AllPosition.put("All", "0");
        }
        EmployeeFilter.setItems(FXCollections.observableArrayList(AllPosition.keySet()));
        System.out.println("Refreshed EmployeeFilter in Mainscreen.fxml");
    }

    public void RefreshEmployeeList(){
        try {
            EmployeeList.clear();
            String filter, sql;
            // Checks if ComboBox is empty
            try {
                filter = EmployeeFilter.getValue().toString();
                if(filter.equals("All")){
                    sql = "SELECT * FROM employee";
                }
                else {
                    String positionID = AllPosition.get(filter);
                    sql = String.format("SELECT * FROM employee WHERE position_id = '%s'", positionID);
                }
            } catch (NullPointerException e) {
                sql = "SELECT * FROM employee";
            }

            Connection conn = Database.connect();
            ResultSet rs = conn.createStatement().executeQuery(sql);

            int colNo = 1;
            while(rs.next()) {
                if (rs.getString("branch_id").equals("")){
                    EmployeeList.add(new Employee(colNo, rs.getString("employee_id"), rs.getString("employee_name"), rs.getString("password"), Database.getPositionName(rs.getString("position_id")), "All"));
                } else {
                    EmployeeList.add(new Employee(colNo, rs.getString("employee_id"), rs.getString("employee_name"), rs.getString("password"), Database.getPositionName(rs.getString("position_id")), Database.getBranchName(rs.getString("branch_id"))));
                }
                colNo++;
            }

            rs.close();
            conn.close();
        } catch (SQLException e) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @FXML
    public void RefreshEmployeeTable() throws NullPointerException{
        RefreshEmployeeList();
        EmpNoCol.setCellValueFactory(new PropertyValueFactory<>("columnNo"));
        EmpIDCol.setCellValueFactory(new PropertyValueFactory<>("EmployeeID"));
        EmpNameCol.setCellValueFactory(new PropertyValueFactory<>("EmployeeName"));
        EmpPositionCol.setCellValueFactory(new PropertyValueFactory<>("Position"));
        EmpBranchCol.setCellValueFactory(new PropertyValueFactory<>("Branch"));
        EmployeeTable.setItems(EmployeeList);
    }

    // Branch Pane Functions
    @FXML
    public void NewBranchClicked() throws IOException, SQLException {
        System.out.println("New_Branch_Label clicked in MainScreen.fxml");
        new FadeIn(NewBranchLabel).setSpeed(5).play();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("BranchForm.fxml"));
        Parent BranchFormParent = loader.load();

        Stage stage = new Stage(); // New stage (window)

        // Get CurrentBranchID; if no Branch exist yet prevBranchID set to 0
        String prevBranchID = "BRC00000";
        if (!BranchList.isEmpty()){
            prevBranchID = Database.getLastBranchID();
        }

        // Passing data to BranchFormController
        BranchFormController controller = loader.getController();
        controller.initData(this, prevBranchID);

        // Setting the stage up
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.setTitle("New Branch Form");
        stage.setScene(new Scene(BranchFormParent));
        stage.showAndWait();
    }

    @FXML
    public void DeleteBranchClicked(){
        System.out.println("Delete_Branch_Label clicked on MainScreen.fxml");
        new FadeIn(DeleteBranchLabel).setSpeed(5).play();

        // Gets Selected Row
        try{
            Branch selectedItem = BranchTable.getSelectionModel().getSelectedItem();
            String id = selectedItem.getBranchID();
            if (!(selectedItem == null)) {
                Database.deleteBranch(id);
                RefreshBranchList();
            }
        } catch (NullPointerException e){
            // Validation with alert box
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("No Data Selected!");
            alert.setContentText("Please select correct row before click delete branch!");

            alert.showAndWait();
        }
    }

    @FXML
    public void EditBranchClicked() throws IOException {
        System.out.println("Edit_Branch_Label clicked on MainScreen.fxml");
        new FadeIn(EditBranchLabel).setSpeed(5).play();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("EditBranchForm.fxml"));
        Parent BranchFormParent = loader.load();

        Stage stage = new Stage(); // New stage (window)

        // Passing data to EditBranchFormController
        EditBranchFormController controller = loader.getController();
        Branch selectedBranch = BranchTable.getSelectionModel().getSelectedItem();

        try {
            controller.initData(this, selectedBranch);
        } catch (NullPointerException e){
            // Validation with alert box
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("No Data Selected!");
            alert.setContentText("Please select correct row before click edit branch!");

            alert.showAndWait();
            return;
        }

        // Setting the stage up
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.setTitle("Edit Branch Form");
        stage.setScene(new Scene(BranchFormParent));
        stage.showAndWait();
    }

    public void RefreshBranchList(){
        BranchList.clear();
        String sql;
        try {
            sql = "SELECT * FROM branch";

            Connection conn = Database.connect();
            ResultSet rs = conn.createStatement().executeQuery(sql);

            int colNo = 1;
            while (rs.next()) {
                BranchList.add(new Branch(colNo, rs.getString("branch_id"), rs.getString("branch_name"), rs.getString("address"), rs.getString("phone")));
                colNo++;
            }

            rs.close();
            conn.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
        BranchNoCol.setCellValueFactory(new PropertyValueFactory<>("columnNo"));
        BranchIDCol.setCellValueFactory(new PropertyValueFactory<>("BranchID"));
        BranchNameCol.setCellValueFactory(new PropertyValueFactory<>("BranchName"));
        BranchAddCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        BranchPhoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        BranchTable.setItems(BranchList);
    }

    @FXML
    public void RefreshBranchTable() throws NullPointerException{
        RefreshBranchList();
        BranchNoCol.setCellValueFactory(new PropertyValueFactory<>("columnNo"));
        BranchIDCol.setCellValueFactory(new PropertyValueFactory<>("BranchID"));
        BranchNameCol.setCellValueFactory(new PropertyValueFactory<>("BranchName"));
        BranchAddCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        BranchPhoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        BranchTable.setItems(BranchList);
    }
}