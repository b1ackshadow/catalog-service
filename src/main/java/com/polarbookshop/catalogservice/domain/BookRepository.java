package com.polarbookshop.catalogservice.domain;

import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface BookRepository extends CrudRepository<Book, Long> {

    Optional<Book> findByIsbn(String isbn);

    Boolean existsByIsbn(String isbn);

    // jdbc only supports read only derived methods
    // https://docs.spring.io/spring-data/relational/reference/jdbc/query-methods.html#jdbc.query-methods.strategies
    @Modifying
    @Transactional
    @Query("DELETE from Book where isbn = :isbn")
    void deleteByIsbn(String isbn);
}
