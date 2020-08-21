package leavemanagement;

import java.util.Date;
import java.util.HashMap;
import java.util.ResourceBundle;  
import java.sql.*;

public class Queries {

    static Employee employeeLoginQuery (int ID) {
        ResourceBundle reader = null;
        reader = ResourceBundle.getBundle("resources/dbconfig");

        Connection con;

        try {
            con = DriverManager.getConnection(reader.getString("db.url"), reader.getString("db.username"), reader.getString("db.password"));
            
            String query = "select * from employees where emp_id = ?";
            
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setInt(1, ID);
            final ResultSet rs = preparedStmt.executeQuery();
            
            if (rs.next()) {
                // System.out.println(rs.getInt(1));
                System.out.println("Logged in.");
                Employee currentEmployee = new Employee(rs.getString(2), rs.getInt(1), rs.getString(3));
                con.close();
                return currentEmployee;
            }
            else {
                System.out.println("Error logging in.");
                con.close();
                return new Employee("", -1, "");
            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
            return new Employee("", -1, "");
        }
    }

    static Manager managerLoginQuery(int ID) {
        ResourceBundle reader = null;
        reader = ResourceBundle.getBundle("resources/dbconfig");

        Connection con;

        try {
            con = DriverManager.getConnection(reader.getString("db.url"), reader.getString("db.username"), reader.getString("db.password"));
            
            String query = "select * from manager where manager_id = ?";
            
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setInt(1, ID);
            final ResultSet rs = preparedStmt.executeQuery();
            
            if (rs.next()) {
                // System.out.println(rs.getInt(1));
                System.out.println("Logged in.");
                Manager currentManager = new Manager(rs.getString(2), rs.getInt(1));
                con.close();
                return currentManager;
            }
            else {
                System.out.println("Error logging in.");
                con.close();
                return new Manager("", -1);
            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
            return new Manager("", -1);
        }
    }

    static void registerQuery(int jobType, int ID, String name, String designation) {

        ResourceBundle reader = null;
        reader = ResourceBundle.getBundle("resources/dbconfig");

        Connection con;
        
        try { 
            con = DriverManager.getConnection(reader.getString("db.url"), reader.getString("db.username"), reader.getString("db.password"));
            
            String query;
            if (jobType == 1) {
                query = "insert into employees values(?, ?, ?)";
                PreparedStatement preparedStmt = con.prepareStatement(query);
                preparedStmt.setInt(1, ID);
                preparedStmt.setString(2, name);
                preparedStmt.setString(3, designation);
                preparedStmt.execute();
            }
            else {
                query = "insert into manager values(?, ?)";
                PreparedStatement preparedStmt = con.prepareStatement(query);
                preparedStmt.setInt(1, ID);
                preparedStmt.setString(2, name);
                preparedStmt.execute();
            }
            
            con.close();
            System.out.println("\nYou have been registered!\n");
        } catch (SQLException sqlEx) {
            //sqlEx.printStackTrace(); 
            System.out.println("\nRegistration could not be done.\n");
        }
    }

    static void applyLeaveQuery(int emp_id, java.sql.Date dateLeave, String leaveMessage) {
        ResourceBundle reader = null;
        reader = ResourceBundle.getBundle("resources/dbconfig");

        Connection con;

        try {
            con = DriverManager.getConnection(reader.getString("db.url"), reader.getString("db.username"), reader.getString("db.password"));
            
            String query = "insert into employee_leave values(?, ?, ?)";
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setInt(1, emp_id);
            preparedStmt.setDate(2, dateLeave);
            preparedStmt.setString(3, leaveMessage);
            preparedStmt.execute();
            
            con.close();
            System.out.println("\nYour leave application has been submitted!\n");
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace(); 
            System.out.println("\nYour leave application couldn't be submitted due to an SQL Exception.\n");
        }
    }

    static HashMap<Date, String> getLeaveQuery(int emp_id) {
        HashMap<Date, String> hashmap = new HashMap<Date, String>();

        ResourceBundle reader = null;
        reader = ResourceBundle.getBundle("resources/dbconfig");

        Connection con;

        try {
            con = DriverManager.getConnection(reader.getString("db.url"), reader.getString("db.username"), reader.getString("db.password"));
            
            String query = "select * from employee_leave where emp_id = ?";
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setInt(1, emp_id);
            final ResultSet rs = preparedStmt.executeQuery();

            while (rs.next()) {
                hashmap.put(rs.getDate(2), rs.getString(3));
            }
            con.close();
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace(); 
            System.out.println("\nYour previous leaves couldn't be fetched due to an SQL Exception.\n");
        }

        return hashmap;
    }
}