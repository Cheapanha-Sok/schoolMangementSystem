package com.example.schoolmanagement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class logInController {
    @FXML
    private TextField userName;
    @FXML
    private PasswordField password;
    @FXML
    private Label lableisValidated;

    public void onLoginButtonClick(ActionEvent event) throws IOException {
        String userName = this.userName.getText();
        String password = this.password.getText();

        if (userName.equals("Admin_1") && password.equals("pass123")){
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("FXML/Homepage/MenuSite.fxml")));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
         }
        lableisValidated.setText("Wrong username or password !");
        lableisValidated.setTextFill(Color.RED);
    }
}
