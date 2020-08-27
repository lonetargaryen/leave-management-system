package leavemanagement;

import java.util.Date;

public class Leave {
    int emp_ID, leaveStatus;
    Date dateLeave;
    String leaveMessage;

    public Leave() {
        emp_ID = -1;
        dateLeave = new Date();
        leaveMessage = "";
        leaveStatus = 0;
    }

    public Leave(int emp_ID, Date dateLeave, String leaveMessage, int leaveStatus) {
        this.emp_ID = emp_ID;
        this.dateLeave = dateLeave;
        this.leaveMessage = leaveMessage;
        this.leaveStatus = leaveStatus;
    }

    public void printLeaveDetails() {
        System.out.println("\n--------------------------------------------------");
        System.out.println("\nLeave requested by employee ID - " + emp_ID);
        System.out.println("\nLeave date - " + dateLeave);
        System.out.println("\nLeave application - " + leaveMessage);
        if (leaveStatus == 1) {
            System.out.println("\nThis leave has been sanctioned.\n");
        }
        else if (leaveStatus == -1) {
            System.out.println("\nThis leave has been rejected.\n");
        }
        else {
            System.out.println("\nThis leave has not yet been sanctioned.\n");
        }
        System.out.println("--------------------------------------------------\n");
    }
}