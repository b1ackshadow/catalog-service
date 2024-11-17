package com.polarbookshop.catalogservice.demo;

import com.polarbookshop.catalogservice.domain.Book;
import com.polarbookshop.catalogservice.domain.BookRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Component
@Profile(value = "testdata")
//@ConditionalOnProperty(value = "polar.loadTestData", havingValue = "true")
public class BookDataLoader {
    private final BookRepository bookRepository;

    BookDataLoader(BookRepository bookRepository){
        this.bookRepository = bookRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void loadBookTestData(){
        List<Book> books = new LinkedList<>(Arrays.asList(
                new Book("1231231231","Title1", "Author1", 100.0),
                new Book("1231231232","Title2", "Author2", 200.0)
        ));
        books.forEach(this.bookRepository::save);
    }
}
