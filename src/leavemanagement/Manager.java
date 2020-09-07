package leavemanagement;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Manager {
    
    public String manager_name;
    public int manager_ID;
    static Queue<Leave> leaveApplicationQueue;

    public Manager() {
        manager_name = "";
        manager_ID = 0;
        leaveApplicationQueue = new LinkedList<Leave>();
    }

    public Manager(String manager_name, int manager_ID) {
        this.manager_name = manager_name;
        this.manager_ID = manager_ID;
    }

    public void displayManagerMenu(Scanner sc) {
        System.out.println("\n---------- MANAGER MENU ----------\n");
        System.out.println("1. Display manager details.\n");
        System.out.println("2. View all employees' leave applications.\n");
        System.out.println("3. Sanction a leave application.\n");
        System.out.println("4. Reject a leave application.\n");
        System.out.println("5. Show company's leave policy.\n");
        System.out.println("6. Receive texts from employees.\n");
        System.out.println("7. Logout.");

        do {
            System.out.println("\nEnter your choice: ");
            int userChoice = sc.nextInt();

            switch (userChoice) {
                case 1: {
                    displayDetails();
                    break;
                }
                case 2: {
                    printLeaveApplicationQueue();
                    break;
                }
                case 3: {
                    sanctionLeave(sc);
                    break;
                }
                case 4: {
                    System.out.println("\nNot yet ready.\n");
                    break;
                }
                case 5: {
                    System.out.println("\nNot ready yet.\n");
                    break;
                }
                case 6: {
                    ServerSideChat(sc);
                    break;
                }
                case 7: {
                    System.out.println("\nLogging out.\n");
                    return;
                }
                default: {
                    System.out.println("\nInvalid choice.\n");
                    break;
                }
            }
        } while (true);
    }

    public void displayDetails() {
        System.out.println("\nname - " + manager_name + " ID - " + manager_ID);
    }

    public void printLeaveApplicationQueue() {
        leaveApplicationQueue = Queries.getLeaveManagerQuery();
        if (leaveApplicationQueue.isEmpty()) {
            System.out.println("\nThe leave application queue is empty.\n");
        }
        else {
            for (Leave obj : leaveApplicationQueue) {
                obj.printLeaveDetails();
            }
        }
    }

    static void deleteLeave(int emp_id, Date cancelDate) {
        Queue<Leave> tempQueue = new LinkedList<Leave>();
        Manager temp = new Manager();
        temp.trialFunction();
        for (Leave itr : leaveApplicationQueue) {
            if ((itr.emp_ID == emp_id) && (itr.dateLeave == cancelDate)) {
                continue;
            }
            else {
                tempQueue.add(itr);
            }
        }
        leaveApplicationQueue = tempQueue;
    }

    public void trialFunction() {
        //do nothing lmao
    }

    private void sanctionLeave(Scanner sc) {
        System.out.println("\nEnter ID of employee whose leave is to be sanctioned - ");
        int sanctionID = sc.nextInt();
        sc.nextLine();
        System.out.println("\nEnter date of leave requested - ");
        String parseDate = sc.nextLine();
        Date sanctionDate = new Date();
        try {
            sanctionDate = new SimpleDateFormat("dd/MM/yyyy").parse(parseDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        java.sql.Date sanctionDateSQL = new java.sql.Date(sanctionDate.getTime());

        Queries.sanctionLeaveApplicationQuery(sanctionID, sanctionDateSQL);
    }

    private void ServerSideChat(Scanner sc) {
        try {
            ServerSocket server = new ServerSocket(5000);
            System.out.println("\nServer started.\n"); 
  
            System.out.println("\nWaiting for a client...\n"); 
  
            Socket socket = server.accept(); 
            System.out.println("Client accepted");

            DataInputStream in = new DataInputStream( 
                new BufferedInputStream(socket.getInputStream())); 
  
            String line = ""; 
  
            // reads message from client until "Over" is sent 
            while (!line.equals("Over")) 
            { 
                try
                { 
                    line = in.readUTF(); 
                    System.out.println(line); 
  
                } 
                catch(IOException i) 
                { 
                    System.out.println(i); 
                } 
            } 
            System.out.println("Closing connection"); 
  
            // close connection 
            socket.close();
            server.close();
            in.close(); 
        } catch (IOException i) {
            System.out.println(i);
        }
    }
}