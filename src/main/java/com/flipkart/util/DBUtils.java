package com.flipkart.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.log4j.Logger;



public class DBUtils {

    private static Connection connection = null;

    private static Logger logger = Logger.getLogger(DBUtils.class);

    /**
     * To extablish conncetion to database
     * @return  connection object
     */
    public static Connection getConnection() {

        if (connection != null)
            return connection;
        else {
            try {
                Properties prop = new Properties();
                InputStream inputStream = DBUtils.class.getClassLoader().getResourceAsStream("config.properties");
                prop.load(inputStream);
                String driver = prop.getProperty("driver");
                String url = prop.getProperty("url");
                String user = prop.getProperty("user");
                String password = prop.getProperty("password");
                Class.forName(driver);
                return DriverManager.getConnection(url, user, password);
                //connection = DriverManager.getConnection(url, user, password);
            } catch (ClassNotFoundException e) {
                logger.error(e.getMessage());
            } catch (SQLException e) {
                logger.error(e.getMessage());
            } catch (FileNotFoundException e) {
                logger.error(e.getMessage());
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
            catch(Exception e)
            {
            	logger.info(e.getMessage());
            }
            return connection;
        }

    }

    /**
     * to close the database connection.
     */
    public static void closeConnection() {
        System.out.println("Closing Connection!!");
        try {
            if(connection != null) {
                connection.close();
            }
            else {
                logger.info("Connection already closed!");
            }
        }catch (SQLException e) {
            logger.error(e.getMessage());
        }
    }
}