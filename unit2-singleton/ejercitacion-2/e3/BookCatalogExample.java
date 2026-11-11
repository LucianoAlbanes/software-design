package e3;

// ALBANES, Luciano Joaquín

import java.util.ArrayList;
import java.util.List;

class BookCatalog {
    private static final BookCatalog INSTANCE = new BookCatalog();
    private final List<Book> books = new ArrayList<>();

    private BookCatalog() {}

    public static record Book(String code, String title, String author, int copies) {
        @Override
        public String toString() {
            return code + " | " + title + " - " + author + " (" + copies + ")";
        }
    }

    // Public Methods
    public static BookCatalog getInstance() {
        return INSTANCE;
    }

    public synchronized void addBook(String code, String title, String author, int copies) {
        if (code == null || code.isBlank() || title == null || title.isBlank()) {
            throw new IllegalArgumentException("Code and title can't be blank.");
        }
        if (copies < 0) {
            throw new IllegalArgumentException("Copies can't be negative.");
        }
        if (findByCode(code) != null) {
            throw new IllegalArgumentException("Duplicated code: " + code);
        }
        books.add(new Book(code, title, author, copies));
    }

    // Case-insensitive search by title or author.
    public synchronized List<Book> search(String text) {
        String query = text.toLowerCase();
        return books.stream()
            .filter(book -> book.title().toLowerCase().contains(query)
                    || book.author().toLowerCase().contains(query))
            .toList();
    }

    // Records are immutable, so the old one is replaced.
    public synchronized void updateCopies(String code, int copies) {
        if (copies < 0) {
            throw new IllegalArgumentException("Copies can't be negative.");
        }
        Book book = findByCode(code);
        if (book == null) {
            throw new IllegalArgumentException("Book not found: " + code);
        }
        books.set(books.indexOf(book), new Book(book.code(), book.title(), book.author(), copies));
    }

    public synchronized List<Book> getBooks() {
        return List.copyOf(books);
    }

    private Book findByCode(String code) {
        for (Book book : books) {
            if (book.code().equals(code)) {
                return book;
            }
        }
        return null;
    }
}

public class BookCatalogExample {
    public static void main(String[] args) {
        BookCatalog staffScreen = BookCatalog.getInstance();
        BookCatalog queryScreen = BookCatalog.getInstance();

        System.out.println("Same instance? " + (staffScreen == queryScreen));

        // Staff adds books
        staffScreen.addBook("B101", "Design Patterns", "Gamma et al.", 2);
        staffScreen.addBook("B102", "Clean Code", "Robert C. Martin", 3);
        staffScreen.addBook("B103", "Ficciones", "Jorge Luis Borges", 1);

        // Query screen sees them without reloading
        System.out.println("Query screen: " + queryScreen.getBooks());
        System.out.println("Search 'borges': " + queryScreen.search("borges"));

        staffScreen.updateCopies("B101", 5);
        System.out.println("Search 'design': " + queryScreen.search("design"));

        // Invalid inputs
        try {
            staffScreen.addBook("B101", "Refactoring", "Martin Fowler", 1);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        try {
            staffScreen.addBook("B104", " ", "Anonymous", 1);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        try {
            staffScreen.updateCopies("B102", -1);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}
