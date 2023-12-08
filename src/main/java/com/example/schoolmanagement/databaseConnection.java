package com.example.schoolmanagement;

import java.sql.*;

public class databaseConnection {
    static Connection databaseLink;

   public static Connection getConnection(){
        String databaseName = "schoolmanagement"; // Avoid spaces in database names
        String databaseUser = "root";
        String databasePassword = "nha@061809946Q";
        String url = "jdbc:mysql://127.0.0.1:3306/" + databaseName;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            databaseLink = DriverManager.getConnection(url, databaseUser, databasePassword);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return databaseLink;
    }
}
