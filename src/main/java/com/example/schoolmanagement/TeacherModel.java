package com.example.schoolmanagement.Teacher;

public class TeacherModel {
    private int teacherId;
    private String teacherName;
    private String gender;
    private String dob;
    private String phoneNumber;
    private String address;

    // Contractor with 7 parameter
    public TeacherModel(int teacherId, String teacherName, String gender, String dob, String phoneNumber, String address ) {
        this.teacherId = teacherId;
        this.teacherName = teacherName;
        this.gender = gender;
        this.dob = dob;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public int getTeacherId() {
        return teacherId;
    }


    public String getTeacherName() {
        return teacherName;
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
}
