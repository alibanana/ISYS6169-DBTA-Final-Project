package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.TextFields;

import javax.xml.crypto.Data;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
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
    @FXML private Label orderStatusLabel;
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
    public void initialize(URL url, ResourceBundle rb){}

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
        grandTotalLabel.setText(String.valueOf(order.getTotal()));
        cash.setText(String.valueOf(order.getCash()));
        RefreshSubOrderList();
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

    private void RefreshSubOrderTable(){
        // RefreshSubOrderList();
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
            String sql = " SELECT orders_details.*, price FROM orders_details, products WHERE orders_details.product_id = products.product_id;";

            Connection conn = Database.connect();
            ResultSet rs = conn.createStatement().executeQuery(sql);

            int colNo = 1;
            while(rs.next()) {
                SubOrderList.add(new SubOrder(colNo, rs.getString("product_id"),
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

        Database.clearSubOrders(order.getOrderID());

        // SQL queries
        for (SubOrder subOrder: SubOrderList){
            OrderID = order.getOrderID();
            ProductID = subOrder.getProductID();
            Qty = subOrder.getQty();
            Description = subOrder.getDescription();
            Database.addSubOrder(OrderID, ProductID, Qty, Description);
        }
        Database.editOrder(order.getOrderID(), Integer.parseInt(grandTotalLabel.getText()), Integer.parseInt(changeLabel.getText()));

        // Close Stage & Refresh Table
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
        parentController.RefreshOrderTable();
    }

    @FXML
    public void calculatePaid(){
        int Change = 0;
        // Getting Grand Total value
        int gTotal = Integer.parseInt(grandTotalLabel.getText());
        // Getting Paid value
        int Paid = Integer.parseInt(cash.getText());

        Change =  Paid - gTotal;
        changeLabel.setText(String.valueOf(Change));
    }

}