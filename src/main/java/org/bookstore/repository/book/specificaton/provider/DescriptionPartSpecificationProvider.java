package org.bookstore.repository.book.specificaton.provider;

import org.bookstore.constant.SpecificationProviderKey;
import org.bookstore.model.Book;
import org.bookstore.repository.SpecificationProvider;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class DescriptionPartSpecificationProvider implements SpecificationProvider<Book, String> {

    @Override
    public String getKey() {
        return SpecificationProviderKey.Book.DESCRIPTION_PART;
    }

    @Override
    public Specification<Book> getSpecification(String descriptionPart) {
        return (root, query, criteriaBuilder)
                -> criteriaBuilder.like(
                criteriaBuilder.lower(root.get("description")),
                "%" + descriptionPart.toLowerCase() + "%");
    }
}
