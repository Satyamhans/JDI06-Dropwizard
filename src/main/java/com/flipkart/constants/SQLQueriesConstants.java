package com.flipkart.constants;


/**
 * Class for all the SQL queries being used by the application.
 */
public class SQLQueriesConstants {

    //Admin SQL Queries
    public static final String UPDATE_STUDENT_QUERY = "UPDATE studenttable SET isApproved = \"t\" WHERE studentId = ?";
    public static final String VIEW_STUDENTS_TO_APPROVE = "SELECT studentId, username FROM studenttable, usertable\nWHERE studentId = userId\nand isApproved = \"f\"";
    public static final String CHECK_FEE_STATUS = "SELECT hasPaidFee FROM studenttable WHERE studentId = ?";
    public static final String UPDATE_REPORT_STATUS = "UPDATE studenttable SET isReportGenerated = \"t\" WHERE studentId = ?";
    public static final String ADD_COURSE = "INSERT INTO coursetable VALUES (?, ?, ?, ?, ?, ?, ?)";
    public static final String CHECK_COURSE_EXISTS = "SELECT courseId FROM coursetable WHERE courseId = ?";
    public static final String DELETE_COURSE = "DELETE FROM coursetable WHERE courseId = ?";
    public static final String UPDATE_COURSE ="UPDATE coursetable SET courseName = ?, courseFee = ?, courseProfessor = ?, courseDescription =?, catalogueId = ?, professorId = ? WHERE courseId = ?";
    public static final String ADD_USER = "INSERT into usertable (username, email, password, usertype) VALUES (?, ?, ?, ?)";
    public static final String CHECK_USER = "select userId from usertable where email = ? ";
    public static final String ADD_PROFESSOR = "INSERT into professortable VALUES (?, ?, ?)";
    public static final String ADD_STUDENT = "INSERT into studenttable VALUES (?, ?, ?, \"f\", ?, \"f\", 0, \"f\" ,? ,\"f\")";
    public static final String ADD_ADMIN = "INSERT into admintable (userId, adminId) VALUES (?, ?)";
    public static final String FETCH_USERID = "SELECT userId FROM usertable WHERE email = ?";
    public static final String REMOVE_USER = "DELETE FROM usertable WHERE email = ?";
    public static final String CHECK_PROFESSOR = "SELECT usertype, userId FROM usertable WHERE email = ?";
    public static final String SET_PROF_NULL = "UPDATE coursetable set courseProfessor = NULL, professorId = NULL WHERE courseId = ?";
    public static final String GET_PROFESSOR_COURSES = "SELECT * from coursetable where professorId = ?";


    //PaymentServices
    public static final String SET_STUDENT_FEE = "update studenttable set totalFees = ? where studentId = ?";
    public static final String GET_STUDENT_TOTAL_FEES = "select totalFees from studenttable where studentId = ?";

    //Student
    public static final String ADD_COURSE_STUDENT_QUERY = "insert into studentgradetable(studentId, courseId, grade) values(?,?,?)";
    public static final String GET_REGISTERED_COURSES_QUERY = "select courseId from studentgradetable where studentId = ?";
    public static final String DROP_COURSE_STUDENT_QUERY = "delete from studentgradetable where courseId = ? and studentId = ?";
    public static final String GET_COURSE_DETAILS_FROM_COURSETABLE = "select courseName, courseProfessor, courseFee, catalogueId, courseDescription, professorId from coursetable where courseId = ?";
    public static final String VIEW_GRADES_QUERY = "select courseId, grade from studentgradetable where studentId = ?";
    public static final String MAKE_FEE_PAYMENT = "update studenttable set hasPaidFee = 't' where studentId = ?";
    public static final String UPDATE_PAYMENT_TABLE = "insert into paymenttable(studentId, amount, paymentMode) values (?,?,?)";
    public static final String SET_STUDENT_IS_REGISTERED_FLAG = "update studenttable set isRegistered = 't' where studentId = ?";
    public static final String GET_STUDENT_IS_REGISTERED_FLAG = "select isRegistered from studenttable where studentId = ?";
    public static final String GET_STUDENT_IS_REPORT_GENERATED_FLAG = "select isReportGenerated from studenttable where studentId = ?";
    public static final String GET_STUDENT_HAS_PAID_FEE_FLAG = "select hasPaidFee from studenttable where studentId = ?";
    
    
    //authUser
    public static final String GET_USER_QUERY = "Select * from usertable where email=?";

    //authStudent
    public static final String GET_STUDENT_QUERY = "Select * from studenttable join usertable on studenttable.studentId=usertable.userId where email=?";
    public static final String GET_STUDENT_COURSE_LIST_QUERY = "Select courseId from studentgradetable where studentId=?";
    public static final String GET_COURSE_QUERY = "Select * from coursetable where courseId=?";

    //authProfessor
    public static final String GET_PROFESSOR_QUERY = "Select * from professortable join usertable on professortable.professorId=usertable.userId where email=?";
    public static final String GET_PROFESSOR_COURSES_QUERY = "Select * from  coursetable join professortable where professortable.professorId=?";

    //authAdmin
    public static final String GET_ADMIN_QUERY = "Select * from admintable join usertable on admintable.userId=usertable.userId where email=?";

    // RegisterStudent
    public static final String ADD_USER_QUERY = "INSERT into usertable(userName,email,password,usertype) VALUES ( ?, ?, ?, ? )";
    public static final String GET_USER_ID_QUERY = "SELECT userId from usertable where email=?";
    public static final String ADD_STUDENT_QUERY = "INSERT into studenttable VALUES (?,?,?,'f','f','f',0.0,'f',?,'f')";

    //Course queries
    public static final String VIEW_COURSE_CATALOGUE = "select * from coursetable";
    public static final String GET_COURSE_SEATS = "select count(*) from studentgradetable where courseId = ?";
    
    //Notification queries
    public static final String ADD_NOTIFICATION = "insert into notificationtable values (?, ?,?,?)";
    public static final String GET_NO_OF_ROWS = "select count(*) from notificationtable";
    public static final String GET_TRANSACTION_ID = "select transactionId from paymenttable where studentId = ?";

    //Professor
    public static final String VIEW_ENROLLED_STUDENTS = "select s.*, u.username  from studenttable s, usertable u where s.studentId In (select studentId from studentgradetable where courseId = ?) and u.userId = s.studentId";
    public static final String GRADE_STUDENTS = "Update studentgradetable set grade = ? where studentId = ? and courseId = ?";
    public static final String VIEW_PROFESSOR_COURSES = "select * from coursetable where professorId = ?";
    
   

}
