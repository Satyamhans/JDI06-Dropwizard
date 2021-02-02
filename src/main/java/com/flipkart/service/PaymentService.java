package com.flipkart.service;
import com.flipkart.bean.*;
import com.flipkart.dao.PaymentDaoImplementation;

import java.util.List;

import org.apache.log4j.Logger;



public class PaymentService {

    private static volatile PaymentService instance = null;
    private PaymentService() {
    }

    public static PaymentService getInstance() {
        if (instance == null) {
            synchronized (PaymentService.class) {
                instance = new PaymentService();
            }
        }
        return instance;
    }

    //Logger
    private static Logger logger = Logger.getLogger(PaymentService.class);

    /**
     * To calculate total amount for the student
     * @param student entity
     * @return status
     */
    public static double calculateAmount(Student student)
    {
    	if(student.getCourseList() == null)
        	return 0;
    	
        logger.debug("Calculating Amount");
        
        List<Course> courseList = student.getCourseList();
        double amount = 0.0;
        int scholarshipPercent = 40;
        for(Course course:courseList)
        {
            amount += course.getCourseFee();
        }
        // deduct scholarship (Dummy Scholarship Amount)
        if(student.isHasScholarship())
        {
            amount = amount - (amount*scholarshipPercent)/100;
        }
        
        amount = PaymentDaoImplementation.calculateAmount(student,amount);
        student.setTotalFees(amount);
        
        return amount;
    }

    /**
     * Option to pay by Card
     * @return status
     */
    public static boolean payCard()
    {
        logger.debug("Paying through Card");
        return true;
    }

    /**
     * Option to pay by cheque
     * @return status
     */
    public static boolean payCheque()
    {
        logger.debug("Paying through Cheque");
        return true;
    }

    /**
     * Option to pay by net banking
     * @return status
     */
    public static boolean payNetBanking()
    {
        logger.debug("Paying through NetBanking");
        return true;
    }

    public static double getTotalFees(Student student)
    {
    	
        return PaymentDaoImplementation.getTotalFees(student);
    }

}
