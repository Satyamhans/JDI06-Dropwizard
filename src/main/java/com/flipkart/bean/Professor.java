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

    /**
     * @param username
     * @param userId
     * @param emailId
     * @param password
     * @param userType
     * @param department
     * @param gender
     * @param courseList
     */
    public Professor(String username, int userId, String emailId, String password, UserType userType, String department, String gender, List<Course> courseList) {
        super(username, userId, emailId, password, userType);
        this.department = department;
        this.gender = gender;
        this.courseList = courseList;
    }


    /**
     * @param user
     * @param department
     * @param gender
     */
    public Professor(User user, String department, String gender) {
        super(user.username, user.userId, user.emailId, user.password, user.userType);
        this.department = department;
        this.gender = gender;
    }

    
    /**
	 * @return the department
	 */
	public String getDepartment() {
		return department;
	}

	/**
	 * @param department the department to set
	 */
	public void setDepartment(String department) {
		this.department = department;
	}

	/**
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * @param gender the gender to set
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}

	/**
	 * @return the courseList
	 */
	public List<Course> getCourseList() {
		return courseList;
	}

	/**
	 * @param courseList the courseList to set
	 */
	public void setCourseList(List<Course> courseList) {
		this.courseList = courseList;
	}

}
