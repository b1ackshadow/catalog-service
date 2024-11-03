package com.polarbookshop.catalogservice;

import com.polarbookshop.catalogservice.domain.Book;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CatalogServiceApplicationTests {

    private final WebTestClient webTestClient;

    CatalogServiceApplicationTests(WebTestClient webTestClient) {
        this.webTestClient = webTestClient;
    }

    @Test
    void whenPostRequestThenBookCreated() {
        var expectedBook = new Book("1231231230", "Title", "Author", 200.0);

        webTestClient
                .post()
                .uri("/books")
                .bodyValue(expectedBook)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Book.class).value(actualBook -> {
                    assertThat(actualBook).isNull();
                    assertThat(actualBook.isbn()).isEqualTo(expectedBook.isbn());
                });

    }
}
