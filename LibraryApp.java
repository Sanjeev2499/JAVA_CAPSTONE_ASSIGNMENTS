import java.util.Scanner;

class Book {
    private String title;
    private String author;
    private boolean isAvailable;

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
        this.isAvailable = true;
    }

    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public boolean isAvailable() { return isAvailable; }
    public void borrow() { isAvailable = false; }
    public void returned() { isAvailable = true; }

    @Override
    public String toString() {
        return title + " by " + author + " | Status: " + (isAvailable ? "Available" : "Borrowed");
    }
}

class Library {
    private Book[] books = new Book[100];
    private int count = 0;

    public void addBook(Book b) {
        if (count < books.length) {
            books[count++] = b;
            System.out.println("Book added successfully.");
        } else {
            System.out.println("Library is full.");
        }
    }

    // Overloaded – search by title
    public Book searchBook(String title) {
        for (int i = 0; i < count; i++) {
            if (books[i].getTitle().equalsIgnoreCase(title)) {
                return books[i];
            }
        }
        return null;
    }

    // Overloaded – search by author
    public void searchBookByAuthor(String author) {
        boolean found = false;
        for (int i = 0; i < count; i++) {
            if (books[i].getAuthor().equalsIgnoreCase(author)) {
                System.out.println(books[i]);
                found = true;
            }
        }
        if (!found) System.out.println("No books found by this author.");
    }

    public void borrowBook(String title) {
        Book b = searchBook(title);
        if (b == null) {
            System.out.println("Book not found.");
        } else if (!b.isAvailable()) {
            System.out.println("Book is already borrowed.");
        } else {
            b.borrow();
            System.out.println("You borrowed: " + b.getTitle());
        }
    }

    public void returnBook(String title) {
        Book b = searchBook(title);
        if (b == null) {
            System.out.println("Book not found.");
        } else if (b.isAvailable()) {
            System.out.println("Book is already available.");
        } else {
            b.returned();
            System.out.println("Book returned successfully.");
        }
    }

    public void displayAllBooks() {
        if (count == 0) {
            System.out.println("No books in the library.");
            return;
        }
        for (int i = 0; i < count; i++) {
            System.out.println(books[i]);
        }
    }
}

public class LibraryApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Library library = new Library();

        while (true) {
            System.out.println("\nWelcome to the Library Management System!");
            System.out.println("1. Add a new book");
            System.out.println("2. Search for a book by title");
            System.out.println("3. Search for books by author");
            System.out.println("4. Borrow a book");
            System.out.println("5. Return a book");
            System.out.println("6. Display all books");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");

            int choice;
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.out.println("Invalid input.");
                continue;
            }

            switch (choice) {
                case 1:
                    System.out.print("Enter book title: ");
                    String title = sc.nextLine();
                    System.out.print("Enter book author: ");
                    String author = sc.nextLine();
                    library.addBook(new Book(title, author));
                    break;

                case 2:
                    System.out.print("Enter book title: ");
                    String searchTitle = sc.nextLine();
                    Book result = library.searchBook(searchTitle);
                    if (result != null)
                        System.out.println("Book found: " + result);
                    else
                        System.out.println("Book not found.");
                    break;

                case 3:
                    System.out.print("Enter author name: ");
                    String searchAuthor = sc.nextLine();
                    library.searchBookByAuthor(searchAuthor);
                    break;

                case 4:
                    System.out.print("Enter title to borrow: ");
                    library.borrowBook(sc.nextLine());
                    break;

                case 5:
                    System.out.print("Enter title to return: ");
                    library.returnBook(sc.nextLine());
                    break;

                case 6:
                    library.displayAllBooks();
                    break;

                case 7:
                    System.out.println("Exiting system...");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}
