package com.flairs.libraryapi.repository;

import com.flairs.libraryapi.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    boolean existsByIsbn(String isbn);

    boolean existsByIsbnAndIdNot(String isbn, Long id);

    @EntityGraph(attributePaths = "author")
    @Query("""
            SELECT b
            FROM Book b
            WHERE LOWER(b.author.name) LIKE LOWER(CONCAT('%', COALESCE(:author, ''), '%'))
            """)
    Page<Book> search(@Param("author") String author, Pageable pageable);

    @Query("SELECT b FROM Book b JOIN FETCH b.author a WHERE a.id = :authorId ORDER BY b.year")
    List<Book> findByAuthorIdWithAuthor(@Param("authorId") Long authorId);
}