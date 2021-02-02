/**
 * 
 */
package com.flipkart.service;

import com.flipkart.bean.Course;

import com.flipkart.bean.Student;
import com.flipkart.constants.Grades;
import com.flipkart.dao.CourseDaoImplementation;
import com.flipkart.dao.CourseDaoInterface;
import com.flipkart.dao.StudentDaoImplementation;
import com.flipkart.dao.StudentDaoInterface;
import com.flipkart.exception.*;
import com.flipkart.validation.*;
import org.apache.log4j.Logger;
import com.flipkart.util.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class StudentOperation implements StudentInterface {
	private static volatile StudentOperation instance = null;
	private StudentOperation() {
	}

	public static StudentOperation getInstance() {
		if (instance == null) {
			synchronized (StudentOperation.class) {
				instance = new StudentOperation();
			}
		}
		return instance;
	}
	StudentDaoInterface studentDao = StudentDaoImplementation.getInstance();       //object of StudentDaoInterface
	CourseDaoInterface courseDao = CourseDaoImplementation.getInstance(); 
	NotificationSystem notificationSystem = new NotificationSystem();
	
	private static Logger logger = Logger.getLogger(StudentOperation.class);


	/**
	 * To register courses added by student
	 * @param student studentId
	 * @param courseList<Course> courselist of courses student wants to register for
	 * @return boolean for successful/unsuccessful registration
	 */
	@Override
	public synchronized List<Course> registerCourses(Student student, List<Course> courseList) throws UserCRSException{
		logger.info("Student is registering for courses");
		
		if(StudentValidationDao.isRegistered(student))throw new UserCRSException("You are already Registered, No modification allowed now!", student.getUserId());
		if(student.getCourseList().size() != 6)throw new UserCRSException("Not enough course selected, 6 Courses are required!", student.getUserId());
		
		CourseDaoInterface courseDao = CourseDaoImplementation.getInstance();
		List<Course> registrationCourseList = new ArrayList<Course>();
		
		for(Course course: courseList)
		{
			if(courseDao.getSeatCount(course) < 10) {
				registrationCourseList.add(course);
			}
			if(registrationCourseList.size()==4)
				break;
		}
		if(registrationCourseList.size() == 4) {
			List<Course> courses = studentDao.registerCourses(student, registrationCourseList);
			student.setCourseList(courses);
			student.setIsRegistered(true);
			PaymentService.calculateAmount(student);
			logger.info(notificationSystem.notifyStudent(student,'R'));
			return courses;      		//return course list.

		}
		return courseList;
	}

	/**
	 * To view the grades obtained by student during in the semester
	 * @param student studentId
	 * @return Map of course and the respective grades
	 */
	@Override
	public Map<Course, Grades> viewGrades(Student student) throws UserCRSException{
		logger.info("Student is viewing grades");
		
		if(!StudentValidationDao.isRegistered(student))throw new UserCRSException("Student is Not Registered yet!", student.getUserId());
		if(!StudentValidationDao.isReportGenerated(student))throw new UserCRSException("Report Card has not been generated yet!", student.getUserId());
		
		Map<Course, Grades> grades = new HashMap<Course, Grades>();		
		grades = studentDao.viewGrades(student);
		
		return grades;
	}

	/**
	 * Make payment for student
	 * @param student studentId
	 * @return boolean for successful/unsuccessful payment
	 */
	@Override
	public boolean makeFeePayment(Student student) throws UserCRSException{
		// TODO Auto-generated method stub
		logger.info("Student is paying fees");
		
		if(!StudentValidationDao.isRegistered(student))throw new UserCRSException("Student is Not Registered yet!", student.getUserId());
		if(StudentValidationDao.isHasPaidFee(student)) throw new UserCRSException("You have already paid the fees!", student.getUserId());
		
		boolean flag = studentDao.makeFeePayment(student);
		PaymentService.calculateAmount(student);
		if(flag) {
			logger.info(notificationSystem.notifyStudent(student,'P'));
		}
		else
		{
			logger.info(notificationSystem.notifyStudent(student,'F'));
		}
		
		return flag;//return status of payment.
	}

	/**
	 * To add courses that the student wants to register
	 * @param course course to be added
	 * @param student student to which course will be added
	 * @return	boolean for successful/unsuccessful addition
	 */
	@Override
	public boolean addCourses(Course course, Student student) throws UserCRSException {
		logger.info("Student is adding courses");
		
		if(StudentValidationDao.isRegistered(student))throw new UserCRSException("You are already Registered, No modification allowed now!", student.getUserId());
		if(student.getCourseList().size() == 6)throw new UserCRSException("You are already up with your Course List, Can't add more than 6 courses!!", student.getUserId());
		if(!courseDao.checkCourse(course))throw new UserCRSException("No Course exists for the Course Id: "+ course.getCourseId() + " !" , student.getUserId());
		if(this.hasThisCourse(student, course))throw new UserCRSException("Course with course Id: " + course.getCourseId() + " has already been added to your Course list, Not allowed!", student.getUserId());
		
		return student.getCourseList().add(course);			//return status
	}
	
	/**
	 * To fetch courseList of the student
	 * @param student student whose courseList has to be fetched
	 * @return return courseList
	 */
	
	public List<Course> getCourseList(Student student) throws UserCRSException{
		logger.info("Student is dropping courses");
		
		if(!StudentValidationDao.isRegistered(student)) {
			return student.getCourseList();
		}
		
		return studentDao.getCourseList(student);			//return status.
	}

	/**
	 * To drop a course by Student
	 * @param course course to be added
	 * @param student student to which course will be added
	 * @return boolean for successful/unsuccessful drop
	 */
	@Override
	public boolean dropCourses(Course course, Student student) throws UserCRSException{
		logger.info("Student is dropping courses");
		
		if(StudentValidationDao.isRegistered(student))throw new UserCRSException("You are already Registered, No modification allowed now!", student.getUserId());
		if(!this.hasThisCourse(student, course))throw new UserCRSException("Course with course Id: "+ course.getCourseId() + " has not been Selected yet, Not allowed!", student.getUserId());
		
		return student.getCourseList().remove(course);			//return status.
	}

	/**
	 * To check whether the student has exact 6 courses while registration
	 * @param student student for which courseList has to be checked
	 * @return return status
	 */

	private boolean hasThisCourse(Student student, Course course){
		List<Course> courseList = student.getCourseList();
		for(Course crs : courseList){
			if(crs.getCourseId().equals(course.getCourseId())){
				return true;
			}
		}
		return false;
	}
	
	
}
