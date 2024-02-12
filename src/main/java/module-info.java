module com.example.schoolmanagement {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    requires mysql.connector.j;
    exports com.example.schoolmanagement;
    opens com.example.schoolmanagement to javafx.fxml;
}
