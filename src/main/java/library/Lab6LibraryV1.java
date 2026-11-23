
package library;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Lab6LibraryV1 {

    private static String readLine(Scanner input, String prompt) {
        System.out.print(prompt);

        if (!input.hasNextLine()) {
            return null;
        }

        return input.nextLine().trim();
    }

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

            String choice = readLine(input, "Choice: ");

            if (choice == null) {
                System.out.println("\nInput ended. Goodbye!");
                break;
            }

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
                        String text = readLine(input, "Enter title or author: ");

                        if (text == null) {
                            running = false;
                            break;
                        }

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
                        String isbn = readLine(input, "ISBN: ");
                        if (isbn == null) {
                            running = false;
                            break;
                        }

                        String title = readLine(input, "Title: ");
                        if (title == null) {
                            running = false;
                            break;
                        }

                        String author = readLine(input, "Author: ");
                        if (author == null) {
                            running = false;
                            break;
                        }

                        library.addBook(new Book(isbn, title, author));
                        System.out.println("Book added successfully.");
                        break;
                    }

                    case "4": {
                        String isbn = readLine(input, "ISBN: ");
                        if (isbn == null) {
                            running = false;
                            break;
                        }

                        String memberInput = readLine(input, "Member id: ");
                        if (memberInput == null) {
                            running = false;
                            break;
                        }

                        int memberId = Integer.parseInt(memberInput);

                        Loan loan = library.borrow(
                                isbn, memberId, LocalDate.now()
                        );

                        System.out.println("Borrowed: " + loan);
                        break;
                    }

                    case "5": {
                        String isbn = readLine(input, "ISBN: ");
                        if (isbn == null) {
                            running = false;
                            break;
                        }

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
