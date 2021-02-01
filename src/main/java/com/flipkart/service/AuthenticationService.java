package com.flipkart.service;

import com.flipkart.bean.Admin;
import com.flipkart.bean.Professor;
import com.flipkart.bean.Student;
import com.flipkart.bean.User;
import com.flipkart.dao.AuthenticationDaoImplementation;
import org.apache.log4j.Logger;

public class AuthenticationService {
    private static volatile AuthenticationService instance = null;
    private AuthenticationService() {
    }

    public static AuthenticationService getInstance() {
        if (instance == null) {
            synchronized (AuthenticationService.class) {
                instance = new AuthenticationService();
            }
        }
        return instance;
    }

    private static Logger logger = Logger.getLogger(AuthenticationService.class);

    /**
     * To authenticate User
     * @param email
     * @param password
     * @return User object fetched by DB
     */
    public static User authUser(String email, String password){
        logger.debug("Authenticate User");

        return AuthenticationDaoImplementation.authUser(email,password);
    }

    /**
     * To authenticate Student
     * @param email
     * @param password
     * @return Student object fetched by DB
     */
    public static Student authStudent(String email, String password){
        logger.debug("Authenticate Student");

        return AuthenticationDaoImplementation.authStudent(email,password);
    }

    /**
     * To authenticate Professor
     * @param email
     * @param password
     * @return Professor object fetched by DB
     */
    public static Professor authProfessor(String email, String password){
        logger.debug("Authenticate Professor");

        return AuthenticationDaoImplementation.authProfessor(email,password);
    }

    /**
     * To authenticate Admin
     * @param email
     * @param password
     * @return Admin object fetched by DB
     */
    public static Admin authAdmin(String email, String password){
        logger.debug("Authenticate Admin");

        return AuthenticationDaoImplementation.authAdmin(email,password);
    }

    /**
     * To add new student
     * @param username
     * @param password
     * @param email
     * @param branch
     * @param gender
     * @return Boolean true or false based on whether the student is registered successfully or not.
     */
    public static boolean registerStudent(String username, String email, String password, String branch , String gender,String address){
        logger.debug("Register new student");

        return AuthenticationDaoImplementation.registerStudent( username, email,password,  branch ,  gender, address);
    }
}
