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
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;
import java.util.ResourceBundle;

public class assignCourseController implements Initializable {

    @FXML
    private TableView<AssignCourseModels> assignCourseTable;

    @FXML
    private Button btnInsert;

    @FXML
    private Button btnRemove;

    @FXML
    private TableColumn<AssignCourseModels, Integer> courseId;

    @FXML
    private TableColumn<AssignCourseModels, Integer> teacherId;

    @FXML
    private TextField tfCourseId;

    @FXML
    private TextField tfTeacherId;

    @FXML
    void btnClearRecord() {
        this.assignCourseTable.getSelectionModel().clearSelection();
        this.setTextToEmpty();
    }

    private void setTextToEmpty() {
        this.tfCourseId.setText("");
        this.tfTeacherId.setText("");
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
        }
    }

    private void removeRecord() {
        String query = "DELETE FROM `AssignCourse` WHERE teacherId = " + this.tfTeacherId.getText() + " AND courseId = " + this.tfCourseId.getText();
        this.executeQuery(query);
        this.setTextToEmpty();
        this.showAssignCourse();
    }
    private void insertRecord() {
        String query = "INSERT INTO `AssignCourse`(`teacherId`, `courseId`) VALUES ('" +
                this.tfTeacherId.getText() + "','" +
                this.tfCourseId.getText() + "')";
        this.executeQuery(query);
        this.setTextToEmpty();
        this.showAssignCourse();
    }
    public ObservableList<AssignCourseModels> getAssignCourse() {
        ObservableList<AssignCourseModels> assignCourseModels = FXCollections.observableArrayList();
        databaseConnection databaseConnection = new databaseConnection();
        Connection connection = databaseConnection.getConnection();
        String query = "SELECT * FROM `AssignCourse`";

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                AssignCourseModels assignCourseModel = new AssignCourseModels(
                        resultSet.getInt("teacherId"),
                        resultSet.getInt("courseId"));
                assignCourseModels.add(assignCourseModel);
            }
        } catch (Exception i) {
            System.out.println("Error :" +i);
        }

        return assignCourseModels;
    }
    private void showAssignCourse(){
        ObservableList<AssignCourseModels> list = this.getAssignCourse();
        this.teacherId.setCellValueFactory(new PropertyValueFactory<>("teacherId"));
        this.courseId.setCellValueFactory(new PropertyValueFactory<>("courseId"));
        this.assignCourseTable.setItems(list);
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
            this.showAlert("Error","Error while crud operation . try again ", Alert.AlertType.ERROR);
        }
    }
    private void showAlert(String headerText ,String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle("Assign Courses");
        alert.setHeaderText(headerText);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void handleMouseAction() {
        AssignCourseModels assignCourseModel = assignCourseTable.getSelectionModel().getSelectedItem();
        try{
            this.tfTeacherId.setText(String.valueOf(assignCourseModel.getTeacherId()));
            this.tfCourseId.setText(String.valueOf(assignCourseModel.getCourseId()));
        }catch(Exception e) {
            this.showAlert("No Row Selected", "Please select a row before performing this action", Alert.AlertType.WARNING);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.showAssignCourse();
    }
}
