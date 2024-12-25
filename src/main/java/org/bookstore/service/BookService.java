package org.bookstore.service;

import java.util.List;
import org.bookstore.model.Book;

public interface BookService {

    Book save(Book book);

    Book findById(Long id);

    List<Book> findAll();
}
