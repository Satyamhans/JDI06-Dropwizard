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
    
    public User(String username, int userId, String emailId, String password, UserType userType) {
        this.username = username;
        this.userId = userId;
        this.emailId = emailId;
        this.password = password;
        this.userType = userType;
    }

    public String getUsername() {
        return username;
    }

    public void setUserName(String username) {
        this.username = username;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }
}
