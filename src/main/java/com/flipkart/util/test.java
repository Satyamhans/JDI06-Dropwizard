package com.flipkart.util;

import com.flipkart.service.AuthenticationService;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class test {
    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        try{

            System.out.println("Connecting to database...");
            conn = DBUtils.getConnection();
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT * FROM admintable";
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                String first = rs.getString("AdminId");
                String second = rs.getString("userId");
                System.out.println("First: " + first);
                System.out.println("Last: " + second);
            }
            rs.close();
            stmt.close();
            conn.close();
        }catch(SQLException se){
            se.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try{
                if(stmt!=null)
                    stmt.close();
            }catch(SQLException se2){
            }
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
        System.out.println("Goodbye!");


        //AuthenticationService.authUser("pooja@gmail.com","1234");
        AuthenticationService.authStudent("ven@gmail.com","1234");
        //AuthenticationService.authProfessor("nit@gmail.com","1234");
        //AuthenticationService.authAdmin("vik@gmail.com","1234");
        //AuthenticationService.registerStudent("Ram","RAM@gmail.com","1234","CSE","M","Delhi");
    }
}
