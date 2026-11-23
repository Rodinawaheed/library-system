
package com.flairs.library;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class LibraryTest {

    private Library library;

    @BeforeEach
    void setUp() {
        library = new Library();

        library.addBook(new Book(
                "111", "Clean Code", "Robert C. Martin",
                2008, "Programming"
        ));

        library.addBook(new Book(
                "222", "The Hobbit", "J.R.R. Tolkien",
                1937, "Fantasy"
        ));
    }

    @Test
    void shouldFindBookByIsbn() {
        Optional<Book> result = library.findBook("111");

        assertTrue(result.isPresent());
        assertEquals("Clean Code", result.get().title());
    }

    @Test
    void shouldReturnEmptyWhenBookDoesNotExist() {
        assertTrue(library.findBook("999").isEmpty());
    }

    @Test
    void shouldReturnBooksSortedByTitle() {
        List<Book> books = library.allBooksByTitle();

        assertEquals("Clean Code", books.get(0).title());
        assertEquals("The Hobbit", books.get(1).title());
    }

    @Test
    void shouldSearchBooksByAuthor() {
        List<Book> results = library.search("Tolkien");

        assertEquals(1, results.size());
        assertEquals("The Hobbit", results.get(0).title());
    }

    @Test
    void shouldNotAllowDuplicateIsbn() {
        Book duplicate = new Book(
                "111", "Another Book", "Another Author",
                2020, "Programming"
        );

        assertThrows(
                LibraryException.class,
                () -> library.addBook(duplicate)
        );
    }

    @Test
    void shouldBorrowBookWithCorrectDueDate() {
        library.addMember(
                new Member(1, "Mona", MemberType.STUDENT)
        );

        LocalDate today = LocalDate.of(2026, 11, 6);

        Loan loan = library.borrow("111", 1, today);

        assertEquals(today, loan.borrowedOn());
        assertEquals(today.plusDays(14), loan.dueOn());
    }

    @Test
    void shouldMarkBookAsUnavailableAfterBorrowing() {
        library.addMember(
                new Member(1, "Mona", MemberType.STUDENT)
        );

        library.borrow("111", 1, LocalDate.now());

        assertFalse(library.isAvailable("111"));
    }

    @Test
    void shouldNotAllowBorrowingAlreadyBorrowedBook() {
        library.addMember(
                new Member(1, "Mona", MemberType.STUDENT)
        );

        LocalDate today = LocalDate.now();

        library.borrow("111", 1, today);

        assertThrows(
                LibraryException.class,
                () -> library.borrow("111", 1, today)
        );
    }

    @Test
    void shouldNotAllowStudentToBorrowMoreThanTwoBooks() {
        library.addMember(
                new Member(1, "Mona", MemberType.STUDENT)
        );

        library.addBook(new Book(
                "333", "Java Basics", "John Smith",
                2020, "Programming"
        ));

        LocalDate today = LocalDate.now();

        library.borrow("111", 1, today);
        library.borrow("222", 1, today);

        assertThrows(
                LibraryException.class,
                () -> library.borrow("333", 1, today)
        );
    }

    @Test
    void shouldMakeBookAvailableAfterReturning() {
        library.addMember(
                new Member(1, "Mona", MemberType.STUDENT)
        );

        LocalDate today = LocalDate.now();

        library.borrow("111", 1, today);
        library.returnBook("111", today.plusDays(5));

        assertTrue(library.isAvailable("111"));
    }

    @Test
    void shouldNotBorrowBookThatDoesNotExist() {
        library.addMember(
                new Member(1, "Mona", MemberType.STUDENT)
        );

        assertThrows(
                LibraryException.class,
                () -> library.borrow("999", 1, LocalDate.now())
        );
    }

    @Test
    void shouldNotBorrowBookWithUnknownMember() {
        assertThrows(
                LibraryException.class,
                () -> library.borrow("111", 999, LocalDate.now())
        );
    }

    @Test
    void shouldNotReturnBookThatIsNotOnLoan() {
        assertThrows(
                LibraryException.class,
                () -> library.returnBook("111", LocalDate.now())
        );
    }

    @Test
    void shouldFindOverdueLoans() {
        library.addMember(
                new Member(1, "Mona", MemberType.STUDENT)
        );

        LocalDate borrowedOn = LocalDate.of(2026, 1, 1);

        library.borrow("111", 1, borrowedOn);

        List<Loan> overdueLoans =
                library.overdue(borrowedOn.plusDays(20));

        assertEquals(1, overdueLoans.size());
        assertEquals("111", overdueLoans.get(0).isbn());
    }

    @Test
    void shouldNotConsiderReturnedLoanAsOverdue() {
        library.addMember(
                new Member(1, "Mona", MemberType.STUDENT)
        );

        LocalDate borrowedOn = LocalDate.of(2026, 1, 1);

        library.borrow("111", 1, borrowedOn);

        library.returnBook("111", borrowedOn.plusDays(20));

        List<Loan> overdueLoans =
                library.overdue(borrowedOn.plusDays(30));

        assertTrue(overdueLoans.isEmpty());
    }

    // New test: returning the same book twice should fail.
    @Test
    void shouldNotAllowReturningBookTwice() {
        library.addMember(
                new Member(1, "Mona", MemberType.STUDENT)
        );

        LocalDate today = LocalDate.now();

        library.borrow("111", 1, today);

        library.returnBook("111", today.plusDays(5));

        assertThrows(
                LibraryException.class,
                () -> library.returnBook("111", today.plusDays(6))
        );
    }

    // New test: a loan is not overdue on its due date.
    @Test
    void shouldNotConsiderLoanOverdueOnDueDate() {
        library.addMember(
                new Member(1, "Mona", MemberType.STUDENT)
        );

        LocalDate borrowedOn = LocalDate.of(2026, 1, 1);

        Loan loan = library.borrow("111", 1, borrowedOn);

        List<Loan> overdueOnDueDate =
                library.overdue(loan.dueOn());

        assertTrue(overdueOnDueDate.isEmpty());

        List<Loan> overdueAfterDueDate =
                library.overdue(loan.dueOn().plusDays(1));

        assertEquals(1, overdueAfterDueDate.size());
    }
}
