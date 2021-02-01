package com.flipkart.service;

import java.util.ArrayList;

import com.flipkart.bean.Course;

public interface CourseInterface {

	/**
     * To view the list of courses in course catalogue
     * @return List<Course> of course available in the course catalogue
     */
	public ArrayList<Course> viewCourseCatalogue();
	
	/**
	 * To get no of seats in a course
	 * @param course course to count seat
	 * @return count of seats in course
	 */
	public int getSeatCount(Course course);
	
	/**
	 * To know if a course exist in database
	 * @return if a course exist or not
	 */
	public boolean checkCourse(Course course);
	
}
