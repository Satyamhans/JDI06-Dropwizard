package com.flipkart.exception;

public class UserNotFoundException extends Exception {
    int userId;
	  
	public UserNotFoundException(int userId) {
		
		this.userId = userId;
		
	}
	/**
	 * @return the userId
	 */
	public int getUserId() {
		
		return userId;
		
	}
}
