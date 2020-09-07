package leavemanagement;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;

public class Employee {

    public String emp_name;
    public int emp_ID;
    public String emp_designation;
    public HashMap<Date, String> current_employee_leaves;
    public HashMap<Date, Integer> leaveStatusMap;

    public Employee() {
        emp_name = "";
        emp_ID = 0;
        emp_designation = "";
        current_employee_leaves = new HashMap<Date, String>();
        leaveStatusMap = new HashMap<Date, Integer>();
    }

    public Employee(String emp_name, int emp_ID, String emp_designation) {
        this.emp_name = emp_name;
        this.emp_ID = emp_ID;
        this.emp_designation = emp_designation;
        current_employee_leaves = Queries.getLeaveQuery(emp_ID);
        leaveStatusMap = Queries.getLeaveStatuses(emp_ID);
    }

    public void displayEmployeeMenu(Scanner sc) {

        System.out.println("\n---------- EMPLOYEE MENU ----------\n");
        System.out.println("1. Display employee details.\n");
        System.out.println("2. Apply for a leave.\n");
        System.out.println("3. Cancel a leave application.\n");
        System.out.println("4. View all my leave applications.\n");
        System.out.println("5. Show company's leave policy.\n");
        System.out.println("6. Send request for urgent sanction to your manager.\n");
        System.out.println("7. Logout.");
        do {
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
                case 3: {
                    cancelLeave(sc);
                    break;
                }
                case 4: {
                    displayAllLeaves();
                    break;
                }
                case 5: {
                    System.out.println("\nNot ready yet.\n");
                    break;
                }
                case 6: {
                    ClientSideChat(sc);
                    break;
                }
                case 7: {
                    System.out.println("\n" + emp_name + " has been logged out.\n");
                    return;
                }
                default: {
                    System.out.println("\nInvalid choice.\n");
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
        //System.out.println(dateLeave);
        //System.out.println(leaveMessage);
        java.sql.Date dateLeaveSQL = new java.sql.Date(dateLeave.getTime());

        Queries.applyLeaveQuery(emp_ID, dateLeaveSQL, leaveMessage);

        Leave newLeave = new Leave(emp_ID, dateLeave, leaveMessage, 0);
        Manager temp = new Manager();
        temp.trialFunction();
        Manager.leaveApplicationQueue.add(newLeave);
        current_employee_leaves.put(dateLeave, leaveMessage);
        leaveStatusMap.put(dateLeave, 0);
    }

    private void displayAllLeaves() {
        if (current_employee_leaves.isEmpty()) {
            System.out.println("\nYou haven't applied for any leaves yet.\n");
            return;
        }
        int currentLeaveNumber = 1;
        for (Date dateIterator : current_employee_leaves.keySet()) {
            System.out.println("\n------- LEAVE NO. " + currentLeaveNumber + " -----------------------");
            System.out.println("\nDate requested - " + dateIterator);
            System.out.println("\nLeave application sent - " + current_employee_leaves.get(dateIterator));
            if (leaveStatusMap.get(dateIterator) == 1) {
                System.out.println("\nApplication status - Sanctioned\n");
            }
            else if (leaveStatusMap.get(dateIterator) == -1) {
                System.out.println("\nApplication status - Rejected\n");
            }
            else {
                System.out.println("\nApplication status - Pending\n");
            }
            System.out.println("------------------------------------------------------");
            currentLeaveNumber++;
        }
    }

    private void cancelLeave(Scanner sc) {
        sc.nextLine();
        System.out.println("\nWhich date's holiday do you want to cancel? (dd/mm/yyyy) - ");
        String cancelString = sc.nextLine();
        Date cancelDate = new Date();
        try {
            cancelDate = new SimpleDateFormat("dd/MM/yyyy").parse(cancelString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (current_employee_leaves.containsKey(cancelDate)) {
            current_employee_leaves.remove(cancelDate);
            Manager.deleteLeave(emp_ID, cancelDate);
            java.sql.Date cancelDateSQL = new java.sql.Date(cancelDate.getTime());
            Queries.deleteLeaveQuery(emp_ID, cancelDateSQL);

            System.out.println("\nThe leave has been cancelled and project manager has been notified.\n");
        }
        else {
            System.out.println("\nYou haven't applied for a leave on this date.\n");
            return;
        }
    }

    private void ClientSideChat(Scanner sc) {
        try
        {
            System.out.println("\nEstablishing connection...\n");
            Socket socket = new Socket("127.0.0.1", 5000);  
            System.out.println("\nConnected.\n"); 
  
            // takes input from terminal 
            DataInputStream input  = new DataInputStream(System.in); 
  
            // sends output to the socket 
            DataOutputStream out    = new DataOutputStream(socket.getOutputStream()); 
            String line = ""; 

            while (!line.equals("Over")) 
        { 
            try
            { 
                line = input.readLine(); 
                out.writeUTF(line); 
            } 
            catch(IOException i) 
            { 
                System.out.println(i); 
            } 
        } 
        socket.close();
        } 
        catch(UnknownHostException u) 
        { 
            System.out.println(u); 
        } 
        catch(IOException i) 
        { 
            System.out.println(i); 
        }
  
        // keep reading until "Over" is input 
        
    }
}