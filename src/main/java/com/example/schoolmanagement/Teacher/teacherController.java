package com.example.schoolmanagement.Teacher;

import com.example.schoolmanagement.databaseConnection;
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
import java.sql.Statement;
import java.util.Objects;
import java.util.ResourceBundle;

public class teacherController implements Initializable {

    @FXML
    private TableColumn<TeacherModel, String> address;

    @FXML
    private Button btnInsert;

    @FXML
    private Button btnRemove;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<TeacherModel, String> dob;

    @FXML
    private TableColumn<TeacherModel, String> gender;

    @FXML
    private TableColumn<TeacherModel, String> phoneNumber;

    @FXML
    private TableColumn<TeacherModel, Integer> teacherId;

    @FXML
    private TableColumn<TeacherModel, String> teacherName;

    @FXML
    private TableView<TeacherModel> teacherTable;
    @FXML
    private TextField tfTeacherId;

    @FXML
    private TextField tfAddress;

    @FXML
    private TextField tfDob;

    @FXML
    private TextField tfGender;

    @FXML
    private TextField tfPhoneNumber;

    @FXML
    private TextField tfTeacherName;

    @FXML
    void btnClearRecord() {
        this.teacherTable.getSelectionModel().clearSelection();
        this.setTextToEmpty();
    }

    private void setTextToEmpty() {
        this.tfTeacherId.setText("");
        this.tfTeacherName.setText("");
        this.tfGender.setText("");
        this.tfDob.setText("");
        this.tfPhoneNumber.setText("");
        this.tfAddress.setText("");
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

    private void updateRecord() {
        String query = "UPDATE `Teachers` SET " +
                "`teacherName` = '" + tfTeacherName.getText() + "', " +
                "`gender` = '" + tfGender.getText() + "', " +
                "`dob` = '" + tfDob.getText() + "', " +
                "`phoneNumber` = '" + tfPhoneNumber.getText() + "', " +
                "`address` = '" + tfAddress.getText() + "' " +
                "WHERE `teacherId` = " + tfTeacherId.getText();
        this.executeQuery(query);
        this.setTextToEmpty();
        this.showTeacher();
    }

    private void insertRecord() {
        String query = "INSERT INTO `Teachers`(`teacherId`,`teacherName`,`gender`,`dob`, `phoneNumber`, `address`) VALUES ('" +
                this.tfTeacherId.getText()+"','"+
                this.tfTeacherName.getText() + "','" +
                this.tfGender.getText() + "','" +
                this.tfDob.getText() + "','" +
                this.tfPhoneNumber.getText() + "','" +
                this.tfAddress.getText() +  "')";
        this.executeQuery(query);
        this.setTextToEmpty();
        this.showTeacher();
    }

    private void removeRecord() {
        String query = "DELETE FROM `Teachers` WHERE `teacherId` = " + this.tfTeacherId.getText();
        this.executeQuery(query);
        this.setTextToEmpty();
        this.showTeacher();
    }

    @FXML
    void handleMouseAction() {
        TeacherModel teacherModel = teacherTable.getSelectionModel().getSelectedItem();
        try {
            this.tfTeacherId.setText(String.valueOf(teacherModel.getTeacherId()));
            this.tfTeacherName.setText(teacherModel.getTeacherName());
            this.tfGender.setText(teacherModel.getGender());
            this.tfDob.setText(teacherModel.getDob());
            this.tfPhoneNumber.setText(teacherModel.getPhoneNumber());
            this.tfDob.setText(teacherModel.getDob());
            this.tfAddress.setText(teacherModel.getAddress());
        }catch (Exception e){
            this.showAlert("No Row Selected", "Please select a row before performing this action", Alert.AlertType.WARNING);
        }

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
        alert.setTitle("Mange Teachers");
        alert.setHeaderText(headerText);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.showTeacher();
    }
    private void showTeacher(){
        ObservableList<TeacherModel> list = this.getTeacherList();
        this.teacherId.setCellValueFactory(new PropertyValueFactory<>("teacherId"));
        this.teacherName.setCellValueFactory(new PropertyValueFactory<>("teacherName"));
        this.gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        this.dob.setCellValueFactory(new PropertyValueFactory<>("dob"));
        this.phoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        this.address.setCellValueFactory(new PropertyValueFactory<>("address"));
        this.teacherTable.setItems(list);
    }

    public ObservableList<TeacherModel> getTeacherList() {
        ObservableList<TeacherModel> teacherModels = FXCollections.observableArrayList();
        databaseConnection databaseConnection = new databaseConnection();
        Connection connection = databaseConnection.getConnection();
        String query = "SELECT * FROM `Teachers`";

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                TeacherModel teacherModel  = new TeacherModel(
                        resultSet.getInt("teacherId"),
                        resultSet.getString("teacherName"),
                        resultSet.getString("gender"),
                        resultSet.getString("dob"),
                        resultSet.getString("phoneNumber"),
                        resultSet.getString("address"));
                teacherModels.add(teacherModel);
            }
        } catch (Exception i) {
            System.out.println("Error :" +i);
        }

        return teacherModels;
    }
}
