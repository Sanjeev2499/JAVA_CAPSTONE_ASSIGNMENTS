import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Contact {
    private String name;
    private String phoneNumber;
    private String email;

    public Contact(String name, String phoneNumber, String email) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public String getName() { return name; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getEmail() { return email; }

    public String toFileFormat() {
        return name + "," + phoneNumber + "," + email;
    }

    @Override
    public String toString() {
        return "Name: " + name + " | Phone: " + phoneNumber + " | Email: " + email;
    }
}

class ContactManager {
    private HashMap<String, Contact> contacts = new HashMap<>();

    public void addContact(String name, String phone, String email) {
        contacts.put(name, new Contact(name, phone, email));
    }

    public Contact searchContact(String name) {
        return contacts.get(name);
    }

    public boolean removeContact(String name) {
        return contacts.remove(name) != null;
    }

    public void displayAllContacts() {
        if (contacts.isEmpty()) {
            System.out.println("No contacts available.");
            return;
        }
        for (Map.Entry<String, Contact> entry : contacts.entrySet()) {
            System.out.println(entry.getValue());
        }
    }

    public void saveContactsToFile(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Contact c : contacts.values()) {
                writer.write(c.toFileFormat());
                writer.newLine();
            }
            System.out.println("Contacts saved to file successfully.");
        } catch (IOException e) {
            System.out.println("Error saving contacts: " + e.getMessage());
        }
    }

    public void loadContactsFromFile(String filename) {
        File file = new File(filename);

        if (!file.exists()) {
            System.out.println("File not found.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            contacts.clear();
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");

                if (parts.length == 3) {
                    String name = parts[0];
                    String phone = parts[1];
                    String email = parts[2];
                    contacts.put(name, new Contact(name, phone, email));
                }
            }
            System.out.println("Contacts loaded successfully.");
        } catch (IOException e) {
            System.out.println("Error loading contacts: " + e.getMessage());
        }
    }
}

public class ContactListApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ContactManager manager = new ContactManager();

        while (true) {
            System.out.println("\nWelcome to the Contact List Application!");
            System.out.println("1. Add Contact");
            System.out.println("2. Search Contact");
            System.out.println("3. Display All Contacts");
            System.out.println("4. Save to File");
            System.out.println("5. Load from File");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            int choice = 0;
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.out.println("Invalid choice. Try again.");
                continue;
            }

            switch (choice) {
                case 1:
                    System.out.print("Enter contact name: ");
                    String name = sc.nextLine();

                    System.out.print("Enter phone number: ");
                    String phone = sc.nextLine();

                    System.out.print("Enter email: ");
                    String email = sc.nextLine();

                    manager.addContact(name, phone, email);
                    System.out.println("Contact added successfully.");
                    break;

                case 2:
                    System.out.print("Enter name to search: ");
                    String searchName = sc.nextLine();
                    Contact c = manager.searchContact(searchName);

                    if (c != null)
                        System.out.println(c);
                    else
                        System.out.println("Contact not found.");

                    break;

                case 3:
                    manager.displayAllContacts();
                    break;

                case 4:
                    System.out.print("Enter filename to save: ");
                    String saveFile = sc.nextLine();
                    manager.saveContactsToFile(saveFile);
                    break;

                case 5:
                    System.out.print("Enter filename to load: ");
                    String loadFile = sc.nextLine();
                    manager.loadContactsFromFile(loadFile);
                    break;

                case 6:
                    System.out.println("Exiting application...");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}
