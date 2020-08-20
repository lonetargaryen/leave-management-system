package leavemanagement;

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
        System.out.println("3. Show company's leave policy.\n");
        System.out.println("4. Logout.");
    }

    public void displayDetails() {
        System.out.println("name - " + manager_name + " ID - " + manager_ID);
    }

    public void printLeaveApplicationQueue() {
        if (leaveApplicationQueue.isEmpty()) {
            System.out.println("\nThe leave application queue is empty.\n");
        }
        else {
            for (Leave obj : leaveApplicationQueue) {
                obj.printLeaveDetails();
            }
        }
    }
}