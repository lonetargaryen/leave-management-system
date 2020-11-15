package leavemanagement;

import java.io.BufferedInputStream;
// import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
// import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Employee implements Runnable {

    public String emp_name;
    public int emp_ID;
    public String emp_designation;
    public HashMap<Date, String> current_employee_leaves;
    public HashMap<Date, Integer> leaveStatusMap;
    private Thread t;

    public void run() {
        System.out.println("\nEmployee ID - " + emp_ID);
        System.out.println("\nEmployee name - " + emp_name);
        System.out.println("\nDesignation - " + emp_designation);
        return;
    }

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
        // System.out.println("\nEmployee ID - " + emp_ID);
        // System.out.println("\nEmployee name - " + emp_name);
        // System.out.println("\nDesignation" + emp_designation);
        t = new Thread (this, "show employee details thread");
        System.out.println("Starting thread number - " + t.getId());
        t.start();
        t.interrupt();
        return;
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
            // BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
            DataInputStream in = new DataInputStream( 
                new BufferedInputStream(socket.getInputStream())); 
            // sends output to the socket 
            DataOutputStream out    = new DataOutputStream(socket.getOutputStream()); 
            String line = ""; 
            sc.nextLine();
            while (!line.equals("Over")) 
        { 
            try
            { 
                System.out.printf("You - ");
                line = sc.nextLine();
                out.writeUTF(line);
                line = in.readUTF();
                System.out.printf("Manager - ");
                System.out.println(line);
            } 
            catch(Exception i) 
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

    public JPanel getEmployeeMenuGUI(JFrame mainFrame, CardLayout cardLayout) {
        JPanel employeeMenuPanel = new JPanel();
        employeeMenuPanel.setLayout(new BoxLayout(employeeMenuPanel, BoxLayout.Y_AXIS));

        // Creating the header panel for the employee menu.
        JPanel welcomePanel = new JPanel();
        welcomePanel.setLayout(new GridLayout(1,1));
        welcomePanel.setMaximumSize(new Dimension(300,150));
        welcomePanel.setPreferredSize(new Dimension(300,150));
        JLabel welcomeLabel = new JLabel("Welcome " + emp_name + "!",JLabel.CENTER );
        welcomeLabel.setFont(welcomeLabel.getFont().deriveFont(16.0f));
        welcomePanel.add(welcomeLabel);

        // Creating buttons for employee menu panel.
        JButton displayEmployeeDetailsButton = new JButton("Display employee details.");
        displayEmployeeDetailsButton.setPreferredSize(new Dimension(250, 30));
        displayEmployeeDetailsButton.setMaximumSize(new Dimension(250, 30));
        JButton applyLeaveButton = new JButton("Apply for a leave.");
        applyLeaveButton.setPreferredSize(new Dimension(250, 30));
        applyLeaveButton.setMaximumSize(new Dimension(250, 30));
        JButton cancelLeaveButton = new JButton("Cancel a leave application.");
        cancelLeaveButton.setPreferredSize(new Dimension(250, 30));
        cancelLeaveButton.setMaximumSize(new Dimension(250, 30));
        JButton displayAllLeavesButton = new JButton("View all my leave applications.");
        displayAllLeavesButton.setPreferredSize(new Dimension(250, 30));
        displayAllLeavesButton.setMaximumSize(new Dimension(250, 30));
        JButton showPolicyButton = new JButton("Show the company's leave policy.");
        showPolicyButton.setPreferredSize(new Dimension(250, 30));
        showPolicyButton.setMaximumSize(new Dimension(250, 30));
        JButton clientSideChatButton = new JButton("Send a request for urgent sanction to your manager.");
        clientSideChatButton.setPreferredSize(new Dimension(350, 30));
        clientSideChatButton.setMaximumSize(new Dimension(350, 30));
        JButton logoutButton = new JButton("Logout.");
        logoutButton.setPreferredSize(new Dimension(100, 30));
        logoutButton.setMaximumSize(new Dimension(100, 30));

        // Creating button listeners for each button

        displayEmployeeDetailsButton.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent e) {
                JDialog d = new JDialog(mainFrame , "Employee Details", true);
                d.setLayout( new FlowLayout() );
                d.setBounds(650, 300, 200, 200);
                JButton b = new JButton ("OK");
                b.addActionListener (new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        d.setVisible(false);
                    }
                });
                d.add(Box.createRigidArea(new Dimension(0, 30)));
                d.add( new JLabel ("ID - " + emp_ID));
                d.add(Box.createRigidArea(new Dimension(0, 30)));
                d.add( new JLabel ("Name - " + emp_name));
                d.add(Box.createRigidArea(new Dimension(0, 30)));
                d.add( new JLabel ("Designation - " + emp_designation));
                d.add(Box.createRigidArea(new Dimension(20, 30)));
                d.add(b);
                d.setSize(200, 200);
                d.setVisible(true);
                return;
            }
        });

        applyLeaveButton.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent e) {
                JDialog d = new JDialog(mainFrame , "Apply Leave Menu", true);
                d.setLayout( new FlowLayout() );
                d.setBounds(500, 250, 400, 250);
                JButton b = new JButton ("Apply");
                

                JPanel innerLeaveDatePanel = new JPanel();
                innerLeaveDatePanel.setLayout(new FlowLayout());
                JLabel innerLeaveDateMessage = new JLabel("Enter date of leave (dd/mm/yyyy) - ");
                JTextField t1 = new JTextField();
                t1.setSize(new Dimension(70,30));
                t1.setMaximumSize(new Dimension(70,30));
                t1.setPreferredSize(new Dimension(70,30));
                innerLeaveDatePanel.add(innerLeaveDateMessage);
                innerLeaveDatePanel.add(t1);

                JPanel innerLeaveMessagePanel = new JPanel();
                innerLeaveMessagePanel.setLayout(new FlowLayout());
                JLabel innerLeaveMessageMessage = new JLabel("Enter reason for leave - ");
                JTextField t2 = new JTextField();
                t2.setSize(new Dimension(150,30));
                t2.setMaximumSize(new Dimension(150,30));
                t2.setPreferredSize(new Dimension(150,30));
                innerLeaveMessagePanel.add(innerLeaveMessageMessage);
                innerLeaveMessagePanel.add(t2);

                b.addActionListener (new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        String dateLeaveString = t1.getText();
                        String reasonLeave = t2.getText();

                        Date dateLeave = new Date();
                        try {
                            dateLeave = new SimpleDateFormat("dd/MM/yyyy").parse(dateLeaveString);
                        } catch (ParseException ex) {
                            ex.printStackTrace();
                        }
                        java.sql.Date dateLeaveSQL = new java.sql.Date(dateLeave.getTime());

                        Queries.applyLeaveQuery(emp_ID, dateLeaveSQL, reasonLeave);

                        Leave newLeave = new Leave(emp_ID, dateLeave, reasonLeave, 0);
                        Manager temp = new Manager();
                        temp.trialFunction();
                        Manager.leaveApplicationQueue.add(newLeave);
                        current_employee_leaves.put(dateLeave, reasonLeave);
                        leaveStatusMap.put(dateLeave, 0);

                        JDialog d2 = new JDialog(mainFrame , "Notification", true);
                        d2.setLayout( new FlowLayout() );
                        d2.setBounds(650, 300, 300, 120);
                        JButton b2 = new JButton ("OK");
                        b2.addActionListener (new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                d2.setVisible(false);
                            }
                        });
                        d2.add(Box.createRigidArea(new Dimension(0, 30)));
                        d2.add( new JLabel ("Your leave application has been submitted!"));
                        d2.add(Box.createRigidArea(new Dimension(0, 30)));
                        d2.add(b2);
                        d2.setSize(300, 120);
                        d2.setVisible(true);
                        d.setVisible(false);
                    }
                });

                d.add(Box.createRigidArea(new Dimension(0, 30)));
                d.add(innerLeaveDatePanel);
                d.add(innerLeaveMessagePanel);
                d.add(Box.createRigidArea(new Dimension(50, 0)));
                d.add(b);
                d.setSize(400, 250);
                d.setVisible(true);
                return;
            }
        });

        cancelLeaveButton.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent e) {
                JDialog d = new JDialog(mainFrame , "Cancel Leave Menu", true);
                d.setLayout( new FlowLayout() );
                d.setBounds(500, 250, 400, 250);
                JButton b = new JButton ("Cancel");
                

                JPanel innerLeaveDatePanel = new JPanel();
                innerLeaveDatePanel.setLayout(new FlowLayout());
                JLabel innerLeaveDateMessage = new JLabel("Enter date of leave to cancel (dd/mm/yyyy) - ");
                JTextField t1 = new JTextField();
                t1.setSize(new Dimension(70,30));
                t1.setMaximumSize(new Dimension(70,30));
                t1.setPreferredSize(new Dimension(70,30));
                innerLeaveDatePanel.add(innerLeaveDateMessage);
                innerLeaveDatePanel.add(t1);

                b.addActionListener (new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        String dateLeaveString = t1.getText();

                        Date dateLeave = new Date();
                        try {
                            dateLeave = new SimpleDateFormat("dd/MM/yyyy").parse(dateLeaveString);
                        } catch (ParseException ex) {
                            ex.printStackTrace();
                        }
                        java.sql.Date dateLeaveSQL = new java.sql.Date(dateLeave.getTime());

                        if (current_employee_leaves.containsKey(dateLeaveSQL)) {
                            current_employee_leaves.remove(dateLeaveSQL);
                            Manager.deleteLeave(emp_ID, dateLeaveSQL);
                            Queries.deleteLeaveQuery(emp_ID, dateLeaveSQL);
                
                            JDialog d2 = new JDialog(mainFrame , "Notification", true);
                            d2.setLayout( new FlowLayout() );
                            d2.setBounds(650, 300, 300, 120);
                            JButton b2 = new JButton ("OK");
                            b2.addActionListener (new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    d2.setVisible(false);
                                }
                            });
                            d2.add(Box.createRigidArea(new Dimension(0, 30)));
                            d2.add( new JLabel ("Your leave application has been cancelled!"));
                            d2.add(Box.createRigidArea(new Dimension(0, 30)));
                            d2.add(b2);
                            d2.setSize(300, 120);
                            d2.setVisible(true);

                            System.out.println("\nThe leave has been cancelled and project manager has been notified.\n");
                        }
                        else {
                            JDialog d2 = new JDialog(mainFrame , "Notification", true);
                            d2.setLayout( new FlowLayout() );
                            d2.setBounds(650, 300, 300, 120);
                            JButton b2 = new JButton ("OK");
                            b2.addActionListener (new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    d2.setVisible(false);
                                }
                            });
                            d2.add(Box.createRigidArea(new Dimension(0, 30)));
                            d2.add( new JLabel ("You haven't applied for a leave on this date."));
                            d2.add(Box.createRigidArea(new Dimension(0, 30)));
                            d2.add(b2);
                            d2.setSize(300, 120);
                            d2.setVisible(true);
                            System.out.println("\nYou haven't applied for a leave on this date.\n");
                            return;
                        }

            
                        d.setVisible(false);
                    }
                });

                d.add(Box.createRigidArea(new Dimension(0, 30)));
                d.add(innerLeaveDatePanel);
                d.add(Box.createRigidArea(new Dimension(50, 0)));
                d.add(b);
                d.setSize(400, 250);
                d.setVisible(true);
                return;
            }
        });

        displayAllLeavesButton.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent e) {
                JDialog d = new JDialog(mainFrame , "Employee Leaves", true);
                d.setLayout(new FlowLayout());
                d.setBounds(550, 200, 400, 400);
                JButton b = new JButton ("OK");
                b.addActionListener (new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        d.setVisible(false);
                    }
                });

                JPanel allLeaveDetailsPanel = new JPanel();
                allLeaveDetailsPanel.setLayout(new BoxLayout(allLeaveDetailsPanel, BoxLayout.Y_AXIS));


                if (current_employee_leaves.isEmpty()) {
                    d.add( new JLabel ("You haven't applied for any leaves yet."));
                }
                else {
                    int currentLeaveNumber = 1;
                    for (Date dateIterator : current_employee_leaves.keySet()) {
                        JPanel leaveDetailsPanel = new JPanel();
                        leaveDetailsPanel.setLayout(new BoxLayout(leaveDetailsPanel, BoxLayout.Y_AXIS));
                        leaveDetailsPanel.add( new JLabel ("------- LEAVE NO. " + currentLeaveNumber + " -----------------------"));
                        leaveDetailsPanel.add( new JLabel ("Date requested - " + dateIterator));
                        leaveDetailsPanel.add( new JLabel ("Leave application sent - " + current_employee_leaves.get(dateIterator)));

                        if (leaveStatusMap.get(dateIterator) == 1) {
                            leaveDetailsPanel.add( new JLabel ("Application status - Sanctioned"));
                        }
                        else if (leaveStatusMap.get(dateIterator) == -1) {
                            leaveDetailsPanel.add( new JLabel ("Application status - Rejected"));
                        }
                        else {
                            leaveDetailsPanel.add( new JLabel ("Application status - Pending"));
                        }

                        leaveDetailsPanel.add( new JLabel ("------------------------------------------------------"));
                        currentLeaveNumber++;
                        allLeaveDetailsPanel.add(leaveDetailsPanel);
                    }
                }

                d.add(allLeaveDetailsPanel);
                d.add(b);
                d.setSize(400, 400);
                d.setVisible(true);
                return;
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent e) {
                JDialog d = new JDialog(mainFrame , "Notice", true);
                d.setLayout( new FlowLayout() );
                d.setBounds(650, 300, 200, 120);
                JButton b = new JButton ("OK");
                b.addActionListener (new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        d.setVisible(false);
                        cardLayout.show(mainFrame.getContentPane(), "mainPanel");
                    }
                });
                d.add(Box.createRigidArea(new Dimension(0, 30)));
                d.add( new JLabel (emp_name + " has been logged out!"));
                d.add(Box.createRigidArea(new Dimension(0, 30)));
                d.add(b);
                d.setSize(200, 120);
                d.setVisible(true);
                return;
            }
        });

        clientSideChatButton.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent e) {
                try {
                    // System.out.println("\nEstablishing connection...\n");
                    // Socket socket = new Socket("127.0.0.1", 5000);  
                    // System.out.println("\nConnected.\n"); 
        
                    // // takes input from terminal 
                    // // BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
                    // DataInputStream in = new DataInputStream( 
                    //     new BufferedInputStream(socket.getInputStream())); 
                    // // sends output to the socket 
                    // DataOutputStream out    = new DataOutputStream(socket.getOutputStream());
                    
                    JDialog d = new JDialog(mainFrame , "Client Side Chat", true);
                    d.setLayout( new FlowLayout() );
                    d.setBounds(650, 300, 200, 120);

                    JPanel chatMessagesArea = new JPanel();
                    chatMessagesArea.setLayout(new BoxLayout(chatMessagesArea, BoxLayout.Y_AXIS));
                    chatMessagesArea.setMaximumSize(new Dimension(200, 70));
                    chatMessagesArea.setPreferredSize(new Dimension(200, 70));


                    JPanel sendMessagesArea = new JPanel();
                    sendMessagesArea.setLayout( new FlowLayout() );

                    JTextField t1 = new JTextField();
                    t1.setSize(new Dimension(70,30));
                    t1.setMaximumSize(new Dimension(70,30));
                    t1.setPreferredSize(new Dimension(70,30));

                    JButton b = new JButton ("Send");
                    b.addActionListener (new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            
                        }
                    });
                    d.add(Box.createRigidArea(new Dimension(0, 30)));
                    d.add( new JLabel (emp_name + " has been logged out!"));
                    d.add(Box.createRigidArea(new Dimension(0, 30)));
                    d.add(b);
                    d.setSize(200, 120);
                    d.setVisible(true);
                } catch (Exception excep) {
                    System.out.println("Error.");
                }
                
            }
        });


        // Creating panels for each button.
        JPanel displayEmployeeDetailsPanel = new JPanel();
        displayEmployeeDetailsPanel.setLayout(new BoxLayout(displayEmployeeDetailsPanel, BoxLayout.X_AXIS));
        displayEmployeeDetailsPanel.add(displayEmployeeDetailsButton);
        JPanel applyLeavePanel = new JPanel();
        applyLeavePanel.setLayout(new BoxLayout(applyLeavePanel, BoxLayout.X_AXIS));
        applyLeavePanel.add(applyLeaveButton);
        JPanel cancelLeavePanel  = new JPanel();
        cancelLeavePanel.setLayout(new BoxLayout(cancelLeavePanel, BoxLayout.X_AXIS));
        cancelLeavePanel.add(cancelLeaveButton);
        JPanel displayAllLeavesPanel  = new JPanel();
        displayAllLeavesPanel.setLayout(new BoxLayout(displayAllLeavesPanel, BoxLayout.X_AXIS));
        displayAllLeavesPanel.add(displayAllLeavesButton);
        JPanel showPolicyPanel  = new JPanel();
        showPolicyPanel.setLayout(new BoxLayout(showPolicyPanel, BoxLayout.X_AXIS));
        showPolicyPanel.add(showPolicyButton);
        // JPanel clientSideChatPanel  = new JPanel();
        // clientSideChatPanel.setLayout(new BoxLayout(clientSideChatPanel, BoxLayout.X_AXIS));
        // clientSideChatPanel.add(clientSideChatButton);
        JPanel logoutPanel  = new JPanel();
        logoutPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        logoutPanel.add(logoutButton);

        JPanel menuButtonPanel = new JPanel();
        menuButtonPanel.setLayout(new BoxLayout(menuButtonPanel,BoxLayout.Y_AXIS));
        menuButtonPanel.add(displayEmployeeDetailsPanel);
        menuButtonPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        menuButtonPanel.add(applyLeavePanel);
        menuButtonPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        menuButtonPanel.add(cancelLeavePanel);
        menuButtonPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        menuButtonPanel.add(displayAllLeavesPanel);
        menuButtonPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        // menuButtonPanel.add(showPolicyPanel);
        // menuButtonPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        // menuButtonPanel.add(clientSideChatPanel);
        // menuButtonPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        menuButtonPanel.add(logoutPanel);

        employeeMenuPanel.add(welcomePanel);
        employeeMenuPanel.add(menuButtonPanel);

        return employeeMenuPanel;
    }
}