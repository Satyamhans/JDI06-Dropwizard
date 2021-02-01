package com.flipkart.service;

import java.util.ArrayList;
import java.util.List;

import com.flipkart.bean.Admin;
import com.flipkart.constants.UserType;
import org.apache.log4j.Logger;

import com.flipkart.bean.Course;
import com.flipkart.bean.Professor;
import com.flipkart.bean.Student;
import com.flipkart.bean.User;
import com.flipkart.dao.AdminDaoImplementation;
import com.flipkart.dao.AdminDaoInterface;

public class AdminOperation implements AdminInterface
{
	private static volatile AdminOperation instance = null;
	private AdminOperation() {
	}

	public static AdminOperation getInstance() {
		if (instance == null) {
			synchronized (AdminOperation.class) {
				instance = new AdminOperation();
			}
		}
		return instance;
	}

	AdminDaoInterface adminDao = AdminDaoImplementation.getInstance();

	/**
	 * To generate student report card
	 * @param student userId is used to generate report for a student
	 * @return boolean for successful/unsuccessful report card generation
	 */
	@Override
	public boolean generateReport(Student student) 
	{
		return adminDao.generateReport(student);
	}

	/**
	 * To approve student accounts
	 * @param student userId is used to approve student accounts
	 * @return boolean for successful/unsuccessful approval of accounts
	 */
	@Override
	public boolean approveStudents(Student student)
	{	
		return adminDao.approveStudents(student);
	}

	/**
	 * To add a user in DB
	 * @param user that is being created
	 * @return boolean for successful/unsuccessful add user
	 */
	@Override
	public boolean addUser(User user)
	{
		return adminDao.addUser(user);
	}

	/**
	 * To remove a user from DB for a given userID
	 * @param user  userId
	 * @return boolean for successful/unsuccessful removal
	 */
	@Override
	public boolean removeUser(User user) 
	{	
		return adminDao.removeUser(user);
	}

	/**
	 * To update a course in DB courseId
	 * @param course courseId
	 * @return boolean for successful/unsuccessful update of course
	 */
	@Override
	public boolean updateCourseInCatalogue(Course course) 
	{
		return adminDao.updateCourseInCatalogue(course);
	}

	/**
	 * To add a course in DB
	 * @param course is being created
	 * @return boolean for successful/unsuccessful addition of course
	 */
	@Override
	public boolean addCourseInCatalogue(Course course) 
	{
		return adminDao.addCourseInCatalogue(course);
	}

	/**
	 * To remove a course from DB for a given courseId
	 * @param course courseId
	 * @return boolean for successful/unsuccessful removal
	 */
	@Override
	public boolean deleteCourseInCatalogue(Course course) 
	{
		return adminDao.deleteCourseInCatalogue(course);
	}

	/**
	 * To approve students accounts through admin approval
	 * @param
	 * @return List<Student> is the list of students awaiting account approval by admin
	 */
	@Override
	public List<Student> viewStudentsToApprove() 
	{
		return adminDao.viewStudentsToApprove();
	}
}
