package com.flipkart.service;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.flipkart.bean.Course;
import com.flipkart.bean.Student;
import com.flipkart.dao.CourseDaoImplementation;
import com.flipkart.dao.CourseDaoInterface;
import com.flipkart.dao.ProfessorDaoImplementation;
import com.flipkart.dao.ProfessorDaoInterface;

public class CourseOperation implements CourseInterface {
	private static volatile CourseOperation instance = null;
	private CourseOperation() {
	}

	public static CourseOperation getInstance() {
		if (instance == null) {
			synchronized (CourseOperation.class) {
				instance = new CourseOperation();
			}
		}
		return instance;
	}

	private static Logger logger = Logger.getLogger( CourseOperation.class);
	
	CourseDaoInterface courseDao = CourseDaoImplementation.getInstance();
	
	/**
     * To view the list of courses in course catalogue
     * @return List<Course> of course available in the course catalogue
     */
	public ArrayList<Course> viewCourseCatalogue(){
		
		logger.info("User is viewing courses");
		 
		ArrayList<Course> courses = courseDao.viewCourseCatalogue();
		
		return courses;
	}
	
	/**
	 * To get no of seats in a course
	 * @param course course
	 * @return count of seats in course
	 */
	public int getSeatCount(Course course) {
		
		logger.info("Getting count of seats in a course");
		
		return courseDao.getSeatCount(course);
		
	}
	
	/**
	 * To know if a course exist in database
	 * @return if a course exist or not
	 */
	public boolean checkCourse(Course course) {
		
		logger.info("Checking if the course exist");
		
		return courseDao.checkCourse(course);
	}
	
	
}
