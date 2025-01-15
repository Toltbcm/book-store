package org.bookstore.repository.book.specificaton.provider;

import org.bookstore.constant.ColumnName;
import org.bookstore.constant.SpecificationProviderKey;
import org.bookstore.model.Book;
import org.bookstore.repository.SpecificationProvider;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class TitlePartSpecificationProvider implements SpecificationProvider<Book, String> {

    @Override
    public String getKey() {
        return SpecificationProviderKey.Book.TITLE_PART;
    }

    @Override
    public Specification<Book> getSpecification(String titlePart) {
        return (root, query, criteriaBuilder)
                -> criteriaBuilder.like(
                criteriaBuilder.lower(root.get(ColumnName.Book.TITLE)),
                "%" + titlePart.toLowerCase() + "%");
    }
}
