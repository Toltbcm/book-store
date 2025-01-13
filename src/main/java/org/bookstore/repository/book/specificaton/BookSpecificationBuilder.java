package org.bookstore.repository.book.specificaton;

import lombok.RequiredArgsConstructor;
import org.bookstore.constant.SpecificationProviderKey;
import org.bookstore.dto.request.search.BookSearchParametersRequestDto;
import org.bookstore.model.Book;
import org.bookstore.repository.SpecificationBuilder;
import org.bookstore.repository.SpecificationProviderManager;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookSpecificationBuilder
        implements SpecificationBuilder<Book, BookSearchParametersRequestDto> {

    private final SpecificationProviderManager<Book> bookSpecificationProviderManager;

    @Override
    public Specification<Book> build(BookSearchParametersRequestDto searchParameters) {
        Specification<Book> specification = Specification.where(null);

        if (searchParameters.titlePart() != null) {
            specification = specification.and(bookSpecificationProviderManager
                    .getSpecificationProvider(SpecificationProviderKey.Book.TITLE_PART)
                    .getSpecification(searchParameters.titlePart()));
        }
        if (searchParameters.authorPart() != null) {
            specification = specification.and(bookSpecificationProviderManager
                    .getSpecificationProvider(SpecificationProviderKey.Book.AUTHOR_PART)
                    .getSpecification(searchParameters.authorPart()));
        }
        if (searchParameters.isbnPart() != null) {
            specification = specification.and(bookSpecificationProviderManager
                    .getSpecificationProvider(SpecificationProviderKey.Book.ISBN_PART)
                    .getSpecification(searchParameters.isbnPart()));
        }
        if (searchParameters.descriptionPart() != null) {
            specification = specification.and(bookSpecificationProviderManager
                    .getSpecificationProvider(SpecificationProviderKey.Book.DESCRIPTION_PART)
                    .getSpecification(searchParameters.descriptionPart()));
        }
        if (searchParameters.minPrice() != null) {
            specification = specification.and(bookSpecificationProviderManager
                    .getSpecificationProvider(SpecificationProviderKey.Book.MIN_PRICE)
                    .getSpecification(searchParameters.minPrice()));
        }
        if (searchParameters.maxPrice() != null) {
            specification = specification.and(bookSpecificationProviderManager
                    .getSpecificationProvider(SpecificationProviderKey.Book.MAX_PRICE)
                    .getSpecification(searchParameters.maxPrice()));
        }

        return specification;
    }
}
