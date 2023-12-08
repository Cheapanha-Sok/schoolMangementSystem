package com.example.schoolmanagement;

public class AssignCourseModels {
    private int teacherId;
    private int courseId;

    AssignCourseModels(int teacherId, int courseId){
        this.teacherId=teacherId;
        this.courseId=courseId;
    }
    public int getCourseId(){
        return courseId;
    }
    public int getTeacherId(){
        return teacherId;
    }
}
