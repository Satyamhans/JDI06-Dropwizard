package com.flipkart.exception;

public class CourseCRSException extends Exception {

    private String msg;
    private String courseId;

    public CourseCRSException(String msg, String courseId) {
        this.msg = msg;
        this.courseId = courseId;
    }

    public String getMsg() {
        return msg;
    }

    public String getCourseId() {
        return courseId;
    }
}
