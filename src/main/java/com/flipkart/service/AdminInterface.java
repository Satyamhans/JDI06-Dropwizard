package com.flipkart.service;

import com.flipkart.bean.Admin;
import com.flipkart.bean.Course;
import com.flipkart.bean.Student;
import com.flipkart.bean.User;
import com.flipkart.exception.CourseCRSException;
import com.flipkart.exception.UserCRSException;

import java.util.List;

public interface AdminInterface extends UserInterface
{
	/**
	 * To generate student report card
	 * @param student userId is used to generate report for a student
	 * @return boolean for successful/unsuccessful report card generation
	 */
    public boolean generateReport(Student student) throws UserCRSException;
    
    /**
	 * To approve student accounts
	 * @param student userId is used to approve student accounts
	 * @return boolean for successful/unsuccessful approval of accounts
	 */
    public boolean approveStudents(Student student) throws UserCRSException;
    
    /**
	 * To add a user in DB
	 * @param user that is being created
	 * @return boolean for successful/unsuccessful add user
	 */
    public boolean addUser(User user) throws UserCRSException;
    
    /**
	 * To remove a user from DB for a given userID
	 * @param user  userId
	 * @return boolean for successful/unsuccessful removal
	 */
    public boolean removeUser(User user) throws UserCRSException;
    
    /**
	 * To update a course in DB courseId
	 * @param course courseId
	 * @return boolean for successful/unsuccessful update of course
	 */
    public boolean updateCourseInCatalogue(Course course) throws CourseCRSException;
    
    /**
	 * To add a course in DB
	 * @param course is being created
	 * @return boolean for successful/unsuccessful addition of course
	 */
    public boolean addCourseInCatalogue(Course course) throws CourseCRSException;
    
    /**
	 * To remove a course from DB for a given courseId
	 * @param course courseId
	 * @return boolean for successful/unsuccessful removal
	 */
    public boolean deleteCourseInCatalogue(Course course) throws CourseCRSException;
    
    /**
	 * To approve students accounts through admin approval
	 * @param 
	 * @return List<Student> is the list of students awaiting account approval by admin
	 */
    public List<Student> viewStudentsToApprove();

}
