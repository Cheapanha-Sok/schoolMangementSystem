package com.example.schoolmanagement;

public class DepartmentModel {
    private int departmentId;
    private String departmentName;
    private String headName;
    private int officeNumber;
    private int facultyId;

    public DepartmentModel(int departmentId, String departmentName, String headName, int officeNumber , int facultyId) {
        this.departmentId=departmentId;
        this.departmentName = departmentName;
        this.headName = headName;
        this.officeNumber = officeNumber;
        this.facultyId = facultyId;
    }
    public int getDepartmentId() {
        return departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public String getHeadName() {
        return headName;
    }
    public int getOfficeNumber() {
        return officeNumber;
    }

    public int getFacultyId() {
        return facultyId;
    }
}
