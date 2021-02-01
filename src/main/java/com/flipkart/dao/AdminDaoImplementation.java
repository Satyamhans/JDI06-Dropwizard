package com.flipkart.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.flipkart.bean.*;
import com.flipkart.util.DBUtils;
import com.flipkart.validation.AdminValidationDao;
import org.apache.log4j.Logger;
import com.flipkart.constants.SQLQueriesConstants;

import com.flipkart.constants.UserType;

public class AdminDaoImplementation implements AdminDaoInterface{

	private static volatile AdminDaoImplementation instance = null;
	private AdminDaoImplementation() {
	}

	public static AdminDaoImplementation getInstance() {
		if (instance == null) {
			synchronized (AdminDaoImplementation.class) {
				instance = new AdminDaoImplementation();
			}
		}
		return instance;
	}

    private static Logger logger = Logger.getLogger(AdminDaoImplementation.class);
	private static Connection conn = null;

	/**
	 * To approve student accounts
	 * @param student userId is used to approve student accounts
	 * @return boolean for successful/unsuccessful approval of accounts
	 */
	@Override
	public boolean approveStudents(Student student){
		logger.debug("Admin is approving the student with ID: " + student.getUserId() );
		PreparedStatement stmt = null;
		try{
			conn = DBUtils.getConnection();
			stmt = conn.prepareStatement(SQLQueriesConstants.UPDATE_STUDENT_QUERY);
			stmt.setInt(1, student.getUserId());
			int row = stmt.executeUpdate();
			if(row==0) {
				logger.info("Student with ID : " + student.getUserId() + " does not exists.");
				return false;
			}
			else
				return true;
		}
		catch (SQLException ex){
			logger.error(ex.getMessage());
		}
		return false;
	}

	/**
	 * To approve students accounts through admin approval
	 * @param
	 * @return List<Student> is the list of students awaiting account approval by admin
	 */
	@Override
	public List<Student> viewStudentsToApprove() {
		logger.debug("Admin is viewing the list of student IDs which needs approval");
		List<Student> studentList = new ArrayList<Student>();
		PreparedStatement stmt = null;
		try{
			conn = DBUtils.getConnection();
			stmt = conn.prepareStatement(SQLQueriesConstants.VIEW_STUDENTS_TO_APPROVE);
			ResultSet rs = stmt.executeQuery(SQLQueriesConstants.VIEW_STUDENTS_TO_APPROVE);
			while(rs.next()){
				Student student = new Student();
				student.setUserId(rs.getInt(1));
				student.setUserName(rs.getString(2));
				studentList.add(student);
			}
		}
		catch(SQLException ex){
			logger.error(ex.getMessage());
		}
		return studentList;
	}

	/**
	 * To generate student report card
	 * @param student userId is used to generate report for a student
	 * @return boolean for successful/unsuccessful report card generation
	 */
	@Override
	public boolean generateReport(Student student) {
		logger.debug("Admin is generating the report card for the student with ID: " + student.getUserId());
		PreparedStatement stmt = null;
		try{
			boolean feeStatus = AdminValidationDao.checkFeeStatus(student.getUserId());
			if(feeStatus){
				conn = DBUtils.getConnection();
				stmt = conn.prepareStatement(SQLQueriesConstants.UPDATE_REPORT_STATUS);
				stmt.setInt(1, student.getUserId());
				int row = stmt.executeUpdate();
				return row != 0 ? true : false;
			}
		}
		catch (SQLException ex){
			logger.error(ex.getMessage());
		}
		return false;
	}



	/**
	 * To add a course in DB
	 * @param course is being created
	 * @return boolean for successful/unsuccessful addition of course
	 */
	@Override
	public boolean addCourseInCatalogue(Course course) {
		logger.debug("Admin is adding a new course to catalogue with courseID: " + course.getCourseId());
		PreparedStatement stmt = null;
		try {
			conn = DBUtils.getConnection();
			boolean courseExists = AdminValidationDao.checkCourse(course.getCourseId());
			if (courseExists)
				return false;
			stmt = conn.prepareStatement(SQLQueriesConstants.ADD_COURSE);
			stmt.setString(1, course.getCourseId());
			stmt.setString(2, course.getCourseName());
			stmt.setString(3, course.getCourseProfessor());
			stmt.setDouble(4, course.getCourseFee());
			stmt.setString(5, course.getCatalogueId());
			stmt.setString(6, course.getCourseDescription());
			stmt.setInt(7, course.getProfessorId());
			int rows = stmt.executeUpdate();
			if(rows == 1)
				return true;
			else {
				logger.info("Error with the input course values");
				return false;
			}
		}
		catch(SQLException ex){
			logger.error(ex.getMessage());
		}
		return false;
	}

	/**
	 * To remove a course from DB for a given courseId
	 * @param course courseId
	 * @return boolean for successful/unsuccessful removal
	 */
	@Override
	public boolean deleteCourseInCatalogue(Course course) {
		logger.debug("Admin is deleting a course from catalogue with courseID: " + course.getCourseId());
		PreparedStatement stmt = null;
		try {
			boolean courseExists = AdminValidationDao.checkCourse(course.getCourseId());
			if (!courseExists) {
				return false;
			}
			conn = DBUtils.getConnection();
			stmt = conn.prepareStatement(SQLQueriesConstants.DELETE_COURSE);
			stmt.setString(1, course.getCourseId());
			int rows = stmt.executeUpdate();
			if(rows == 1)
				return true;
			else {
				logger.info("Error while deleting the course");
				return false;
			}
		}
		catch(SQLException ex){
			logger.error(ex.getMessage());
		}
		return false;
	}



	/**
	 * To update a course in DB courseId
	 * @param course courseId
	 * @return boolean for successful/unsuccessful update of course
	 */
	@Override
	public boolean updateCourseInCatalogue(Course course) {
		logger.debug("Admin is updating course with ID: " + course.getCourseId());
		PreparedStatement stmt = null;
		try {
			boolean courseExists = AdminValidationDao.checkCourse(course.getCourseId());
			if (!courseExists)
				return false;
			conn = DBUtils.getConnection();
			stmt = conn.prepareStatement(SQLQueriesConstants.UPDATE_COURSE);
			stmt.setString(1, course.getCourseName());
			stmt.setDouble(2, course.getCourseFee());
			stmt.setString(3, course.getCourseProfessor());
			stmt.setString(4, course.getCourseDescription());
			stmt.setString(5, course.getCatalogueId());
			stmt.setInt(6, course.getProfessorId());
			stmt.setString(7, course.getCourseId());
			int rows = stmt.executeUpdate();
			if(rows == 1)
				return true;
			else {
				logger.info("Error with the input course values");
				return false;
			}
		}
		catch(SQLException ex){
			logger.error(ex.getMessage());
		}
		return true;
	}


	/**
	 * To add a user in DB
	 * @param user that is being created
	 * @return boolean for successful/unsuccessful add user
	 */
	public boolean addUser(User user){
		if(AdminValidationDao.checkUser(user)){
			return false;
		}
		else {
			if (UserType.ADMIN.equals(user.getUserType())) {
				return addAdmin((Admin) user);
			} else if (UserType.STUDENT.equals(user.getUserType()))
				return addStudent((Student) user);
			else
				return addProfessor((Professor) user);
		}
	}

	/**
	 * Helper function to add user to user table in DB
	 * @param user entity
	 * @return boolean
	 */
	private boolean addUserHelper(User user){
		logger.debug("Adding user in user table");
		PreparedStatement stmt = null;
		try{
			conn = DBUtils.getConnection();
			stmt = conn.prepareStatement(SQLQueriesConstants.ADD_USER);
			stmt.setString(1, user.getUsername());
			stmt.setString(2, user.getEmailId());
			stmt.setString(3, user.getPassword());

			if(UserType.ADMIN.equals(user.getUserType()))
				stmt.setString(4, "A");
			else if(UserType.STUDENT.equals(user.getUserType()))
				stmt.setString(4, "S");
			else
				stmt.setString(4, "P");

			int row = stmt.executeUpdate();
			if(row == 1){
				logger.debug("Added to user table successfully.");
				return true;
			}
			else {
				logger.debug("Unable to add user in the user table");
				return false;
			}
		}
		catch (SQLException ex){
			logger.error(ex.getMessage());
		}
		return false;

	}

	/**
	 * Helper function to fetch userid from DB
	 * @param email unique entry
	 * @return userid
	 */
	private int getUserId(String email){
		logger.debug("Fetching userID from database.");
		PreparedStatement stmt = null;
		int userId = -1;
		try{
			conn = DBUtils.getConnection();
			stmt = conn.prepareStatement(SQLQueriesConstants.FETCH_USERID);
			stmt.setString(1, email);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()){
				userId = rs.getInt(1);
			}
			return userId;
		}
		catch (SQLException ex){
			logger.error(ex.getMessage());
		}
		return userId;
	}

	/**
	 * Helper function to add new admin
	 * @param admin entity
	 * @return boolean
	 */
	private boolean addAdmin(Admin admin) {
		logger.debug("Admin is creating a new user of type: Admin");
		PreparedStatement stmt = null;
		try{
			boolean status = addUserHelper(admin);
			if(status) {
				int userId = getUserId(admin.getEmailId());
				if (userId == -1) {
					logger.debug("Unable to generate userID for new user.");
					return false;
				}
				else {
					logger.debug("Adding entry to admin table");
					conn = DBUtils.getConnection();
					stmt = conn.prepareStatement(SQLQueriesConstants.ADD_ADMIN);
					stmt.setInt(1, userId);
					stmt.setInt(2, userId);
					int row = stmt.executeUpdate();
					return row==1 ? true : false;
				}
			}
			else
				return false;
		}
		catch (SQLException ex){
			logger.error(ex.getMessage());
		}
		return false;
	}

	/**
	 * Helper function to add new student
	 * @param student entity
	 * @return boolean
	 */
	private boolean addStudent(Student student) {
		logger.debug("Admin is creating a new user of type: Student");
		PreparedStatement stmt = null;
		try{
			boolean status = addUserHelper(student);
			if(status) {
				int userId = getUserId(student.getEmailId());
				if (userId == -1) {
					logger.debug("Unable to generate userID for new user.");
					return false;
				}
				else {
					logger.debug("Adding entry to student table");
					conn = DBUtils.getConnection();
					stmt = conn.prepareStatement(SQLQueriesConstants.ADD_STUDENT);
					stmt.setInt(1, userId);
					stmt.setString(2, student.getBranch());
					stmt.setString(3, student.getGender());
					if(student.isHasScholarship())
						stmt.setString(4, "t");
					else
						stmt.setString(4, "f");
					stmt.setString(5, student.getAddress());
					int row = stmt.executeUpdate();
					return row==1 ? true : false;
				}
			}
			else
				return false;
		}
		catch (SQLException ex){
			logger.error(ex.getMessage());
		}
		return false;
	}

	/**
	 * Helper function to add new professor
	 * @param professor entity
	 * @return boolean
	 */
	private boolean addProfessor(Professor professor) {
		logger.debug("Admin is creating a new user of type: Professor");
		PreparedStatement stmt = null;
		try{
			boolean status = addUserHelper(professor);
			if(status) {
				int userId = getUserId(professor.getEmailId());
				if (userId == -1) {
					logger.debug("Unable to generate user ID for new user.");
					return false;
				}
				else {
					logger.debug("Adding entry to professor table");
					conn = DBUtils.getConnection();
					stmt = conn.prepareStatement(SQLQueriesConstants.ADD_PROFESSOR);
					stmt.setInt(1, userId);
					stmt.setString(2, professor.getGender());
					stmt.setString(3, professor.getDepartment());
					int row = stmt.executeUpdate();
					return row==1 ? true : false;
				}
			}
			else
				return false;
		}
		catch (SQLException ex){
			logger.error(ex.getMessage());
		}
		return false;
	}



	/**
	 * To remove a user from DB for a given userID
	 * @param user  userId
	 * @return boolean for successful/unsuccessful removal
	 */
	@Override
	public boolean removeUser(User user)
	{
		logger.debug("Admin is removing user with email ID: " + user.getEmailId());
		PreparedStatement stmt = null;
		try {
			boolean userExists = AdminValidationDao.checkUser(user);
			if (!userExists) {
				return false;
			}
			checkIfProfessor(user);
			conn = DBUtils.getConnection();
			stmt = conn.prepareStatement(SQLQueriesConstants.REMOVE_USER);
			stmt.setString(1, user.getEmailId());
			int rows = stmt.executeUpdate();
			if(rows == 1)
				return true;
			else {
				logger.info("Error while removing the user");
				return false;
			}
		}
		catch(SQLException ex){
			logger.error(ex.getMessage());
		}
		return false;
	}


	/**
	 * Check if user is a professor
	 * @param user entity
	 * @return boolean
	 */
	private boolean checkIfProfessor(User user){
		logger.debug("Admin is checking if the removed user is Professor for : " + user.getEmailId());
		PreparedStatement stmt = null;
		try{
			conn = DBUtils.getConnection();

			stmt = conn.prepareStatement(SQLQueriesConstants.CHECK_PROFESSOR);
			stmt.setString(1, user.getEmailId());
			ResultSet rs = stmt.executeQuery();
			String usertype = null;
			int userId = 0;
			while(rs.next()){
				usertype = rs.getString(1);
				userId = rs.getInt(2);
			}
			if(usertype.equals("P") || usertype.equals("p")){
				logger.debug("User to be removed is Professor.");
				logger.debug("Fetching courses taught by the Professor.");
				stmt = conn.prepareStatement(SQLQueriesConstants.GET_PROFESSOR_COURSES);
				stmt.setInt(1, userId);
				List<Course> courseList = new ArrayList<>();
				rs = stmt.executeQuery();
				while(rs.next()){
					Course course = new Course();
					course.setCourseId(rs.getString(1));
					courseList.add(course);
				}
				return setCourseNull(courseList);
			}
			else{
				return false;
			}
		}
		catch (SQLException ex){
			logger.error(ex.getMessage());
		}
		return false;
	}

	/**
	 * Sets the course to null for removed professor.
	 * @param courseList courses
	 * @return boolean
	 */
	private boolean setCourseNull(List<Course> courseList){
		if(courseList.size()==0) {
			logger.debug("No course is assigned to the professor.");
			return true;
		}
		PreparedStatement stmt = null;
		int rows =0;
		try{
			conn = DBUtils.getConnection();

			for(Course course : courseList){
				stmt = conn.prepareStatement(SQLQueriesConstants.SET_PROF_NULL);
				stmt.setString(1, course.getCourseId());
				rows += stmt.executeUpdate();
			}
			if(rows == courseList.size()){
				logger.debug("Courses set to null successfully");
				return true;
			}
			else{
				logger.error("Error occurred while setting professor to null");
				return false;
			}
		}
		catch (SQLException ex){
			logger.error(ex.getMessage());
		}
		return false;
	}
}
