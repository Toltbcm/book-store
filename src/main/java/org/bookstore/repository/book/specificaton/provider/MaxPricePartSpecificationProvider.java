package org.bookstore.repository.book.specificaton.provider;

import java.math.BigDecimal;
import org.bookstore.constant.SpecificationProviderKey;
import org.bookstore.model.Book;
import org.bookstore.repository.SpecificationProvider;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class MaxPricePartSpecificationProvider implements SpecificationProvider<Book, BigDecimal> {

    @Override
    public String getKey() {
        return SpecificationProviderKey.Book.MAX_PRICE;
    }

    @Override
    public Specification<Book> getSpecification(BigDecimal maxPrice) {
        return (root, query, criteriaBuilder)
                -> criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice);
    }
}
