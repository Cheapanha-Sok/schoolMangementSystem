package com.example.schoolmanagement;

public class CourseModels {
    private int courseId;
    private String courseName;
    private float credit;
    private String courseType;
    private int departmentId;

    // Contractor with 6 parameter
    public CourseModels(int courseId, String courseName, float credit, String courseType, int departmentId) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.credit = credit;
        this.courseType = courseType;
        this.departmentId = departmentId;
    }

    public int getCourseId() {
        return courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public float getCredit() {
        return credit;
    }

    public String getCourseType() {
        return courseType;
    }

    public int getDepartmentId() {
        return departmentId;
    }
}
