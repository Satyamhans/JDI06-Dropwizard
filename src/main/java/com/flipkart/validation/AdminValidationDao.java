package com.flipkart.validation;

import com.flipkart.bean.Course;
import com.flipkart.bean.User;
import com.flipkart.constants.SQLQueriesConstants;
import com.flipkart.dao.AdminDaoImplementation;
import com.flipkart.util.DBUtils;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminValidationDao {

    private static Logger logger = Logger.getLogger(AdminDaoImplementation.class);
    private static Connection conn = null;
    /**
     * Helper function to check fee status of the student
     * @param studentId for student
     * @return boolean
     */
    public static boolean checkFeeStatus(int studentId){
        logger.debug("Checking fee payment status for student ID: "+ studentId);
        PreparedStatement stmt = null;
        try{
            conn = DBUtils.getConnection();
            stmt = conn.prepareStatement(SQLQueriesConstants.CHECK_FEE_STATUS);
            stmt.setInt(1, studentId);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                String hasPaidFee = rs.getString(1);
                if (hasPaidFee.equals("t")) {
                    logger.debug("Fee is paid by the student : " + studentId);
                    return true;
                } else{
                    logger.debug("Fee not yet paid by student : " + studentId);
                    return false;
                }
            }
        }
        catch (SQLException ex){
            logger.error(ex.getMessage());
        }
        return false;
    }



    /**
     * Checks whether a particular course exists in the Database or not
     * @param courseId to check
     * @return boolean
     */
    public static boolean checkCourse(String courseId){
        logger.debug("Checking whether the course exists in the Database");
        PreparedStatement stmt = null;
        try{
            conn = DBUtils.getConnection();
            stmt = conn.prepareStatement(SQLQueriesConstants.CHECK_COURSE_EXISTS);
            stmt.setString(1, courseId);
            ResultSet rs = stmt.executeQuery();
            if(rs.next() == false) {
                logger.debug("No course with course ID: "+ courseId+ " exists.");
                return false;
            }
            else {
                logger.debug("Course with course ID: "+ courseId+ " exists.");
                return true;
            }
        }
        catch(SQLException ex){
            logger.error(ex.getMessage());
        }
        return false;
    }

    /**
     * Checks whether the user is present in the DB or not
     * @param user to check
     * @return boolean
     */
    public static boolean checkUser(User user){
        logger.debug("Checking whether the user exists in the Database");
        PreparedStatement stmt = null;
        try{
            conn = DBUtils.getConnection();
            stmt = conn.prepareStatement(SQLQueriesConstants.CHECK_USER);
            stmt.setString(1, user.getEmailId());
            ResultSet rs = stmt.executeQuery();
            if(rs.next() == false) {
                logger.debug("No user with email ID: "+ user.getEmailId()+ " exists.");
                return false;
            }
            else {
                logger.debug("User with email ID: "+ user.getEmailId()+ " exists.");
                return true;
            }
        }
        catch(SQLException ex){
            logger.error(ex.getMessage());
        }
        return false;
    }



}
