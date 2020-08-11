package leavemanagement;

import java.util.ResourceBundle;  
import java.sql.*;

public class Queries {

    static void loginQuery(int ID) {
        ResourceBundle reader = null;
        reader = ResourceBundle.getBundle("resources/dbconfig");

        Connection con;

        try { 
            con = DriverManager.getConnection(reader.getString("db.url"), reader.getString("db.username"), reader.getString("db.password"));
            String query = "select * from employees where emp_id = ?";
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setInt(1, ID);
            final ResultSet rs = preparedStmt.executeQuery();
            
            while(rs.next()) {
                System.out.println(rs.getInt(1));
            }
            System.out.println("Logged in.");
            con.close();
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace(); 
        }
    }

    static void registerQuery(int ID, String name, String designation) {

        ResourceBundle reader = null;
        reader = ResourceBundle.getBundle("resources/dbconfig");

        Connection con;
        
        try { 
            con = DriverManager.getConnection(reader.getString("db.url"), reader.getString("db.username"), reader.getString("db.password"));
            String query = "insert into employees values(?, ?, ?)";
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setInt(1, ID);
            preparedStmt.setString(2, name);
            preparedStmt.setString(3, designation);
            preparedStmt.execute();
            con.close();
            System.out.println("Record has been inserted successfully.");
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace(); 
        }
    }
}