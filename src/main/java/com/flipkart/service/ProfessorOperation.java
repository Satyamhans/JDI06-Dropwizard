package com.flipkart.service;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.flipkart.bean.Course;
import com.flipkart.bean.Professor;
import com.flipkart.bean.Student;
import com.flipkart.constants.Grades;
import com.flipkart.dao.ProfessorDaoImplementation;
import com.flipkart.dao.ProfessorDaoInterface;
import com.flipkart.exception.*;

public class ProfessorOperation implements ProfessorInterface
{
	private static volatile ProfessorOperation instance = null;
	private ProfessorOperation() {
	}

	public static ProfessorOperation getInstance() {
		if (instance == null) {
			synchronized (ProfessorOperation.class) {
				instance = new ProfessorOperation();
			}
		}
		return instance;
	}


	private static Logger logger = Logger.getLogger(ProfessorOperation.class);
	ProfessorDaoInterface professorDao = ProfessorDaoImplementation.getInstance();


	/**
	 * To view the list of Enrolled Students for a given Course
	 * @param course course for which list is to be displayed
	 * @return List of enrolled students
	 */
	@Override
	public ArrayList<Student> viewEnrolledStudents(Professor professor,Course course)  throws UserCRSException
	{	
		boolean courseCheck = false;
		for(Course professorCourse: professor.getCourseList())
		{
			if(professorCourse.getCourseId().equals(course.getCourseId()))
			{
				courseCheck = true;
			}
		}
		
		if(courseCheck == false)
			throw new UserCRSException("Course: " + course.getCourseId() + " is not in your Course List, Not allowed!", professor.getUserId());
		
		// Returns a list of students enrolled in a given course
		logger.info("Professor is viewing students enrolled in course");
		 
		ArrayList<Student> enrolledStudents = professorDao.viewEnrolledStudents(course);
		
		return enrolledStudents;
	}

	/**
	 * To grade the students for a particular course
	 * @param student  student to grade
	 * @param course  course for which grade is given
	 * @param grade  grade value
	 * @return boolean for successful/unsuccessful grade allotment
	 */
	@Override
	public boolean gradeStudents(Professor professor,Student student,Course course,Grades grade) throws UserCRSException
	{
		boolean courseCheck = false;
		for(Course professorCourse: professor.getCourseList())
		{
			if(professorCourse.getCourseId().equals(course.getCourseId()))
			{
				courseCheck = true;
			}
		}
		
		if(courseCheck == false)
			throw new UserCRSException("Course: " + course.getCourseId() + " is not in your Course List, Not allowed!", professor.getUserId());
		
		if(grade == Grades.N)
			throw new UserCRSException("Invalid Grade!", professor.getUserId());
		
		// Method to grade students enrolled in a course
		logger.info("Professor is grading students");
		
		return professorDao.gradeStudents(student,course,grade);
	}

	/**
	 * To vie courses taken up by Professor in the semester
	 * @param professor for which the list needs to be displayed
	 * @return  List of courses
	 */
	@Override
	public ArrayList<Course> viewCourses(Professor professor)
	{
		// Method to view the courses a professor is undertaking
		logger.info("Professor is viewing courses");
		
		ArrayList<Course> courses = professorDao.viewCourses(professor);
		
		return courses;
	}

}
