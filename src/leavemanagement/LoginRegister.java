package leavemanagement;

import java.util.Scanner;

public class LoginRegister {

    static void employeeLoginHandler(Scanner sc) {

        System.out.println("\nEnter your employee ID - ");
        int ID = sc.nextInt();

        Employee currentEmployee = Queries.employeeLoginQuery(ID);

        if (currentEmployee.emp_ID != -1) {
            //create employee object
            System.out.println("\nWelcome back " + currentEmployee.emp_name + "!\n");
            currentEmployee.displayEmployeeMenu(sc);
        }
        else {
            System.out.println("\nCould not login.\n");
        }
    }

    static void managerLoginHandler(Scanner sc) {

        System.out.println("\nEnter your manager ID - ");
        int ID = sc.nextInt();

        Manager currentManager = Queries.managerLoginQuery(ID);

        if (currentManager.manager_ID != -1) {
            //create employee object
            System.out.println("\nWelcome back " + currentManager.manager_name + "!\n");
            currentManager.displayManagerMenu(sc);
        }
        else {
            System.out.println("\nCould not login.\n");
        }
    }

    static void registerHandler(Scanner sc) {
        
        System.out.println("Enter 1 for employee and 2 for project manager - ");
        int jobType = sc.nextInt();
        int ID;
        String askID = (jobType == 1) ? "Enter employee ID - " : "Enter manager ID - ";
        System.out.println(askID);
        ID = sc.nextInt();
        System.out.println("Enter your name - ");
        String name = sc.next();
        String designation = "";
        if (jobType == 1) {
            System.out.println("Enter your designation - ");
            designation = sc.next();
        }
        System.out.println(jobType + " " + ID + " " + name + " " + designation);
        Queries.registerQuery(jobType, ID, name, designation);
    }
}