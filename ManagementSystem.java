import java.util.Scanner;

class Employee {
    protected int employeeId;
    protected String name;
    protected double salary;

    public Employee(int employeeId, String name, double salary) {
        this.employeeId = employeeId;
        this.name = name;
        this.salary = salary;
    }
    
    public void setDetails(int employeeId, String name) {
        this.employeeId = employeeId;
        this.name = name;
    }

    public void setDetails(int employeeId, String name, double salary) {
        this.employeeId = employeeId;
        this.name = name;
        this.salary = salary;
    }

    //  (overridden in subclasses)
    public double calculateBonus() {
        return salary * 0.05;
    }

    public void displayDetails() {
        System.out.println("Employee ID: " + employeeId);
        System.out.println("Name       : " + name);
        System.out.println("Salary     : " + salary);
        System.out.println("Bonus      : " + calculateBonus());
    }

    public int getEmployeeId() {
        return employeeId;
    }
}

class Manager extends Employee {
    private String department;

    public Manager(int employeeId, String name, double salary, String department) {
        super(employeeId, name, salary);
        this.department = department;
    }

    @Override
    public double calculateBonus() {
        return salary * 0.10;
    }

    @Override
    public void displayDetails() {
        System.out.println("---- Manager Details ----");
        super.displayDetails();
        System.out.println("Department : " + department);
    }
}

class Developer extends Employee {
    private String programmingLanguage;

    public Developer(int employeeId, String name, double salary, String programmingLanguage) {
        super(employeeId, name, salary);
        this.programmingLanguage = programmingLanguage;
    }

    @Override
    public double calculateBonus() {
        return salary * 0.07;
    }

    @Override
    public void displayDetails() {
        System.out.println("---- Developer Details ----");
        super.displayDetails();
        System.out.println("Language   : " + programmingLanguage);
    }
}

public class ManagementSystem {

    private static final int MAX = 100;
    private Employee[] employees = new Employee[MAX];
    private int count = 0;

    Scanner sc = new Scanner(System.in);

    // Add Manager
    public void addManager() {
        System.out.print("Enter Employee ID: ");
        int id = sc.nextInt(); sc.nextLine();

        System.out.print("Enter Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Salary: ");
        double salary = sc.nextDouble(); sc.nextLine();

        System.out.print("Enter Department: ");
        String dept = sc.nextLine();

        employees[count++] = new Manager(id, name, salary, dept);
        System.out.println("Manager added successfully.\n");
    }

    // Add Developer
    public void addDeveloper() {
        System.out.print("Enter Employee ID: ");
        int id = sc.nextInt(); sc.nextLine();

        System.out.print("Enter Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Salary: ");
        double salary = sc.nextDouble(); sc.nextLine();

        System.out.print("Enter Programming Language: ");
        String lang = sc.nextLine();

        employees[count++] = new Developer(id, name, salary, lang);
        System.out.println("Developer added successfully.\n");
    }

    // Display specific employee
    public void displayEmployee() {
        System.out.print("Enter Employee ID to search: ");
        int id = sc.nextInt();

        for (int i = 0; i < count; i++) {
            if (employees[i].getEmployeeId() == id) {
                employees[i].displayDetails();
                return;
            }
        }
        System.out.println("Employee not found.\n");
    }

    // Display all employees
    public void displayAll() {
        if (count == 0) {
            System.out.println("No employees available.\n");
            return;
        }

        for (int i = 0; i < count; i++) {
            employees[i].displayDetails();
            System.out.println("---------------------------");
        }
    }

    // Main Menu
    public void menu() {
        while (true) {
            System.out.println("\nEmployee Management System");
            System.out.println("1. Add Manager");
            System.out.println("2. Add Developer");
            System.out.println("3. Display Employee Details");
            System.out.println("4. Display All Employees");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            int ch = sc.nextInt();

            switch (ch) {
                case 1: addManager(); break;
                case 2: addDeveloper(); break;
                case 3: displayEmployee(); break;
                case 4: displayAll(); break;
                case 5:
                    System.out.println("Exiting system...");
                    return;
                default:
                    System.out.println("Invalid choice.\n");
            }
        }
    }

    public static void main(String[] args) {
        ManagementSystem ms = new ManagementSystem();
        ms.menu();
    }
}
