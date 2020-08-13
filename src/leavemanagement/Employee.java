package leavemanagement;

public class Employee {

    public String emp_name;
    public int emp_ID;
    public String emp_designation;

    public Employee() {
        emp_name = "";
        emp_ID = 0;
        emp_designation = "";
    }

    public Employee(String emp_name, int emp_ID, String emp_designation) {
        this.emp_name = emp_name;
        this.emp_ID = emp_ID;
        this.emp_designation = emp_designation;
    }

    public void displayDetails() {
        System.out.println("name - " + emp_name + " ID - " + emp_ID + " designation - " + emp_designation);
    }

    public void displayEmployeeMenu() {
        System.out.println("\n---------- EMPLOYEE MENU ----------\n");
        System.out.println("1. Display employee details.\n");
        System.out.println("2. Apply for a leave.\n");
        System.out.println("3. Cancel a leave application.\n");
        System.out.println("4. View all my leave applications.\n");
        System.out.println("5. Show company's leave policy.\n");
        System.out.println("6. Logout.");
    }
}