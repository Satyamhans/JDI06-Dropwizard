package com.flipkart.dao;

import com.flipkart.bean.Course;
import com.flipkart.bean.Professor;
import com.flipkart.bean.Student;
import com.flipkart.constants.Grades;
import com.flipkart.constants.SQLQueriesConstants;
import com.flipkart.util.DBUtils;
import java.sql.*;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class ProfessorDaoImplementation implements ProfessorDaoInterface{

    private static volatile ProfessorDaoImplementation instance = null;
    private ProfessorDaoImplementation() {
    }

    public static ProfessorDaoImplementation getInstance() {
        if (instance == null) {
            synchronized (ProfessorDaoImplementation.class) {
                instance = new ProfessorDaoImplementation();
            }
        }
        return instance;
    }

    private static Logger logger = Logger.getLogger(ProfessorDaoImplementation.class);
    Connection connection = (Connection) DBUtils.getConnection();


    /**
     * To view the list of Enrolled Students for a given Course
     * @param course course for which list is to be displayed
     * @return List of enrolled students
     */
    @Override
    public ArrayList<Student> viewEnrolledStudents(Course course) {
        logger.debug("DAO : Professor is viewing students enrolled in course");
        ArrayList<Student> enrolledStudents = new ArrayList<>();
        try {
            PreparedStatement stmt = null;
            stmt = connection.prepareStatement(SQLQueriesConstants.VIEW_ENROLLED_STUDENTS);
            stmt.setString(1, course.getCourseId());
            ResultSet rs = stmt.executeQuery();

            while(rs.next())
            {
                Student student=new Student();
                student.setUserId(rs.getInt("studentId"));
                student.setUsername(rs.getString("username"));
                enrolledStudents.add(student);
            }
        }catch(SQLException se){
            logger.error(se.getMessage());
        }catch(Exception e){
            logger.error(e.getMessage());
        }
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
    public boolean gradeStudents(Student student, Course course, Grades grade) {
        logger.debug("DAO: Professor is grading students");
       try {
           PreparedStatement stmt = null;
           stmt = connection.prepareStatement(SQLQueriesConstants.GRADE_STUDENTS);
           char c=grade.toString().charAt(0);
           stmt.setString(1,c+"");
           stmt.setInt(2,student.getUserId());
           stmt.setString(3,course.getCourseId());
           
           int rows = stmt.executeUpdate();
           
           if(rows > 0)
        	   logger.debug("Inserted grade");
           else
        	   logger.error("Insertion into the database failed");
           
       }catch(SQLException se){
           logger.error(se.getMessage());
       }catch(Exception e){
           logger.error(e.getMessage());
       }
        logger.info(student.getUserId() + " " + course.getCourseId() + " " + grade);
        return true;
    }

    /**
     * To vie courses taken up by Professor in the semester
     * @param professor for which the list needs to be displayed
     * @return  List of courses
     */
    @Override
    public ArrayList<Course> viewCourses(Professor professor) {
        logger.debug("DAO: Professor is viewing courses");
        ArrayList<Course> signedCourses = new ArrayList<>();
        try{
            PreparedStatement stmt = null;
            stmt = connection.prepareStatement(SQLQueriesConstants.VIEW_PROFESSOR_COURSES);
            stmt.setInt(1, professor.getUserId());
            ResultSet rs=stmt.executeQuery();
            while(rs.next())
            {
                Course course=new Course();
                course.setCourseId(rs.getString("courseId"));
                course.setCourseName(rs.getString("courseName"));
                course.setCourseProf(rs.getString("courseProfessor"));
                course.setCourseFee(rs.getDouble("courseFee"));
                signedCourses.add(course);
            }

        }
        catch(SQLException se){
        logger.error(se.getMessage());
    }
        catch(Exception e){
        logger.error(e.getMessage());
    }
        return signedCourses;
    }
}
