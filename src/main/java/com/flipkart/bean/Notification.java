package com.flipkart.bean;

/**
 * @author pooja
 */
public class Notification {
		private int notificationId;
		private String notificationMsg;
		private int userId;
		private char notificationType;
		
		public Notification() {
			
		}
		
		public Notification(int notificationId, String notificationMsg, int userId, char notificationType){
			this.notificationId = notificationId;
			this.notificationMsg = notificationMsg;
			this.userId = userId;
			this.notificationType = notificationType ;
		}
			
		public int getNotificationId() {
			return notificationId;
		}
		public void setNotificationId(int notificationId) {
			this.notificationId = notificationId;
		}
		public String getNotificationMsg() {
			return notificationMsg;
		}
		public void setNotificationMsg(String notificationMsg) {
			this.notificationMsg = notificationMsg;
		}
		public int getUserId() {
			return userId;
		}
		public void setUserId(int userId) {
			this.userId = userId;
		}
		public char getNotificationType() {
			return notificationType;
		}
		public void setNotificationType(char notificationType) {
			this.notificationType = notificationType;
		}
		
}
