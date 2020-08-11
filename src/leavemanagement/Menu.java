package leavemanagement;

import java.util.Scanner;

public class Menu {
    static void displayMainMenu() {
        System.out.println("\n-------------- LEAVE MANAGEMENT SYSTEM --------------\n");
        System.out.println("1. Login.\n");
        System.out.println("2. Register.\n");
        System.out.println("Enter your choice: ");
        Scanner sc = new Scanner(System.in);
        int userChoice = sc.nextInt();

        switch (userChoice) {
            case 1: {
                System.out.println("Login handler will be called.\n");
                LoginRegister.loginHandler();
                sc.close();
                break;
            }
            case 2: {
                System.out.println("Register handler will be called.\n");
                LoginRegister.registerHandler();
                sc.close();
                break;
            }
            default: {
                System.out.println("Invalid option.\n");
                sc.close();
                break;
            }
        }
    }
}