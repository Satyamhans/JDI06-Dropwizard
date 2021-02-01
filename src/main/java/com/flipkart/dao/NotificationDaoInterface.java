package com.flipkart.dao;

import com.flipkart.bean.Notification;

public interface NotificationDaoInterface {


    /**
     * To add notification details in database
     * @param notification notification to be added
     * @return status of add notification
     */
	public boolean addNotification(Notification notification);

    /**
     * To get no of entries in database
     * @return status of add notification
     */
    public int noOfEntries();
	
    /**
     * To get transaction Id for a payment
     * @param studentId
     * @return
     */
    public int getTransactionId(int studentId);
}
