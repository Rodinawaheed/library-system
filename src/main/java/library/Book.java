package library;

import java.util.Objects;

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

    String getAuthor(){return author;}

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

@Override
    public boolean equals (Object obj){
        if (this==obj) return true;
        if (!(obj instanceof Book)) return false;

        Book other = (Book)  obj;
        return Objects.equals(isbn, other.isbn);
    }
    @Override
    public int hashCode(){
        return Objects.hash(isbn);
    }

}
