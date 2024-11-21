package com.polarbookshop.catalogservice;

import com.polarbookshop.catalogservice.domain.Book;
import com.polarbookshop.catalogservice.domain.BookNotFoundException;
import com.polarbookshop.catalogservice.domain.BookRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(value = { "integration" })
class CatalogServiceApplicationTests {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private BookRepository bookRepository;

    @BeforeEach
    void before() {
        bookRepository.deleteAll();
    }

    @Test
    void whenPostRequestThenBookCreated() {
        var expectedBook = Book.of("1231231230", "Title", "Author", 200.0, null);

        webTestClient
                .post()
                .uri("/books")
                .bodyValue(expectedBook)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Book.class).value(actualBook -> {
                    assertThat(actualBook).isNotNull();
                    assertThat(actualBook.isbn()).isEqualTo(expectedBook.isbn());
                });
    }

    @Test
    void whenGetExistingBookThenReturnBook() {
        var expectedBook = Book.of("1231231230", "Title", "Author", 200.0, null);

        webTestClient
                .post()
                .uri("/books")
                .bodyValue(expectedBook)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Book.class).value(actualBook -> {
                    assertThat(actualBook).isNotNull();
                    assertThat(actualBook.isbn()).isEqualTo(expectedBook.isbn());
                });

        webTestClient
                .get()
                .uri("/books/" + expectedBook.isbn())
                .exchange()
                .expectStatus().isOk()
                .expectBody(Book.class).value(actualBook -> {
                    assertThat(actualBook).isNotNull();
                    assertThat(actualBook.isbn()).isEqualTo(expectedBook.isbn());
                });
    }

    @Test
    void whenGetNonExistingBookThenReturnNotFound() {

        String nonExistingIsbn = "123789123";
        BookNotFoundException expected = new BookNotFoundException(nonExistingIsbn);

        webTestClient
                .get()
                .uri("/books/" + nonExistingIsbn)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(String.class)
                .isEqualTo(expected.getMessage());
    }

    @Test
    void whenPutRequestThenBookUpdated() {
        var createdBook = Book.of("1231231230", "Title", "Author", 200.0, null);

        webTestClient
                .post()
                .uri("/books")
                .bodyValue(createdBook)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Book.class).value(actualBook -> {
                    assertThat(actualBook).isNotNull();
                    assertThat(actualBook.isbn()).isEqualTo(createdBook.isbn());
                });

        var bookToUpdate = new Book(createdBook.id(), createdBook.publisher(), createdBook.isbn(), createdBook.title(),
                createdBook.author(),
                7.95,
                createdBook.createdDate(), createdBook.lastModifiedDate(), createdBook.version());
        // PUT request
        webTestClient
                .put()
                .uri("/books/" + bookToUpdate.isbn())
                .bodyValue(bookToUpdate)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Book.class).value(actualBook -> {
                    assertThat(actualBook).isNotNull();
                    assertThat(actualBook.isbn()).isEqualTo(bookToUpdate.isbn());
                    assertThat(actualBook.price()).isEqualTo(bookToUpdate.price());
                });
    }

    @Test
    void whenDeleteRequestThenBookDeleted() {

        var createdBook = Book.of("1231231230", "Title", "Author", 200.0, null);

        webTestClient
                .post()
                .uri("/books")
                .bodyValue(createdBook)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Book.class).value(actualBook -> {
                    assertThat(actualBook).isNotNull();
                    assertThat(actualBook.isbn()).isEqualTo(createdBook.isbn());
                });

        // DELETE request
        webTestClient
                .delete()
                .uri("/books/" + createdBook.isbn())
                .exchange()
                .expectStatus().isNoContent();

        // verify book is indeed deleted

        var expectedError = new BookNotFoundException(createdBook.isbn());
        webTestClient
                .get()
                .uri("/books/" + createdBook.isbn())
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(String.class)
                .isEqualTo(expectedError.getMessage());
    }
}
