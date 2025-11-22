import java.util.InputMismatchException;
import java.util.Scanner;

class InvalidMarksException extends Exception {
    public InvalidMarksException(String message) {
        super(message);
    }
}

class Student {
    private int rollNumber;
    private String studentName;
    private int[] marks = new int[3];

    public Student(int rollNumber, String studentName, int[] marks) {
        this.rollNumber = rollNumber;
        this.studentName = studentName;
        this.marks = marks;
    }

    public void validateMarks() throws InvalidMarksException {
        for (int i = 0; i < marks.length; i++) {
            if (marks[i] < 0 || marks[i] > 100) {
                throw new InvalidMarksException("Invalid marks for subject " + (i + 1) + ": " + marks[i]);
            }
        }
    }

    public double calculateAverage() {
        return (marks[0] + marks[1] + marks[2]) / 3.0;
    }

    public void displayResult() {
        System.out.println("Roll Number : " + rollNumber);
        System.out.println("Name        : " + studentName);
        System.out.println("Marks       : " + marks[0] + ", " + marks[1] + ", " + marks[2]);
        double avg = calculateAverage();
        System.out.println("Average     : " + avg);
        System.out.println("Result      : " + (avg >= 40 ? "Pass" : "Fail"));
    }

    public int getRollNumber() {
        return rollNumber;
    }
}

public class ResultManager {

    private Student[] students = new Student[100];
    private int count = 0;
    private Scanner sc = new Scanner(System.in);

    public void addStudent() throws InvalidMarksException {
        System.out.print("Enter Roll Number: ");
        int roll = readInt();

        System.out.print("Enter Student Name: ");
        sc.nextLine();
        String name = sc.nextLine();

        int[] marks = new int[3];

        for (int i = 0; i < 3; i++) {
            System.out.print("Enter marks for subject " + (i + 1) + ": ");
            marks[i] = readInt();
        }

        Student s = new Student(roll, name, marks);
        s.validateMarks();   // may throw custom exception

        students[count++] = s;
        System.out.println("Student added successfully.");
    }

    public void showStudentDetails() {
        System.out.print("Enter Roll Number to search: ");
        int roll = readInt();

        for (int i = 0; i < count; i++) {
            if (students[i].getRollNumber() == roll) {
                students[i].displayResult();
                return;
            }
        }
        System.out.println("Student not found.");
    }

    private int readInt() {
        while (true) {
            try {
                return sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.print("Invalid input. Enter a valid number: ");
                sc.nextLine();
            }
        }
    }

    public void mainMenu() {
        try {
            while (true) {
                System.out.println("\n===== Student Result Management System =====");
                System.out.println("1. Add Student");
                System.out.println("2. Show Student Details");
                System.out.println("3. Exit");
                System.out.print("Enter your choice: ");

                int choice = readInt();

                try {
                    switch (choice) {
                        case 1:
                            addStudent();
                            break;

                        case 2:
                            showStudentDetails();
                            break;

                        case 3:
                            System.out.println("Exiting system...");
                            return;

                        default:
                            System.out.println("Invalid choice.");
                    }
                } catch (InvalidMarksException e) {
                    System.out.println("Error: " + e.getMessage() + ". Returning to main menu...");
                } catch (NullPointerException e) {
                    System.out.println("Error: Missing data encountered.");
                } catch (Exception e) {
                    System.out.println("Unexpected error: " + e.getMessage());
                }
            }
        } finally {
            System.out.println("System closed.");
            sc.close();
        }
    }

    public static void main(String[] args) {
        ResultManager rm = new ResultManager();
        rm.mainMenu();
    }
}
