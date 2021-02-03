/**
 * 
 */
package com.flipkart.restController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import com.flipkart.bean.Course;
import com.flipkart.bean.Student;
import com.flipkart.constants.Grades;
import com.flipkart.service.PaymentService;
import com.flipkart.service.StudentInterface;
import com.flipkart.service.StudentOperation;
import com.flipkart.exception.*;
/**
 * @author vikramc
 *
 */

@Path("/studentapi")
public class StudentRESTAPI 
{
	
	StudentInterface studentOperation = StudentOperation.getInstance();
	private static Logger logger = Logger.getLogger(StudentOperation.class);
	
	
	
	/**
	 * To add course to the list of registered courses
	 * @param student
	 * @param courseId
	 * @return response
	 */
	@POST
	@Path("/addCourse/{courseId}")
	@Consumes("application/json")
	@Produces(MediaType.APPLICATION_JSON)
	public Response addCourse(Student student, @PathParam("courseId") String courseId) 			//Student , Course 
	{
		Course course = new Course();
		course.setCourseId(courseId);
//		
		System.out.println(student.toString() + " ______ " + courseId);
		for(Course course1:student.getCourseList())
			System.out.println(course1);
		
//		boolean response = false;
		List<Course> response = null;
		try{
			studentOperation.addCourses(course, student);
			response = student.getCourseList();
		}
		catch(UserCRSException e) {
			logger.info(e.getMsg());
			return Response.status(500).entity(e.getMsg()).build();
		}
		return Response.status(200).entity(student).build();
	}
	
	
	
	
	/**
	 * To drop a course from the list of registered courses.
	 * @param student
	 * @param courseId
	 * @return response
	 */
	@POST
	@Path("/dropCourse/{courseId}")								//Student , Course 
	@Consumes("application/json")
	@Produces(MediaType.APPLICATION_JSON)
	public Response dropCourse(Student student, @PathParam("courseId") String courseId) 
	{
		Course course = new Course();
		course.setCourseId(courseId);
		
		List<Course> response = null;
		try{
			studentOperation.dropCourses(course, student);
			response = student.getCourseList();
		}
		catch(UserCRSException e) {
			logger.info(e.getMsg());
			return Response.status(500).entity(e.getMsg()).build();
		}
		
		return Response.status(200).entity(student).build();
	}
	
	
	
	/**
	 * To view grades of a student.
	 * @param student
	 * @return response
	 */
	@POST
	@Path("/viewGrades")
	@Consumes("application/json")
	@Produces(MediaType.APPLICATION_JSON)
	public Response viewGrades(Student student) 
	{
		Map<Course, Grades> reportCard = null;
		
		try{
			reportCard = studentOperation.viewGrades(student);
		}
		catch(UserCRSException e) {
			logger.info(e.getMsg());
			return Response.status(500).entity(e.getMsg()).build();
		}
		// think about returning Map<String, Grade> 
		return Response.status(200).entity(reportCard).build();
	}
	
	
	
	/**
	 * To view the course catalogue
	 * @return List of courses in the catalogue
	 */
	@GET
	@Path("/viewCourseCatalogue")
	@Produces(MediaType.APPLICATION_JSON)
	public Response viewCourseCatalogue() 
	{
		List<Course> courseCatalogue = studentOperation.viewCourseCatalogue();
		
		return Response.status(200).entity(courseCatalogue).build();
	}
	
	
	
	/**
	 * To view the registered courses
	 * @param student
	 * @return List of courses
	 */
	@POST
	@Path("/viewRegisteredCourses")
	@Consumes("application/json")
	@Produces(MediaType.APPLICATION_JSON)
	public Response viewRegisteredCourses(Student student) 
	{
		List<Course> courseList = null;
		try{
			courseList = studentOperation.getCourseList(student);
		}
		catch(UserCRSException e) {
			logger.info(e.getMsg());
			return Response.status(404).entity(e.getMsg()).build();
		}
		
		return Response.status(200).entity(courseList).build();
	}
	
	
	
	/**
	 * To get the total semester fee.
	 * @param student
	 * @return amount
	 */
	@POST
	@Path("/viewSemesterFees")
	@Consumes("application/json")
	@Produces(MediaType.APPLICATION_JSON)
	public Response viewSemesterFees(Student student)
	{
		double totalFees = PaymentService.getTotalFees(student);
		return Response.status(200).entity(totalFees).build();
	}
	
	
	
	/**
	 * To make fee payment.
	 * @param student
	 * @return response
	 */
	@POST				// might be POST/PUT ??
	@Path("/makeFeePayment")
	@Consumes("application/json")
	@Produces(MediaType.APPLICATION_JSON)
	public Response makeFeePayment(Student student)
	{
		boolean response = false;
		try{
			response = studentOperation.makeFeePayment(student);
		}
		catch(UserCRSException e) {
			logger.info(e.getMsg());
			return Response.status(404).entity(e.getMsg()).build();
		}
		return Response.status(200).entity(response).build();
	}
	
	
	
	/**
	 * To register the courses.
	 * @param student
	 * @return List of courses.
	 */
	@POST
	@Path("/registerCourses")
	@Consumes("application/json")
	@Produces(MediaType.APPLICATION_JSON)
	public Response registerCourses(Student student)
	{
		List<Course> courseList = null;
		
		try{
			courseList = studentOperation.registerCourses(student, student.getCourseList());
		}
		catch(UserCRSException e) {
			logger.info(e.getMsg());
			return Response.status(404).entity(e.getMsg()).build();
		}
		
		return Response.status(200).entity(courseList).build();
	}
}
