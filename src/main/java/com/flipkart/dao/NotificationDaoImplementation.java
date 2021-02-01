package com.flipkart.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.flipkart.service.StudentOperation;
import org.apache.log4j.Logger;

import com.flipkart.bean.Course;
import com.flipkart.bean.Notification;
import com.flipkart.constants.SQLQueriesConstants;
import com.flipkart.util.DBUtils;

public class NotificationDaoImplementation implements NotificationDaoInterface {

	private static volatile NotificationDaoImplementation instance = null;
	private NotificationDaoImplementation() {
	}

	public static NotificationDaoImplementation getInstance() {
		if (instance == null) {
			synchronized (NotificationDaoImplementation.class) {
				instance = new NotificationDaoImplementation();
			}
		}
		return instance;
	}

	private static Logger logger = Logger.getLogger(NotificationDaoImplementation.class);
	
	Connection connection = DBUtils.getConnection();


	/**
	 * To add notification details in database
	 * @param notification notification to be added
	 * @return status of add notification
	 */
	public boolean addNotification(Notification notification) {

		logger.debug("Adding to database");
		PreparedStatement stmt = null;
		try {
			stmt = connection.prepareStatement(SQLQueriesConstants.ADD_NOTIFICATION);
			stmt.setInt(1, notification.getNotificationId());
			stmt.setString(2, notification.getNotificationMsg());
			stmt.setInt(3, notification.getUserId());
			stmt.setString(4, String.valueOf(notification.getNotificationType()));
			int response = stmt.executeUpdate();
			if (response > 0) {
				return true;
			}
			stmt.close();
		} catch (SQLException se) {
			logger.error(se.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return false;
	}

	/**
	 * To get no of entries in database
	 * @return status of add notification
	 */
	public int noOfEntries() {
		logger.debug("Getting count");
		PreparedStatement stmt = null;
		try {
			stmt = connection.prepareStatement(SQLQueriesConstants.GET_NO_OF_ROWS);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				return rs.getInt(1);
			}
			stmt.close();
		} catch (SQLException se) {
			logger.error(se.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return 1;
	}
	
	/**
     * To get transaction Id for a payment
     * @param studentId
     * @return
     */
	public int getTransactionId(int studentId) {
		logger.debug("Getting transactionId");

		PreparedStatement stmt = null;

		try {
			stmt = connection.prepareStatement(SQLQueriesConstants.GET_TRANSACTION_ID);
			stmt.setInt(1, studentId);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				return rs.getInt(1);
			}
			stmt.close();
		} catch (SQLException se) {
			logger.error(se.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		return 0;
	}
	
}
