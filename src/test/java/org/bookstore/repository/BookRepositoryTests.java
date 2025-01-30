package org.bookstore.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;
import java.util.Set;
import org.bookstore.model.Book;
import org.bookstore.model.Category;
import org.hibernate.LazyInitializationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional(propagation = Propagation.NOT_SUPPORTED)
class BookRepositoryTests {

    @Autowired
    private BookRepository bookRepository;

    @Nested
    @DisplayName("Tests for `getAllByCategoryId`:")
    @Sql(scripts = "classpath:database/books/add-5-books.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
    @Sql(scripts = "classpath:database/books/clear.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_CLASS)
    class GetAllByCategoryTests {

        @Test
        @DisplayName("should return page with three books by category with id=3")
        void categoryIdIsCorrect_ReturnsThreeBooks() {
            Long categoryId = 3L;
            Page<Book> books = bookRepository.getAllByCategoryId(categoryId, Pageable.unpaged());

            assertEquals(3, books.getSize());
        }

        @Test
        @DisplayName("should return empty page for non-existent category id")
        void categoryIdIsWrong_ReturnsEmptyPage() {
            Long nonExistentCategoryId = 77L;
            Page<Book> books = bookRepository.getAllByCategoryId(
                    nonExistentCategoryId, Pageable.unpaged());

            assertTrue(books.isEmpty());
        }

        @Test
        @DisplayName("should throw LazyInitializationException if try getting categories ")
        void gettingCategories_ThrowsLazyInitializationException() {
            Long categoryId = 2L;
            Page<Book> books = bookRepository.getAllByCategoryId(
                    categoryId, PageRequest.of(0, 20));
            Set<Category> categories = books.getContent().getFirst().getCategories();

            assertThrows(LazyInitializationException.class, categories::size);
        }
    }

    @Nested
    @DisplayName("Tests for `findByIdWithCategory`:")
    @Sql(scripts = "classpath:database/books/add-5-books.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
    @Sql(scripts = "classpath:database/books/clear.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_CLASS)
    class FindByIdWithCategoryTests {

        @Test
        @DisplayName("should return Optional with book with four categories by id=3")
        void bookIdIsCorrect_ReturnsOptionalWithBookWithFourCategories() {
            Long categoryId = 3L;
            Book book = bookRepository.findByIdWithCategory(categoryId).get();

            assertEquals(categoryId, book.getId());
            assertEquals("000-0-00-000000-3", book.getIsbn());
            assertEquals(4, book.getCategories().size());
        }

        @Test
        @DisplayName("should return empty Optional for non-existent book id")
        void bookIdIsWrong_ReturnsEmptyOptional() {
            Long nonExistentCategoryId = 77L;
            Optional<Book> optional = bookRepository.findByIdWithCategory(nonExistentCategoryId);

            assertTrue(optional.isEmpty());
        }
    }
}
