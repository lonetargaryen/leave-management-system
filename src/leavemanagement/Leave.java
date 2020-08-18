package leavemanagement;

import java.util.Date;

public class Leave {
    int emp_ID;
    Date dateLeave;
    String leaveMessage;

    public Leave() {
        emp_ID = -1;
        dateLeave = new Date();
        leaveMessage = "";
    }

    public Leave(int emp_ID, Date dateLeave, String leaveMessage) {
        this.emp_ID = emp_ID;
        this.dateLeave = dateLeave;
        this.leaveMessage = leaveMessage;
    }

    public void printLeaveDetails() {
        System.out.println("\nLeave requested by employee ID - " + emp_ID);
        System.out.println("\nLeave date - " + dateLeave);
        System.out.println("\nLeave application - " + leaveMessage);
    }
}