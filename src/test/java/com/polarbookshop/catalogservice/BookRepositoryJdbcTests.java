package com.polarbookshop.catalogservice;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.test.context.ActiveProfiles;

import com.polarbookshop.catalogservice.config.DataConfig;
import com.polarbookshop.catalogservice.domain.Book;
import com.polarbookshop.catalogservice.domain.BookRepository;

/**
 * BookRepositoryJdbcTests
 */
@DataJdbcTest
@Import(DataConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles(value = { "testdata", "integration" })
public class BookRepositoryJdbcTests {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private JdbcAggregateTemplate jdbcAggregateTemplate;

    @Test
    public void findBookByIsbnWhenExisting() {
        var isbn = "1231231231";

        var book = Book.of(isbn, "Title", "Author", 200.0);
        jdbcAggregateTemplate.insert(book);

        Optional<Book> actualBook = this.bookRepository.findByIsbn(isbn);

        assertThat(actualBook).isNotNull();
        assertThat(actualBook.get().isbn()).isEqualTo(book.isbn());
    }

    @Test
    public void findBookByIsbnWhenNotExisting() {

        var isbn = "1231231231";
        Optional<Book> actualBook = this.bookRepository.findByIsbn(isbn);
        assertThat(actualBook).isEmpty();
    }
}
