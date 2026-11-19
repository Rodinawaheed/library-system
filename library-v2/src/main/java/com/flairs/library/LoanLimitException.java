package com.flairs.library;


public class LoanLimitException extends LibraryException {
    public LoanLimitException(String message) {
        super(message);
    }
}