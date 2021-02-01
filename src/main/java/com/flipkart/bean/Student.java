package com.flipkart.bean;

import com.flipkart.constants.UserType;

import java.util.List;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author pooja
 */
@XmlRootElement(name = "student")
public class Student extends User {
	
    private String branch;
    
    @Pattern(message = "Enter valid Gender, Valid genders are M & F!",regexp = "^[MF]$")
    private String gender;
    
    private boolean isApproved;
    private boolean isRegistered;
    private boolean hasScholarship;
    private boolean isReportGenerated;
    private double totalFees;
    private boolean hasPaidFee;
    
    @Size(min=0, max=6)
    private List<Course> courseList;
    
    @Size(min=1, max=50, message = "Address has to be atleast 1 character long and atmost 50 characters long")
    private String address;

    public Student()
    {
    
    }
    
    public Student(String username, int userId, String emailId, String password, UserType userType, String branch, String gender, boolean isApproved, boolean hasScholarship, boolean isReportGenerated, double totalFees, boolean hasPaidFee, List<Course> courseList, String address) {
        super(username, userId, emailId, password, userType);
        this.branch = branch;
        this.gender = gender;
        this.isApproved = isApproved;
        this.hasScholarship = hasScholarship;
        this.isReportGenerated = isReportGenerated;
        this.totalFees = totalFees;
        this.hasPaidFee = hasPaidFee;
        this.courseList = courseList;
        this.address = address;
    }

    public boolean isReportGenerated() {
        return isReportGenerated;
    }

    public void setReportGenerated(boolean reportGenerated) {
        isReportGenerated = reportGenerated;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public boolean isApproved() {
        return isApproved;
    }
    
    public boolean isRegistered() {
        return isRegistered;
    }

    public void setIsRegistered(boolean flag)
    {
    	isRegistered = flag;
    }
    
    public void setApproved(boolean approved) {
        isApproved = approved;
    }

    public boolean isHasScholarship() {
        return hasScholarship;
    }

    public void setHasScholarship(boolean hasScholarship) {
        this.hasScholarship = hasScholarship;
    }

    public double getTotalFees() {
        return totalFees;
    }

    public void setTotalFees(double totalFees) {
        this.totalFees = totalFees;
    }

    public boolean isHasPaidFee() {
        return hasPaidFee;
    }

    public void setHasPaidFee(boolean hasPaidFee) {
        this.hasPaidFee = hasPaidFee;
    }

    public List<Course> getCourseList() {
        return courseList;
    }

    public void setCourseList(List<Course> courseList) {
        this.courseList = courseList;
    }
    
    public String toString() {
    	return userId+"\t"+username+"\t"+userType;
    }

    public void setRegistered(boolean registered) {
        isRegistered = registered;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Student(User user, String branch, String gender, boolean hasScholarship, String address) {
        super(user.username, user.userId, user.emailId, user.password, user.userType);
        this.branch = branch;
        this.gender = gender;
        this.hasScholarship = hasScholarship;
        this.address = address;
    }

}
