package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class EmployeeFormController implements Initializable {

    private Controller parentController;

    private int prevEmployeeID;
    private String newEmployeeID;

    @FXML private TextField employeeName;
    @FXML private TextField password;
    @FXML private ComboBox position;
    @FXML private TextField branch;

    @Override
    public void initialize(URL url, ResourceBundle rb){}

    public void initData(Controller parentController, String prevEmployeeID){
        // Set parentController;
        this.parentController = parentController;
        // Remove alphabetic char and get integer value from latest customer/member
        this.prevEmployeeID = Integer.parseInt(prevEmployeeID.replaceAll("[^\\d.]", ""));
        // Make new EmployeeID
        newEmployeeID = String.format("EMP%05d", this.prevEmployeeID+1);
        System.out.println(newEmployeeID);
    }

    @FXML
    public void addEmployee(ActionEvent event) throws SQLException {
        System.out.println("Add_Employee_Button clicked in EmployeeForm.fxml");
        // Set ID for query
        String positionID = "";
        String branchID = "";

        if(position.getSelectionModel().equals("Area Manager")){
            positionID = "POS00001";
        } else if(position.getSelectionModel().equals("Branch Manager")){
            positionID = "POS00002";
        } else if(position.getSelectionModel().equals("Cashier")){
            positionID = "POS00003";
        }

        Database.addEmployee(newEmployeeID, employeeName.getText(), password.getText(), positionID, branchID);

        // Close Stage & Refresh Table
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
//        parentController.RefrestEmployeeTable();
    }

}