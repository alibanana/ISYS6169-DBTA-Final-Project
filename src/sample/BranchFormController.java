package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class BranchFormController implements Initializable {

    private Controller parentController;

    private int prevBranchID;
    private String newBranchID;

    @FXML private TextField branchName;
    @FXML private TextArea address;
    @FXML private TextField phone;

    @Override
    public void initialize(URL url, ResourceBundle rb){}

    public void initData(Controller parentController, String prevBranchID){
        // Set parentController;
        this.parentController = parentController;
        // Remove alphabetic char and get integer value from latest customer/member
        this.prevBranchID = Integer.parseInt(prevBranchID.replaceAll("[^\\d.]", ""));
        // Make new BranchID
        newBranchID = String.format("BRC%05d", this.prevBranchID+1);
        System.out.println(newBranchID);
    }

    @FXML
    public void addBranch(ActionEvent event) throws SQLException {
        System.out.println("Add_Branch_Button clicked in BranchForm.fxml");

        Database.addBranch(newBranchID, branchName.getText(), address.getText(), phone.getText());

        // Close Stage & Refresh Table
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
        parentController.RefreshBranchTable();
    }
}