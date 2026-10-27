package library;

import java.time.LocalDate;

public class Lab4LibraryModel {
    public static void main(String[] args){
        Book book = new Book("978-0134685991", "Effective Java", "Joshua Bloch");
        Member mona = new Member(1, "Mona", MemberType.STUDENT);
        LocalDate today = LocalDate.of(2026, 10, 18);
        System.out.println(book);
        System.out.println(mona);
        System.out.println(mona.canBorrow(1));

        Loan loan = new Loan(book, mona, today);
        System.out.println(loan);
        System.out.println(book.isAvailable());
        System.out.println(loan.isOverdue(today.plusDays(10)));
        System.out.println(loan.isOverdue(today.plusDays(20)));

        loan.returnBook();
        System.out.println(book.isAvailable());
    }
}
