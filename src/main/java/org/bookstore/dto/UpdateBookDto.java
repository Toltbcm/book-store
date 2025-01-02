package org.bookstore.dto;

import jakarta.validation.constraints.Min;
import java.math.BigDecimal;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

@Data
public class UpdateBookDto {

    private String title;

    private String author;

    private String isbn;

    @Min(0)
    private BigDecimal price;

    private String description;

    @URL
    private String coverImage;
}
