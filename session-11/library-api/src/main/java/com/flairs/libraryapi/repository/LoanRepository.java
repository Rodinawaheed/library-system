package com.flairs.libraryapi.repository;

import com.flairs.libraryapi.model.Loan;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LoanRepository extends JpaRepository<Loan, Long> {

    long countByMember_IdAndReturnedAtIsNull(Long memberId);

    long countByBookIdAndReturnedAtIsNull(Long bookId);


    default long countByMemberIdAndReturnedAtIsNull(Long memberId) {
        return countByMember_IdAndReturnedAtIsNull(memberId);
    }

    @Query("""
            SELECT l
            FROM Loan l
            JOIN FETCH l.book
            JOIN FETCH l.member
            WHERE l.id = :id
            """)
    Optional<Loan> findWithBookAndMemberById(@Param("id") Long id);

    @EntityGraph(attributePaths = {"book", "book.author", "member"})
    List<Loan> findByMember_IdOrderByBorrowedAtDesc(Long memberId);

    default List<Loan> findByMemberIdWithDetails(Long memberId) {
        return findByMember_IdOrderByBorrowedAtDesc(memberId);
    }
}