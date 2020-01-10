package sample;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class EditBranchFormController implements Initializable {

    private Controller parentController;
    private Branch selectedBranch;

    @FXML private TextField branchName;
    @FXML private TextArea address;
    @FXML private TextField phone;

    @Override
    public void initialize(URL url, ResourceBundle rb){}

    public void initData(Controller parentController, Branch selectedBranch){
        this.parentController = parentController;
        this.selectedBranch = selectedBranch;
        branchName.setText(this.selectedBranch.getBranchName());
        address.setText(this.selectedBranch.getAddress());
        phone.setText(this.selectedBranch.getPhone());
    }

    @FXML
    public void editBranch(ActionEvent event) throws SQLException {
        System.out.println("Edit_Branch_Button clicked in EditBranchForm.fxml");

        // Query edits to the database
        Database.editBranch(selectedBranch.getBranchID(), branchName.getText(), address.getText(), phone.getText());
        System.out.println(String.format("Edited %s at the branch", branchName.getText()));

        // Close Stage & Refresh Table
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
//        parentController.RefreshBranchTable();
    }
}
