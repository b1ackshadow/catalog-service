package com.polarbookshop.catalogservice.domain;

public class BookNotFoundException extends RuntimeException {
    public static final String MSG = "The book with ISBN %s does not exist in the catalog.";

    public BookNotFoundException(String isbn) {
        super(String.format(MSG, isbn));
    }
}
