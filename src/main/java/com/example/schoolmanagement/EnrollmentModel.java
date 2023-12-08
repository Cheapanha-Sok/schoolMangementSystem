package com.example.schoolmanagement;

public class EnrollmentModel {
    private int studentId;
    private int departmentId;
    EnrollmentModel(int studentId , int departmentId){
        this.studentId = studentId;
        this.departmentId= departmentId;
    }

    public int getStudentId() {
        return studentId;
    }

    public int getDepartmentId() {
        return departmentId;
    }

}
