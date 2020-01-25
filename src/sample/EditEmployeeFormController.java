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

public class EditEmployeeFormController implements Initializable {

    private Controller parentController;
    private Employee selectedEmployee;
    private int Settings;

    @FXML private TextField employeeName;
    @FXML private TextField password;
    @FXML private ComboBox position;
    @FXML private ComboBox branch;

    private HashMap<String, String> AllPosition;
    private HashMap<String, String> AllBranch;

    @Override
    public void initialize(URL url, ResourceBundle rb){}

    public void initData(Controller parentController, Employee selectedEmployee, HashMap<String, String> allPosition, HashMap<String, String> allBranch, int settings){
        this.parentController = parentController;
        this.selectedEmployee = selectedEmployee;
        AllPosition = allPosition;
        AllBranch = allBranch;
        Settings = settings;
        if(Settings == 2){
            AllPosition.remove("Area Manager");
        } else if(Settings == 3){
            position.setDisable(true);
            branch.setDisable(true);
        }
        position.setItems(FXCollections.observableArrayList(AllPosition.keySet()));
        branch.setItems(FXCollections.observableArrayList(AllBranch.keySet()));
        // Set value for selectedEmployee
        employeeName.setText(this.selectedEmployee.getEmployeeName());
        password.setText(this.selectedEmployee.getPassword());
        position.setPromptText(this.selectedEmployee.getPosition());
        branch.setPromptText(this.selectedEmployee.getBranch());
    }

    @FXML
    public void editEmployee(ActionEvent event) throws SQLException {
        System.out.println("Edit_Employee_Button clicked in EditEmployeeForm.fxml");
        // Set ID for query
        String positionID;
        try {
            positionID = Database.getPositionID(position.getValue().toString());
        } catch (NullPointerException e){
            positionID =  Database.getPositionID(selectedEmployee.getPosition());
        }
        String branchID;
        try {
            branchID =  Database.getBranchID(branch.getValue().toString());
        } catch (NullPointerException e){
            branchID =  Database.getBranchID(selectedEmployee.getBranch());
        }

        // Query edits to the database
        Database.editEmployee(selectedEmployee.getEmployeeID(), employeeName.getText(), password.getText(), positionID, branchID);
        System.out.println(String.format("Edited %s at the employee", employeeName.getText()));

        // Close Stage & Refresh Table
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
        parentController.RefreshEmployeeTable();
    }
}