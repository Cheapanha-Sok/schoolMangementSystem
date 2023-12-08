package com.example.schoolmanagement;

public class StudentModel {
    private int studentId;
    private String studentName;
    private String gender;
    private String dob;
    private String phoneNumber;
    private String address;
    private int generation;
    private int studentYear;
    private String degree;

    public StudentModel(int studentId, String studentName, String gender, String dob, String phoneNumber, String address, int generation, int studentYear, String degree) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.gender = gender;
        this.dob = dob;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.generation = generation;
        this.studentYear = studentYear;
        this.degree = degree;
    }

    public String getDegree() {
        return degree;
    }

    public int getStudentId() {
        return studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getGender() {
        return gender;
    }

    public String getDob() {
        return dob;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public int getGeneration() {
        return generation;
    }

    public int getStudentYear() {
        return studentYear;
    }
}
