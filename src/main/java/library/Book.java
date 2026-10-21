package library;

class Book {

    private final String isbn;
    private final String title;
    private final String author;
    private BookStatus status = BookStatus.AVAILABLE;

    Book(String isbn, String title, String author) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
    }

    String getIsbn() {
        return isbn;
    }

    String getTitle() {
        return title;
    }

    boolean isAvailable() {
        return status == BookStatus.AVAILABLE;
    }

    void markBorrowed() {
        status = BookStatus.BORROWED;
    }

    void markReturned() {
        status = BookStatus.AVAILABLE;
    }

    @Override
    public String toString() {
        return title + " by " + author + " [" + status + "]";
    }
}
