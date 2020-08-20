package leavemanagement;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Employee {

    public String emp_name;
    public int emp_ID;
    public String emp_designation;

    public Employee() {
        emp_name = "";
        emp_ID = 0;
        emp_designation = "";
    }

    public Employee(String emp_name, int emp_ID, String emp_designation) {
        this.emp_name = emp_name;
        this.emp_ID = emp_ID;
        this.emp_designation = emp_designation;
    }

    public void displayDetails() {
        System.out.println("\nname - " + emp_name + " ID - " + emp_ID + " designation - " + emp_designation);
    }

    public void displayEmployeeMenu(Scanner sc) {
        do {
            System.out.println("\n---------- EMPLOYEE MENU ----------\n");
            System.out.println("1. Display employee details.\n");
            System.out.println("2. Apply for a leave.\n");
            System.out.println("3. Cancel a leave application.\n");
            System.out.println("4. View all my leave applications.\n");
            System.out.println("5. Show company's leave policy.\n");
            System.out.println("6. Logout.");
            System.out.println("\nEnter your choice - ");
            
            int userChoice = sc.nextInt();
            
            switch (userChoice) {
                case 1: {
                    showEmployeeDetails();
                    break;
                }
                case 2: {
                    applyLeave(sc);
                    break;
                }
                default: {
                    System.out.println("\nInvalid choice.");
                    break;
                }
            }
        } while (true);
    }

    private void showEmployeeDetails() {
        System.out.println("\nEmployee ID - " + emp_ID);
        System.out.println("\nEmployee name - " + emp_name);
        System.out.println("\nDesignation" + emp_designation);
    }

    private void applyLeave(Scanner sc) {
        String leaveMessage, leaveDate;
        sc.nextLine();
        System.out.println("\nEnter date of leave (dd/mm/yyyy) - ");
        leaveDate = sc.nextLine();
        System.out.println("\nEnter leave application - ");
        leaveMessage = sc.nextLine();
        Date dateLeave = new Date();
        try {
            dateLeave = new SimpleDateFormat("dd/MM/yyyy").parse(leaveDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(dateLeave);
        System.out.println(leaveMessage);
        java.sql.Date dateLeaveSQL = new java.sql.Date(dateLeave.getTime());

        Queries.applyLeaveQuery(emp_ID, dateLeaveSQL, leaveMessage);

        Leave newLeave = new Leave(emp_ID, dateLeave, leaveMessage);
        Manager templmao = new Manager();
        Manager.leaveApplicationQueue.add(newLeave);
        templmao.printLeaveApplicationQueue();
    }
}