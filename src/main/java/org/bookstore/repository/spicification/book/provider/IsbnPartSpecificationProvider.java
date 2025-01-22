package org.bookstore.repository.spicification.book.provider;

import org.bookstore.constant.SpecificationProviderKey;
import org.bookstore.model.Book;
import org.bookstore.repository.SpecificationProvider;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class IsbnPartSpecificationProvider implements SpecificationProvider<Book, String> {

    @Override
    public String getKey() {
        return SpecificationProviderKey.Book.ISBN_PART;
    }

    @Override
    public Specification<Book> getSpecification(String isbnPart) {
        return (root, query, criteriaBuilder)
                -> criteriaBuilder.like(root.get("isbn"), "%" + isbnPart + "%");
    }
}
