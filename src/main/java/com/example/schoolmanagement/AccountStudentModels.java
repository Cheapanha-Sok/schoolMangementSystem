package com.example.schoolmanagement;

public class AccountStudentModels {
    private int accountId;
    private String password;
    private String username;
    private String phoneNumber;
    private int studentId;
    // Contractor with 5 parameter
    AccountStudentModels(int accountId, String username, String password, String phoneNumber , int studentId) {
        this.accountId = accountId;
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.studentId = studentId;
    }

    public int getAccountId() {
        return accountId;
    }

    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
    public int getStudentId() {
        return studentId;
    }
}
