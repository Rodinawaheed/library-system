package com.flairs.library;

public class BookNotFoundException extends LibraryException{
    public BookNotFoundException(String message){
        super(message);
    }
}
