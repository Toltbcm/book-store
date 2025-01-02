package org.bookstore.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import static org.bookstore.constant.ColumnName.BookColumns.AUTHOR;
import static org.bookstore.constant.ColumnName.BookColumns.COVER_IMAGE;
import static org.bookstore.constant.ColumnName.BookColumns.DESCRIPTION;
import static org.bookstore.constant.ColumnName.BookColumns.ID;
import static org.bookstore.constant.ColumnName.BookColumns.ISBN;
import static org.bookstore.constant.ColumnName.BookColumns.IS_DELETED;
import static org.bookstore.constant.ColumnName.BookColumns.PRICE;
import static org.bookstore.constant.ColumnName.BookColumns.TITLE;

@Data
@Entity
@SQLDelete(sql = "UPDATE books SET is_deleted = true WHERE id = ?")
@SQLRestriction("is_deleted = false")
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ID)
    private Long id;

    @Column(name = TITLE, nullable = false)
    private String title;

    @Column(name = AUTHOR, nullable = false)
    private String author;

    @Column(name = ISBN, nullable = false, unique = true)
    private String isbn;

    @Column(name = PRICE, nullable = false)
    private BigDecimal price;

    @Column(name = DESCRIPTION)
    private String description;

    @Column(name = COVER_IMAGE)
    private String coverImage;

    @Column(name = IS_DELETED, nullable = false)
    private boolean isDeleted = false;
}
