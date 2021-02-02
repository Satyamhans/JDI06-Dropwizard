package com.flipkart.validation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.flipkart.bean.Student;
import com.flipkart.constants.SQLQueriesConstants;
import com.flipkart.dao.StudentDaoImplementation;
import com.flipkart.util.DBUtils;

public class StudentValidationDao {

	
	private static Logger logger = Logger.getLogger(StudentDaoImplementation.class);
	private static Connection connection = DBUtils.getConnection();
	
	public static boolean isRegistered(Student student) {
		
		logger.info("inside student validation class");
		boolean flag = false;
		PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement(SQLQueriesConstants.GET_STUDENT_IS_REGISTERED_FLAG);
            stmt.setInt(1, student.getUserId());
            ResultSet rs = stmt.executeQuery();
            rs.next();
            if(rs.getString(1).equals("t"))  {
            	flag=true;
            }
            stmt.close();
 
        }
        catch(SQLException e){
            logger.error(e.getMessage());
            return flag;
        }
        return flag;
	}
	
	public static boolean isReportGenerated(Student student) {
		
		boolean flag = false;
		PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement(SQLQueriesConstants.GET_STUDENT_IS_REPORT_GENERATED_FLAG);
            stmt.setInt(1, student.getUserId());
            ResultSet rs = stmt.executeQuery();
            rs.next();
            if(rs.getString(1).equals("t")) {
            	flag=true;
            }
            stmt.close();
 
        }
        catch(SQLException e){
            logger.error(e.getMessage());
            return flag;
        }
        return flag;
	}
	
	public static boolean isHasPaidFee(Student student) {
		
		boolean flag = false;
		PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement(SQLQueriesConstants.GET_STUDENT_HAS_PAID_FEE_FLAG);
            stmt.setInt(1, student.getUserId());
            ResultSet rs = stmt.executeQuery();
            rs.next();
            if(rs.getString(1).equals("t"))  {
            	flag=true;
            }
            stmt.close();
 
        }
        catch(SQLException e){
            logger.error(e.getMessage());
            return flag;
        }
        return flag;
	}
}
