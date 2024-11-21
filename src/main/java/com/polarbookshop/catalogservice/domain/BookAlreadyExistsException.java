package com.polarbookshop.catalogservice.domain;

public class BookAlreadyExistsException extends RuntimeException {
    public static final String MESSAGE = "A book with the give isbn %s already exists.";

    public BookAlreadyExistsException(String isbn) {
        super(String.format(MESSAGE, isbn));
    }
}
