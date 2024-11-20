package com.polarbookshop.catalogservice.web;

import com.polarbookshop.catalogservice.domain.Book;
import com.polarbookshop.catalogservice.domain.BookService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing books in the catalog.
 */
@RestController
@Validated
@RequestMapping("books")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    /**
     * Retrieves a list of all books in the catalog.
     *
     * @return an iterable collection of see [Book](#Book) objects
     */
    @GetMapping
    public Iterable<Book> get() {
        return this.bookService.viewBookList();
    }

    /**
     * Retrieves the details of a specific book by its ISBN.
     *
     * @param isbn the ISBN of the book to retrieve
     * @return the {@link Book} object
     */
    @GetMapping("{isbn}")
    public Book getByIsbn(@PathVariable String isbn) {
        return this.bookService.viewBookDetails(isbn);
    }

    /**
     * Adds a new book to the catalog.
     *
     * @param book the {@link Book} object to add
     * @return the added {@link Book} object
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Book post(@Valid @RequestBody Book book) {
        return this.bookService.addBookToCatalog(book);

    }

    /**
     * Updates the details of an existing book in the catalog.
     *
     * @param book the updated {@link Book} object
     * @param isbn the ISBN of the book to update
     * @return the updated {@link Book} object
     */
    @PutMapping("{isbn}")
    public Book put(@Valid @RequestBody Book book, @PathVariable String isbn) {
        return this.bookService.editBookDetails(book, isbn);
    }

    /**
     * Removes a book from the catalog by its ISBN.
     *
     * @param isbn the ISBN of the book to delete
     */
    @DeleteMapping("{isbn}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String isbn) {
        this.bookService.removeBookFromCatalog(isbn);
    }
}
