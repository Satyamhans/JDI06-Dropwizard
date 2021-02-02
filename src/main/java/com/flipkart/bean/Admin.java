package com.flipkart.bean;

import com.flipkart.bean.User;
import com.flipkart.constants.UserType;

/**
 * @author pooja
 */
public class Admin extends User {

    private int adminId;   

	public Admin()
    {
    	
    }
    
    /**
     * @param username
     * @param userId
     * @param emailId
     * @param password
     * @param userType
     * @param adminId
     */
    public Admin(String username, int userId, String emailId, String password, UserType userType, int adminId) {
        super(username, userId, emailId, password, userType);
        this.adminId = adminId;
    }


    /**
     * @param user
     * @param adminId
     */
    public Admin(User user, int adminId) {
        super(user.username, user.userId, user.emailId, user.password, user.userType);
        this.adminId = adminId;
    }
    
    /**
   	 * @return the adminId
   	 */
   	public int getAdminId() {
   		return adminId;
   	}

   	/**
   	 * @param adminId the adminId to set
   	 */
   	public void setAdminId(int adminId) {
   		this.adminId = adminId;
   	}

}
