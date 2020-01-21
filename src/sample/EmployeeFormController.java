package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.ResourceBundle;

public class EmployeeFormController implements Initializable {

    private Controller parentController;

    private int prevEmployeeID;
    private String newEmployeeID;
    private int Settings;

    @FXML private TextField employeeName;
    @FXML private TextField password;
    @FXML private ComboBox position;
    @FXML private ComboBox branch;

    private HashMap<String, String> AllPosition;
    private HashMap<String, String> AllBranch;

    @Override
    public void initialize(URL url, ResourceBundle rb){}

    public void initData(Controller parentController, String prevEmployeeID, HashMap<String, String> allPosition,  HashMap<String, String> allBranch, int settings){
        // Set parentController;
        this.parentController = parentController;
        // Remove alphabetic char and get integer value from latest customer/member
        this.prevEmployeeID = Integer.parseInt(prevEmployeeID.replaceAll("[^\\d.]", ""));
        // Make new EmployeeID
        newEmployeeID = String.format("EMP%05d", this.prevEmployeeID+1);
        AllPosition = allPosition;
        AllBranch = allBranch;
        Settings = settings;
        // Branch Manager settings
        if(Settings == 2){
            AllPosition.remove("Area Manager");
        }
        position.setItems(FXCollections.observableArrayList(AllPosition.keySet()));
        branch.setItems(FXCollections.observableArrayList(AllBranch.keySet()));
        System.out.println(newEmployeeID);
    }

    @FXML
    public void addEmployee(ActionEvent event) throws SQLException {
        System.out.println("Add_Employee_Button clicked in EmployeeForm.fxml");
        // Set ID for query
        String positionID = Database.getPositionID(position.getValue().toString());
        String branchID =  Database.getBranchID(branch.getValue().toString());

        Database.addEmployee(newEmployeeID, employeeName.getText(), password.getText(), positionID, branchID);

        // Close Stage & Refresh Table
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
        parentController.RefreshEmployeeTable();
    }
}