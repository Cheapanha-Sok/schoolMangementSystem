package com.example.schoolmanagement;

public class AccountTeacherModels {
    private int accountId;
    private String username;
    private String password;
    private String phoneNumber;
    private int teacherId;
    AccountTeacherModels(int accountId, String username, String password, String phoneNumber , int teacherId) {
        this.accountId = accountId;
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.teacherId = teacherId;
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
    public int getTeacherId() {
        return teacherId;
    }

}
