package leavemanagement;

import java.util.ResourceBundle;  
import java.sql.*;

public class Queries {

    static Employee loginQuery(int jobType, int ID) {
        ResourceBundle reader = null;
        reader = ResourceBundle.getBundle("resources/dbconfig");

        Connection con;

        try { 
            con = DriverManager.getConnection(reader.getString("db.url"), reader.getString("db.username"), reader.getString("db.password"));
            
            String query;
            if (jobType == 1) {
                query = "select * from employees where emp_id = ?";
            }
            else {
                query = "select * from manager where manager_id = ?";
            }
            
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setInt(1, ID);
            final ResultSet rs = preparedStmt.executeQuery();
            
            if (rs.next()) {
                // System.out.println(rs.getInt(1));
                System.out.println("Logged in.");
                Employee currentEmployee = new Employee(rs.getString(2), rs.getInt(1), rs.getString(3));
                con.close();
                return currentEmployee;
                // todo: create employee object, call constructor and display menu
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
            System.out.println("Record has been inserted successfully.");
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace(); 
        }
    }
}