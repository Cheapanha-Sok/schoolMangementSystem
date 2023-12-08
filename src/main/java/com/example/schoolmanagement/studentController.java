package com.example.schoolmanagement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

public class studentController implements Initializable {

    @FXML
    private Button btnInsert;

    @FXML
    private Button btnRemove;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<StudentModel, String> address;

    @FXML
    private TableColumn<StudentModel, String> degree;

    @FXML
    private TableColumn<StudentModel, String> dob;

    @FXML
    private TableView<StudentModel> studentTable;

    @FXML
    private TableColumn<StudentModel, String> gender;

    @FXML
    private TableColumn<StudentModel, Integer> generation;
    @FXML
    private TableColumn<StudentModel, Integer> phoneNumber;
    @FXML
    private TableColumn<StudentModel, Integer> studentId;

    @FXML
    private TableColumn<StudentModel, String> studentName;

    @FXML
    private TableColumn<StudentModel, Integer> studentYear;

    @FXML
    private TextField tfAddress;
    @FXML
    private TextField tfStudentId;

    @FXML
    private TextField tfDegree;

    @FXML
    private TextField tfDob;

    @FXML
    private TextField tfGender;

    @FXML
    private TextField tfGeneration;

    @FXML
    private TextField tfStudentName;

    @FXML
    private TextField tfStudentYear;
    @FXML
    private TextField tfPhoneNumber;

    @FXML
    private void btnClearRecord() {
        this.studentTable.getSelectionModel().clearSelection();
        this.setTextToEmpty();
    }

    private void setTextToEmpty() {
        this.tfStudentId.setText("");
        this.tfStudentName.setText("");
        this.tfGender.setText("");
        this.tfDob.setText("");
        this.tfPhoneNumber.setText("");
        this.tfAddress.setText("");
        this.tfGeneration.setText("");
        this.tfStudentYear.setText("");
        this.tfDegree.setText("");
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
        } else if (event.getSource() == this.btnUpdate) {
            this.updateRecord();
        } else if (event.getSource() == this.btnRemove) {
            this.removeRecord();
        }
    }

    private void updateRecord() {
            String query = "UPDATE `Students` SET " +
                    "`studentName` = '" + tfStudentName.getText() + "', " +
                    "`gender` = '" + tfGender.getText() + "', " +
                    "`dob` = '" + tfDob.getText() + "', " +
                    "`phoneNumber` = '" + tfPhoneNumber.getText() + "', " +
                    "`address` = '" + tfAddress.getText() + "', " +
                    "`generation` = '" + tfGeneration.getText() + "', " +
                    "`studentYear` = '" + tfStudentYear.getText() + "', " +
                    "`degree` = '" + tfDegree.getText() + "' " +
                    "WHERE `studentId` = " + tfStudentId.getText();
            this.executeQuery(query);
            this.setTextToEmpty();
            this.showStudent();
        }

    private void insertRecord() {
        String query = "INSERT INTO `Students`(`studentId`,`studentName`,`gender`,`dob`, `phoneNumber`, `address`,`generation`, `studentYear`, `degree`) VALUES ('" +
                this.tfStudentId.getText()+"','"+
                this.tfStudentName.getText() + "','" +
                this.tfGender.getText() + "','" +
                this.tfDob.getText() + "','" +
                this.tfPhoneNumber.getText() + "','" +
                this.tfAddress.getText() + "','" +
                this.tfGeneration.getText() + "','" +
                this.tfStudentYear.getText() + "','" +
                this.tfDegree.getText() + "')";
        this.executeQuery(query);
        this.setTextToEmpty();
        this.showStudent();
    }

    private void removeRecord() {
            String query = "DELETE FROM `Students` WHERE `studentId` = " + this.tfStudentId.getText();
            this.executeQuery(query);
            this.setTextToEmpty();
            this.showStudent();
        }
    public ObservableList<StudentModel> getStudentList() {
        ObservableList<StudentModel> studentModels = FXCollections.observableArrayList();
        databaseConnection databaseConnection = new databaseConnection();

        try (Connection connection = databaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM `Students`")) {

            while (resultSet.next()) {
                StudentModel studentModel = new StudentModel(
                        resultSet.getInt("studentId"),
                        resultSet.getString("studentName"),
                        resultSet.getString("gender"),
                        resultSet.getString("dob"),
                        resultSet.getString("phoneNumber"),
                        resultSet.getString("address"),
                        resultSet.getInt("generation"),
                        resultSet.getInt("studentYear"),
                        resultSet.getString("degree")
                );
                studentModels.add(studentModel);
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }

        return studentModels;
    }

    public void showStudent() {
        ObservableList<StudentModel> list = this.getStudentList();
        this.studentId.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        this.studentName.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        this.gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        this.dob.setCellValueFactory(new PropertyValueFactory<>("dob"));
        this.phoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        this.address.setCellValueFactory(new PropertyValueFactory<>("address"));
        this.generation.setCellValueFactory(new PropertyValueFactory<>("generation"));
        this.studentYear.setCellValueFactory(new PropertyValueFactory<>("studentYear"));
        this.degree.setCellValueFactory(new PropertyValueFactory<>("degree"));
        this.studentTable.setItems(list);
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
            this.showAlert("Error","Error :" +e.getMessage(), Alert.AlertType.ERROR);
        }
    }
    private void showAlert(String headerText ,String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle("Mange Students");
        alert.setHeaderText(headerText);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    void handleMouseAction() {
        StudentModel studentModel = studentTable.getSelectionModel().getSelectedItem();
        try {
            this.tfStudentId.setText(String.valueOf(studentModel.getStudentId()));
            this.tfStudentName.setText(studentModel.getStudentName());
            this.tfGender.setText(studentModel.getGender());
            this.tfDob.setText(studentModel.getDob());
            this.tfPhoneNumber.setText(studentModel.getPhoneNumber());
            this.tfAddress.setText(studentModel.getAddress());
            this.tfGeneration.setText(String.valueOf(studentModel.getGeneration()));
            this.tfStudentYear.setText(String.valueOf(studentModel.getStudentYear()));
            this.tfDegree.setText(studentModel.getDegree());
        }catch (Exception e){
            this.showAlert("No Row Selected", "Please select a row before performing this action", Alert.AlertType.WARNING);
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.showStudent();
    }
}
