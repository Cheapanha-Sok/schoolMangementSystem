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
import java.lang.reflect.Executable;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;
import java.util.ResourceBundle;

public class facultyController implements Initializable {
    @FXML
    private TextField tfFacultyId;
    @FXML
    private Button btnRemove;
    @FXML
    private Button btnInsert;
    @FXML
    private Button btnUpdate;
    @FXML
    private TextField tfDeanName;

    @FXML
    private TextField tfFacultyName;

    @FXML
    private TextField tfOfficeNumber;
    @FXML
    private TableView<FacultyModel> facultyTable;

    @FXML
    private TableColumn<FacultyModel, String> deanName;

    @FXML
    private TableColumn<FacultyModel, String> facultyId;

    @FXML
    private TableColumn<FacultyModel, String> facultyName;

    @FXML
    private TableColumn<FacultyModel, String> officeNumber;

    @FXML
    private void btnHandleButton(ActionEvent event) {
        if (event.getSource() == this.btnInsert) {
            this.insertRecord();
        } else if (event.getSource() == this.btnUpdate) {
            this.updateRecord();
        } else if (event.getSource() == this.btnRemove) {
            this.removeRecord();
        }

    }

    public void initialize(URL url, ResourceBundle rb) {
        this.showFaculty();
    }

    public ObservableList<FacultyModel> getFacultyList() {
        ObservableList<FacultyModel> facultyModels = FXCollections.observableArrayList();
        databaseConnection databaseConnection = new databaseConnection();
        Connection connection = databaseConnection.getConnection();
        String query = "SELECT * FROM `Facultys`";

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                FacultyModel facultyModel = new FacultyModel(resultSet.getInt("facultyId")
                        , resultSet.getString("facultyName"),
                        resultSet.getString("deanName"),
                        resultSet.getInt("officeNumber"));
                facultyModels.add(facultyModel);
            }
        } catch (Exception i) {
            System.out.println("Error :" +i);
        }

        return facultyModels;
    }

    public void showFaculty() {
        ObservableList<FacultyModel> list = this.getFacultyList();
        this.facultyId.setCellValueFactory(new PropertyValueFactory<>("facultyId"));
        this.facultyName.setCellValueFactory(new PropertyValueFactory<>("facultyName"));
        this.deanName.setCellValueFactory(new PropertyValueFactory<>("deanName"));
        this.officeNumber.setCellValueFactory(new PropertyValueFactory<>("officeNumber"));
        this.facultyTable.setItems(list);
    }

    private void insertRecord() {
        String query = "INSERT INTO `Facultys`(`facultyId`,`facultyName`, `deanName`, `officeNumber`) VALUES ('" +
                this.tfFacultyId.getText()+"','"+
                this.tfFacultyName.getText() + "','" +
                this.tfDeanName.getText() + "','" +
                this.tfOfficeNumber.getText() + "')";
        this.executeQuery(query);
        this.setTextToEmpty();
        this.showFaculty();
    }

    private void updateRecord() {
        String query = "UPDATE `Facultys` SET " +
                "`facultyName` = '" + tfFacultyName.getText() + "', " +
                "`deanName` = '" + this.tfDeanName.getText() + "', " +
                "`officeNumber` = '" + this.tfOfficeNumber.getText() + "' " +
                "WHERE `facultyId` = " + this.tfFacultyId.getText();
        this.executeQuery(query);
        this.setTextToEmpty();
        this.showFaculty();
    }


    private void removeRecord(){
        String query = "DELETE FROM `Facultys` WHERE facultyId = " + this.tfFacultyId.getText();
        this.executeQuery(query);
        this.setTextToEmpty();
        this.showFaculty();

    }
    private void setTextToEmpty(){
        this.tfFacultyId.setText("");
        this.tfFacultyName.setText("");
        this.tfDeanName.setText("");
        this.tfOfficeNumber.setText("");
    }

    private void executeQuery(String query) {
        Connection connection = databaseConnection.getConnection();
        Statement statement;
        try {
            statement = connection.createStatement();
            statement.executeUpdate(query);
            showAlert("Completely","Your record have been saved", Alert.AlertType.INFORMATION);
        } catch (SQLException e) {
            showAlert("Error","Error :"+e.getMessage(), Alert.AlertType.ERROR);
        }
    }
    private void showAlert(String headerText ,String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle("Mange Faculty");
        alert.setHeaderText(headerText);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    private void handleMouseAction() {
        FacultyModel facultyModel = facultyTable.getSelectionModel().getSelectedItem();
        try {
            tfFacultyId.setText(String.valueOf(facultyModel.getFacultyId()));
            tfFacultyName.setText(facultyModel.getFacultyName());
            tfDeanName.setText(facultyModel.getDeanName());
            tfOfficeNumber.setText(String.valueOf(facultyModel.getOfficeNumber()));
        }catch (Exception e){
            showAlert("No Row Selected", "Please select a row before performing this action", Alert.AlertType.WARNING);
        }
    }

    public void btnClickBack(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("FXML/HomePage/MenuSite.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void btnClearRecord() {
        this.facultyTable.getSelectionModel().clearSelection();
        this.setTextToEmpty();
    }
}
