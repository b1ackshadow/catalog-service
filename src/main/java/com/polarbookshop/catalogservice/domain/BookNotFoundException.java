package com.polarbookshop.catalogservice.domain;

public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(String isbn) {
        super(String.format("The book with ISBN %s does not exist in the catalog.", isbn));
    }
}
