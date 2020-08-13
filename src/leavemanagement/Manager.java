package leavemanagement;

public class Manager {
    
    public String manager_name;
    public int manager_ID;

    public Manager() {
        manager_name = "";
        manager_ID = 0;
    }

    public Manager(String manager_name, int manager_ID) {
        this.manager_name = manager_name;
        this.manager_ID = manager_ID;
    }

    public void displayDetails() {
        System.out.println("name - " + manager_name + " ID - " + manager_ID);
    }
}