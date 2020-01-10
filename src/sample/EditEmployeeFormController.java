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

public class EditEmployeeFormController implements Initializable {

    private Controller parentController;
    private Employee selectedEmployee;

    @FXML private TextField employeeName;
    @FXML private TextField password;
    @FXML private ComboBox position;
    @FXML private TextField branch;

    @Override
    public void initialize(URL url, ResourceBundle rb){}

    public void initData(Controller parentController, Employee selectedEmployee){
        this.parentController = parentController;
        this.selectedEmployee = selectedEmployee;
        employeeName.setText(this.selectedEmployee.getEmployeeName());
        password.setText(this.selectedEmployee.getPassword());
        // Position Code <><><>
        // Branch Code <><><>
    }

    @FXML
    public void editEmployee(ActionEvent event) throws SQLException {
        System.out.println("Edit_Employee_Button clicked in EditEmployeeForm.fxml");
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

        // Query edits to the database
        Database.editEmployee(selectedEmployee.getEmployeeID(), employeeName.getText(), password.getText(), positionID, branchID);
        System.out.println(String.format("Edited %s at the employee", employeeName.getText()));

        // Close Stage & Refresh Table
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
//        parentController.RefreshEmployeeTable();
    }
}
