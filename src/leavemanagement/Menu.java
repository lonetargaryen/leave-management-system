package leavemanagement;

import java.util.Scanner;

public class Menu {
    static void displayMainMenu() {
        Scanner sc = new Scanner(System.in);
        outerloop:
        do {
            System.out.println("\n-------------- LEAVE MANAGEMENT SYSTEM --------------\n");
            System.out.println("1. Login.\n");
            System.out.println("2. Register.\n");
            System.out.println("3. Exit.\n");
            System.out.println("Enter your choice: ");
            
            int userChoice = sc.nextInt();

            switch (userChoice) {
                case 1: {
                    System.out.println("\nLogin handler will be called.\n");
                    System.out.println("\nEnter 1 for employee login and 2 for project manager login - ");
                    int jobType = sc.nextInt();
                    if (jobType == 1) {
                        //call employee login
                        LoginRegister.employeeLoginHandler(sc);
                    }
                    else if (jobType ==2) {
                        //call PM login
                        LoginRegister.managerLoginHandler(sc);
                    }
                    else {
                        System.out.println("\nInvalid choice.\n");
                    }
                    break;
                }
                case 2: {
                    System.out.println("Register handler will be called.\n");
                    LoginRegister.registerHandler(sc);
                    break;
                }
                case 3: {
                    System.out.println("\nExiting...\n");
                    sc.close();
                    break outerloop;
                }
                default: {
                    System.out.println("Invalid option.\n");
                    break;
                }
            }
        } while (true);
        
        sc.close();
    }
}