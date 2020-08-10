package leavemanagement;

import java.util.Scanner;
import java.util.ResourceBundle;  
import java.sql.*;

public class Menu {
    static void displayMainMenu() {
        System.out.println("\n-------------- LEAVE MANAGEMENT SYSTEM --------------\n");
        System.out.println("1. Login.\n");
        System.out.println("2. Register.\n");
        System.out.println("Enter your choice: ");
        Scanner sc = new Scanner(System.in);
        int userChoice = sc.nextInt();
        System.out.println("You entered - " + userChoice);

        switch (userChoice) {
            case 1: {
                System.out.println("Login handler will be called.");
                break;
            }
            case 2: {
                System.out.println("Register handler will be called.");
                break;
            }
            default: {
                System.out.println("Invalid option.");
                break;
            }
        }
        
        ResourceBundle reader = null;
        reader = ResourceBundle.getBundle("resources/dbconfig");

        Connection con; 
        Statement stmt; 
        ResultSet rs; 
        String query = "select * from employees";
        try { 
            con = DriverManager.getConnection(reader.getString("db.url"), reader.getString("db.username"), reader.getString("db.password"));
            stmt = con.createStatement(); // executing SELECT query 
            rs = stmt.executeQuery(query);
            // while (rs.next()) { 
            //     System.out.println(rs);
            //     int count = rs.getInt(1); 
            //     System.out.println("Total number of employees in the table : " + count);
            // }
            rs.next();
            System.out.println(rs.getString(2));
        } catch (SQLException sqlEx) { 
            sqlEx.printStackTrace(); 
        }
        sc.close();
    }
}