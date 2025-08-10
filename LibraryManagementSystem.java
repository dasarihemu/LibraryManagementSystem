import java.util.*;

class Book {
    private int id;
    private String title;
    private String author;
    private boolean isAvailable;

    public Book(int id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isAvailable = true;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean status) {
        this.isAvailable = status;
    }

    public void display() {
        System.out.println(id + " - " + title + " by " + author + " | Available: " + isAvailable);
    }
}

class User {
    private int id;
    private String name;
    private List<Book> issuedBooks;

    public User(int id, String name) {
        this.id = id;
        this.name = name;
        this.issuedBooks = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void issueBook(Book book) {
        issuedBooks.add(book);
    }

    public boolean returnBook(int bookId) {
        for (Book b : issuedBooks) {
            if (b.getId() == bookId) {
                issuedBooks.remove(b);
                return true;
            }
        }
        return false;
    }

    public void displayIssuedBooks() {
        if (issuedBooks.isEmpty()) {
            System.out.println(name + " has no books issued.");
        } else {
            System.out.println(name + "'s issued books:");
            for (Book b : issuedBooks) {
                System.out.println(" - " + b.getTitle());
            }
        }
    }
}

class Library {
    private Map<Integer, Book> books;
    private Map<Integer, User> users;

    public Library() {
        books = new HashMap<>();
        users = new HashMap<>();
    }

    public void addBook(Book book) {
        books.put(book.getId(), book);
    }

    public void addUser(User user) {
        users.put(user.getId(), user);
    }

    public void issueBook(int userId, int bookId) {
        User user = users.get(userId);
        Book book = books.get(bookId);

        if (user == null) {
            System.out.println("User not found!");
        } else if (book == null) {
            System.out.println("Book not found!");
        } else if (!book.isAvailable()) {
            System.out.println("Book is already issued!");
        } else {
            book.setAvailable(false);
            user.issueBook(book);
            System.out.println("Book issued successfully to " + userId);
        }
    }

    public void returnBook(int userId, int bookId) {
        User user = users.get(userId);
        Book book = books.get(bookId);

        if (user == null || book == null) {
            System.out.println("Invalid user or book ID!");
            return;
        }

        boolean removed = user.returnBook(bookId);
        if (removed) {
            book.setAvailable(true);
            System.out.println("Book returned successfully!");
        } else {
            System.out.println("User did not issue this book.");
        }
    }

    public void showAllBooks() {
        for (Book book : books.values()) {
            book.display();
        }
    }

    public void showUserIssuedBooks(int userId) {
        User user = users.get(userId);
        if (user != null) {
            user.displayIssuedBooks();
        } else {
            System.out.println("User not found.");
        }
    }
}

public class LibraryManagementSystem {
    public static void main(String[] args) {
        Library library = new Library();

        // Adding some books
        library.addBook(new Book(1, "1984", "George Orwell"));
        library.addBook(new Book(2, "To Kill a Mockingbird", "Harper Lee"));
        library.addBook(new Book(3, "The Great Gatsby", "F. Scott Fitzgerald"));

        // Adding some users
        library.addUser(new User(101, "Alice"));
        library.addUser(new User(102, "Bob"));

        // Menu for interaction
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n--- Library Menu ---");
            System.out.println("1. Show all books");
            System.out.println("2. Issue a book");
            System.out.println("3. Return a book");
            System.out.println("4. Show user's issued books");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            choice = scanner.nextInt();
            int userId, bookId;

            switch (choice) {
                case 1:
                    library.showAllBooks();
                    break;
                case 2:
                    System.out.print("Enter User ID: ");
                    userId = scanner.nextInt();
                    System.out.print("Enter Book ID: ");
                    bookId = scanner.nextInt();
                    library.issueBook(userId, bookId);
                    break;
                case 3:
                    System.out.print("Enter User ID: ");
                    userId = scanner.nextInt();
                    System.out.print("Enter Book ID: ");
                    bookId = scanner.nextInt();
                    library.returnBook(userId, bookId);
                    break;
                case 4:
                    System.out.print("Enter User ID: ");
                    userId = scanner.nextInt();
                    library.showUserIssuedBooks(userId);
                    break;
                case 0:
                    System.out.println("Exiting system...");
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        } while (choice != 0);

        scanner.close();
    }
}
