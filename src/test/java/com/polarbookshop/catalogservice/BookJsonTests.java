package com.polarbookshop.catalogservice;

import com.polarbookshop.catalogservice.domain.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class BookJsonTests {

    @Autowired
    private JacksonTester<Book> json;

    @Test
    void testSerialize() throws Exception {
        var book = new Book("1231231230", "Title", "Author", 200.0);
        var jsonContent = json.write(book);
        assertThat(jsonContent).extractingJsonPathStringValue("@.isbn").isEqualTo(book.isbn());
        assertThat(jsonContent).extractingJsonPathStringValue("@.title").isEqualTo(book.title());
        assertThat(jsonContent).extractingJsonPathStringValue("@.author").isEqualTo(book.author());
        assertThat(jsonContent).extractingJsonPathNumberValue("@.price").isEqualTo(book.price());
    }

    @Test
    void testDeserialize() throws Exception {
        var content = """
                {
                  "isbn": "0525564454",
                  "title": "The Myth of Sisyphus",
                  "author": "Albert Camus",
                  "price": 200.0
                }
                """;
        var expectedParse = new Book("0525564454","The Myth of Sisyphus", "Albert Camus", 200.0 );
        assertThat(json.parse(content)).usingRecursiveComparison().isEqualTo(expectedParse);
    }
}
