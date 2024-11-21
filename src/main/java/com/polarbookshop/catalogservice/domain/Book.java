package com.polarbookshop.catalogservice.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import org.springframework.data.annotation.*;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;

public record Book(

        @Id Long id,

        // nums only of len 10/13 only
        @NotBlank(message = "The Book ISBN must be defined.") @Pattern(regexp = "^([0-9]{10}|[0-9]{13})$", message = "The ISBN format must be valid.") @Schema(description = "ISBN formatted string") String isbn,

        @NotBlank(message = "The Book title must be defined.") String title,

        @NotBlank(message = "The Book author must be defined.") String author,

        @NotNull(message = "The book price must be defined") @Positive(message = "The book price must be greater than zero") Double price,

        @CreatedDate Instant createdDate,

        @LastModifiedDate Instant lastModifiedDate,

        @Version int version) {
    public static Book of(String isbn, String title, String author, Double price) {
        return new Book(
                null, isbn, title, author, price, null, null, 0);
    }
}
