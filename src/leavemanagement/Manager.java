package leavemanagement;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

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
  
            DataOutputStream out    = new DataOutputStream(socket.getOutputStream()); 

            String line = "", line2 = "";
            sc.nextLine();
            // reads message from client until "Over" is sent 
            while (!line.equals("Over")) 
            { 
                try
                { 
                    line = in.readUTF(); 
                    System.out.printf("Employee - ");
                    System.out.println(line);
                    System.out.printf("You - ");
                    line2 = sc.nextLine();
                    out.writeUTF(line2);
  
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

    public JPanel getManagerMenuGUI(JFrame mainFrame, CardLayout cardLayout) {
        JPanel managerMenuPanel = new JPanel();
        managerMenuPanel.setLayout(new BoxLayout(managerMenuPanel, BoxLayout.Y_AXIS));

        // Creating the header panel for the employee menu.
        JPanel welcomePanel = new JPanel();
        welcomePanel.setLayout(new GridLayout(1,1));
        welcomePanel.setMaximumSize(new Dimension(300,150));
        welcomePanel.setPreferredSize(new Dimension(300,150));
        JLabel welcomeLabel = new JLabel("Welcome " + manager_name + "!",JLabel.CENTER );
        welcomeLabel.setFont(welcomeLabel.getFont().deriveFont(16.0f));
        welcomePanel.add(welcomeLabel);

        // Creating buttons for employee menu panel.
        JButton displayManagerDetailsButton = new JButton("Display manager details.");
        displayManagerDetailsButton.setPreferredSize(new Dimension(250, 30));
        displayManagerDetailsButton.setMaximumSize(new Dimension(250, 30));
        JButton displayAllLeavesButton = new JButton("View all employees' leave applications.");
        displayAllLeavesButton.setPreferredSize(new Dimension(250, 30));
        displayAllLeavesButton.setMaximumSize(new Dimension(250, 30));
        JButton sanctionLeaveButton = new JButton("Sanction a leave application.");
        sanctionLeaveButton.setPreferredSize(new Dimension(250, 30));
        sanctionLeaveButton.setMaximumSize(new Dimension(250, 30));
        JButton rejectLeaveButton = new JButton("Reject a leave application.");
        rejectLeaveButton.setPreferredSize(new Dimension(250, 30));
        rejectLeaveButton.setMaximumSize(new Dimension(250, 30));
        JButton showPolicyButton = new JButton("Show the company's leave policy.");
        showPolicyButton.setPreferredSize(new Dimension(250, 30));
        showPolicyButton.setMaximumSize(new Dimension(250, 30));
        JButton serverSideChatButton = new JButton("Accept employee chat requests.");
        serverSideChatButton.setPreferredSize(new Dimension(350, 30));
        serverSideChatButton.setMaximumSize(new Dimension(350, 30));
        JButton logoutButton = new JButton("Logout.");
        logoutButton.setPreferredSize(new Dimension(100, 30));
        logoutButton.setMaximumSize(new Dimension(100, 30));

        // Creating button listeners for each button

        displayManagerDetailsButton.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent e) {
                JDialog d = new JDialog(mainFrame , "Manager Details", true);
                d.setLayout( new FlowLayout() );
                d.setBounds(650, 300, 200, 200);
                JButton b = new JButton ("OK");
                b.addActionListener (new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        d.setVisible(false);
                    }
                });
                d.add(Box.createRigidArea(new Dimension(0, 30)));
                d.add( new JLabel ("ID - " + manager_ID));
                d.add(Box.createRigidArea(new Dimension(0, 30)));
                d.add( new JLabel ("Name - " + manager_name));
                d.add(Box.createRigidArea(new Dimension(20, 30)));
                d.add(b);
                d.setSize(200, 200);
                d.setVisible(true);
                return;
            }
        });

        displayAllLeavesButton.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent e) {
                JDialog d = new JDialog(mainFrame , "All Employee Leave Menu", true);
                d.setLayout( new FlowLayout() );
                d.setBounds(500, 250, 400, 250);
                JButton b = new JButton ("OK");
                b.addActionListener (new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        d.setVisible(false);
                    }
                });

                leaveApplicationQueue = Queries.getLeaveManagerQuery();

                JPanel allLeaveDetailsPanel = new JPanel();
                allLeaveDetailsPanel.setLayout(new BoxLayout(allLeaveDetailsPanel, BoxLayout.Y_AXIS));


                if (leaveApplicationQueue.isEmpty()) {
                    d.add( new JLabel ("You haven't applied for any leaves yet."));
                }
                else {
                    for (Leave obj : leaveApplicationQueue) {
                        JPanel leaveDetailsPanel = new JPanel();
                        leaveDetailsPanel.setLayout(new BoxLayout(leaveDetailsPanel, BoxLayout.Y_AXIS));
                        leaveDetailsPanel.add( new JLabel ("--------------------------------------------------"));
                        leaveDetailsPanel.add( new JLabel ("Leave requested by employee ID - " + obj.emp_ID));
                        leaveDetailsPanel.add( new JLabel ("Date requested - " + obj.dateLeave));
                        leaveDetailsPanel.add( new JLabel ("Leave application sent - " + obj.leaveMessage));

                        if (obj.leaveStatus == 1) {
                            leaveDetailsPanel.add( new JLabel ("Application status - Sanctioned"));
                        }
                        else if (obj.leaveStatus == -1) {
                            leaveDetailsPanel.add( new JLabel ("Application status - Rejected"));
                        }
                        else {
                            leaveDetailsPanel.add( new JLabel ("Application status - Pending"));
                        }

                        leaveDetailsPanel.add( new JLabel ("------------------------------------------------------"));
                        allLeaveDetailsPanel.add(leaveDetailsPanel);
                    }
                }

                d.add(allLeaveDetailsPanel);
                d.add(b);

                d.setSize(400, 250);
                d.setVisible(true);
                return;
            }
        });

        sanctionLeaveButton.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent e) {
                JDialog d = new JDialog(mainFrame , "Sanction Leave Menu", true);
                d.setLayout( new FlowLayout() );
                d.setBounds(500, 250, 400, 250);
                JButton b = new JButton ("Sanction");
                
                JPanel innerLeaveIDPanel = new JPanel();
                innerLeaveIDPanel.setLayout(new FlowLayout());
                JLabel innerLeaveIDMessage = new JLabel("Enter ID of employee - ");
                JTextField t2 = new JTextField();
                t2.setSize(new Dimension(70,30));
                t2.setMaximumSize(new Dimension(70,30));
                t2.setPreferredSize(new Dimension(70,30));
                innerLeaveIDPanel.add(innerLeaveIDMessage);
                innerLeaveIDPanel.add(t2);

                JPanel innerLeaveDatePanel = new JPanel();
                innerLeaveDatePanel.setLayout(new FlowLayout());
                JLabel innerLeaveDateMessage = new JLabel("Enter date of leave to sanction (dd/mm/yyyy) - ");
                JTextField t1 = new JTextField();
                t1.setSize(new Dimension(70,30));
                t1.setMaximumSize(new Dimension(70,30));
                t1.setPreferredSize(new Dimension(70,30));
                innerLeaveDatePanel.add(innerLeaveDateMessage);
                innerLeaveDatePanel.add(t1);

                b.addActionListener (new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        String dateLeaveString = t1.getText();
                        int sanctionID = Integer.parseInt(t2.getText());

                        Date dateLeave = new Date();
                        try {
                            dateLeave = new SimpleDateFormat("dd/MM/yyyy").parse(dateLeaveString);
                        } catch (ParseException ex) {
                            ex.printStackTrace();
                        }
                        java.sql.Date dateLeaveSQL = new java.sql.Date(dateLeave.getTime());

                        Queries.sanctionLeaveApplicationQuery(sanctionID, dateLeaveSQL);

                        JDialog d2 = new JDialog(mainFrame , "Notification", true);
                            d2.setLayout( new FlowLayout() );
                            d2.setBounds(650, 300, 300, 120);
                            JButton b2 = new JButton ("OK");
                            b2.addActionListener (new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    d2.setVisible(false);
                                    d.setVisible(false);
                                }
                            });
                            d2.add(Box.createRigidArea(new Dimension(0, 30)));
                            d2.add( new JLabel ("The leave application has been sanctioned!"));
                            d2.add(Box.createRigidArea(new Dimension(0, 30)));
                            d2.add(b2);
                            d2.setSize(300, 120);
                            d2.setVisible(true);
                            // d.setVisible(false);
                    }
                });

                d.add(Box.createRigidArea(new Dimension(0, 30)));
                d.add(innerLeaveIDPanel);
                d.add(Box.createRigidArea(new Dimension(0, 30)));
                d.add(innerLeaveDatePanel);
                d.add(Box.createRigidArea(new Dimension(50, 0)));
                d.add(b);
                d.setSize(400, 250);
                d.setVisible(true);
                return;
            }
        });

        rejectLeaveButton.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent e) {
                JDialog d = new JDialog(mainFrame , "Reject Leave Menu", true);
                d.setLayout(new FlowLayout());
                d.setBounds(550, 200, 400, 400);
                JButton b = new JButton ("Reject");

                JPanel innerLeaveIDPanel = new JPanel();
                innerLeaveIDPanel.setLayout(new FlowLayout());
                JLabel innerLeaveIDMessage = new JLabel("Enter ID of employee - ");
                JTextField t2 = new JTextField();
                t2.setSize(new Dimension(70,30));
                t2.setMaximumSize(new Dimension(70,30));
                t2.setPreferredSize(new Dimension(70,30));
                innerLeaveIDPanel.add(innerLeaveIDMessage);
                innerLeaveIDPanel.add(t2);

                JPanel innerLeaveDatePanel = new JPanel();
                innerLeaveDatePanel.setLayout(new FlowLayout());
                JLabel innerLeaveDateMessage = new JLabel("Enter date of leave to reject (dd/mm/yyyy) - ");
                JTextField t1 = new JTextField();
                t1.setSize(new Dimension(70,30));
                t1.setMaximumSize(new Dimension(70,30));
                t1.setPreferredSize(new Dimension(70,30));
                innerLeaveDatePanel.add(innerLeaveDateMessage);
                innerLeaveDatePanel.add(t1);

                b.addActionListener (new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        String dateLeaveString = t1.getText();
                        int rejectID = Integer.parseInt(t2.getText());

                        Date dateLeave = new Date();
                        try {
                            dateLeave = new SimpleDateFormat("dd/MM/yyyy").parse(dateLeaveString);
                        } catch (ParseException ex) {
                            ex.printStackTrace();
                        }
                        java.sql.Date dateLeaveSQL = new java.sql.Date(dateLeave.getTime());

                        Queries.rejectLeaveApplicationQuery(rejectID, dateLeaveSQL);

                        JDialog d2 = new JDialog(mainFrame , "Notification", true);
                            d2.setLayout( new FlowLayout() );
                            d2.setBounds(650, 300, 300, 120);
                            JButton b2 = new JButton ("OK");
                            b2.addActionListener (new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    d2.setVisible(false);
                                    d.setVisible(false);
                                }
                            });
                            d2.add(Box.createRigidArea(new Dimension(0, 30)));
                            d2.add( new JLabel ("The leave application has been rejected!"));
                            d2.add(Box.createRigidArea(new Dimension(0, 30)));
                            d2.add(b2);
                            d2.setSize(300, 120);
                            d2.setVisible(true);
                            // d.setVisible(false);
                    }
                });

                d.add(Box.createRigidArea(new Dimension(0, 30)));
                d.add(innerLeaveIDPanel);
                d.add(Box.createRigidArea(new Dimension(0, 30)));
                d.add(innerLeaveDatePanel);
                d.add(Box.createRigidArea(new Dimension(50, 0)));
                d.add(b);
                d.setSize(400, 250);
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
                d.add( new JLabel (manager_name + " has been logged out!"));
                d.add(Box.createRigidArea(new Dimension(0, 30)));
                d.add(b);
                d.setSize(200, 120);
                d.setVisible(true);
                return;
            }
        });

        serverSideChatButton.addActionListener(new ActionListener() {
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
                    d.add( new JLabel (manager_name + " has been logged out!"));
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
        displayEmployeeDetailsPanel.add(displayManagerDetailsButton);
        JPanel displayAllLeavesPanel = new JPanel();
        displayAllLeavesPanel.setLayout(new BoxLayout(displayAllLeavesPanel, BoxLayout.X_AXIS));
        displayAllLeavesPanel.add(displayAllLeavesButton);
        JPanel sanctionLeavePanel  = new JPanel();
        sanctionLeavePanel.setLayout(new BoxLayout(sanctionLeavePanel, BoxLayout.X_AXIS));
        sanctionLeavePanel.add(sanctionLeaveButton);
        JPanel rejectLeavePanel  = new JPanel();
        rejectLeavePanel.setLayout(new BoxLayout(rejectLeavePanel, BoxLayout.X_AXIS));
        rejectLeavePanel.add(rejectLeaveButton);
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
        menuButtonPanel.add(displayAllLeavesPanel);
        menuButtonPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        menuButtonPanel.add(sanctionLeavePanel);
        menuButtonPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        menuButtonPanel.add(rejectLeavePanel);
        menuButtonPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        menuButtonPanel.add(showPolicyPanel);
        menuButtonPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        // menuButtonPanel.add(clientSideChatPanel);
        // menuButtonPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        menuButtonPanel.add(logoutPanel);

        managerMenuPanel.add(welcomePanel);
        managerMenuPanel.add(menuButtonPanel);

        return managerMenuPanel;
    }
}