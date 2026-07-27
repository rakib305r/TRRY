import java.util.ArrayList;
import java.util.Scanner;

/**
 * Console based Mini Library Management System.
 * Books are stored in memory using an ArrayList and the user
 * interacts with the program through a menu driven interface.
 */
public class LibraryManagement {

    // In-memory storage for all books
    private static final ArrayList<Book> books = new ArrayList<>();

    // Single Scanner instance used for every input in the program
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        printWelcomeBanner();

        boolean running = true;
        while (running) {
            printMenu();
            int choice = readInt("Enter your choice (1-9): ");

            switch (choice) {
                case 1:
                    addBook();
                    break;
                case 2:
                    viewAllBooks();
                    break;
                case 3:
                    searchBookById();
                    break;
                case 4:
                    searchBookByName();
                    break;
                case 5:
                    issueBook();
                    break;
                case 6:
                    returnBook();
                    break;
                case 7:
                    deleteBook();
                    break;
                case 8:
                    displayTotalBooks();
                    break;
                case 9:
                    running = false;
                    System.out.println("\nThank you for using the Library Management System. Goodbye!");
                    break;
                default:
                    System.out.println("Error: Invalid choice. Please enter a number between 1 and 9.");
            }
        }

        scanner.close();
    }

    // ---------------- User interface helpers ----------------

    /**
     * Prints a professional welcome banner shown once at startup.
     */
    private static void printWelcomeBanner() {
        System.out.println("==========================================================");
        System.out.println("||                                                      ||");
        System.out.println("||           MINI LIBRARY MANAGEMENT SYSTEM             ||");
        System.out.println("||               Java Console Application               ||");
        System.out.println("||                                                      ||");
        System.out.println("==========================================================");
    }

    /**
     * Prints the main menu options.
     */
    private static void printMenu() {
        System.out.println("\n---------------------- MAIN MENU ----------------------");
        System.out.println("1. Add Book");
        System.out.println("2. View All Books");
        System.out.println("3. Search Book by ID");
        System.out.println("4. Search Book by Name");
        System.out.println("5. Issue Book");
        System.out.println("6. Return Book");
        System.out.println("7. Delete Book");
        System.out.println("8. Display Total Number of Books");
        System.out.println("9. Exit Program");
        System.out.println("-------------------------------------------------------");
    }

    // ---------------- Feature 1: Add Book ----------------

    private static void addBook() {
        System.out.println("\n--- Add Book ---");

        int id = readInt("Enter Book ID (positive number): ");
        if (id <= 0) {
            System.out.println("Error: Book ID must be a positive number.");
            return;
        }

        // Duplicate Book IDs are not allowed
        if (findBookById(id) != null) {
            System.out.println("Error: A book with ID " + id + " already exists.");
            return;
        }

        String name = readNonEmptyText("Enter Book Name: ");
        String author = readNonEmptyText("Enter Author Name: ");
        String category = readNonEmptyText("Enter Category: ");

        books.add(new Book(id, name, author, category));
        System.out.println("Success: Book added successfully.");
    }

    // ---------------- Feature 2: View All Books ----------------

    private static void viewAllBooks() {
        System.out.println("\n--- All Books ---");
        if (books.isEmpty()) {
            System.out.println("No books available in the library.");
            return;
        }

        printTableHeader();
        for (Book book : books) {
            book.displayBook();
        }
        printTableLine();
        System.out.println("Total books: " + books.size());
    }

    // ---------------- Feature 3: Search Book by ID ----------------

    private static void searchBookById() {
        System.out.println("\n--- Search Book by ID ---");
        int id = readInt("Enter Book ID: ");

        Book book = findBookById(id);
        if (book == null) {
            System.out.println("Error: No book found with ID " + id + ".");
            return;
        }

        printTableHeader();
        book.displayBook();
        printTableLine();
    }

    // ---------------- Feature 4: Search Book by Name ----------------

    private static void searchBookByName() {
        System.out.println("\n--- Search Book by Name ---");
        String keyword = readNonEmptyText("Enter Book Name (or part of it): ").toLowerCase();

        boolean found = false;
        for (Book book : books) {
            if (book.getBookName().toLowerCase().contains(keyword)) {
                if (!found) {
                    printTableHeader();
                    found = true;
                }
                book.displayBook();
            }
        }

        if (found) {
            printTableLine();
        } else {
            System.out.println("Error: No book found matching the given name.");
        }
    }

    // ---------------- Feature 5: Issue Book ----------------

    private static void issueBook() {
        System.out.println("\n--- Issue Book ---");
        int id = readInt("Enter Book ID to issue: ");

        Book book = findBookById(id);
        if (book == null) {
            System.out.println("Error: No book found with ID " + id + ".");
        } else if (book.isIssued()) {
            System.out.println("Error: Book \"" + book.getBookName() + "\" is already issued.");
        } else {
            book.setIssued(true);
            System.out.println("Success: Book \"" + book.getBookName() + "\" issued successfully.");
        }
    }

    // ---------------- Feature 6: Return Book ----------------

    private static void returnBook() {
        System.out.println("\n--- Return Book ---");
        int id = readInt("Enter Book ID to return: ");

        Book book = findBookById(id);
        if (book == null) {
            System.out.println("Error: No book found with ID " + id + ".");
        } else if (!book.isIssued()) {
            System.out.println("Error: Book \"" + book.getBookName() + "\" is already available.");
        } else {
            book.setIssued(false);
            System.out.println("Success: Book \"" + book.getBookName() + "\" returned successfully.");
        }
    }

    // ---------------- Feature 7: Delete Book ----------------

    private static void deleteBook() {
        System.out.println("\n--- Delete Book ---");
        int id = readInt("Enter Book ID to delete: ");

        Book book = findBookById(id);
        if (book == null) {
            System.out.println("Error: No book found with ID " + id + ".");
            return;
        }

        books.remove(book);
        System.out.println("Success: Book \"" + book.getBookName() + "\" deleted successfully.");
    }

    // ---------------- Feature 8: Total Number of Books ----------------

    private static void displayTotalBooks() {
        System.out.println("\n--- Library Summary ---");

        int issuedCount = 0;
        for (Book book : books) {
            if (book.isIssued()) {
                issuedCount++;
            }
        }

        System.out.println("Total number of books : " + books.size());
        System.out.println("Issued books          : " + issuedCount);
        System.out.println("Available books       : " + (books.size() - issuedCount));
    }

    // ---------------- Utility methods ----------------

    /**
     * Returns the book with the given ID, or null when it does not exist.
     */
    private static Book findBookById(int id) {
        for (Book book : books) {
            if (book.getBookId() == id) {
                return book;
            }
        }
        return null;
    }

    /**
     * Reads an integer from the user and keeps asking until the input is valid.
     */
    private static int readInt(String message) {
        while (true) {
            System.out.print(message);
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a valid whole number.");
            }
        }
    }

    /**
     * Reads a text value from the user and rejects empty input.
     */
    private static String readNonEmptyText(String message) {
        while (true) {
            System.out.print(message);
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("Error: This field cannot be empty.");
        }
    }

    private static void printTableHeader() {
        printTableLine();
        System.out.printf("| %-6s | %-25s | %-20s | %-15s | %-9s |%n",
                "ID", "BOOK NAME", "AUTHOR", "CATEGORY", "STATUS");
        printTableLine();
    }

    private static void printTableLine() {
        System.out.println("+--------+---------------------------+----------------------+"
                + "-----------------+-----------+");
    }
}
