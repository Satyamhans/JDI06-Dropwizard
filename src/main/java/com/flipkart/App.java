package com.flipkart;


import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.flipkart.restController.*;



/**
 * @author vikram.c
 * @author nitin.pothineni
 * @author satyam.hans
 * @author pooja.tulsyan
 * @author vennela.reddy
 * @author rishab.sahu
 *
 */
public class App extends Application<Configuration> {
    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);
 
    @Override
    public void initialize(Bootstrap<Configuration> b) {
    }
 
    @Override
    public void run(Configuration c, Environment e) throws Exception {
        LOGGER.info("Registering REST resources");
       
        e.jersey().register(new AdminRESTAPI());
        e.jersey().register(new StudentRESTAPI());
        e.jersey().register(new ProfessorRESTAPI());
        e.jersey().register(new UserRESTAPI());
    }
 
    public static void main(String[] args) throws Exception {
        new App().run(args);
    }
}