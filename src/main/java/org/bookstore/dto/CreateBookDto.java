package org.bookstore.dto;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class CreateBookDto {

    @NotNull
    private String title;

    @NotNull
    private String author;

    @NotNull
    private String isbn;

    @NotNull
    private BigDecimal price;

    private String description;

    private String coverImage;
}
