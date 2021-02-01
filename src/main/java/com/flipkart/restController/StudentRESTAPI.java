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
		}
		
		return Response.status(200).entity(reportCard).build();
	}
	
	@GET
	@Path("/viewCourseCatalogue")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Course> viewCourseCatalogue() 
	{
		List<Course> courseCatalogue = studentOperation.viewCourseCatalogue();
		
		return courseCatalogue;
	}
	
	@POST
	@Path("/viewRegisteredCourses")
	@Consumes("application/json")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Course> viewRegisteredCourses(Student student) 
	{
		// fetch from database instead ..?
		List<Course> courseList = student.getCourseList();
		
		return courseList;
	}
	
	@POST
	@Path("/viewSemesterFees")
	@Consumes("application/json")
	@Produces(MediaType.APPLICATION_JSON)
	public double viewSemesterFees(Student student)
	{
		return student.getTotalFees();
	}
	
	@POST				// might be POST/PUT ??
	@Path("/makeFeePayment")
	@Consumes("application/json")
	@Produces(MediaType.APPLICATION_JSON)
	public boolean makeFeePayment(Student student)
	{
		boolean response = false;
		try{
			response = studentOperation.makeFeePayment(student);
		}
		catch(UserCRSException e) {
			logger.info(e.getMsg());
		}
		return response;
	}
	
	@POST
	@Path("/registerCourses")
	@Consumes("application/json")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Course> registerCourses(Student student)
	{
		List<Course> courseList = null;
		
		try{
			courseList = studentOperation.registerCourses(student, student.getCourseList());
			PaymentService.calculateAmount(student);
		}
		catch(UserCRSException e) {
			logger.info(e.getMsg());
		}
		System.out.println(student.getCourseList().size());
		return courseList;
	}
}
