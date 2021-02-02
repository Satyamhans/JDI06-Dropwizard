package com.flipkart.bean;

/**
 * @author pooja
 */
public class Notification {
	
		private String notificationMsg;
		private int userId;
		private char notificationType;
		private int notificationId;
		
		public Notification() {
			
		}
		
		/**
		 * @param notificationId
		 * @param notificationMsg
		 * @param userId
		 * @param notificationType
		 */
		public Notification(int notificationId, String notificationMsg, int userId, char notificationType){
			this.notificationId = notificationId;
			this.notificationMsg = notificationMsg;
			this.userId = userId;
			this.notificationType = notificationType ;
		}
		
		
		/**
		 * @return the notificationId
		 */
		public int getNotificationId() {
			return notificationId;
		}

		/**
		 * @param notificationId the notificationId to set
		 */
		public void setNotificationId(int notificationId) {
			this.notificationId = notificationId;
		}

		/**
		 * @return the notificationMsg
		 */
		public String getNotificationMsg() {
			return notificationMsg;
		}

		/**
		 * @param notificationMsg the notificationMsg to set
		 */
		public void setNotificationMsg(String notificationMsg) {
			this.notificationMsg = notificationMsg;
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
		 * @return the notificationType
		 */
		public char getNotificationType() {
			return notificationType;
		}

		/**
		 * @param notificationType the notificationType to set
		 */
		public void setNotificationType(char notificationType) {
			this.notificationType = notificationType;
		}		
		
}
