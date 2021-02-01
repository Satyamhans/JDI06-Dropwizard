package com.flipkart.restController;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.flipkart.bean.Course;
import com.flipkart.bean.Professor;
import com.flipkart.bean.Student;
import com.flipkart.constants.Grades;
import com.flipkart.service.*;
import com.flipkart.exception.*;

/**
 * 
 * @author vikramc
 *
 */

@Path("/professorapi")
public class ProfessorRESTAPI {
	
	ProfessorInterface professorOperation = ProfessorOperation.getInstance(); 
	private static Logger logger = Logger.getLogger(StudentOperation.class);

	@POST
	@Path("/viewCourses")
	@Consumes("application/json")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Course> viewCourses(Professor professor)
	{
		List<Course>courseList = null;
		courseList = professorOperation.viewCourses(professor);
		return courseList;
	}
	
	@POST
	@Path("/viewEnrolledStudents/{courseId}")
	@Consumes("application/json")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Student> viewEnrolledStudents(Professor professor, @PathParam("courseId") String courseId)		// professor, course
	{
		Course course = new Course();
		course.setCourseId(courseId);
		
		List<Student> studentList = null;
		try {
			studentList = professorOperation.viewEnrolledStudents(professor, course);
		}
		catch(UserCRSException e) {
			logger.info(e.getMsg());
		}
		
		return studentList;
	}
	
	@POST
	@Path("/gradeStudents/{studentId}/{courseId}/{grade}")
	@Consumes("application/json")
	@Produces(MediaType.APPLICATION_JSON)
	public boolean gradeStudents(Professor professor, @PathParam("studentId") String studentId, @PathParam("courseId") String courseId, @PathParam("grade") char grade_to_assign) 	// professor, student, course, grade
	{
		Student student = new Student();
		int id = Integer.parseInt(studentId);
		student.setUserId(id);
		
		Course course = new Course();
		course.setCourseId(courseId);
		
		Grades grade;
		if(grade_to_assign == 'A')grade = Grades.A;
		else if(grade_to_assign == 'B')grade = Grades.B;
		else if(grade_to_assign == 'C')grade = Grades.C;
		else if(grade_to_assign == 'D')grade = Grades.D;
		else if(grade_to_assign == 'E')grade = Grades.E;
		else if(grade_to_assign == 'F')grade = Grades.F;
		else {
			grade = Grades.N;
		}
		
		boolean response = false;
		try{
			response = professorOperation.gradeStudents(professor, student, course, grade);
		}
		catch(UserCRSException e) {
			logger.info(e.getMsg());
		}
		
		return response;
	}
	
	@GET
	@Path("/viewCourseCatalogue")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Course> printCourseCatalogue() 
	{
		List<Course> courseCatalogue = professorOperation.viewCourseCatalogue();
		
		return courseCatalogue;
	}
}
