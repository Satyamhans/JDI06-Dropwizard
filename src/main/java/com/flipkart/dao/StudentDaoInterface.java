package com.flipkart.dao;

import com.flipkart.bean.Course;
import com.flipkart.bean.Student;
import com.flipkart.constants.Grades;

import java.util.List;
import java.util.Map;

public interface StudentDaoInterface {
	
	/**
	 * To register for the courses
	 * @param student student who is going to register the courses
	 * @param courseList list of all the courses student is going to register for 
	 * @return list of all the registered courses
	 */
	public List<Course> registerCourses(Student student, List<Course> courseList);

	
	/**
	 * To view the grades of a particular student
	 * @param student student who is going to view the grades
	 * @return map of all course-grades pair
	 */
    public Map<Course, Grades> viewGrades(Student student);

	/**
	 * To make Fee Payment
	 * @param student student who is going to make fee payment
	 * @return boolean status of payment
	 */
    public boolean makeFeePayment(Student student);

    /**
	 * To add a courses
	 * @param course course that is going to be added
	 * @param student student who is going to add the particular course
	 * @return boolean confirmation for course addition
	 */
    public boolean addCourses(Course course, Student student);

    /**
	 * To drop courses
	 * @param course course that is going to be dropeed
	 * @param student student who is going to drop the course
	 * @return boolean confirmation for course dropping
	 */
    public boolean dropCourses(Course course, Student student);

}
