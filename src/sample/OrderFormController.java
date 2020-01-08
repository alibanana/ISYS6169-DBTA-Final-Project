package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.TextFields;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OrderFormController implements Initializable {
    private Controller parentController;
    private ObservableList<Product> ProductList = FXCollections.observableArrayList();
    private ArrayList<String> ListofProductNames = new ArrayList<>();
    private Product selectedProduct;

    // Orders ID (prev and new)
    private int prevOrderID;
    private String newOrderID;

    // FXML Members
    @FXML private TextField productName;
    @FXML private TextField productPrice;
    @FXML private TextField qty;
    @FXML private TextArea productDescription;

    @FXML private Label cashierName;
    @FXML private Label orderID;
    @FXML private DatePicker orderDate;
    @FXML private Label orderStatus;

    @FXML private Label grandTotal;
    @FXML private TextField cash;
    @FXML private Label change;

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

        // Set Order Date to Current Date
        orderDate.setValue(LocalDate.now());
    }

    public void initData(Controller parentController, String prevOrderID, ObservableList<Product> ProductList){
        this.parentController = parentController;
        this.ProductList = ProductList;
        this.prevOrderID = Integer.parseInt(prevOrderID.replaceAll("[^\\d.]", ""));

        bindProductName();

        // Make new CustomerID
        newOrderID = String.format("ORD%05d", this.prevOrderID+1);
        System.out.println("Order ID: " + newOrderID);

        // Set value
        orderID.setText(newOrderID);
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
        System.out.println("Selected Product: " + selectedProduct.getProductName());
    }

    private void setSelectedProduct(String selectedProductName) {
        for (Product product : ProductList) {
            if (product.getProductName().equals(selectedProductName)){
                selectedProduct = product;
                return;
            }
        }
    }

    @FXML
    public void addItemClicked() {
        System.out.println("AddItemButton clicked on OrderForm.fxml");
        SubOrderList.add(new SubOrder(SubOrderList.size()+1, selectedProduct.getProductID(), selectedProduct.getProductName(), Integer.parseInt(qty.getText()), productDescription.getText(), selectedProduct.getPrice()));
        RefreshSubOrderTable();
        clearTextfields();
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
    }

    @FXML
    public void calculatePaid(){
        int Change = 0;
        // Getting Grand Total value
        int gTotal = Integer.parseInt(grandTotal.getText());
        // Getting Paid value
        int Paid = Integer.parseInt(cash.getText());

        Change =  Paid - gTotal;
        change.setText(String.valueOf(Change));
    }

    @FXML
    public void addOrder(ActionEvent event) throws SQLException{
        // Set Sub-Orders variables
        String OrderID;
        String ProductID;
        int Qty;
        String Description;

        // SQL queries
        Database.addOrder(newOrderID, cashierName.getText(), orderDate.getValue(), Integer.valueOf(grandTotal.getText()), Integer.valueOf(cash.getText()), Integer.valueOf(change.getText()), orderStatus.getText());
        for (SubOrder subOrder: SubOrderList){
            OrderID = newOrderID;
            ProductID = subOrder.getProductID();
            Qty = subOrder.getQty();
            Description = subOrder.getDescription();
            Database.addSubOrder(OrderID, ProductID, Qty, Description);
        }

        // Close Stage & Refresh Table
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
        parentController.RefreshOrderTable();
    }

    private void RefreshSubOrderTable(){
        // RefreshSubOrderList();
        SubOrderTable.setItems(SubOrderList);

        // Calculate subTotal
        int gTotal = 0;
        for (SubOrder suborder : SubOrderList) {
            gTotal += suborder.getQty() * suborder.getPrice();
        }

        // Set Labels;
        grandTotal.setText(String.valueOf(gTotal));
    }
}
