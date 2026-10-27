package library;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
public class Lab6LibraryV1 {
    public static void main(String[] args) {
        Library library = new Library();
        try {
            library.addBook(new Book("111", "Clean Code", "Robert C. Martin"));
            library.addBook(new Book("222", "Effective Java", "Joshua Bloch"));
            library.addBook(new Book("333", "The Hobbit", "J.R.R. Tolkien"));
        } catch (LibraryException e) {
            System.out.println("Error: " + e.getMessage());
        }
        library.addMember(new Member(1, "Mona", MemberType.STUDENT));
        library.addMember(new Member(2, "Ahmed", MemberType.STAFF));
        Scanner input = new Scanner(System.in);
        boolean running = true;
        while (running) {
            System.out.println("\n===== Library System =====");
            System.out.println("1) List books");
            System.out.println("2) Search");
            System.out.println("3) Add book");
            System.out.println("4) Borrow");
            System.out.println("5) Return");
            System.out.println("6) Overdue");
            System.out.println("0) Exit");
            System.out.print("Choice: ");
            String choice = input.nextLine().trim();
            try {
                switch (choice) {
                    case "1": {
                        List<Book> books = library.allBooks();
                        for (Book book : books) {
                            System.out.println(book);
                        }
                        if (books.isEmpty()) {
                            System.out.println("No books found.");
                        }
                        break;
                    }
                    case "2": {
                        System.out.print("Enter title or author: ");
                        String text = input.nextLine().trim();
                        List<Book> results = library.search(text);
                        if (results.isEmpty()) {
                            System.out.println("No matching books found.");
                        } else {
                            for (Book book : results) {
                                System.out.println(book);
                            }
                        }
                        break;
                    }
                    case "3": {
                        System.out.print("ISBN: ");
                        String isbn = input.nextLine().trim();
                        System.out.print("Title: ");
                        String title = input.nextLine().trim();
                        System.out.print("Author: ");
                        String author = input.nextLine().trim();
                        library.addBook(new Book(isbn, title, author));
                        System.out.println("Book added successfully.");
                        break;
                    }
                    case "4": {
                        System.out.print("ISBN: ");
                        String isbn = input.nextLine().trim();
                        System.out.print("Member id: ");
                        int memberId = Integer.parseInt(input.nextLine().trim());
                        Loan loan = library.borrow(
                                isbn, memberId, LocalDate.now()
                        );
                        System.out.println("Borrowed: " + loan);
                        break;
                    }
                    case "5": {
                        System.out.print("ISBN: ");
                        String isbn = input.nextLine().trim();
                        library.returnBook(isbn);
                        System.out.println("Book returned successfully.");
                        break;
                    }
                    case "6": {
                        List<Loan> overdueLoans =
                                library.overdue(LocalDate.now());
                        if (overdueLoans.isEmpty()) {
                            System.out.println("No overdue loans.");
                        } else {
                            for (Loan loan : overdueLoans) {
                                System.out.println(loan);
                            }
                        }
                        break;
                    }
                    case "0": {
                        running = false;
                        System.out.println("Goodbye!");
                        break;
                    }
                    default:
                        System.out.println("Invalid choice. Please enter 0-6.");
                }
            } catch (LibraryException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a valid number.");
            }
        }
        input.close();
    }
}