import java.util.Scanner;

public class BankingApplication {

    // ------------------ ACCOUNT CLASS ------------------
    static class Account {
        private int accountNumber;
        private String accountHolderName;
        private double balance;
        private String email;
        private String phoneNumber;

        public Account(int accountNumber, String accountHolderName, double initialDeposit,
                       String email, String phoneNumber) {
            this.accountNumber = accountNumber;
            this.accountHolderName = accountHolderName;
            this.balance = Math.max(0.0, initialDeposit);
            this.email = email;
            this.phoneNumber = phoneNumber;
        }

        public int getAccountNumber() { return accountNumber; }
        public double getBalance() { return balance; }
        public String getEmail() { return email; }
        public String getPhoneNumber() { return phoneNumber; }

        public boolean deposit(double amount) {
            if (amount <= 0) {
                System.out.println("Deposit amount must be positive.");
                return false;
            }
            balance += amount;
            return true;
        }

        public boolean withdraw(double amount) {
            if (amount <= 0) {
                System.out.println("Withdrawal amount must be positive.");
                return false;
            }
            if (amount > balance) {
                System.out.println("Insufficient balance.");
                return false;
            }
            balance -= amount;
            return true;
        }

        public void updateContactDetails(String email, String phoneNumber) {
            // Email check
            if (email != null && email.contains("@") && email.length() >= 5) {
                this.email = email;
            } else {
                System.out.println("Invalid email format.");
            }

            // Phone number check → must be exactly 10 digits
            if (phoneNumber != null && phoneNumber.matches("\\d{10}")) {
                this.phoneNumber = phoneNumber;
            } else {
                System.out.println("Phone number must be EXACTLY 10 digits.");
            }
        }

        public void displayAccountDetails() {
            System.out.println("----- Account Details -----");
            System.out.println("Account Number  : " + accountNumber);
            System.out.println("Account Holder  : " + accountHolderName);
            System.out.printf("Balance         : %.2f\n", balance);
            System.out.println("Email           : " + email);
            System.out.println("Phone Number    : " + phoneNumber);
            System.out.println("---------------------------");
        }
    }

    // ------------------ APPLICATION CLASS ------------------
    private static final int MAX_ACCOUNTS = 100;
    private Account[] accounts = new Account[MAX_ACCOUNTS];
    private int accountCount = 0;
    private int nextAccountNumber = 1001;
    private Scanner sc = new Scanner(System.in);

    public void createAccount() {
        System.out.print("Enter account holder name: ");
        String name = readNonEmpty();

        System.out.print("Enter initial deposit: ");
        double initialDeposit = readDouble();

        System.out.print("Enter email: ");
        String email = sc.nextLine().trim();

        // Phone number validation loop
        String phone;
        while (true) {
            System.out.print("Enter phone number (10 digits): ");
            phone = sc.nextLine().trim();
            if (phone.matches("\\d{10}"))
                break;
            System.out.println("Invalid! Phone number must be exactly 10 digits.");
        }

        int accNum = nextAccountNumber++;
        accounts[accountCount++] = new Account(accNum, name, initialDeposit, email, phone);

        System.out.println("Account created successfully! Account Number: " + accNum);
    }

    public void performDeposit() {
        Account acc = findAccount();
        if (acc == null) return;

        System.out.print("Enter amount to deposit: ");
        double amount = readDouble();

        if (acc.deposit(amount)) {
            System.out.println("Deposit successful. New balance: " + acc.getBalance());
        }
    }

    public void performWithdrawal() {
        Account acc = findAccount();
        if (acc == null) return;

        System.out.print("Enter amount to withdraw: ");
        double amount = readDouble();

        if (acc.withdraw(amount)) {
            System.out.println("Withdrawal successful. New balance: " + acc.getBalance());
        }
    }

    public void showAccountDetails() {
        Account acc = findAccount();
        if (acc != null) acc.displayAccountDetails();
    }

    public void updateContact() {
        Account acc = findAccount();
        if (acc == null) return;

        System.out.print("Enter new email (or press Enter to keep same): ");
        String email = sc.nextLine().trim();
        if (email.isEmpty()) email = acc.getEmail();

        String phone;
        while (true) {
            System.out.print("Enter new phone number (10 digits) or press Enter to keep same: ");
            phone = sc.nextLine().trim();
            if (phone.isEmpty()) {
                phone = acc.getPhoneNumber();
                break;
            }
            if (phone.matches("\\d{10}"))
                break;

            System.out.println("Phone number must be exactly 10 digits!");
        }

        acc.updateContactDetails(email, phone);
    }

    public void mainMenu() {
        while (true) {
            System.out.println("\n----- Banking Application -----");
            System.out.println("1. Create Account");
            System.out.println("2. Deposit Money");
            System.out.println("3. Withdraw Money");
            System.out.println("4. View Account Details");
            System.out.println("5. Update Contact Details");
            System.out.println("6. Exit");
            System.out.print("Enter choice: ");

            int c = readInt();

            switch (c) {
                case 1: createAccount(); break;
                case 2: performDeposit(); break;
                case 3: performWithdrawal(); break;
                case 4: showAccountDetails(); break;
                case 5: updateContact(); break;
                case 6:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    // ------------------ HELPER METHODS ------------------
    private Account findAccount() {
        System.out.print("Enter account number: ");
        int accNum = readInt();
        for (int i = 0; i < accountCount; i++) {
            if (accounts[i].getAccountNumber() == accNum)
                return accounts[i];
        }
        System.out.println("Account not found.");
        return null;
    }

    private String readNonEmpty() {
        while (true) {
            String s = sc.nextLine().trim();
            if (!s.isEmpty()) return s;
            System.out.print("Cannot be empty. Enter again: ");
        }
    }

    private int readInt() {
        while (true) {
            try { return Integer.parseInt(sc.nextLine().trim()); }
            catch (Exception e) { System.out.print("Enter valid integer: "); }
        }
    }

    private double readDouble() {
        while (true) {
            try { return Double.parseDouble(sc.nextLine().trim()); }
            catch (Exception e) { System.out.print("Enter valid amount: "); }
        }
    }

    // ------------------ MAIN METHOD ------------------
    public static void main(String[] args) {
        new BankingApplication().mainMenu();
    }
}
