/**
 * 
 */
package com.flipkart.restController;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.flipkart.bean.User;
import com.flipkart.constants.UserType;

/**
 * @author vikramc
 *
 */


@Path("/userapi")
public class UserRESTAPI {

	// Say hello!
    @GET
    @Path("/hello")
    @Produces(MediaType.TEXT_PLAIN)
	public String hello() {
		return "Hello World! This is for you.";
	}
    
    @POST
    @Path("/userCredentialHandler")
    @Consumes("application/json")
    @Produces(MediaType.APPLICATION_JSON)
    public Response credentialHandler(User user)		// TODO: need to modify parameters
    {	
		return Response.status(200).entity(user).build();	
    }
}
