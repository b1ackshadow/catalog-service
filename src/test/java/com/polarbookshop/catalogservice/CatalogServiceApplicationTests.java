package com.polarbookshop.catalogservice;

import com.polarbookshop.catalogservice.demo.BookDataLoader;
import com.polarbookshop.catalogservice.domain.Book;
import com.polarbookshop.catalogservice.domain.BookNotFoundException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("testdata")
class CatalogServiceApplicationTests {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private BookDataLoader bookLoader;

    // CatalogServiceApplicationTests(WebTestClient webTestClient) {
    // this.webTestClient = webTestClient;
    // }

    @Test
    void whenPostRequestThenBookCreated() {
        var expectedBook = Book.of("1231231230", "Title", "Author", 200.0);

        webTestClient
                .post()
                .uri("/books")
                .bodyValue(expectedBook)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Book.class).value(actualBook -> {
                    assertThat(actualBook).isNotNull();
                    // assertThat(actualBook.id()).isNotNull();
                    // assertThat(actualBook.createdDate()).isNotNull();
                    // assertThat(actualBook.lastModifiedDate()).isNotNull();
                    assertThat(actualBook.isbn()).isEqualTo(expectedBook.isbn());
                });

    }

    @Test
    void whenGetExistingBookThenReturnBook() {
        Book expectedBook = bookLoader.getSampleBooks().get(0);

        webTestClient
                .get()
                .uri("/books/" + expectedBook.isbn())
                .exchange()
                .expectStatus().isOk()
                .expectBody(Book.class).value(actualBook -> {
                    assertThat(actualBook).isNotNull();
                    // assertThat(actualBook.id()).isNotNull();
                    // assertThat(actualBook.createdDate()).isNotNull();
                    // assertThat(actualBook.lastModifiedDate()).isNotNull();
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
}
