package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginPageController implements Initializable {

    private int PageSettings;
    private Controller MainController;

    @FXML private Label SignInLabel;
    @FXML private TextField UsernameTextField;
    @FXML private PasswordField PasswordField;
    @FXML private Button LoginButton;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        SignInLabel.setFont(Font.loadFont("file:src/fonts/cocoregular.ttf", 30));
        PageSettings = 1;
    }

    public void LoginPageCheckUser(Controller controller, Employee user){
        MainController = controller;
        SignInLabel.setText("Check Password");
        UsernameTextField.setText(user.getEmployeeName());
        UsernameTextField.setEditable(false);
        LoginButton.setText("Check");
        PageSettings = 2;
    }

    @FXML
    public void LoginButtonClicked(ActionEvent event) throws IOException, SQLException {
        System.out.println("LoginButton clicked on LoginPage");

        // Get Username & Password
        String username = UsernameTextField.getText();
        String password = PasswordField.getText();

        // Get Employee data from Database
        Employee employee = null;

        try{
            employee = Database.getEmployeeData(username, password);
        } catch (SQLException e){
            // Validation with alert box
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Wrong Username or Password!");
            alert.setContentText("Please input correct Username or Password!");

            alert.showAndWait();
            return;
        } catch (NullPointerException e){
            // Validation with alert box
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Null Username or Password!");
            alert.setContentText("Please input correct Username or Password!");

            alert.showAndWait();
            return;
        }

        if (PageSettings == 1) {
            // Loads the main page
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("MainScreen.fxml"));
            Parent MainPageParent = loader.load();

            // Passing object user to the MainPageController class
            Controller controller = loader.getController();
            controller.initData(employee);

            // Gets stage's info and setting it up
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setResizable(false);
            stage.setTitle("Main Screen");
            stage.setScene(new Scene(MainPageParent));
            stage.show();

            System.out.println(username + " has Logged In");
        } else {
            MainController.checkPassword = true;

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();

            System.out.println(username + " password is correct");
        }
    }
}