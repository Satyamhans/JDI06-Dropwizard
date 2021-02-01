package com.flipkart.bean;

import com.flipkart.constants.UserType;

import java.util.List;

import javax.validation.constraints.Pattern;

/**
 * @author pooja
 */
public class Professor extends User {

    String department;
    
    @Pattern(message = "Enter valid Gender, Valid genders are M & F!",regexp = "^[MF]$")
    String gender;
    
    List<Course> courseList;

    public Professor()
    {

    }

    public Professor(String username, int userId, String emailId, String password, UserType userType, String department, String gender, List<Course> courseList) {
        super(username, userId, emailId, password, userType);
        this.department = department;
        this.gender = gender;
        this.courseList = courseList;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public List<Course> getCourseList() {
        return courseList;
    }

    public void setCourseList(List<Course> courseList) {
        this.courseList = courseList;
    }
    
    public String toString() {
    	return userId+"\t"+username+"\t"+userType+"\t"+department+"\t"+gender;
    }

    public Professor(User user, String department, String gender) {
        super(user.username, user.userId, user.emailId, user.password, user.userType);
        this.department = department;
        this.gender = gender;
    }

}
