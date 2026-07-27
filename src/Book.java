/**
 * Represents a single book in the library.
 * Holds the book details and its availability status.
 */
public class Book {

    // Fields (kept private to follow encapsulation)
    private int bookId;
    private String bookName;
    private String authorName;
    private String category;
    private boolean issued; // false = Available, true = Issued

    /**
     * Creates a new book that is available by default.
     */
    public Book(int bookId, String bookName, String authorName, String category) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.authorName = authorName;
        this.category = category;
        this.issued = false;
    }

    // ---------------- Getters ----------------

    public int getBookId() {
        return bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getCategory() {
        return category;
    }

    public boolean isIssued() {
        return issued;
    }

    /**
     * Returns the availability status as readable text.
     */
    public String getStatus() {
        return issued ? "Issued" : "Available";
    }

    // ---------------- Setters ----------------

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setIssued(boolean issued) {
        this.issued = issued;
    }

    /**
     * Prints the book details as a single formatted table row.
     */
    public void displayBook() {
        System.out.printf("| %-6d | %-25s | %-20s | %-15s | %-9s |%n",
                bookId, bookName, authorName, category, getStatus());
    }
}
