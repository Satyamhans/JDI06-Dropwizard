package com.flipkart.dao;

import com.flipkart.bean.Course;
import com.flipkart.bean.Professor;
import com.flipkart.bean.Student;
import com.flipkart.constants.Grades;

import java.util.ArrayList;

public interface ProfessorDaoInterface {

    /**
     * To view the list of Enrolled Students for a given Course
     * @param course course for which list is to be displayed
     * @return List of enrolled students
     */
    public ArrayList<Student> viewEnrolledStudents(Course course);

    /**
     * To grade the students for a particular course
     * @param student  student to grade
     * @param course  course for which grade is given
     * @param grade  grade value
     * @return boolean for successful/unsuccessful grade allotment
     */
    public boolean gradeStudents(Student student, Course course, Grades grade);

    /**
     * To vie courses taken up by Professor in the semester
     * @param professor for which the list needs to be displayed
     * @return  List of courses
     */
    public ArrayList<Course> viewCourses(Professor professor);
}
