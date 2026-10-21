package library;

import java.time.LocalDate;

class Loan {
    private final Book book;                        // a Loan HAS-A Book
    private final Member member;                    // and HAS-A Member
    private final LocalDate borrowedOn;
    private final LocalDate dueOn;
    private boolean returned;

    Loan(Book book, Member member, LocalDate borrowedOn) {
        this.book = book;
        this.member = member;
        this.borrowedOn = borrowedOn;
        this.dueOn = borrowedOn.plusDays(member.getType().loanDays());
        book.markBorrowed();
    }

    Book getBook() {
        return book;
    }

    Member getMember() {
        return member;
    }

    boolean isReturned() {
        return returned;
    }

    boolean isOverdue(LocalDate today) {
        return !returned && today.isAfter(dueOn);
    }

    void returnBook() {
        returned = true;
        book.markReturned();
    }

    @Override
    public String toString() {
        return book.getTitle() + " -> " + member.getName() + ", due " + dueOn;
    }
}
