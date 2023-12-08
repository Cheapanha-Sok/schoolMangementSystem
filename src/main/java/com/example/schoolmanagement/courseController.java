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

public class courseController implements Initializable {

    @FXML
    private Button btnInsert;

    @FXML
    private Button btnRemove;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<CourseModels, Integer> courseId;

    @FXML
    private TableColumn<CourseModels, String> courseName;

    @FXML
    private TableColumn<CourseModels, Float> credit;

    @FXML
    private TableColumn<CourseModels, Integer> departmentId;

    @FXML
    private TableView<CourseModels> courseTable;

    @FXML
    private TextField tfCourseName;

    @FXML
    private TextField tfCredit;

    @FXML
    private TextField tfDepartmentId;

    @FXML
    private TextField tfType;
    @FXML
    private TextField tfCourseId;

    @FXML
    private TableColumn<CourseModels, String> type;

    @FXML
    void btnClearRecord() {
        this.courseTable.getSelectionModel().clearSelection();
        this.setTextToEmpty();
    }

    private void setTextToEmpty() {
        this.tfCourseId.setText("");
        this.tfCourseName.setText("");
        this.tfCredit.setText("");
        this.tfType.setText("");
        this.tfDepartmentId.setText("");
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

    private void removeRecord() {
            String query = "DELETE FROM `Courses` WHERE `courseId` = " + tfCourseId.getText();
            this.executeQuery(query);
            this.showCourse();
            this.setTextToEmpty();
    }

    private void updateRecord() {
            String query = "UPDATE `Courses` SET `courseName` = '" + this.tfCourseName.getText() +
                    "', `credit` = " + this.tfCredit.getText() +
                    ", `type` = '" + this.tfType.getText() +
                    "', `departmentId` = " + this.tfDepartmentId.getText() +
                    " WHERE `courseId` = " + this.tfCourseId.getText();
            this.executeQuery(query);
            this.showCourse();
            this.setTextToEmpty();
        }

    private void insertRecord() {
        String query = "INSERT INTO `Courses`(`courseId`,`courseName`,`credit`,`type`,`departmentId`)VALUES ('" +
                this.tfCourseId.getText()+"','"+
                this.tfCourseName.getText()+"','" +
                this.tfCredit.getText() + "','" +
                this.tfType.getText() + "','" +
                this.tfDepartmentId.getText()+"')";
        this.executeQuery(query);
        this.showCourse();
        this.setTextToEmpty();
    }


    @FXML
    void handleMouseAction() {
        CourseModels courseModel = courseTable.getSelectionModel().getSelectedItem();
        try {
            this.tfCourseId.setText(String.valueOf(courseModel.getCourseId()));
            this.tfCourseName.setText(courseModel.getCourseName());
            this.tfCredit.setText(String.valueOf(courseModel.getCredit()));
            this.tfType.setText(courseModel.getCourseType());
            this.tfDepartmentId.setText(String.valueOf(courseModel.getDepartmentId()));
        } catch (Exception e) {
            this.showAlert("No Row Selected", "Please select a row before performing this action", Alert.AlertType.WARNING);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.showCourse();
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
        alert.setTitle("Mange Courses");
        alert.setHeaderText(headerText);
        alert.setContentText(message);
        alert.showAndWait();
    }
    public ObservableList<CourseModels> getCourseList() {
        ObservableList<CourseModels> courseModels = FXCollections.observableArrayList();
        databaseConnection databaseConnection = new databaseConnection();
        Connection connection = databaseConnection.getConnection();
        String query = "SELECT * FROM `Courses`";

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                CourseModels courseModel  = new CourseModels(
                        resultSet.getInt("courseId"),
                        resultSet.getString("courseName"),
                        resultSet.getFloat("credit"),
                        resultSet.getString("type"),
                        resultSet.getInt("departmentId"));
                courseModels.add(courseModel);
            }
        } catch (Exception i) {
            System.out.println("Error :" +i);
        }

        return courseModels;
    }
    private void showCourse(){
        ObservableList<CourseModels> list = this.getCourseList();
        this.courseId.setCellValueFactory(new PropertyValueFactory<>("courseId"));
        this.courseName.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        this.type.setCellValueFactory(new PropertyValueFactory<>("courseType"));
        this.credit.setCellValueFactory(new PropertyValueFactory<>("credit"));
        this.departmentId.setCellValueFactory(new PropertyValueFactory<>("departmentId"));
        this.courseTable.setItems(list);
    }
}
