package org.bookstore.dto;

import jakarta.validation.constraints.Min;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class UpdateBookDto {

    private String title;

    private String author;

    private String isbn;

    @Min(0)
    private BigDecimal price;

    private String description;

    private String coverImage;
}
