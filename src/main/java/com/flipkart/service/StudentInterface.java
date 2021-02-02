package com.flipkart.service;

import com.flipkart.bean.Course;
import com.flipkart.bean.Student;
import com.flipkart.constants.Grades;
import com.flipkart.exception.*;

import java.util.List;
import java.util.Map;

public interface StudentInterface extends UserInterface
{
	/**
	 * To register courses added by student
	 * @param student studentId
	 * @param courseList<Course> courselist of courses student wants to register for
	 * @return boolean for successful/unsuccessful registration
	 */
	public List<Course> registerCourses(Student student, List<Course> courseList) throws UserCRSException;

	/**
	 * To view the grades obtained by student during in the semester
	 * @param student studentId
	 * @return Map of course and the respective grades
	 */
	public Map<Course, Grades> viewGrades(Student student) throws UserCRSException;

	/**
	 * Make payment for student
	 * @param student studentId
	 * @return boolean for successful/unsuccessful payment
	 */
	public boolean makeFeePayment(Student student) throws UserCRSException;

	/**
	 * To add courses that the student wants to register
	 * @param course course to be added
	 * @param student student to which course will be added
	 * @return	boolean for successful/unsuccessful addition
	 */
	public boolean addCourses(Course course, Student student)  throws UserCRSException;

	/**
	 * To drop a course by Student
	 * @param course course to be added
	 * @param student student to which course will be added
	 * @return boolean for successful/unsuccessful drop
	 */
	public boolean dropCourses(Course course, Student student) throws UserCRSException;

	 /**
	 * To fetch courseList of the student
	 * @param student student whose courseList has to be fetched
	 * @return return courseList
	 */
    public List<Course> getCourseList(Student student) throws UserCRSException;


}
