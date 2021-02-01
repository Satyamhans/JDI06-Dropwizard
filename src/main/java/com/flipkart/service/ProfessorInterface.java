package com.flipkart.service;

import java.util.ArrayList;

import com.flipkart.bean.Course;
import com.flipkart.bean.Professor;
import com.flipkart.bean.Student;
import com.flipkart.constants.Grades;
import com.flipkart.exception.*;

public interface ProfessorInterface extends UserInterface
{
	/**
	 * To view the list of Enrolled Students for a given Course
	 * @param course course for which list is to be displayed
	 * @return List of enrolled students
	 */
	public ArrayList<Student> viewEnrolledStudents(Professor professor,Course course) throws UserCRSException;

	/**
	 * To grade the students for a particular course
	 * @param student  student to grade
	 * @param course  course for which grade is given
	 * @param grade  grade value
	 * @return boolean for successful/unsuccessful grade allotment
	 */
	public boolean gradeStudents(Professor professor,Student student, Course course, Grades grade) throws UserCRSException;

	/**
	 * To vie courses taken up by Professor in the semester
	 * @param professor for which the list needs to be displayed
	 * @return  List of courses
	 */
	public ArrayList<Course> viewCourses(Professor professor);						
}
