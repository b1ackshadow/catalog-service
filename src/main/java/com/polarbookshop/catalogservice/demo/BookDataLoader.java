package com.polarbookshop.catalogservice.demo;

import com.polarbookshop.catalogservice.domain.Book;
import com.polarbookshop.catalogservice.domain.BookRepository;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Component
@Profile(value = { "testdata", "integration" })
// @ConditionalOnProperty(value = "polar.loadTestData", havingValue = "true")
public class BookDataLoader {
    private final BookRepository bookRepository;
    private final List<Book> books = new LinkedList<>(Arrays.asList(
            Book.of("1231231231", "Title1", "Author1", 100.0),
            Book.of("1231231232", "Title2", "Author2", 200.0)));

    BookDataLoader(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void loadBookTestData() {
        this.bookRepository.deleteAll();
         this.bookRepository.saveAll(books);
    }

    public List<Book> getSampleBooks() {
        List<Book> books = new LinkedList<>();
        this.bookRepository.findAll().forEach(books::add);
        return books;
    }
}
