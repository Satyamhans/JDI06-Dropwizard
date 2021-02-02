package com.flipkart.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import com.flipkart.bean.Course;
import com.flipkart.constants.*;
import com.flipkart.bean.Student;
import com.flipkart.service.PaymentService;
import com.flipkart.service.StudentOperation;
import com.flipkart.util.DBUtils;
import org.apache.log4j.Logger;

import java.util.*;

public class StudentDaoImplementation implements StudentDaoInterface{
    private static volatile StudentDaoImplementation instance = null;
    private StudentDaoImplementation() {
    }

    public static StudentDaoImplementation getInstance() {
        if (instance == null) {
            synchronized (StudentDaoImplementation.class) {
                instance = new StudentDaoImplementation();
            }
        }
        return instance;
    }

    private static Logger logger = Logger.getLogger(StudentDaoImplementation.class);
    private static Connection connection = DBUtils.getConnection();


    /**
     * To register for the courses
     * @param student student who is going to register the courses
     * @param courseList list of all the courses student is going to register for
     * @return list of all the registered courses
     */
    @Override
    public List<Course> registerCourses(Student student, List<Course> courseList) {
        logger.debug("DAO: Student is registering for courses");

        //List of course objects
        List<Course> courses = new ArrayList<Course>();

        try {
            PreparedStatement stmt = null;
            for (Course course : courseList) {
                stmt = connection.prepareStatement(SQLQueriesConstants.ADD_COURSE_STUDENT_QUERY);
                stmt.setInt(1, student.getUserId());
                stmt.setString(2, course.getCourseId());
                stmt.setNull(3, 0);
                int rows = stmt.executeUpdate();
                stmt.close();
            }
            stmt = connection.prepareStatement(SQLQueriesConstants.SET_STUDENT_IS_REGISTERED_FLAG);
            stmt.setInt(1, student.getUserId());
            int flag = stmt.executeUpdate();
            stmt.close();
            stmt = connection.prepareStatement(SQLQueriesConstants.GET_REGISTERED_COURSES_QUERY);
            stmt.setInt(1, student.getUserId());
            ResultSet rs = stmt.executeQuery();
            Course course = new Course();
            while (rs.next()) {
                String courseId = rs.getString(1);
                stmt = connection.prepareStatement(SQLQueriesConstants.GET_COURSE_DETAILS_FROM_COURSETABLE);
                stmt.setString(1, courseId);
                ResultSet rst = stmt.executeQuery();
                while (rst.next()) {
                    course = new Course(courseId, rst.getString(1), rst.getString(2), rst.getDouble(3), rst.getString(4), rst.getString(5), rst.getInt(6));
                }
                courses.add(course);

            }
        }
        catch(SQLException e){
            logger.error(e.getMessage());
        }

        return courses;			//return list of courses registered
    }

    /**
     * To view the grades of a particular student
     * @param student student who is going to view the grades
     * @return map of all course-grades pair
     */
    @Override
    public Map<Course, Grades> viewGrades(Student student) {
        logger.debug("DAO: Student is viewing grades");
        Map<Course, Grades> grades = new HashMap<Course, Grades>();
        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement(SQLQueriesConstants.VIEW_GRADES_QUERY);
            stmt.setInt(1, student.getUserId());
            ResultSet rs = stmt.executeQuery();
            Course course = new Course();
            while (rs.next()) {
                String courseId = rs.getString(1);
                String grade = rs.getString(2);
                stmt = connection.prepareStatement(SQLQueriesConstants.GET_COURSE_DETAILS_FROM_COURSETABLE);
                stmt.setString(1, courseId);
                ResultSet rst = stmt.executeQuery();
                while (rst.next()) {
                    course = new Course(courseId, rst.getString(1), rst.getString(2), rst.getDouble(3), rst.getString(4), rst.getString(5),rst.getInt(6));
                }
                if(grade == null) {
                    grades.put(course, Grades.N);
                }
                else {
                    if (grade.equals("A")) {
                        grades.put(course, Grades.A);
                    } else if (grade.equals("B")) {
                        grades.put(course, Grades.B);
                    } else if (grade.equals("C")) {
                        grades.put(course, Grades.A);
                    } else if (grade.equals("D")) {
                        grades.put(course, Grades.D);
                    } else if (grade.equals("E")) {
                        grades.put(course, Grades.E);
                    } else if (grade.equals("F")) {
                        grades.put(course, Grades.F);
                    } else{
                        grades.put(course, Grades.N);
                    }
                }
            }
        }
        catch(SQLException e){
            logger.error(e.getMessage());
        }
        finally{
            try {
                stmt.close();
            }
            catch(Exception e){
                logger.error(e.getMessage());
            }
        }
        return grades;
    }


    /**
     * To make Fee Payment
     * @param student student who is going to make fee payment
     * @return boolean status of payment
     */
    @Override
    public boolean makeFeePayment(Student student) {
        logger.debug("DAO: Student is paying fees: " + PaymentService.getTotalFees(student));
        String paymentMode = "UPI";

        int flag1=0, flag2=0;
        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement(SQLQueriesConstants.MAKE_FEE_PAYMENT);
            stmt.setInt(1, student.getUserId());
            flag1 = stmt.executeUpdate();
            stmt.close();
            stmt = connection.prepareStatement(SQLQueriesConstants.UPDATE_PAYMENT_TABLE);
            stmt.setInt(1, student.getUserId());
            stmt.setDouble(2, student.getTotalFees());
            stmt.setString(3, paymentMode);
            flag2 = stmt.executeUpdate();
            stmt.close();
        }
        catch(SQLException e){
            logger.error(e.getMessage());
        }
        if(flag1 > 0  && flag2 > 0) {
            return true;
        }
        return false;
    }

    /**
     * To add a courses
     * @param course course that is going to be added
     * @param student student who is going to add the particular course
     * @return boolean confirmation for course addition
     */
    @Override
    public boolean addCourses(Course course, Student student){
        logger.debug("DAO: Student is adding courses");

        try {
            PreparedStatement stmt = null;
            stmt = connection.prepareStatement(SQLQueriesConstants.ADD_COURSE_STUDENT_QUERY);
            stmt.setInt(1, student.getUserId());
            stmt.setString(2, course.getCourseId());
            stmt.setNull(3, 0);
            int rows = stmt.executeUpdate();
            stmt.close();
            if (rows > 0) {
                return true;
            }
        }
        catch(SQLException e){
            logger.error(e.getMessage());
        }
        return false;
    }

    /**
     * To drop courses
     * @param course course that is going to be dropeed
     * @param student student who is going to drop the course
     * @return boolean confirmation for course dropping
     */
    @Override
    public boolean dropCourses(Course course, Student student) {
        logger.debug("DAO: Student is dropping courses");

        try {
            PreparedStatement stmt = null;
            stmt = connection.prepareStatement(SQLQueriesConstants.DROP_COURSE_STUDENT_QUERY);
            stmt.setString(1, course.getCourseId());
            stmt.setInt(2, student.getUserId());
            int rows = stmt.executeUpdate();
            stmt.close();
            if (rows > 0) {
                return true;
            }
        }
        catch(SQLException e){
            logger.error(e.getMessage());
        }
        return false;
    }
    @Override
    public List<Course> getCourseList(Student student){
    	List<Course> courseList = new ArrayList<Course>();
    	
    	PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement(SQLQueriesConstants.GET_REGISTERED_COURSES_QUERY);
            stmt.setInt(1, student.getUserId());
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
            	Course course = new Course();
            	course.setCourseId(rs.getString(1));
            	courseList.add(course);
            }
            stmt.close();
 
        }
        catch(SQLException e){
            logger.error(e.getMessage());
        }
        return courseList;
    }
    
}
