package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.TextFields;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ProductFormController implements Initializable {

    private Controller parentController;

    private int prevProductID;
    private String newProductID;

    @FXML private TextField productName;
    @FXML private TextField productPrice;

    @Override
    public void initialize(URL url, ResourceBundle rb){}

    public void initData(Controller parentController, String prevProductID){
        // Set parentController;
        this.parentController = parentController;
        // Remove alphabetic char and get integer value from latest customer/member
        this.prevProductID = Integer.parseInt(prevProductID.replaceAll("[^\\d.]", ""));
        // Make new CustomerID
        newProductID = String.format("PRO%05d", this.prevProductID+1);
        System.out.println(newProductID);
    }

    @FXML
    public void addProduct(ActionEvent event) throws SQLException {
        System.out.println("Add_Product_Button clicked in ProductForm.fxml");

        Database.addProduct(newProductID, productName.getText(), Integer.parseInt(productPrice.getText()));

        // Close Stage & Refresh Table
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
        parentController.RefreshProductTable();
    }

}