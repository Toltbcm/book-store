package org.bookstore.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.Data;
import org.bookstore.constant.ColumnName;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Data
@Entity
@SQLDelete(sql = "UPDATE books SET is_deleted = true WHERE id = ?")
@SQLRestriction("is_deleted = false")
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ColumnName.Book.ID)
    private Long id;

    @Column(name = ColumnName.Book.TITLE, nullable = false)
    private String title;

    @Column(name = ColumnName.Book.AUTHOR, nullable = false)
    private String author;

    @Column(name = ColumnName.Book.ISBN, nullable = false, unique = true)
    private String isbn;

    @Column(name = ColumnName.Book.PRICE, nullable = false)
    private BigDecimal price;

    @Column(name = ColumnName.Book.DESCRIPTION)
    private String description;

    @Column(name = ColumnName.Book.COVER_IMAGE)
    private String coverImage;

    @Column(name = ColumnName.Book.IS_DELETED, nullable = false)
    private boolean isDeleted = false;
}
