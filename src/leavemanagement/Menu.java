package leavemanagement;

//import java.util.Scanner;

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
             registerButton.addActionListener(new ActionListener() {
                public void actionPerformed (ActionEvent e) {
                   cardLayout.show(mainFrame.getContentPane(), "registerPanel");
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

            JPanel registerButtonPanel = new JPanel();
            registerButtonPanel.setLayout(new BoxLayout(registerButtonPanel, BoxLayout.X_AXIS));
            registerButtonPanel.add(registerButton);

            JPanel exitPanel = new JPanel();
            exitPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
            exitPanel.add(exitButton);

            // Creating control panel and adding buttons.
            JPanel controlPanel = new JPanel();
            controlPanel.setLayout(new BoxLayout(controlPanel,BoxLayout.Y_AXIS));
            controlPanel.add(loginButtonPanel);
            controlPanel.add(Box.createRigidArea(new Dimension(0, 40)));
            controlPanel.add(registerButtonPanel);
            controlPanel.add(Box.createRigidArea(new Dimension(0, 160)));
            controlPanel.add(exitPanel);

            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
            mainPanel.add(headerPanel);
            mainPanel.add(controlPanel);


            
            // BEGINNING OF LOGIN PANEL CODE

            // Creating main login panel.
            JPanel loginPanel = new JPanel();
            loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.Y_AXIS));

            // Creating the login header panel.
            JPanel loginHeaderPanel = new JPanel();
            loginHeaderPanel.setLayout(new GridLayout(1,1));
            loginHeaderPanel.setMaximumSize(new Dimension(700,150));
            loginHeaderPanel.setPreferredSize(new Dimension(700,150));
            JLabel loginHeaderLabel = new JLabel("LOGIN MENU", SwingConstants.CENTER );
            loginHeaderLabel.setFont(loginHeaderLabel.getFont().deriveFont(20.0f));
            loginHeaderPanel.add(loginHeaderLabel);

            // Creating employee login panel
            JPanel employeeLoginPanel = new JPanel();
            employeeLoginPanel.setLayout(new BoxLayout(employeeLoginPanel, BoxLayout.Y_AXIS));
            employeeLoginPanel.setMaximumSize(new Dimension(400,150));
            employeeLoginPanel.setPreferredSize(new Dimension(400,150));
            JLabel employeeLoginHeading = new JLabel("Employee Login", SwingConstants.CENTER);
            employeeLoginHeading.setFont(employeeLoginHeading.getFont().deriveFont(18.0f));
            JPanel innerEmployeeLoginMessage = new JPanel();
            innerEmployeeLoginMessage.setLayout(new FlowLayout());
            JLabel employeeLoginMessage = new JLabel("Enter Employee ID - ");
            employeeLoginMessage.setFont(employeeLoginMessage.getFont().deriveFont(16.0f));

            // Creating employee ID input field
            JTextField t1 = new JTextField();
            t1.setSize(new Dimension(60,30));
            t1.setMaximumSize(new Dimension(60,30));
            t1.setPreferredSize(new Dimension(60,30));

            // Creating employee login button.
            JButton employeeLoginButton = new JButton("Login as Employee");
            employeeLoginButton.setPreferredSize(new Dimension(150, 30));
            employeeLoginButton.setMaximumSize(new Dimension(150, 30));

            // Adding everything to employee login panel.
            innerEmployeeLoginMessage.add(employeeLoginMessage);
            innerEmployeeLoginMessage.add(t1);
            innerEmployeeLoginMessage.add(employeeLoginButton);
            employeeLoginPanel.add(employeeLoginHeading);
            employeeLoginPanel.add(innerEmployeeLoginMessage);

            // Making the manager login panel.
            JPanel managerLoginPanel = new JPanel();
            managerLoginPanel.setLayout(new BoxLayout(managerLoginPanel, BoxLayout.Y_AXIS));
            managerLoginPanel.setMaximumSize(new Dimension(400,150));
            managerLoginPanel.setPreferredSize(new Dimension(400,150));
            JLabel managerLoginHeading = new JLabel("Manager Login");
            managerLoginHeading.setFont(managerLoginHeading.getFont().deriveFont(18.0f));
            JPanel innerManagerLoginMessage = new JPanel();
            innerManagerLoginMessage.setLayout(new FlowLayout());
            JLabel managerLoginPanelMessage = new JLabel("Enter Manager ID - ");
            managerLoginPanelMessage.setFont(managerLoginPanelMessage.getFont().deriveFont(16.0f));

            // Making the manager ID input field.
            JTextField t2 = new JTextField();
            t2.setSize(new Dimension(60,30));
            t2.setMaximumSize(new Dimension(60,30));
            t2.setPreferredSize(new Dimension(60,30));

            // Creating manager login button.
            JButton managerLoginButton = new JButton("Login as Manager");
            managerLoginButton.setPreferredSize(new Dimension(150, 30));
            managerLoginButton.setMaximumSize(new Dimension(150, 30));

            // Adding everything to manager login panel.
            innerManagerLoginMessage.add(managerLoginPanelMessage);
            innerManagerLoginMessage.add(t2);
            innerManagerLoginMessage.add(managerLoginButton);
            managerLoginPanel.add(managerLoginHeading);
            managerLoginPanel.add(innerManagerLoginMessage);

            // Adding all panels to login panel
            loginPanel.add(loginHeaderPanel);
            loginPanel.add(employeeLoginPanel);
            loginPanel.add(managerLoginPanel);

            // BEGINNING OF REGISTER PANEL CODE

            // Creating main register panel.
            JPanel registerPanel = new JPanel();
            registerPanel.setLayout(new BoxLayout(registerPanel, BoxLayout.Y_AXIS));

            // Creating the register header panel.
            JPanel registerHeaderPanel = new JPanel();
            registerHeaderPanel.setLayout(new GridLayout(1,1));
            registerHeaderPanel.setMaximumSize(new Dimension(700,100));
            registerHeaderPanel.setPreferredSize(new Dimension(700,100));
            JLabel registerHeaderLabel = new JLabel("REGISTRATION MENU", SwingConstants.CENTER );
            registerHeaderLabel.setFont(registerHeaderLabel.getFont().deriveFont(20.0f));
            registerHeaderPanel.add(registerHeaderLabel);

            // Creating the employee register panel.
            JPanel employeeRegisterPanel = new JPanel();
            employeeRegisterPanel.setLayout(new BoxLayout(employeeRegisterPanel, BoxLayout.Y_AXIS));
            employeeRegisterPanel.setMaximumSize(new Dimension(550,150));
            employeeRegisterPanel.setPreferredSize(new Dimension(550,150));
            JLabel employeeRegisterHeading = new JLabel("Employee Registration", SwingConstants.CENTER);
            employeeRegisterHeading.setFont(employeeRegisterHeading.getFont().deriveFont(18.0f));
            employeeRegisterPanel.add(employeeRegisterHeading);

            JPanel employeeRegisterID = new JPanel();
            employeeRegisterID.setLayout(new FlowLayout(FlowLayout.LEFT));
            JLabel employeeRegisterIDMessage = new JLabel("Enter your ID - ");
            employeeRegisterIDMessage.setFont(employeeRegisterIDMessage.getFont().deriveFont(16.0f));
            employeeRegisterID.add(employeeRegisterIDMessage);
            employeeRegisterPanel.add(employeeRegisterID);

            JTextField employeeRegisterIDText = new JTextField();
            employeeRegisterIDText.setSize(new Dimension(60,25));
            employeeRegisterIDText.setMaximumSize(new Dimension(60,25));
            employeeRegisterIDText.setPreferredSize(new Dimension(60,25));
            employeeRegisterID.add(employeeRegisterIDText);

            JPanel employeeRegisterName = new JPanel();
            employeeRegisterName.setLayout(new FlowLayout(FlowLayout.LEFT));
            JLabel employeeRegisterNameMessage = new JLabel("Enter your name - ");
            employeeRegisterNameMessage.setFont(employeeRegisterNameMessage.getFont().deriveFont(16.0f));
            employeeRegisterName.add(employeeRegisterNameMessage);
            employeeRegisterPanel.add(employeeRegisterName);

            JTextField employeeRegisterNameText = new JTextField();
            employeeRegisterNameText.setSize(new Dimension(60,25));
            employeeRegisterNameText.setMaximumSize(new Dimension(60,25));
            employeeRegisterNameText.setPreferredSize(new Dimension(60,25));
            employeeRegisterName.add(employeeRegisterNameText);

            JPanel employeeRegisterDesignation = new JPanel();
            employeeRegisterDesignation.setLayout(new FlowLayout(FlowLayout.LEFT));
            JLabel employeeRegisterDesignationMessage = new JLabel("Enter your designation - ");
            employeeRegisterDesignationMessage.setFont(employeeRegisterDesignationMessage.getFont().deriveFont(16.0f));
            employeeRegisterDesignation.add(employeeRegisterDesignationMessage);
            employeeRegisterPanel.add(employeeRegisterDesignation);

            JTextField employeeRegisterDesignationText = new JTextField();
            employeeRegisterDesignationText.setSize(new Dimension(60,25));
            employeeRegisterDesignationText.setMaximumSize(new Dimension(60,25));
            employeeRegisterDesignationText.setPreferredSize(new Dimension(60,25));
            employeeRegisterDesignation.add(employeeRegisterDesignationText);

            JButton employeeRegisterButton = new JButton("Register as Employee");
            employeeRegisterButton.setPreferredSize(new Dimension(200, 30));
            employeeRegisterButton.setMaximumSize(new Dimension(200, 30));
            employeeRegisterPanel.add(employeeRegisterButton);

            // Creating the manager register panel.
            JPanel managerRegisterPanel = new JPanel();
            managerRegisterPanel.setLayout(new BoxLayout(managerRegisterPanel, BoxLayout.Y_AXIS));
            managerRegisterPanel.setMaximumSize(new Dimension(550,150));
            managerRegisterPanel.setPreferredSize(new Dimension(550,150));
            JLabel managerRegisterHeading = new JLabel("Manager Registration", SwingConstants.CENTER);
            managerRegisterHeading.setFont(managerRegisterHeading.getFont().deriveFont(18.0f));
            managerRegisterPanel.add(managerRegisterHeading);

            JPanel managerRegisterID = new JPanel();
            managerRegisterID.setLayout(new FlowLayout(FlowLayout.LEFT));
            JLabel managerRegisterIDMessage = new JLabel("Enter your ID - ");
            managerRegisterIDMessage.setFont(managerRegisterIDMessage.getFont().deriveFont(16.0f));
            managerRegisterID.add(managerRegisterIDMessage);
            managerRegisterPanel.add(managerRegisterID);

            JTextField managerRegisterIDText = new JTextField();
            managerRegisterIDText.setSize(new Dimension(60,25));
            managerRegisterIDText.setMaximumSize(new Dimension(60,25));
            managerRegisterIDText.setPreferredSize(new Dimension(60,25));
            managerRegisterID.add(managerRegisterIDText);

            JPanel managerRegisterName = new JPanel();
            managerRegisterName.setLayout(new FlowLayout(FlowLayout.LEFT));
            JLabel managerRegisterNameMessage = new JLabel("Enter your name - ");
            managerRegisterNameMessage.setFont(managerRegisterNameMessage.getFont().deriveFont(16.0f));
            managerRegisterName.add(managerRegisterNameMessage);
            managerRegisterPanel.add(managerRegisterName);

            JTextField managerRegisterNameText = new JTextField();
            managerRegisterNameText.setSize(new Dimension(60,25));
            managerRegisterNameText.setMaximumSize(new Dimension(60,25));
            managerRegisterNameText.setPreferredSize(new Dimension(60,25));
            managerRegisterName.add(managerRegisterNameText);

            JButton managerRegisterButton = new JButton("Register as Manager");
            managerRegisterButton.setPreferredSize(new Dimension(200, 30));
            managerRegisterButton.setMaximumSize(new Dimension(200, 30));
            managerRegisterPanel.add(managerRegisterButton);

            // Adding everything to the register panel.
            registerPanel.add(registerHeaderPanel);
            registerPanel.add(employeeRegisterPanel);
            registerPanel.add(managerRegisterPanel);

            // Adding all panels to main frame.
            mainFrame.add("mainPanel", mainPanel);
            mainFrame.add("loginPanel", loginPanel);
            mainFrame.add("registerPanel", registerPanel);
            mainFrame.setBounds(500, 150, 500, 500);
            mainFrame.setVisible(true);

    }
}