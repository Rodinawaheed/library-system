package com.flairs.library;

import java.time.LocalDate;

public class Lab4LibraryModel {

    public static void main(String[] args) {

        Book book = new Book(
                "978-0134685991",
                "Effective Java",
                "Joshua Bloch",
                2018,
                "Programming"
        );

        Member mona = new Member(1, "Mona", MemberType.STUDENT);

        LocalDate today = LocalDate.of(2026, 10, 18);

        System.out.println(book);
        System.out.println(mona);

        Library library = new Library();

        library.addBook(book);
        library.addMember(mona);

        System.out.println(library.isAvailable(book.isbn()));

        Loan loan = library.borrow(book.isbn(), mona.id(), today);

        System.out.println(loan);
        System.out.println(library.isAvailable(book.isbn()));

        System.out.println(loan.isOverdue(today.plusDays(10)));
        System.out.println(loan.isOverdue(today.plusDays(20)));

        library.returnBook(book.isbn(), today.plusDays(20));

        System.out.println(library.isAvailable(book.isbn()));
    }
}