package com.example.schoolmanagement;

public class FacultyModel {
    private int facultyId;
    private String facultyName;
    private String deanName;
    private int officeNumber;

    public FacultyModel(int facultyId, String facultyName, String deanName, int officeNumber) {
        this.facultyId = facultyId;
        this.facultyName = facultyName;
        this.deanName = deanName;
        this.officeNumber = officeNumber;
    }
    public String getFacultyName() {
        return facultyName;
    }

    public String getDeanName() {
        return deanName;
    }

    public int getOfficeNumber() {
        return officeNumber;
    }
    public int getFacultyId() {
        return facultyId;
    }
}
