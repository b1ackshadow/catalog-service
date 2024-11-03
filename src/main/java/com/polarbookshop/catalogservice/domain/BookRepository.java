package com.polarbookshop.catalogservice.domain;

import java.util.Optional;

public interface BookRepository {
    Iterable<Book> findAll();
    Optional<Book> findByIsbn(String isbn);
    Book save(Book book);
    Boolean existsByIsbn(String isbn);
    void deleteByIsbn(String isbn);
}
