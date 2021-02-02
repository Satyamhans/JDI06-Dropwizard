package com.flipkart.bean;

import com.flipkart.constants.UserType;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * @author pooja
 */
@XmlRootElement(name = "user")
public class User {
	
	@NotNull
	@Size(min = 3, max = 10, message = "The length of username should be between 3 to 10")
    String username;
	
	int userId;
    
    @NotNull
    @Pattern(message = "Invalid Email Address->" + "Valid emails:user@gmail.com or my.user@domain.com etc.",regexp = "^[a-zA-Z0-9_!#$%&�*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    String emailId;
    
    @NotNull
    @Pattern(message = "Password is too weak!",regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$")
    String password;
    
    UserType userType;

    public User()
    {
    	
    }
    
    /**
     * @param username
     * @param userId
     * @param emailId
     * @param password
     * @param userType
     */
    public User(String username, int userId, String emailId, String password, UserType userType) {
        this.username = username;
        this.userId = userId;
        this.emailId = emailId;
        this.password = password;
        this.userType = userType;
    }

    /**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the userId
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}

	/**
	 * @return the emailId
	 */
	public String getEmailId() {
		return emailId;
	}

	/**
	 * @param emailId the emailId to set
	 */
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the userType
	 */
	public UserType getUserType() {
		return userType;
	}

	/**
	 * @param userType the userType to set
	 */
	public void setUserType(UserType userType) {
		this.userType = userType;
	}

  
}
