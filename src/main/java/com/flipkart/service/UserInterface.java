package com.flipkart.service;

import com.flipkart.bean.Course;
import com.flipkart.dao.CourseDaoImplementation;
import com.flipkart.dao.CourseDaoInterface;

import java.util.ArrayList;
import java.util.List;

public interface UserInterface {

	/**
	 * common methods to be inherited into Student,Professor and Admin interfaces.
	 * @return arraylist of courses
	 */
	public default ArrayList<Course> viewCourseCatalogue()
    {
		CourseDaoInterface courseDao = CourseDaoImplementation.getInstance();
		ArrayList<Course> catalogue = courseDao.viewCourseCatalogue();
		
		return catalogue;
    }
}
