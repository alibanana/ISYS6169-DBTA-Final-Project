package sample;

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

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class OrderFormController implements Initializable {
    private Controller parentController;
    private ObservableList<Product> ProductList = FXCollections.observableArrayList();
    private ArrayList<String> ListofProductNames = new ArrayList<>();
    private Product selectedProduct;
    private Employee user;

    // Orders ID (prev and new)
    private int prevOrderID;
    private String newOrderID;

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

        // Set Order Date to Current Date
        orderDateLabel.setText(String.valueOf(LocalDate.now()));
        orderTimeLabel.setText(String.valueOf(LocalTime.now()));
    }

    public void initData(Controller parentController, String prevOrderID, ObservableList<Product> ProductList, Employee user) throws SQLException {
        this.parentController = parentController;
        this.ProductList = ProductList;
        this.prevOrderID = Integer.parseInt(prevOrderID.replaceAll("[^\\d.]", ""));
        this.user = user;

        bindProductName();

        // Make new CustomerID
        newOrderID = String.format("ORD%05d", this.prevOrderID+1);
        System.out.println("Order ID: " + newOrderID);

        // Set value
        cashierNameLabel.setText(user.getEmployeeName());
        branchLabel.setText(Database.getBranchName(user.getBranchID()));
        orderIDLabel.setText(newOrderID);
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
        try {
            System.out.println("AddItemButton clicked on OrderForm.fxml");
            System.out.println(selectedProduct.getProductID());
            SubOrderList.add(new SubOrder(SubOrderList.size() + 1, selectedProduct.getProductID(), selectedProduct.getProductName(), Integer.parseInt(qty.getText()), productDescription.getText(), selectedProduct.getPrice()));
            RefreshSubOrderTable();
            clearTextfields();
        } catch (NullPointerException e){
            // Validation with alert box
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Null Item!");
            alert.setContentText("Please input correct Items or enter Product Name text field after using its dropdown function!");

            alert.showAndWait();
        } catch (NumberFormatException e){
            // Validation with alert box
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Qty Has Wrong Format!");
            alert.setContentText("Please input correct format of Qty!");

            alert.showAndWait();
        }
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
        int gTotal = Integer.parseInt(grandTotalLabel.getText());
        // Getting Paid value
        int Paid = Integer.parseInt(cash.getText());

        Change =  Paid - gTotal;
        if(Change < 0){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Change Has Minus Format!");
            alert.setContentText("Please input correct paid value!");

            alert.showAndWait();
            return;
        }
        changeLabel.setText(String.valueOf(Change));
    }

    @FXML
    public void addOrder(ActionEvent event) throws SQLException{
        // Set Sub-Orders variables
        String OrderID;
        String ProductID;
        int Qty;
        String Description;

        LocalDateTime dateTime = LocalDate.parse(orderDateLabel.getText()).atTime(LocalTime.parse(orderTimeLabel.getText()));

        // SQL queries
        Database.addOrder(newOrderID, user.getEmployeeID(), dateTime, user.getBranchID(), Integer.valueOf(grandTotalLabel.getText()), Integer.valueOf(cash.getText()));
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
        SubOrderTable.setItems(SubOrderList);

        // Calculate subTotal
        int gTotal = 0;
        for (SubOrder suborder : SubOrderList) {
            gTotal += suborder.getQty() * suborder.getPrice();
        }

        // Set Labels;
        grandTotalLabel.setText(String.valueOf(gTotal));
    }
}