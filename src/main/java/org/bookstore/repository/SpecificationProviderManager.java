package org.bookstore.repository;

public interface SpecificationProviderManager<T> {

    <P> SpecificationProvider<T, P> getSpecificationProvider(String key);
}
