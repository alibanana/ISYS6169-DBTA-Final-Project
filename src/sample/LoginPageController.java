package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginPageController implements Initializable {

    @FXML private Label SignInLabel;
    @FXML private TextField UsernameTextField;
    @FXML private PasswordField PasswordField;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        SignInLabel.setFont(Font.loadFont("file:src/fonts/cocoregular.ttf", 30));
    }

    @FXML
    public void LoginButtonClicked(ActionEvent event) throws IOException, SQLException {
        System.out.println("LoginButton clicked on LoginPage");

        // Get Username & Password
        String username = UsernameTextField.getText();
        String password = PasswordField.getText();

        // Get Employee data from Database
        Employee employee = Database.getEmployeeData(username, password);

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
    }

}
