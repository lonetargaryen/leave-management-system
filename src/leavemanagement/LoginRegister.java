package leavemanagement;

import java.util.Scanner;

public class LoginRegister {

    static void loginHandler() {

        System.out.println("Enter 1 for employee login and 2 for project manager login - ");
        Scanner sc = new Scanner(System.in);
        int jobType = sc.nextInt();
        int ID;
        String askID = (jobType == 1) ? "Enter employee ID - " : "Enter manager ID - ";
        System.out.println(askID);
        ID = sc.nextInt();

        Employee currentEmployee = Queries.loginQuery(jobType, ID);

        if ((currentEmployee.emp_ID != -1) && (jobType == 1)) {
            //create employee object
            System.out.println("\nWelcome back " + currentEmployee.emp_name + "!\n");
            currentEmployee.displayEmployeeMenu();
        }
        else if ((currentEmployee.emp_ID != -1) && (jobType == 2)) {
            //create pm object
            currentEmployee.displayEmployeeMenu();
        }
        else {
            Menu.displayMainMenu();
        }

        sc.close();
    }

    static void registerHandler() {
        
        System.out.println("Enter 1 for employee and 2 for project manager - ");
        Scanner sc = new Scanner(System.in);
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
        sc.close(); 
    }
}