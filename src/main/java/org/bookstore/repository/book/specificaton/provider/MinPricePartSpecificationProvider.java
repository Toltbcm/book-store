package org.bookstore.repository.book.specificaton.provider;

import java.math.BigDecimal;
import org.bookstore.constant.ColumnName;
import org.bookstore.constant.SpecificationProviderKey;
import org.bookstore.model.Book;
import org.bookstore.repository.SpecificationProvider;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class MinPricePartSpecificationProvider implements SpecificationProvider<Book, BigDecimal> {

    @Override
    public String getKey() {
        return SpecificationProviderKey.Book.MIN_PRICE;
    }

    @Override
    public Specification<Book> getSpecification(BigDecimal minPrice) {
        return (root, query, criteriaBuilder)
                -> criteriaBuilder.greaterThanOrEqualTo(root.get(ColumnName.Book.PRICE), minPrice);
    }
}
