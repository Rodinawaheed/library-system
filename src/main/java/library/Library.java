package library;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
class Library {
    private final Map<String, Book> books = new HashMap<>();
    private final Map<Integer, Member> members = new HashMap<>();
    private final List<Loan> loans = new ArrayList<>();

    void addBook(Book book) throws DuplicateBookException {
        if (books.containsKey(book.getIsbn())) {
            throw new DuplicateBookException(
                    "Book with ISBN " + book.getIsbn() + " already exists"
            );
        }
        books.put(book.getIsbn(), book);
    }

    void addMember(Member member) {
        members.put(member.getId(), member);
    }

    Book findBook(String isbn) throws BookNotFoundException {
        Book book = books.get(isbn);
        if (book == null) {
            throw new BookNotFoundException("No book with ISBN " + isbn);
        }
        return book;
    }

    Member findMember(int id) throws MemberNotFoundException {
        Member member = members.get(id);
        if (member == null) {
            throw new MemberNotFoundException("No member with id " + id);
        }
        return member;
    }

    List<Book> allBooks() {
        List<Book> result = new ArrayList<>(books.values());
        result.sort(Comparator.comparing(Book::getTitle));
        return result;
    }

    List<Book> search(String text) {
        List<Book> result = new ArrayList<>();
        String query = text.toLowerCase();
        for (Book book : books.values()) {
            if (book.getTitle().toLowerCase().contains(query)
                    || book.getAuthor().toLowerCase().contains(query)) {
                result.add(book);
            }
        }
        result.sort(Comparator.comparing(Book::getTitle));
        return result;
    }

    int activeLoans(Member member) {
        int count = 0;
        for (Loan loan : loans) {
            if (loan.getMember().equals(member) && !loan.isReturned()) {
                count++;
            }
        }
        return count;
    }

    Loan borrow(String isbn, int memberId, LocalDate today)
            throws LibraryException {
        Book book = findBook(isbn);
        Member member = findMember(memberId);
        if (!book.isAvailable()) {
            throw new BookUnavailableException(
                    book.getTitle() + " is already borrowed"
            );
        }
        if (!member.canBorrow(activeLoans(member))) {
            throw new LoanLimitException(
                    member.getName() + " has reached the loan limit"
            );
        }
        Loan loan = new Loan(book, member, today);
        loans.add(loan);
        return loan;
    }

    void returnBook(String isbn) throws LibraryException {
        Book book = findBook(isbn);
        for (Loan loan : loans) {
            if (loan.getBook().equals(book) && !loan.isReturned()) {
                loan.returnBook();
                return;
            }
        }
        throw new BookUnavailableException(
                book.getTitle() + " is not currently borrowed"
        );
    }

    List<Loan> overdue(LocalDate today) {
        List<Loan> result = new ArrayList<>();
        for (Loan loan : loans) {
            if (loan.isOverdue(today)) {
                result.add(loan);
            }
        }
        return result;
    }
}