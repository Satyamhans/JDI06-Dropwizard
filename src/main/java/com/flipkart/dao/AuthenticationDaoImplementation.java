package com.flipkart.dao;

import com.flipkart.bean.*;
import com.flipkart.constants.*;
import com.flipkart.service.StudentOperation;
import com.flipkart.util.DBUtils;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AuthenticationDaoImplementation{

    private static volatile AuthenticationDaoImplementation instance = null;
    private AuthenticationDaoImplementation() {
    }

    public static AuthenticationDaoImplementation getInstance() {
        if (instance == null) {
            synchronized (AuthenticationDaoImplementation.class) {
                instance = new AuthenticationDaoImplementation();
            }
        }
        return instance;
    }

    //Logger
    private static Logger logger = Logger.getLogger(AuthenticationDaoImplementation.class);

    // DB connection
    static Connection conn = null;
    public static PreparedStatement stmt = null;


    /**
     * To authenticate User
     * @param email
     * @param password
     * @return User object fetched by DB
     */
    public static User authUser(String email, String password){
        logger.debug("DAO: Authenticate User");

        User user = new User();

        try {
            conn = DBUtils.getConnection();
            stmt = conn.prepareStatement(SQLQueriesConstants.GET_USER_QUERY,ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (!rs.next() ) {
                logger.info("DAO: User doesn't exists");
                return null;
            }
            rs.beforeFirst();  //moving cursor back!


            while (rs.next()) {
                user.setUserId(rs.getInt("userId"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                String userTypeDB = rs.getString("usertype");

                if (userTypeDB == "S") {
                    user.setUserType(UserType.STUDENT);
                } else if (userTypeDB == "P") {
                    user.setUserType(UserType.PROFESSOR);
                } else if (userTypeDB == "A") {
                    user.setUserType(UserType.ADMIN);
                }

                user.setEmailId(rs.getString("email"));
            }

            //logger.info(user.getUserId() +  "\n" + user.getUsername());

            String passDB = user.getPassword();

            logger.info("Got password"+ passDB);

            if (!password.equals(passDB)) {
                logger.info("DAO: Unsuccessful Login");
                return null;
            }


            logger.info("DAO: Successful Login");
            return user;

        }
        catch (SQLException se) {
            logger.error(se.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return null;
    }

    /**
     * To authenticate Student
     * @param email
     * @param password
     * @return Student object fetched from DB
     */
    public static Student authStudent(String email, String password) {
        logger.debug("DAO: Authenticate Student");

        Student student = new Student();

        try {
            conn = DBUtils.getConnection();
            stmt = conn.prepareStatement(SQLQueriesConstants.GET_STUDENT_QUERY,ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();


            if (!rs.next() ) {
                logger.info("DAO: Student doesn't exists");
                return null;
            }
            rs.beforeFirst();  //moving cursor back!

            while (rs.next()) {
                student.setUserId(rs.getInt("studentId"));
                student.setUsername(rs.getString("username"));
                student.setPassword(rs.getString("password"));
                String userTypeDB = rs.getString("usertype");


                if (userTypeDB.equalsIgnoreCase("S")) {
                    student.setUserType(UserType.STUDENT);
                } else if (userTypeDB.equalsIgnoreCase("P")) {
                    student.setUserType(UserType.PROFESSOR);
                } else if (userTypeDB.equalsIgnoreCase("A")) {
                    student.setUserType(UserType.ADMIN);
                }

                student.setEmailId(rs.getString("email"));
                student.setBranch(rs.getString("branch"));
                student.setGender(rs.getString("gender"));


                String tmp = "";
                tmp = rs.getString("isApproved");
                student.setApproved(tmp != null && tmp.equalsIgnoreCase("t"));

                tmp = "";
                tmp = rs.getString("isRegistered");
                student.setIsRegistered(tmp != null && tmp.equalsIgnoreCase("t"));


                tmp = "";
                tmp = rs.getString("hasScholarship");
                student.setHasScholarship(tmp != null && tmp.equalsIgnoreCase("t"));


                tmp = "";
                tmp = rs.getString("isReportGenerated");
                student.setReportGenerated(tmp != null && tmp.equalsIgnoreCase("t"));


                tmp = "";
                tmp = rs.getString("hasPaidFee");
                student.setHasPaidFee(tmp != null && tmp.equalsIgnoreCase("t"));


                student.setTotalFees(rs.getDouble("totalFees"));


            }

            String passDB = student.getPassword();
            logger.info("IsApproved: " + student.isApproved());

            if (!password.equals(passDB)) {
                logger.info("DAO: Unsuccessful Login");
                return null;
            }

            if(!student.isApproved()){
                logger.info("DAO: Student: " + student.getUsername() + " with ID: " + student.getUserId() + " is not approved.");
                return null;
            }

            // Adding courseList to student object
            student.setCourseList(getStudentCourseList(student.getUserId()));

            logger.info("DAO: Successful Login");
            return student;

        }
        catch (SQLException se) {
            logger.error(se.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return null;
    }


    /**
     * To get the registered courses list ( empty if student hadn't registered)
     * @param userId
     * @return List of courses fetched from DB
     */
    public static List<Course> getStudentCourseList(int userId)
    {

        logger.debug("DAO: Getting registered courses list for studentId: " + userId + "");

        String courseId;
        List<Course> courseList = new ArrayList<>();

        try {
            // For fetching courseList from student grade table.
            conn = DBUtils.getConnection();
            stmt = conn.prepareStatement(SQLQueriesConstants.GET_STUDENT_COURSE_LIST_QUERY);
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();


            while (rs.next()) {
                Course course = new Course();
                courseId = rs.getString("courseId");

                // Fetch the course details to build course object from course table.
                stmt = conn.prepareStatement(SQLQueriesConstants.GET_COURSE_QUERY);
                stmt.setString(1, courseId);
                ResultSet rss = stmt.executeQuery();

                while (rss.next()) {
                    course.setCourseId(rss.getString("courseId"));
                    course.setCourseName(rss.getString("courseName"));
                    course.setCourseProf(rss.getString("courseProfessor"));
                    course.setCourseFee(Double.parseDouble(rss.getString("courseFee")));
                    course.setProfessorId(rss.getInt("professorId"));
                    course.setCourseDescription(rss.getString("courseDescription"));
                    course.setCatalogueId(rss.getString("catalogueId"));
                }

                // Adding to list
                courseList.add(course);
            }
        }
        catch (SQLException se) {
            logger.error(se.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return courseList;

    }

    /**
     * To authenticate Professor
     * @param email
     * @param password
     * @return Professor object fetched from DB
     */
    public static Professor authProfessor(String email, String password){
        logger.debug("DAO: Authenticate Professor");


        Professor professor = new Professor();

        try{
            conn = DBUtils.getConnection();
            stmt = conn.prepareStatement(SQLQueriesConstants.GET_PROFESSOR_QUERY,ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();


            if (!rs.next() ) {
                logger.info("DAO: Professor doesn't exists");
                return null;
            }
            rs.beforeFirst();  //moving cursor back!

            while(rs.next())
            {
                professor.setUserId(rs.getInt("professorId"));
                professor.setUsername(rs.getString("username"));
                professor.setPassword(rs.getString("password"));
                String userTypeDB = rs.getString("usertype");

                if (userTypeDB.equalsIgnoreCase("S")) {
                    professor.setUserType(UserType.STUDENT);
                } else if (userTypeDB.equalsIgnoreCase("P")) {
                    professor.setUserType(UserType.PROFESSOR);
                } else if (userTypeDB.equalsIgnoreCase("A")) {
                    professor.setUserType(UserType.ADMIN);
                }

                professor.setEmailId(rs.getString("email"));

                professor.setDepartment(rs.getString("department"));
                professor.setGender(rs.getString("gender"));
            }

            logger.info(professor.getUserId() + "\t" + professor.getUsername());

            String passDB = professor.getPassword();

            if (!password.equals(passDB)) {
                logger.info("DAO: Unsuccessful Login");
                return null;
            }

            // Adding courseList to professor object
            professor.setCourseList(getProfessorCourseList(professor.getUserId()));

            logger.info("DAO: Successful Login");
            return professor;

        }
        catch (SQLException se) {
            logger.error(se.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return null;
    }


    /**
     * To get the list of courses professor is teaching
     * @param userId
     * @return List of courses fetched from DB
     */
    public static List<Course> getProfessorCourseList(int userId)
    {

        logger.debug("DAO: Getting list of courses for professorId: " + userId + "");

        List<Course> courseList = new ArrayList<>();

        try {
            // For fetching courseDetails from course table join professor table through professorID.
            conn = DBUtils.getConnection();
            stmt = conn.prepareStatement(SQLQueriesConstants.GET_PROFESSOR_COURSES_QUERY);
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();


            while (rs.next()) {
                Course course = new Course();

                course.setCourseId(rs.getString("courseId"));
                course.setCourseName(rs.getString("courseName"));
                course.setCourseProf(rs.getString("courseProfessor"));
                course.setCourseFee(Double.parseDouble(rs.getString("courseFee")));
                course.setProfessorId(rs.getInt("professorId"));
                course.setCourseDescription(rs.getString("courseDescription"));
                course.setCatalogueId(rs.getString("catalogueId"));

                // Adding to list
                courseList.add(course);
            }

        }
        catch (SQLException se) {
            logger.error(se.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return courseList;

    }


    /**
     * To authenticate Admin
     * @param email
     * @param password
     * @return Admin object fetched from DB
     */
    public static Admin authAdmin(String email, String password){
        logger.debug("DAO: Authenticate Admin");

        Admin admin = new Admin();

        try{
            conn = DBUtils.getConnection();
            stmt = conn.prepareStatement(SQLQueriesConstants.GET_ADMIN_QUERY,ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (!rs.next() ) {
                logger.info("DAO: Admin doesn't exists");
                return null;
            }
            rs.beforeFirst();  //moving cursor back!

            while(rs.next()) {
                admin.setUserId(rs.getInt("adminId"));
                admin.setUsername(rs.getString("username"));
                admin.setPassword(rs.getString("password"));
                String userTypeDB = rs.getString("usertype");

                if (userTypeDB.equalsIgnoreCase("S")) {
                    admin.setUserType(UserType.STUDENT);
                } else if (userTypeDB.equalsIgnoreCase("P")) {
                    admin.setUserType(UserType.PROFESSOR);
                } else if (userTypeDB.equalsIgnoreCase("A")) {
                    admin.setUserType(UserType.ADMIN);
                }

                admin.setEmailId(rs.getString("email"));
                admin.setAdminId(rs.getInt("AdminId"));
            }

            logger.info(admin.getUserId() +  "\t" + admin.getUsername());

            String passDB = admin.getPassword();

            if (!password.equals(passDB)) {
                logger.info("DAO: Unsuccessful Login");
                return null;
            }



            logger.info("DAO: Successful Login");
            return admin;
        }
        catch (SQLException se) {
            logger.error(se.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        // Dummy
        //Admin admin = new Admin(username,"102",username+"gmail.com",password,UserType.ADMIN,"1101");
        return null;
    }

    /**
     * To add new student to the DB
     * @param username
     * @param password
     * @param email
     * @param branch
     * @param gender
     * @return Boolean true or false based on whether the student is registered successfully or not.
     */
    public static boolean registerStudent(String username, String email, String password, String branch , String gender, String address){
        logger.debug("DAO: Register new student");

        try {
            // Check whether a student with same email is already registered
            // then cancel the registration process
            conn = DBUtils.getConnection();
            stmt = conn.prepareStatement(SQLQueriesConstants.GET_USER_QUERY);
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            String emailDB = "";
            while(rs.next()) {
                emailDB = rs.getString("email");
            }

            logger.info(emailDB);
            if (email.equals(emailDB)) {
                logger.info("DAO: Registration Unsuccessful! Email: " + email + " exists");
                return false;
            }

            // Add to user table DB
            stmt = conn.prepareStatement(SQLQueriesConstants.ADD_USER_QUERY);
            stmt.setString(1,username);
            stmt.setString(2, email);
            stmt.setString(3, password);
            stmt.setString(4, "S");

            int response = stmt.executeUpdate();
            if (response > 0) {
                logger.info("DAO: Updated User table for " + username);
            }
            else
            {
                logger.info("DAO: User table update failed for " + username);
                return false;
            }

            // Generate new userID
            int userIdDB = getUserId(email);

            // Add to student table
            stmt = conn.prepareStatement(SQLQueriesConstants.ADD_STUDENT_QUERY);
            stmt.setInt(1, userIdDB);
            stmt.setString(2, branch);
            stmt.setString(3, gender);
            stmt.setString(4,address);

            response = stmt.executeUpdate();
            if (response > 0) {
                logger.info("DAO: Registration successful for " + username + " and userId: " + userIdDB);
                return true;
            }

        }
        catch (SQLException se) {
            logger.error(se.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return false;
    }

    /**
     * To fecth userId from DB
     * @param email to key
     * @return userId
     */
    public static int getUserId(String email)
    {
        logger.debug("DAO: Get userId for email: " + email + "");

        int userIdDB = -1;

        try {

            // Generate new userID
            conn = DBUtils.getConnection();
            stmt = conn.prepareStatement(SQLQueriesConstants.GET_USER_ID_QUERY);
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                userIdDB = rs.getInt("userId");
            }
            //logger.info(userIdDB);
        }
        catch (SQLException se) {
            logger.error(se.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return userIdDB;
    }

}
