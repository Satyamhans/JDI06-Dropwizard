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
    
    public Admin(String username, int userId, String emailId, String password, UserType userType, int adminId) {
        super(username, userId, emailId, password, userType);
        this.adminId = adminId;
    }

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public Admin(User user, int adminId) {
        super(user.username, user.userId, user.emailId, user.password, user.userType);
        this.adminId = adminId;
    }

}
