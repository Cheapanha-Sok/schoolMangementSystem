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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Objects;
import java.util.ResourceBundle;

public class departmentController implements Initializable {

    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnInsert;
    @FXML
    private Button btnRemove;

    @FXML
    private TextField tfDepartmentId;
    @FXML
    private TextField tfDepartmentName;
    @FXML
    private TextField tfHeadName;
    @FXML
    private TextField tfOfficeNumber;
    @FXML
    private TextField tfFacultyId;
    @FXML
    private TableView<DepartmentModel> departmentTable;
    @FXML
    private TableColumn<DepartmentModel, String> departmentId;
    @FXML
    private TableColumn<DepartmentModel, String> departmentName;
    @FXML
    private TableColumn<DepartmentModel, String> headName;
    @FXML
    private TableColumn<DepartmentModel, String> officeNumber;
    @FXML
    private TableColumn<DepartmentModel, String> facultyId;

    public ObservableList<DepartmentModel> getDepartmentList() {
        ObservableList<DepartmentModel> departmentModels = FXCollections.observableArrayList();
        databaseConnection databaseConnection = new databaseConnection();
        Connection connection = databaseConnection.getConnection();
        String query = "SELECT * FROM `Departments`";

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                DepartmentModel departmentModel  = new DepartmentModel(resultSet.getInt("departmentId")
                        , resultSet.getString("departmentName"),
                        resultSet.getString("headName"),
                        resultSet.getInt("officeNumber"),
                        resultSet.getInt("facultyId"));
                departmentModels.add(departmentModel);
            }
        } catch (Exception i) {
            System.out.println("Error :" +i);
        }

        return departmentModels;
    }
    private void showDepartment(){
        ObservableList<DepartmentModel> list = this.getDepartmentList();
        this.departmentId.setCellValueFactory(new PropertyValueFactory<>("departmentId"));
        this.departmentName.setCellValueFactory(new PropertyValueFactory<>("departmentName"));
        this.headName.setCellValueFactory(new PropertyValueFactory<>("headName"));
        this.officeNumber.setCellValueFactory(new PropertyValueFactory<>("officeNumber"));
        this.facultyId.setCellValueFactory(new PropertyValueFactory<>("facultyId"));
        this.departmentTable.setItems(list);
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.showDepartment();
    }

    public void btnClickBack(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("FXML/HomePage/MenuSite.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void btnHandleButton(ActionEvent event) {
        if (event.getSource() == this.btnInsert){
            this.insertRecord();
        } else if (event.getSource() == this.btnRemove) {
            this.removeRecord();
        } else if (event.getSource() == this.btnUpdate) {
            this.updateRecord();
        }

    }
    private void setTextToEmpty(){
        this.tfDepartmentId.setText("");
        this.tfDepartmentName.setText("");
        this. tfHeadName.setText("");
        this.tfOfficeNumber.setText("");
        this.tfFacultyId.setText("");
    }

    private void updateRecord() {
        String query = "UPDATE `Departments` SET " +
                "`departmentName` = '" + tfDepartmentName.getText() + "', " +
                "`headName` = '" + tfHeadName.getText() + "', " +
                "`officeNumber` = '" + tfOfficeNumber.getText() + "', " +
                "`facultyId` = '" + tfFacultyId.getText() + "' " +
                "WHERE `departmentId` = " + this.tfDepartmentId.getText();
        this.executeQuery(query);
        this.setTextToEmpty();
        this.showDepartment();
    }


    private void removeRecord() {
        String query = "DELETE FROM `Departments` WHERE `departmentId` = " + tfDepartmentId.getText();
        this.executeQuery(query);
        this.showDepartment();
        this.setTextToEmpty();
    }

    private void insertRecord(){
        String query = "INSERT INTO `Departments`(`departmentId`,`departmentName`,`headName`,`officeNumber`,`facultyId`)VALUES ('" +
                this.tfDepartmentId.getText()+"','" +
                this.tfDepartmentName.getText() + "','" +
                this.tfHeadName.getText() + "','" +
                this.tfOfficeNumber.getText() + "','"+
                this.tfFacultyId.getText()+"')";
        this.executeQuery(query);
        this.showDepartment();
        this.setTextToEmpty();
    }
    private void executeQuery(String query) {
        Connection connection = databaseConnection.getConnection();
        Statement statement;
        try {
            statement = connection.createStatement();
            statement.executeUpdate(query);
            this.showAlert("Completely","Your record have been saved", Alert.AlertType.INFORMATION);
        } catch (Exception e) {
            System.out.println(e);
            // Display an error alert
            this.showAlert("Error","Error :" +e.getMessage(), Alert.AlertType.ERROR);
        }
    }
    private void showAlert(String headerText ,String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle("Mange Departments");
        alert.setHeaderText(headerText);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void handleMouseAction() {
        DepartmentModel departmentModel = departmentTable.getSelectionModel().getSelectedItem();
        try {
            this.tfDepartmentId.setText(String.valueOf(departmentModel.getDepartmentId()));
            this.tfDepartmentName.setText(departmentModel.getDepartmentName());
            this.tfHeadName.setText(departmentModel.getHeadName());
            this.tfOfficeNumber.setText(String.valueOf(departmentModel.getOfficeNumber()));
            this.tfFacultyId.setText(String.valueOf(departmentModel.getFacultyId()));
        }catch (Exception e){
            this.showAlert("No Row Selected", "Please select a row before performing this action", Alert.AlertType.WARNING);
        }
    }

    public void btnClearRecord() {
        this.departmentTable.getSelectionModel().clearSelection();
        this.setTextToEmpty();

    }
}
