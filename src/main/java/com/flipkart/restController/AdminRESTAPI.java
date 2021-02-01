/**
 * 
 */
package com.flipkart.restController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import com.flipkart.bean.*;
import com.flipkart.constants.UserType;
import com.flipkart.service.AdminInterface;
import com.flipkart.service.AdminOperation;
import com.flipkart.validation.*;

import javax.validation.Valid;
import javax.validation.ValidationException;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Date;
/**
 * 
 * @author Pooja
 *
 */

@Path("/adminapi")
public class AdminRESTAPI {
	
	Scanner inputObj = new Scanner(System.in);

	private static Logger logger = Logger.getLogger(AdminRESTAPI.class);

	AdminInterface adminInterfaceReference = AdminOperation.getInstance();


	/**
	 * To approve student for a given student ID
	 * @param studentId
	 * @return Response
	 */
	@PUT
	@Path("/approveStudent/{studentId}")
	@Consumes("application/json")
    @Produces(MediaType.TEXT_PLAIN)
	public Response approveStudent(@PathParam("studentId") int studentId) {
		Student student = new Student();
		student.setUserId(studentId);
		boolean status = adminInterfaceReference.approveStudents(student);
		String msg = "";
		if(status) {
			msg = "Student with ID: "+ studentId + "  approved.";
			return Response.status(200).entity(msg).build();
		}
		else {
			msg = "Unable to Approve Student with ID: "+ studentId;
			return Response.status(500).entity(msg).build();
		}
	}
	
	
	/**
	 * To view the list of students to Approve.
	 * @return Response
	 */
	@GET
	@Path("/viewStudentsToApprove")
    @Produces(MediaType.APPLICATION_JSON)
	public Response viewStudentsToApprove() {
		List<Student> studentList = adminInterfaceReference.viewStudentsToApprove();
		List<Map<String, String>> studentsToApprove = new ArrayList<Map<String, String>>();
		for(Student entry : studentList) {
			Map<String , String> studentMap = new HashMap<>();
			studentMap.put("studentName", entry.getUsername());
			studentMap.put("studentId", String.valueOf(entry.getUserId()));
			studentsToApprove.add(studentMap);
		}
		if(studentList.size()==0) {
			String msg = "No Students to approve.";
			return Response.status(200).entity(msg).build();
		}
		else{
			return Response.status(200).entity(studentsToApprove).build();
		}
	}
	

	/**
	 * To set the isReportGenerated as true for students.
	 * @param studentId
	 * @return Response
	 */
	@PUT
	@Path("/generateReport/{studentId}")
	@Consumes("application/json")
    @Produces(MediaType.APPLICATION_JSON)
	public Response generateReport(@PathParam("studentId") int studentId) {
		Student student = new Student();
		student.setUserId(studentId);
		boolean status = adminInterfaceReference.generateReport(student);
		String msg;
		if(status) {
			msg = "Report generated Successfully!";
			return Response.status(200).entity(msg).build();
		}
		else {
			msg = "Unable to generate report for student : "+ studentId;
			return Response.status(500).entity(msg).build();
		}
	}


	/**
	 * To view the course catalog
	 * @return Response
	 */
	@GET
	@Path("/viewCourseCatalogue")
    @Produces(MediaType.APPLICATION_JSON)
	public List<Course> viewCourseCatalogue() {
		return adminInterfaceReference.viewCourseCatalogue();
	}


	/**
	 * Adds a course to the course catalog
	 * @param course
	 * @return
	 */
	@POST
	@Path("/addCourse")
	@Consumes("application/json")
    @Produces(MediaType.APPLICATION_JSON)
	public Response addCourseInCatalogue(Course course) {
		boolean status = adminInterfaceReference.addCourseInCatalogue(course);
		if(status) {
			return Response.status(200).entity(course).build();
		}
		else {
			String msg = "Unable to add course to the Database, please try again.";
			return Response.status(500).entity(msg).build();
		}
	}


	/**
	 * Deletes a course from the course catalog
	 * @param courseId
	 * @return
	 */
	@DELETE
	@Path("/deleteCourse/{courseId}")
    @Produces(MediaType.TEXT_PLAIN)
	public Response deleteCourseInCatalogue(@PathParam("courseId") String courseId) {
		Course course = new Course();
		course.setCourseId(courseId);
		boolean status = adminInterfaceReference.deleteCourseInCatalogue(course);
		String msg;
		if(status) {
			msg = "Course with course ID: "+ courseId+ " deleted successfully";
			return Response.status(200).entity(msg).build();
		}
		else {
			msg = "Unable to delete the course with ID: "+ courseId;
			return Response.status(500).entity(msg).build();
		}
	}

	
	/**
	 * Helper function to update the course catalogue.
	 */
	@PUT
	@Path("/updateCourse")
	@Consumes("application/json")
    @Produces(MediaType.APPLICATION_JSON)
	public Response updateCourseInCatalogue(Course course) {
		boolean status = adminInterfaceReference.updateCourseInCatalogue(course);
		if(status) {
			return Response.status(200).entity(course).build();
		}
		else {
			String msg = "Unable to update course in the Database, please try again.";
			return Response.status(500).entity(msg).build();
		}
	}

	/**
	 * Helper function to add new user to Database by Admin
	 */
	@POST
	@Path("/addAdmin")
	@Consumes("application/json")
    @Produces(MediaType.APPLICATION_JSON)
	public Response addAdmin(Admin admin)
	{
		admin.setUserType(UserType.ADMIN);
		boolean status = adminInterfaceReference.addUser(admin);
		if(status) {
			return Response.status(200).entity(admin).build();
		}
		else
		{
			String msg = "Unable to add admin in the Database, please try again.";
			return Response.status(500).entity(msg).build();
		}
	}

	/**
	 * Helper function to fetch additional details for professor
	 * @param user user details
	 * @return professor
	 */
	@POST
	@Path("/addProfessor")
	@Consumes("application/json")
    @Produces(MediaType.APPLICATION_JSON)
	public Response addProfessor(Professor professor){
		professor.setUserType(UserType.PROFESSOR);
		boolean status = adminInterfaceReference.addUser(professor);
		if(status) {
			return Response.status(200).entity(professor).build();
		}
		else
		{
			String msg = "Unable to add professor in the Database, please try again.";
			return Response.status(500).entity(msg).build();
		}
	}

	/**
	 * Helper function to fetch additional details for student
	 * @param user user details
	 * @return student
	 */
	@POST
	@Path("/addStudent")
	@Consumes("application/json")
    @Produces(MediaType.APPLICATION_JSON)
	public Response addStudent(@Valid Student student) throws ValidationException{
		student.setUserType(UserType.STUDENT);
		boolean status = adminInterfaceReference.addUser(student);
		if(status) {
			return Response.status(200).entity(student).build();
		}
		else
		{
			String msg = "Unable to add student in the Database, please try again.";
			return Response.status(500).entity(msg).build();
		}
	}

	/**
	 * Helper function to remove user from the DB
	 */
	@DELETE
	@Path("/removeUser/{userEmail}")
    @Produces(MediaType.APPLICATION_JSON)
	public Response removeUser(@PathParam("userEmail") String userEmail) {
		User user = new User();
		user.setEmailId(userEmail);
		boolean status = adminInterfaceReference.removeUser(user);
		String msg = "";
		if(status) {
			msg = "User with email : "+ userEmail + " deleted successfully";
			return Response.status(200).entity(msg).build();
		}
		else {
			msg = "Unable to delete the user with email: "+ userEmail;
			return Response.status(500).entity(msg).build();
		}
	}

}
