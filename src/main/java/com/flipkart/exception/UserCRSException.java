package com.flipkart.exception;

public class UserCRSException extends Exception {

    private String msg;
    private int userId;

    public UserCRSException(String msg, int userId) {
        this.msg = msg;
        this.userId = userId;
    }

    public String getMsg() {
        return msg;
    }

    public int getUserId() {
        return userId;
    }
}
