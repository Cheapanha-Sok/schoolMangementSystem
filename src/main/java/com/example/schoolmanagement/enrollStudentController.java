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

public class enrollStudentController implements Initializable {

    @FXML
    private Button btnInsert;

    @FXML
    private Button btnRemove;

    @FXML
    private TableColumn<EnrollmentModel,Integer> studentId;

    @FXML
    private TableColumn<EnrollmentModel, Integer> departmentId;

    @FXML
    private TableView<EnrollmentModel> enrollmentTable;

    @FXML
    private TextField tfDepartmentId;

    @FXML
    private TextField tfStudentId;

    @FXML
    void btnClearRecord() {
        this.enrollmentTable.getSelectionModel().clearSelection();
        this.setTextToEmpty();
    }

    private void setTextToEmpty() {
        this.tfStudentId.setText("");
        this.tfDepartmentId.setText("");
    }

    @FXML
    void btnHandleButton(ActionEvent event) {
        if (event.getSource() == this.btnInsert){
            this.insertRecord();
        } else if (event.getSource() == this.btnRemove) {
            this.removeRecord();
        }
    }
    public ObservableList<EnrollmentModel> getEnrollmentList() {
        ObservableList<EnrollmentModel> enrollmentModels = FXCollections.observableArrayList();
        databaseConnection databaseConnection = new databaseConnection();
        Connection connection = databaseConnection.getConnection();
        String query = "SELECT * FROM `Enrollment`";

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                EnrollmentModel enrollmentModel = new EnrollmentModel(resultSet.getInt("studentId"),
                        resultSet.getInt("departmentId"));
                enrollmentModels.add(enrollmentModel);
            }
        } catch (Exception i) {
            System.out.println("Error :" +i);
        }

        return enrollmentModels;
    }
    private void showEnrollStudent(){
        ObservableList<EnrollmentModel> list = this.getEnrollmentList();
        this.studentId.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        this.departmentId.setCellValueFactory(new PropertyValueFactory<>("departmentId"));
        this.enrollmentTable.setItems(list);
    }

    private void removeRecord() {
        String query = "DELETE FROM `Enrollment` WHERE studentId = " + tfStudentId.getText() + " AND departmentId = " + tfDepartmentId.getText();
        this.executeQuery(query);
        this.setTextToEmpty();
        this.showEnrollStudent();
    }


    private void insertRecord() {
        String query = "INSERT INTO `Enrollment`(`studentId`, `departmentId`) VALUES ('" +
                this.tfStudentId.getText() + "','" +
                this.tfDepartmentId.getText() + "')";
        this.executeQuery(query);
        this.setTextToEmpty();
        this.showEnrollStudent();
    }
    private void executeQuery(String query) {
        Connection connection = databaseConnection.getConnection();
        Statement statement;
        try {
            statement = connection.createStatement();
            statement.executeUpdate(query);
            this.showAlert("Completely","Your record have been saved", Alert.AlertType.INFORMATION);
        } catch (Exception e) {
            // Display an error alert
            this.showAlert("Error","Error :" + e.getMessage(), Alert.AlertType.ERROR);
        }
    }
    private void showAlert(String headerText ,String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle("Enrollment");
        alert.setHeaderText(headerText);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void handleMouseAction() {
        EnrollmentModel enrollmentModel = enrollmentTable.getSelectionModel().getSelectedItem();
        try{
            this.tfStudentId.setText(String.valueOf(enrollmentModel.getStudentId()));
            this.tfDepartmentId.setText(String.valueOf(enrollmentModel.getDepartmentId()));
        }catch (Exception e){
            this.showAlert("No Row Selected", "Please select a row before performing this action", Alert.AlertType.WARNING);
        }

    }
    @FXML
    private void btnClickBack(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("FXML/HomePage/MenuSite.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.showEnrollStudent();
    }
}

