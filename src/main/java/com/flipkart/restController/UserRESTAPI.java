/**
 * 
 */
package com.flipkart.restController;

import javax.validation.Valid;
import javax.validation.ValidationException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import com.flipkart.bean.Admin;
import com.flipkart.bean.Professor;
import com.flipkart.bean.Student;
import com.flipkart.service.AuthenticationService;

/**
 * @author vikramc
 *
 */


@Path("/userapi")
public class UserRESTAPI {
	
	// logger
	private static Logger logger = Logger.getLogger(UserRESTAPI.class);
    
    
    /**
     * To authenticate student using email and password through authentication service.
     * @param email
     * @param password
     * @return response
     */
    @GET
	@Path("/authStudent")
	@Consumes("application/json")
    @Produces(MediaType.APPLICATION_JSON)
	public Response approveStudent(@QueryParam("email") String email,@QueryParam("password") String password) {
		Student student = AuthenticationService.authStudent(email,password);
		String msg = "";
		if(student!=null) {
			logger.info("Student with ID: "+ student.getUserId() + "  loggedin.");
			return Response.status(200).entity(student).build();
		}
		else {
			msg = "login failed try again";
			return Response.status(500).entity(msg).build();
		}
	}
    
    /**
     * To authenticate Professor using email and password through authentication service.
     * @param email
     * @param password
     * @return response
     */
    @GET
	@Path("/authProfessor")
	@Consumes("application/json")
    @Produces(MediaType.APPLICATION_JSON)
	public Response approveProfessor(@QueryParam("email") String email,@QueryParam("password") String password) {
    	Professor professor = AuthenticationService.authProfessor(email,password);
		String msg = "";
		if(professor!=null) {
			logger.info("Professor with ID: "+ professor.getUserId() + "  loggedin.");
			return Response.status(200).entity(professor).build();
		}
		else {
			msg = "login failed try again";
			return Response.status(500).entity(msg).build();
		}
	}
    
    /**
     * To authenticate admin using email and password through authentication service.
     * @param email
     * @param password
     * @return response
     */
    @GET
	@Path("/authAdmin")
	@Consumes("application/json")
    @Produces(MediaType.APPLICATION_JSON)
	public Response approveAdmin(@QueryParam("email") String email,@QueryParam("password") String password) {
    	Admin admin = AuthenticationService.authAdmin(email,password);
		String msg = "";
		if(admin!=null) {
			logger.info("Admin with ID: "+ admin.getUserId() + "  loggedin.");
			return Response.status(200).entity(admin).build();
		}
		else {
			msg = "login failed try again";
			return Response.status(500).entity(msg).build();
		}
	}
    
    /**
     * To register a new student in the CRS
     * @param student
     * @return response
     * @throws ValidationException
     */
    @POST
	@Path("/registerStudent")
	@Consumes("application/json")
    @Produces(MediaType.TEXT_PLAIN)
	public Response registerStudent(@Valid Student student) throws ValidationException{
		String username = student.getUsername();
		String email = student.getEmailId();
		String password = student.getPassword();
		String branch = student.getBranch();
		String gender = student.getGender();
		String address = student.getAddress();
		boolean status = AuthenticationService.registerStudent(username,email,password,branch,gender,address);
		if(status) {
			String msg = "Student Registered Successfully";
			return Response.status(200).entity(msg).build();
		}
		else
		{
			String msg = "Unable to add student in the Database, please try again.";
			return Response.status(500).entity(msg).build();
		}
	}
}
