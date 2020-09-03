package leavemanagement;

// import java.util.Scanner;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class Menu{

    static void displayMainMenu() {
        // Scanner sc = new Scanner(System.in);
        // outerloop:
        // do {
        //     System.out.println("\n-------------- LEAVE MANAGEMENT SYSTEM --------------\n");
        //     System.out.println("1. Login.\n");
        //     System.out.println("2. Register.\n");
        //     System.out.println("3. Exit.\n");
        //     System.out.println("Enter your choice: ");
            
        //     int userChoice = sc.nextInt();

        //     switch (userChoice) {
        //         case 1: {
        //             System.out.println("\nLogin handler will be called.\n");
        //             System.out.println("\nEnter 1 for employee login and 2 for project manager login - ");
        //             int jobType = sc.nextInt();
        //             if (jobType == 1) {
        //                 //call employee login
        //                 LoginRegister.employeeLoginHandler(sc);
        //             }
        //             else if (jobType ==2) {
        //                 //call PM login
        //                 LoginRegister.managerLoginHandler(sc);
        //             }
        //             else {
        //                 System.out.println("\nInvalid choice.\n");
        //             }
        //             break;
        //         }
        //         case 2: {
        //             System.out.println("Register handler will be called.\n");
        //             LoginRegister.registerHandler(sc);
        //             break;
        //         }
        //         case 3: {
        //             System.out.println("\nExiting...\n");
        //             sc.close();
        //             break outerloop;
        //         }
        //         default: {
        //             System.out.println("Invalid option.\n");
        //             break;
        //         }
        //     }
        // } while (true);
        
        // sc.close();


        // NEW GUI EXPERIMENTAL CODE

            // BEGINNING OF MAIN MENU CODE

            // Creating main frame that will have all the cards.
            JFrame mainFrame = new JFrame("SDL Assignment");
            mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            Container c = mainFrame.getContentPane();
            c.setLayout(new CardLayout());
            // Exit program when X button is clicked.
            mainFrame.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent windowEvent){
                   System.exit(0);
                }
             });
            
            // Setting card layout for the main frame.
            CardLayout cardLayout = new CardLayout();
            mainFrame.setLayout(cardLayout);

            // Creating the header panel.
            JPanel headerPanel = new JPanel();
            headerPanel.setLayout(new GridLayout(1,1));
            headerPanel.setMaximumSize(new Dimension(300,150));
            headerPanel.setPreferredSize(new Dimension(300,150));
            JLabel headerLabel = new JLabel("Leave Management System",JLabel.CENTER );
            headerLabel.setFont(headerLabel.getFont().deriveFont(16.0f));
            headerPanel.add(headerLabel);
            
            // Creating buttons for control panel.
            JButton loginButton = new JButton("Login");
            loginButton.setPreferredSize(new Dimension(100, 30));
            loginButton.setMaximumSize(new Dimension(100, 30));
            JButton registerButton = new JButton("Register");
            registerButton.setPreferredSize(new Dimension(100, 30));
            registerButton.setMaximumSize(new Dimension(100, 30));
            JButton exitButton = new JButton("Exit");
            exitButton.setPreferredSize(new Dimension(100, 30));
            exitButton.setMaximumSize(new Dimension(100, 30));

             // Adding action listeners to buttons
             loginButton.addActionListener(new ActionListener() {
                 public void actionPerformed (ActionEvent e) {
                    cardLayout.show(mainFrame.getContentPane(), "loginPanel");
                 }
             });
             exitButton.addActionListener(new ActionListener() {
                 public void actionPerformed (ActionEvent e) {
                     System.exit(0);
                 }
             });

            // Creating panels for each button.
            JPanel loginButtonPanel = new JPanel();
            loginButtonPanel.setLayout(new BoxLayout(loginButtonPanel, BoxLayout.X_AXIS));
            loginButtonPanel.add(loginButton);

            JPanel registerPanel = new JPanel();
            registerPanel.setLayout(new BoxLayout(registerPanel, BoxLayout.X_AXIS));
            registerPanel.add(registerButton);

            JPanel exitPanel = new JPanel();
            exitPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
            exitPanel.add(exitButton);

            // Creating control panel and adding buttons.
            JPanel controlPanel = new JPanel();
            controlPanel.setLayout(new BoxLayout(controlPanel,BoxLayout.Y_AXIS));
            controlPanel.add(loginButtonPanel);
            controlPanel.add(Box.createRigidArea(new Dimension(0, 40)));
            controlPanel.add(registerPanel);
            controlPanel.add(Box.createRigidArea(new Dimension(0, 160)));
            controlPanel.add(exitPanel);

            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
            mainPanel.add(headerPanel);
            mainPanel.add(controlPanel);


            
            // BEGINNING OF LOGIN PANEL CODE

            JPanel loginPanel = new JPanel();
            loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.Y_AXIS));
            JPanel employeeLoginPanel = new JPanel();
            employeeLoginPanel.setLayout(new BoxLayout(employeeLoginPanel, BoxLayout.X_AXIS));
            employeeLoginPanel.setMaximumSize(new Dimension(300,100));
            employeeLoginPanel.setPreferredSize(new Dimension(300,100));
            JLabel employeeLoginMessage = new JLabel("Enter Employee ID - ");
            employeeLoginMessage.setFont(employeeLoginMessage.getFont().deriveFont(16.0f));

            JTextField t = new JTextField();
            t.setSize(new Dimension(100,30));
            t.setMaximumSize(new Dimension(100,30));
            t.setPreferredSize(new Dimension(100,30));

            employeeLoginPanel.add(employeeLoginMessage);
            employeeLoginPanel.add(t);

            JPanel managerLoginPanel = new JPanel();
            managerLoginPanel.setLayout(new BoxLayout(managerLoginPanel, BoxLayout.X_AXIS));
            managerLoginPanel.setMaximumSize(new Dimension(300,100));
            managerLoginPanel.setPreferredSize(new Dimension(300,100));
            JLabel managerLoginPanelMessage = new JLabel("Enter Manager ID - ");
            managerLoginPanelMessage.setFont(managerLoginPanelMessage.getFont().deriveFont(16.0f));

            JTextField t1 = new JTextField();
            t1.setSize(new Dimension(100,30));
            t1.setMaximumSize(new Dimension(100,30));
            t1.setPreferredSize(new Dimension(100,30));

            managerLoginPanel.add(managerLoginPanelMessage);
            managerLoginPanel.add(t1);

            loginPanel.add(employeeLoginPanel);
            loginPanel.add(managerLoginPanel);

            mainFrame.add("mainPanel", mainPanel);
            mainFrame.add("loginPanel", loginPanel);
            mainFrame.setBounds(500, 150, 500, 500);
            mainFrame.setVisible(true);

    }
}