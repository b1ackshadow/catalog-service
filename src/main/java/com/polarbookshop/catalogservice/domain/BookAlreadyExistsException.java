package com.polarbookshop.catalogservice.domain;

public class BookAlreadyExistsException extends RuntimeException {
    public BookAlreadyExistsException(String isbn) {
        super(String.format("A book with the give isbn %s already exists.", isbn));
    }
}
