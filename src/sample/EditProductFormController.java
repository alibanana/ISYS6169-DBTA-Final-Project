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
    private String productID;
    private String PrevTypeID;

    @FXML private TextField productName;
    @FXML private TextField productType;
    @FXML private TextField productPrice;

    @Override
    public void initialize(URL url, ResourceBundle rb){}

    public void initData(Controller ParentController, Product selectedProduct) throws SQLException {
        parentController = ParentController;
        productID = selectedProduct.getProductID();
        PrevTypeID = Database.getTypeID(selectedProduct.getProductType());
        productName.setText(selectedProduct.getProductName());
        productType.setText(selectedProduct.getProductType());
        productPrice.setText(String.valueOf(selectedProduct.getPrice()));
        bindTypeTextFields();
    }

    private void bindTypeTextFields(){
        ArrayList<String> TypeLists = parentController.ProductTypeList;
        TypeLists.remove(0);
        TextFields.bindAutoCompletion(productType, TypeLists);
    }

    @FXML
    public void editProduct(ActionEvent event) throws SQLException {
        System.out.println("EditProductButton clicked on EditProductForm");

        // If ProductType does not exists
        if (!Database.isProductTypeExist(productType.getText())){
            // Make new TypeID
            int LastTypeIDInt = Integer.parseInt(Database.getLastTypeID().replaceAll("[^\\d.]", ""));
            String NewTypeID = String.format("TYP%05d", LastTypeIDInt+1);
            System.out.println(String.format("New TypeID = %s", NewTypeID));
            Database.addProductType(NewTypeID, productType.getText());
            parentController.RefreshProductFilter();
        }

        // Query edits to the database
        Database.editProduct(productID, productName.getText(), Database.getTypeID(productType.getText()), Integer.parseInt(productPrice.getText()));

        // Checks if previous TypeID still in products
        if (!(Database.isProductTypeExistInProduct(PrevTypeID))){
            Database.deleteProductTypeByID(PrevTypeID);
            parentController.RefreshProductFilter();
        }


        // Close Stage & Refresh Table
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
        parentController.RefreshProductTable();
    }

}
