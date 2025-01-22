package org.bookstore.repository.spicification.book;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.bookstore.exception.SpecificationProviderNotFoundException;
import org.bookstore.model.Book;
import org.bookstore.repository.SpecificationProvider;
import org.bookstore.repository.SpecificationProviderManager;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookSpecificationProviderManager implements SpecificationProviderManager<Book> {

    private final List<SpecificationProvider<Book, ?>> phoneSpecificationProviders;

    @Override
    public <P> SpecificationProvider<Book, P> getSpecificationProvider(String key) {
        return (SpecificationProvider<Book, P>) phoneSpecificationProviders.stream()
                .filter(p -> p.getKey().equals(key))
                .findFirst().orElseThrow(() -> new SpecificationProviderNotFoundException(
                        "Can't find specification provider for key: " + key));
    }
}
