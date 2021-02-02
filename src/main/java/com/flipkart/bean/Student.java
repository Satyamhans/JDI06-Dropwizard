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
    
    /**
     * @param username
     * @param userId
     * @param emailId
     * @param password
     * @param userType
     * @param branch
     * @param gender
     * @param isApproved
     * @param hasScholarship
     * @param isReportGenerated
     * @param totalFees
     * @param hasPaidFee
     * @param courseList
     * @param address
     */
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

    /**
     * @param user
     * @param branch
     * @param gender
     * @param hasScholarship
     * @param address
     */
    public Student(User user, String branch, String gender, boolean hasScholarship, String address) {
        super(user.username, user.userId, user.emailId, user.password, user.userType);
        this.branch = branch;
        this.gender = gender;
        this.hasScholarship = hasScholarship;
        this.address = address;
    }
    
private String branch;
    
    /**
	 * @return the branch
	 */
	public String getBranch() {
		return branch;
	}

	/**
	 * @param branch the branch to set
	 */
	public void setBranch(String branch) {
		this.branch = branch;
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
	 * @return the isApproved
	 */
	public boolean isApproved() {
		return isApproved;
	}

	/**
	 * @param isApproved the isApproved to set
	 */
	public void setApproved(boolean isApproved) {
		this.isApproved = isApproved;
	}

	/**
	 * @return the isRegistered
	 */
	public boolean isRegistered() {
		return isRegistered;
	}

	/**
	 * @param isRegistered the isRegistered to set
	 */
	public void setIsRegistered(boolean isRegistered) {
		this.isRegistered = isRegistered;
	}

	/**
	 * @return the hasScholarship
	 */
	public boolean isHasScholarship() {
		return hasScholarship;
	}

	/**
	 * @param hasScholarship the hasScholarship to set
	 */
	public void setHasScholarship(boolean hasScholarship) {
		this.hasScholarship = hasScholarship;
	}

	/**
	 * @return the isReportGenerated
	 */
	public boolean isReportGenerated() {
		return isReportGenerated;
	}

	/**
	 * @param isReportGenerated the isReportGenerated to set
	 */
	public void setReportGenerated(boolean isReportGenerated) {
		this.isReportGenerated = isReportGenerated;
	}

	/**
	 * @return the totalFees
	 */
	public double getTotalFees() {
		return totalFees;
	}

	/**
	 * @param totalFees the totalFees to set
	 */
	public void setTotalFees(double totalFees) {
		this.totalFees = totalFees;
	}

	/**
	 * @return the hasPaidFee
	 */
	public boolean isHasPaidFee() {
		return hasPaidFee;
	}

	/**
	 * @param hasPaidFee the hasPaidFee to set
	 */
	public void setHasPaidFee(boolean hasPaidFee) {
		this.hasPaidFee = hasPaidFee;
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

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}


    
}
