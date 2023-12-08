package com.example.schoolmanagement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;
import java.util.ResourceBundle;

public class accountStudentController implements Initializable {
    @FXML
    private TableColumn<AccountStudentModels, String> password;
    @FXML
    private TableColumn<AccountStudentModels, Integer> accountId;

    @FXML
    private Button btnInsert;

    @FXML
    private Button btnRemove;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<AccountStudentModels, String> phoneNumber;

    @FXML
    private TableColumn<AccountStudentModels, Integer> studentId;

    @FXML
    private TableView<AccountStudentModels> accountTable;
    @FXML
    private TextField tfAccountId;

    @FXML
    private TextField tfPassword;

    @FXML
    private TextField tfPhoneNumber;

    @FXML
    private TextField tfStudentId;

    @FXML
    private TextField tfUsername;

    @FXML
    private TableColumn<AccountStudentModels, String> username;

    @FXML
    void btnClearRecord() {
        this.accountTable.getSelectionModel().clearSelection();
        this.setTextToEmpty();

    }

    private void setTextToEmpty() {
        this.tfUsername.setText("");
        this.tfPassword.setText("");
        this.tfPhoneNumber.setText("");
        this.tfStudentId.setText("");
        this.tfAccountId.setText("");
    }

    private void updateRecord() {

        String query = "UPDATE `AccountStudents` SET " +
                "`username` = '" + this.tfUsername.getText() + "', " +
                "`password` = '" + this.tfPassword.getText() + "', " +
                "`phoneNumber` = '" + this.tfPhoneNumber.getText() + "', " +
                "`studentId` = '" + this.tfStudentId.getText() + "' " +
                "WHERE `accountId` = " + this.tfAccountId.getText();
        this.executeQuery(query);
        this.setTextToEmpty();
        this.showAccount();
    }
    private void removeRecord() {
        String query = "DELETE FROM `AccountStudents` WHERE accountId = " + this.tfAccountId.getText();
        this.executeQuery(query);
        this.setTextToEmpty();
        this.showAccount();
    }

    private void insertRecord() {
        String query = "INSERT INTO `AccountStudents`(`accountId`,`username`,`password`,`phoneNumber`,`studentId`) VALUES ('" +
                this.tfAccountId.getText()+"','"+
                this.tfUsername.getText()+"','"+
                this.tfPassword.getText() + "','" +
                this.tfPhoneNumber.getText() + "','" +
                this.tfStudentId.getText() + "')";
        this.executeQuery(query);
        this.setTextToEmpty();
        this.showAccount();
    }
    private void executeQuery(String query) {
        Connection connection = databaseConnection.getConnection();
        Statement statement;
        try {
            statement = connection.createStatement();
            statement.executeUpdate(query);
            this.showAlert("Completely","Your record have been saved", Alert.AlertType.INFORMATION);
        } catch (SQLException e) {
            // Display an error alert
            this.showAlert("Error","Error :" +e.getMessage(), Alert.AlertType.ERROR);
        }
    }
    private void showAlert(String headerText ,String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle("Create Account Students");
        alert.setHeaderText(headerText);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private ObservableList<AccountStudentModels> getAccountList() {
        ObservableList<AccountStudentModels> accountModels = FXCollections.observableArrayList();
        databaseConnection databaseConnection = new databaseConnection();
        Connection connection = databaseConnection.getConnection();
        String query = "SELECT * FROM `AccountStudents`";

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                AccountStudentModels accountModel = new AccountStudentModels(
                        resultSet.getInt("accountId"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getString("phoneNumber"),
                        resultSet.getInt("studentId"));
                accountModels.add(accountModel);
            }
        } catch (Exception i) {
            System.out.println("Error :" +i);
        }

        return accountModels;
    }
    private void showAccount() {
        ObservableList<AccountStudentModels> list = this.getAccountList();
        this.accountId.setCellValueFactory(new PropertyValueFactory<>("accountId"));
        this.username.setCellValueFactory(new PropertyValueFactory<>("username"));
        this.password.setCellValueFactory(new PropertyValueFactory<>("password"));
        this.phoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        this.studentId.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        this.accountTable.setItems(list);
    }

    @FXML
    void btnClickBack(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("FXML/HomePage/MenuSite.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void btnHandleButton(ActionEvent event) {
        if (event.getSource() == this.btnInsert){
            this.insertRecord();
        } else if (event.getSource() == this.btnRemove) {
            this.removeRecord();
        } else if (event.getSource() == this.btnUpdate) {
            this.updateRecord();
        }
    }

    @FXML
    void handleMouseAction() {
        AccountStudentModels accountModel = accountTable.getSelectionModel().getSelectedItem();
        try {
            this.tfAccountId.setText(String.valueOf(accountModel.getAccountId()));
            this.tfUsername.setText(accountModel.getUsername());
            this.tfPassword.setText(accountModel.getPassword());
            this.tfPhoneNumber.setText(accountModel.getPhoneNumber());
            this.tfStudentId.setText(String.valueOf(accountModel.getStudentId()));
        }catch (Exception e){
            this.showAlert("No Row Selected", "Please select a row before performing this action", Alert.AlertType.WARNING);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.showAccount();
    }
}
