package com.flipkart.dao;

import java.util.ArrayList;

import com.flipkart.bean.Course;

public interface CourseDaoInterface 
{
	/**
     * To view the list of courses in course catalogue
     * @return List<Course> of course available in the course catalogue
     */
	public ArrayList<Course> viewCourseCatalogue();

	/**
	 * To fetch the seat count for a course
	 * @param course input course
	 * @return seat count
	 */
	public int getSeatCount(Course course);

	/**
	 * To check if the course exists in the Database
	 * @param course to check
	 * @return boolean
	 */
	public boolean checkCourse(Course course);
	
}
