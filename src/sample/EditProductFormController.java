package sample;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.TextFields;

import javax.xml.crypto.Data;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class EditProductFormController implements Initializable {

    private Controller parentController;
    private Product selectedProduct;

    @FXML private TextField productName;
    @FXML private TextField productPrice;

    @Override
    public void initialize(URL url, ResourceBundle rb){}

    public void initData(Controller parentController, Product selectedProduct){
        this.parentController = parentController;
        this.selectedProduct = selectedProduct;
        productName.setText(this.selectedProduct.getProductName());
        productPrice.setText(String.valueOf(this.selectedProduct.getPrice()));
    }

    @FXML
    public void editProduct(ActionEvent event) throws SQLException {
        System.out.println("Edit_Product_Button clicked in EditProductForm.fxml");

        // Query edits to the database
        Database.editProduct(selectedProduct.getProductID(), productName.getText(), Integer.parseInt(productPrice.getText()));
        System.out.println(String.format("Edited %s at the products", productName.getText()));

        // Close Stage & Refresh Table
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
        parentController.RefreshProductTable();
    }

}
