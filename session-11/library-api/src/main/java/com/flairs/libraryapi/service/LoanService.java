package com.flairs.libraryapi.service;

import com.flairs.libraryapi.config.LibraryProperties;
import com.flairs.libraryapi.dto.BorrowRequest;
import com.flairs.libraryapi.dto.LoanResponse;
import com.flairs.libraryapi.dto.Mapper;
import com.flairs.libraryapi.exception.ConflictException;
import com.flairs.libraryapi.exception.NotFoundException;
import com.flairs.libraryapi.model.Book;
import com.flairs.libraryapi.model.Loan;
import com.flairs.libraryapi.model.Member;
import com.flairs.libraryapi.repository.BookRepository;
import com.flairs.libraryapi.repository.LoanRepository;
import com.flairs.libraryapi.repository.MemberRepository;

import java.time.Clock;
import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class LoanService {

    private static final Logger log = LoggerFactory.getLogger(LoanService.class);

    private final LoanRepository loans;
    private final BookRepository books;
    private final MemberRepository members;
    private final LibraryProperties props;
    private final Clock clock;

    public LoanService(
            LoanRepository loans,
            BookRepository books,
            MemberRepository members,
            LibraryProperties props,
            Clock clock) {
        this.loans = loans;
        this.books = books;
        this.members = members;
        this.props = props;
        this.clock = clock;
    }

    @Transactional
    public LoanResponse borrow(BorrowRequest request) {
        Book book = books.findById(request.bookId())
                .orElseThrow(() -> new NotFoundException("Book", request.bookId()));

        Member member = members.findById(request.memberId())
                .orElseThrow(() -> new NotFoundException("Member", request.memberId()));

        long activeBookLoans = loans.countByBook_IdAndReturnedAtIsNull(book.getId());

        if (activeBookLoans >= book.getCopies()) {
            throw new ConflictException("No available copies of this book");
        }

        long activeMemberLoans = loans.countByMember_IdAndReturnedAtIsNull(member.getId());

        if (activeMemberLoans >= props.maxActiveLoans()) {
            throw new ConflictException("Member has reached the maximum active loans limit");
        }

        LocalDate dueDate = LocalDate.now(clock).plusDays(props.loanDays());

        Loan loan = new Loan(book, member, dueDate);
        Loan savedLoan = loans.save(loan);

        log.info("Book {} borrowed by member {}", book.getId(), member.getId());

        return Mapper.toResponse(savedLoan);
    }

    @Transactional
    public LoanResponse giveBack(Long loanId) {
        Loan loan = loans.findWithBookAndMemberById(loanId)
                .orElseThrow(() -> new NotFoundException("Loan", loanId));

        if (!loan.isActive()) {
            throw new ConflictException("Loan " + loanId + " has already been returned");
        }

        loan.markReturned();

        log.info("Loan {} returned", loanId);

        return Mapper.toResponse(loan);
    }
}