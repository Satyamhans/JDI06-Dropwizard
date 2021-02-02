package com.flipkart.dao;

import com.flipkart.bean.Course;
import com.flipkart.bean.Student;
import com.flipkart.service.StudentOperation;
import com.flipkart.util.DBUtils;
import java.sql.*;
import com.flipkart.constants.*;
import org.apache.log4j.Logger;

import javax.naming.NamingSecurityException;
import java.util.List;

public class PaymentDaoImplementation {

	private static volatile PaymentDaoImplementation instance = null;
	private PaymentDaoImplementation() {
	}

	public static PaymentDaoImplementation getInstance() {
		if (instance == null) {
			synchronized (PaymentDaoImplementation.class) {
				instance = new PaymentDaoImplementation();
			}
		}
		return instance;
	}
	
	private static Logger logger = Logger.getLogger(PaymentDaoImplementation.class);
	private static Connection connection = DBUtils.getConnection();

	/**
	 * To calculate the total fees of a student for the registered courses
	 * @param student object
	 * @param amount
	 * @return total amount calculated for the student.
	 */
    public static double calculateAmount(Student student,double amount)
    {
        //Logger
        
        PreparedStatement statement = null;
        
        logger.debug("DAO: Calculating Amount");

        // Update amount in Student DB and object.
        try
        {
        	statement = connection.prepareStatement(SQLQueriesConstants.SET_STUDENT_FEE);
        	statement.setDouble(1, amount);
        	statement.setInt(2, student.getUserId());
        	int response = statement.executeUpdate();
        	
        	if(response > 0) {
        		logger.debug("Updated the student fee amount : " + amount);
        	}
        	else {
        		logger.debug("Student fee update failed");
        	}
        	
        	statement.close();
        }
        catch(SQLException e) {
        	logger.error(e.getMessage());
        }
        catch(Exception e) {
        	logger.error(e.getMessage());
        }
        finally
        {
        	try {
        		statement.close();
        	}
        	catch(Exception e) {
        		logger.error(e.getMessage());
        	}
        }
        return amount;
    }
    
    public static double getTotalFees(Student student) {
    	double amount = 0.0;
    	PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement(SQLQueriesConstants.GET_STUDENT_TOTAL_FEES);
            stmt.setInt(1, student.getUserId());
            ResultSet rs = stmt.executeQuery();
            rs.next();
            amount = rs.getDouble(1);
            logger.info(amount);
            stmt.close();
        }
        catch(SQLException e){
            logger.error(e.getMessage());
            return amount;
        }
//        logger.info(amount);
        return amount;
    }
}
