package com.flipkart.restController;

import org.glassfish.jersey.server.ResourceConfig;

public class ApplicationConfig extends ResourceConfig {

	public ApplicationConfig() 
	{
		register(StudentRESTAPI.class);
		register(ProfessorRESTAPI.class);
		register(UserRESTAPI.class);
		register(AdminRESTAPI.class);
	}
}