package com.flipkart.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.flipkart.service.StudentOperation;
import org.apache.log4j.Logger;
import com.flipkart.bean.Course;
import com.flipkart.util.DBUtils;
import com.flipkart.constants.SQLQueriesConstants;

public class CourseDaoImplementation implements CourseDaoInterface
{
	private static volatile CourseDaoImplementation instance = null;
	private CourseDaoImplementation() {
	}

	public static CourseDaoImplementation getInstance() {
		if (instance == null) {
			synchronized (CourseDaoImplementation.class) {
				instance = new CourseDaoImplementation();
			}
		}
		return instance;
	}
    private static Logger logger = Logger.getLogger(CourseDaoImplementation.class);
    Connection connection = DBUtils.getConnection();

	/**
	 * To view the list of courses in course catalogue
	 * @return List<Course> of course available in the course catalogue
	 */
	@Override
	public ArrayList<Course> viewCourseCatalogue() 
	{
		logger.debug("Getting course catalog");
		
		PreparedStatement stmt = null;
		// Returns a list of all the courses available
		ArrayList<Course> courses = new ArrayList<Course>();
		
		try {
			stmt = connection.prepareStatement(SQLQueriesConstants.VIEW_COURSE_CATALOGUE);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				Course course = new Course();
				course.setCourseId(rs.getString("courseId"));
				course.setCourseName(rs.getString("courseName"));
				course.setCourseProf(rs.getString("courseProfessor"));
				course.setCourseFee(rs.getDouble("courseFee"));
				course.setCatalogueId(rs.getString("catalogueId"));
				course.setCourseDescription(rs.getString("courseDescription"));
				course.setProfessorId(rs.getInt("professorId"));
				courses.add(course);
			}
		}catch(SQLException se){
			logger.error(se.getMessage());
		}catch(Exception e){
		  logger.error(e.getMessage());
		}
		
		return courses;
	}

	/**
	 * To fetch the seat count for a course
	 * @param course input course
	 * @return seat count
	 */
	@Override
    public int getSeatCount(Course course)
    {
		logger.debug("Fetching seat count for course : " + course.toString());
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(SQLQueriesConstants.GET_COURSE_SEATS);
			statement.setString(1,course.getCourseId());
			ResultSet resultSet = statement.executeQuery();
			return resultSet.getInt(1);
		}
		catch(SQLException se) {
			logger.error(se.getMessage());
		}
		catch(Exception e) {
			logger.error(e.getMessage());
		}
		
		return 0;    	
    }

	/**
	 * To check if the course exists in the Database
	 * @param course to check
	 * @return boolean
	 */
	public boolean checkCourse(Course course){
		logger.debug("Checking whether the course exists in the Database");
		PreparedStatement stmt = null;
		try{
			connection = DBUtils.getConnection();
			stmt = connection.prepareStatement(SQLQueriesConstants.CHECK_COURSE_EXISTS);
			stmt.setString(1, course.getCourseId());
			ResultSet rs = stmt.executeQuery();
			if(rs.next() == false) {
				logger.debug("No course with course ID: "+ course.getCourseId() +" exists.");
				return false;
			}
			else {
				logger.debug("Course with course ID: "+ course.getCourseId()+ " exists.");
				return true;
			}
		}
		catch(SQLException ex){
			logger.error(ex.getMessage());
		}
		return false;
	}
}
