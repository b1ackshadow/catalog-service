package com.polarbookshop.catalogservice;


import com.polarbookshop.catalogservice.domain.Book;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class BookValidationTests {
    public static Validator validator;

    @BeforeAll
    static void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void whenAllFieldsCorrectThenValidationSucceeds() {
        var book = new Book("1231231230", "Some title", "Albert Camus", 200.0);
        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        assertThat(violations).isEmpty();
    }

    @Test
    void whenISBNDefinedButIncorrectThenValidationFails() {
        final String INVALID_ISBN_MSG = "The ISBN format must be valid.";
        var book = new Book("123", "Some title", "Albert Camus", 200.0);
        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        assertThat(violations).hasSize(1); // only isbn should trigger exception;
        assertThat(violations.iterator().next().getMessage()).isEqualTo(INVALID_ISBN_MSG);
    }
}
