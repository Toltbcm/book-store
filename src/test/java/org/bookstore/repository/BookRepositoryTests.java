package org.bookstore.repository;

import static org.bookstore.util.Constant.CORRECT_ID;
import static org.bookstore.util.Constant.SqlPath.ADD_5_BOOKS_PATH;
import static org.bookstore.util.Constant.SqlPath.CLEAR_BOOKS_PATH;
import static org.bookstore.util.Constant.WRONG_ID;
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
    @Sql(scripts = ADD_5_BOOKS_PATH, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
    @Sql(scripts = CLEAR_BOOKS_PATH, executionPhase = Sql.ExecutionPhase.AFTER_TEST_CLASS)
    class GetAllByCategoryTests {

        @Test
        @DisplayName("should return page with two books by category with id=1")
        void categoryIdIsCorrect_ReturnsTwoBooks() {
            Page<Book> books = bookRepository.getAllByCategoryId(CORRECT_ID, Pageable.unpaged());

            assertEquals(2, books.getSize());
        }

        @Test
        @DisplayName("should return empty page for non-existent category id")
        void categoryIdIsWrong_ReturnsEmptyPage() {
            Page<Book> books = bookRepository.getAllByCategoryId(
                    WRONG_ID, Pageable.unpaged());

            assertTrue(books.isEmpty());
        }

        @Test
        @DisplayName("should throw LazyInitializationException if try getting categories ")
        void gettingCategories_ThrowsLazyInitializationException() {
            Page<Book> books = bookRepository.getAllByCategoryId(
                    CORRECT_ID, PageRequest.of(0, 20));
            Set<Category> categories = books.getContent().getFirst().getCategories();

            assertThrows(LazyInitializationException.class, categories::size);
        }
    }

    @Nested
    @DisplayName("Tests for `findByIdWithCategory`:")
    @Sql(scripts = ADD_5_BOOKS_PATH, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
    @Sql(scripts = CLEAR_BOOKS_PATH, executionPhase = Sql.ExecutionPhase.AFTER_TEST_CLASS)
    class FindByIdWithCategoryTests {

        @Test
        @DisplayName("should return Optional with book with four categories by id=1")
        void bookIdIsCorrect_ReturnsOptionalWithBookWithFourCategories() {
            Book book = bookRepository.findByIdWithCategory(CORRECT_ID).get();

            assertEquals(CORRECT_ID, book.getId());
            assertEquals("000-0-00-000000-1", book.getIsbn());
            assertEquals(4, book.getCategories().size());
        }

        @Test
        @DisplayName("should return empty Optional for non-existent book id")
        void bookIdIsWrong_ReturnsEmptyOptional() {
            Optional<Book> optional = bookRepository.findByIdWithCategory(WRONG_ID);

            assertTrue(optional.isEmpty());
        }
    }
}
