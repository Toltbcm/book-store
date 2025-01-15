package org.bookstore.repository.book.specificaton.provider;

import org.bookstore.constant.ColumnName;
import org.bookstore.constant.SpecificationProviderKey;
import org.bookstore.model.Book;
import org.bookstore.repository.SpecificationProvider;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class AuthorPartSpecificationProvider implements SpecificationProvider<Book, String> {

    @Override
    public String getKey() {
        return SpecificationProviderKey.Book.AUTHOR_PART;
    }

    @Override
    public Specification<Book> getSpecification(String authorPart) {
        return (root, query, criteriaBuilder)
                -> criteriaBuilder.like(
                criteriaBuilder.lower(root.get(ColumnName.Book.AUTHOR)),
                "%" + authorPart.toLowerCase() + "%");
    }
}
