package sample;

import com.itextpdf.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.TextFields;
import pdfGeneration.Invoice;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EditOrderFormController implements Initializable {
    private Controller parentController;
    private Order order;

    private ObservableList<Product> ProductList = FXCollections.observableArrayList();
    private ArrayList<String> ListofProductNames = new ArrayList<>();
    private Product selectedProduct;

    // FXML Members
    @FXML private TextField productName;
    @FXML private TextField productPrice;
    @FXML private TextField qty;
    @FXML private TextArea productDescription;

    @FXML private Label cashierNameLabel;
    @FXML private Label orderIDLabel;
    @FXML private Label orderDateLabel;
    @FXML private Label orderTimeLabel;
    @FXML private Label branchLabel;

    @FXML private Label grandTotalLabel;
    @FXML private TextField cash;
    @FXML private Label changeLabel;

    // Table Members
    @FXML private TableView<SubOrder> SubOrderTable;
    @FXML private TableColumn<SubOrder, Integer> noCol;
    @FXML private TableColumn<SubOrder, String> productNameCol;
    @FXML private TableColumn<SubOrder, Integer> qtyCol;
    @FXML private TableColumn<SubOrder, Integer> priceCol;
    @FXML private ObservableList<SubOrder> SubOrderList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb){
        noCol.setCellValueFactory(new PropertyValueFactory<>("ColNo"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("ProductName"));
        qtyCol.setCellValueFactory(new PropertyValueFactory<>("Qty"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("Price"));
    }

    public void initData(Controller parentController, Order order, ObservableList<Product> ProductList){
        this.parentController = parentController;
        this.order = order;

        // Bind Product
        this.ProductList = ProductList;
        bindProductName();

        cashierNameLabel.setText(order.getCashierName());
        orderIDLabel.setText(order.getOrderID());
        orderDateLabel.setText(order.getLocalDateTime().toLocalDate().toString());
        orderTimeLabel.setText(order.getLocalDateTime().toLocalTime().toString());
        branchLabel.setText(order.getBranch());
        grandTotalLabel.setText(String.valueOf(order.getTotal()));
        cash.setText(String.valueOf(order.getCash()));

        RefreshSubOrderList();
        RefreshSubOrderTable();
        calculatePaid();
    }

    private void bindProductName(){
        for (Product product : ProductList) {
            ListofProductNames.add(product.getProductName());
        }
        TextFields.bindAutoCompletion(productName, ListofProductNames);
    }

    @FXML
    public void ProductNameAction(){
        setSelectedProduct(productName.getText());
        productPrice.setText(String.valueOf(selectedProduct.getPrice()));
    }

    private void setSelectedProduct(String selectedProductName) {
        for (Product product : ProductList) {
            if (product.getProductName().equals(selectedProductName)){
                selectedProduct = product;
                System.out.println("Selected Product: " + selectedProduct.getProductName());
                return;
            }
        }
    }

    @FXML
    public void TableViewOnSelected(){
        System.out.println("TableView selected on EditOrderForm");
        SubOrder subOrder = SubOrderTable.getSelectionModel().getSelectedItem();
        setSelectedProduct(subOrder.getProductName());
        productName.setText(subOrder.getProductName());
        productPrice.setText(String.valueOf(subOrder.getPrice()));
        qty.setText(String.valueOf(subOrder.getQty()));
        productDescription.setText(subOrder.getDescription());
    }

    @FXML
    public void addItemClicked() {
        try {
            System.out.println("AddItemButton clicked on EditOrderForm");
            int colNo;
            // If Product is Edited
            if (productInList(selectedProduct.getProductID())) {
                colNo = SubOrderTable.getSelectionModel().getSelectedItem().getColNo();
                SubOrderList.remove(SubOrderTable.getSelectionModel().getSelectedIndex());
            } else {
                colNo = SubOrderList.size() + 1;
            }
            SubOrderList.add(new SubOrder(colNo, selectedProduct.getProductID(), selectedProduct.getProductName(), Integer.parseInt(qty.getText()), productDescription.getText(), selectedProduct.getPrice()));
            RefreshSubOrderTable();
            calculatePaid();
            clearTextfields();
        } catch (NumberFormatException e){
            // Validation with alert box
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Qty Has Wrong Format!");
            alert.setContentText("Please input correct format of Qty!");

            alert.showAndWait();
        }
    }

    private boolean productInList(String ProductID) {
        for (SubOrder subOrder : SubOrderList) {
            if (subOrder.getProductID().equals(ProductID)) {
                System.out.println(ProductID + " in SubOrderList");
                return true;
            }
        }
        return false;
    }

    private void clearTextfields(){
        productName.clear();
        productPrice.clear();
        qty.clear();
        productDescription.clear();
    }

    @FXML
    public void deleteItemClicked(){
        System.out.println("DeleteItemButton clicked on OrderForm.fxml");
        SubOrderList.remove(SubOrderTable.getSelectionModel().getSelectedIndex());
        RefreshSubOrderTable();
        calculatePaid();
    }

    private void RefreshSubOrderTable(){
        SubOrderTable.setItems(SubOrderList);

        // Calculate subTotal
        int gTotal = 0;
        for (SubOrder suborder : SubOrderList) {
            gTotal += suborder.getQty() * suborder.getPrice();
        }

        // Set Labels;
        grandTotalLabel.setText(String.valueOf(gTotal));
    }

    private void RefreshSubOrderList(){
        SubOrderList.clear();
        try {
            String sql = "SELECT orders_details.*, product_name, price FROM orders_details INNER JOIN products ON orders_details.product_id = products.product_id WHERE order_id = '%s'";
            sql = String.format(sql, order.getOrderID());

            Connection conn = Database.connect();
            ResultSet rs = conn.createStatement().executeQuery(sql);

            int colNo = 1;
            while(rs.next()) {
                SubOrderList.add(new SubOrder(colNo, rs.getString("product_id"), rs.getString("product_name"),
                        rs.getInt("qty"), rs.getString("description"), rs.getInt("price")));
                colNo++;
            }

            rs.close();
            conn.close();
        } catch (SQLException e) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @FXML
    public void editOrder(ActionEvent event) throws SQLException{
        // Set Sub-Orders variables
        String OrderID;
        String ProductID;
        int Qty;
        String Description;

        Database.editOrder(order.getOrderID(), Integer.parseInt(grandTotalLabel.getText()), Integer.parseInt(cash.getText()));

        Database.clearSubOrders(order.getOrderID());

        // SQL queries
        for (SubOrder subOrder: SubOrderList){
            OrderID = order.getOrderID();
            ProductID = subOrder.getProductID();
            Qty = subOrder.getQty();
            Description = subOrder.getDescription();
            Database.addSubOrder(OrderID, ProductID, Qty, Description);
        }

        // Close Stage & Refresh Table
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
        parentController.RefreshOrderTable();

        // Editing the PDF
        Invoice inv = new Invoice(order, SubOrderList);
        inv.makeInvoice();
    }

    @FXML
    public void calculatePaid(){
        try {
            int Change = 0;
            // Getting Grand Total value
            int gTotal = Integer.parseInt(grandTotalLabel.getText());
            // Getting Paid value
            int Paid = Integer.parseInt(cash.getText());

            Change = Paid - gTotal;
            if (Change < 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setHeaderText("Paid Has Minus Format!");
                alert.setContentText("Please input correct paid value!");

                alert.showAndWait();
                return;
            }
            changeLabel.setText(String.valueOf(Change));
        } catch (NumberFormatException e){
            // Validation with alert box
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Paid Has Wrong Format!");
            alert.setContentText("Please input correct format of paid!");

            alert.showAndWait();
        }
    }

    @FXML
    public void GenerateInvoice(ActionEvent event) throws InterruptedException, SQLException, IOException, java.io.IOException {
        editOrder(event);

        Invoice inv = new Invoice(order, SubOrderList);
        inv.makeInvoice();
        inv.openFile();
    }
}