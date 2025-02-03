package org.bookstore.service.impl;

import static org.bookstore.util.Constant.CORRECT_ID;
import static org.bookstore.util.Constant.NAME_PART;
import static org.bookstore.util.Constant.WRONG_ID;
import static org.bookstore.util.ModelsAndDtoMaker.makeBookResponseDto;
import static org.bookstore.util.ModelsAndDtoMaker.makeBookWithId;
import static org.bookstore.util.ModelsAndDtoMaker.makeBookWithoutCategoriesResponseDto;
import static org.bookstore.util.ModelsAndDtoMaker.makeBookWithoutId;
import static org.bookstore.util.ModelsAndDtoMaker.makeCategoryWithId;
import static org.bookstore.util.ModelsAndDtoMaker.makeCreateBookRequestDto;
import static org.bookstore.util.ModelsAndDtoMaker.makeUpdateBookRequestDto;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.bookstore.dto.request.CreateBookRequestDto;
import org.bookstore.dto.request.UpdateBookRequestDto;
import org.bookstore.dto.response.BookResponseDto;
import org.bookstore.dto.response.BookWithoutCategoriesResponseDto;
import org.bookstore.exception.EntityNotFoundException;
import org.bookstore.mapper.BookMapper;
import org.bookstore.model.Book;
import org.bookstore.model.Category;
import org.bookstore.repository.BookRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class BookServiceTests {

    @Spy
    @InjectMocks
    private BookServiceImpl bookService;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookMapper bookMapper;

    @Nested
    @DisplayName("Tests for `save`:")
    class SaveTests {

        @Test
        @DisplayName("should save book and return BookResponseDto")
        void createBookRequestDto_SavesBookAndReturnsBookRequestDto() {
            BigDecimal price = BigDecimal.valueOf(11.11);
            List<Long> categoryIds = List.of(1L, 2L);
            CreateBookRequestDto createBookRequestDto = makeCreateBookRequestDto(
                    NAME_PART, price, categoryIds);
            Set<Category> categories = Set.of(
                    makeCategoryWithId(1L, ""),
                    makeCategoryWithId(2L, ""));
            Book bookWithoutId = makeBookWithoutId(
                    NAME_PART, price, categories);
            Book bookWithId = makeBookWithId(
                    CORRECT_ID, NAME_PART, price, categories);
            BookResponseDto bookResponseDto =
                    makeBookResponseDto(CORRECT_ID, NAME_PART, price, categoryIds);

            when(bookMapper.toModel(createBookRequestDto)).thenReturn(bookWithoutId);
            when(bookRepository.save(bookWithoutId)).thenReturn(bookWithId);
            when(bookMapper.toDto(bookWithId)).thenReturn(bookResponseDto);

            assertEquals(bookResponseDto, bookService.save(createBookRequestDto));
            verify(bookMapper).toModel(createBookRequestDto);
            verify(bookRepository).save(bookWithoutId);
            verify(bookMapper).toDto(bookWithId);
        }
    }

    @Nested
    @DisplayName("Tests for `getById` and `getBookWithCategoryById`:")
    class GetByIdAndGetBookWithCategoryByIdTests {

        @Test
        @DisplayName("should find and return BookResponseDto by book id=1")
        void bookIdIsCorrect_ReturnsBookResponseDto() {
            BigDecimal price = BigDecimal.valueOf(11.11);
            List<Long> categoryIds = List.of(1L, 2L);
            Set<Category> categories = Set.of(
                    makeCategoryWithId(1L, ""),
                    makeCategoryWithId(2L, ""));
            Book bookWithId = makeBookWithId(CORRECT_ID, NAME_PART, price, categories);
            BookResponseDto bookResponseDto = makeBookResponseDto(
                    CORRECT_ID, NAME_PART, price, categoryIds);

            when(bookRepository.findByIdWithCategory(CORRECT_ID))
                    .thenReturn(Optional.of(bookWithId));
            when(bookMapper.toDto(bookWithId)).thenReturn(bookResponseDto);

            assertEquals(bookResponseDto, bookService.getById(CORRECT_ID));
            verify(bookRepository).findByIdWithCategory(CORRECT_ID);
            verify(bookMapper).toDto(bookWithId);
        }

        @Test
        @DisplayName("should throw EntityNotFoundException for non-existent book")
        void bookIdIsWrong_ThrowsEntityNotFoundException() {
            when(bookRepository.findByIdWithCategory(WRONG_ID))
                    .thenReturn(Optional.empty());

            assertThrows(EntityNotFoundException.class,
                    () -> bookService.getById(WRONG_ID));
            verify(bookRepository).findByIdWithCategory(WRONG_ID);
            verify(bookMapper, never()).toDto(any());
        }
    }

    @Nested
    @DisplayName("Tests for `getAll`:")
    class GetAllTests {

        @Test
        @DisplayName("should return page with three BookResponseDtos")
        void pageable_returnsPageWithThreeBooksResponseDtos() {
            Book book1 = makeBookWithId(1L, "1", BigDecimal.valueOf(11.11), Set.of());
            Book book2 = makeBookWithId(2L, "2", BigDecimal.valueOf(22.22), Set.of());
            Book book3 = makeBookWithId(3L, "3", BigDecimal.valueOf(33.33), Set.of());
            BookWithoutCategoriesResponseDto bookWithoutCategoriesResponseDto1 =
                    makeBookWithoutCategoriesResponseDto(1L, "1", BigDecimal.valueOf(11.11));
            BookWithoutCategoriesResponseDto bookWithoutCategoriesResponseDto2 =
                    makeBookWithoutCategoriesResponseDto(2L, "3", BigDecimal.valueOf(22.22));
            BookWithoutCategoriesResponseDto bookWithoutCategoriesResponseDto3 =
                    makeBookWithoutCategoriesResponseDto(2L, "3", BigDecimal.valueOf(33.33));
            Pageable pageable = PageRequest.of(0, 20);

            Page<Book> books = new PageImpl<>(
                    List.of(book1, book2, book3), pageable, 3);

            when(bookRepository.findAll(pageable)).thenReturn(books);
            when(bookMapper.toDtoWithoutCategories(book1))
                    .thenReturn(bookWithoutCategoriesResponseDto1);
            when(bookMapper.toDtoWithoutCategories(book2))
                    .thenReturn(bookWithoutCategoriesResponseDto2);
            when(bookMapper.toDtoWithoutCategories(book3))
                    .thenReturn(bookWithoutCategoriesResponseDto3);

            Page<BookWithoutCategoriesResponseDto> expected = new PageImpl<>(
                    List.of(
                            bookWithoutCategoriesResponseDto1,
                            bookWithoutCategoriesResponseDto2,
                            bookWithoutCategoriesResponseDto3
                    ),
                    pageable, 3);

            assertEquals(expected, bookService.getAll(pageable));
            verify(bookRepository).findAll(pageable);
            verify(bookMapper, times(3)).toDtoWithoutCategories(any());
        }
    }

    @Nested
    @DisplayName("Tests for `update`:")
    class UpdateTests {

        @Test
        @DisplayName("should update book with id=1 and return BookResponseDto")
        void bookIdIsCorrect_UpdatesBookAndReturnsBookRequestDto() {
            BigDecimal price = BigDecimal.valueOf(11.11);
            BigDecimal priceUpdated = BigDecimal.valueOf(22.22);
            List<Long> categoryIds = List.of(1L, 2L);
            UpdateBookRequestDto updateBookRequestDto =
                    makeUpdateBookRequestDto(NAME_PART, priceUpdated, categoryIds);
            Set<Category> categories = Set.of(
                    makeCategoryWithId(1L, ""),
                    makeCategoryWithId(2L, ""));
            Book book = makeBookWithId(CORRECT_ID, NAME_PART, price, categories);
            Book bookUpdated = makeBookWithId(CORRECT_ID, NAME_PART, priceUpdated, categories);
            BookResponseDto bookResponseDto =
                    makeBookResponseDto(CORRECT_ID, NAME_PART, price, categoryIds);

            doReturn(book).when(bookService).getBookById(CORRECT_ID);
            when(bookMapper.updateModel(book, updateBookRequestDto))
                    .thenReturn(bookUpdated);
            when(bookRepository.save(bookUpdated)).thenReturn(bookUpdated);
            when(bookMapper.toDto(bookUpdated)).thenReturn(bookResponseDto);

            assertEquals(bookResponseDto, bookService.update(CORRECT_ID, updateBookRequestDto));
            verify(bookService).getBookById(CORRECT_ID);
            verify(bookMapper).updateModel(book, updateBookRequestDto);
            verify(bookRepository).save(bookUpdated);
            verify(bookMapper).toDto(bookUpdated);
        }

        @Test
        @DisplayName("should throw EntityNotFoundException for non-existent book")
        void bookIdIsWrong_ThrowsEntityNotFoundException() {
            BigDecimal price = BigDecimal.valueOf(11.11);
            List<Long> categoryIds = List.of(1L, 2L);
            UpdateBookRequestDto updateBookRequestDto =
                    makeUpdateBookRequestDto(NAME_PART, price, categoryIds);

            doThrow(EntityNotFoundException.class).when(bookService)
                    .getBookById(WRONG_ID);

            assertThrows(EntityNotFoundException.class,
                    () -> bookService.update(WRONG_ID, updateBookRequestDto));
            verify(bookService).getBookById(WRONG_ID);
            verify(bookRepository, never()).save(any());

        }
    }

    @Nested
    @DisplayName("Tests for `delete`:")
    class DeleteTests {

        @Test
        @DisplayName("should delete book by id=1")
        void bookIdIsCorrect_RemovesBook() {
            when(bookRepository.existsById(CORRECT_ID)).thenReturn(true);

            bookService.delete(CORRECT_ID);

            verify(bookRepository).existsById(CORRECT_ID);
            verify(bookRepository).deleteById(CORRECT_ID);
        }

        @Test
        @DisplayName("should throw EntityNotFoundException for non-existent book")
        void bookIdIsWrong_ThrowsEntityNotFoundException() {
            when(bookRepository.existsById(WRONG_ID)).thenReturn(false);

            assertThrows(EntityNotFoundException.class,
                    () -> bookService.delete(WRONG_ID));
            verify(bookRepository).existsById(WRONG_ID);
            verify(bookRepository, never()).deleteById(any());
        }
    }

    @Nested
    @DisplayName("Tests for `getAllByCategoryId`:")
    class GetAllByCategoryIdTests {

        @Test
        @DisplayName("should return page with two BookResponseDtos by category id=2")
        void pageable_returnsPageWithThreeBooksResponseDtos() {
            Long categoryId = 2L;
            Book book1 = makeBookWithId(1L, "1", BigDecimal.valueOf(11.11),
                    Set.of(
                            makeCategoryWithId(1L, ""),
                            makeCategoryWithId(categoryId, "")));
            Book book2 = makeBookWithId(2L, "2", BigDecimal.valueOf(22.22),
                    Set.of(makeCategoryWithId(categoryId, "")));
            BookWithoutCategoriesResponseDto bookWithoutCategoriesResponseDto1 =
                    makeBookWithoutCategoriesResponseDto(1L, "1", BigDecimal.valueOf(11.11));
            BookWithoutCategoriesResponseDto bookWithoutCategoriesResponseDto2 =
                    makeBookWithoutCategoriesResponseDto(2L, "2", BigDecimal.valueOf(22.22));
            Pageable pageable = PageRequest.of(0, 20);

            Page<Book> books = new PageImpl<>(
                    List.of(book1, book2), pageable, 2);

            when(bookRepository.getAllByCategoryId(categoryId, pageable)).thenReturn(books);
            when(bookMapper.toDtoWithoutCategories(book1))
                    .thenReturn(bookWithoutCategoriesResponseDto1);
            when(bookMapper.toDtoWithoutCategories(book2))
                    .thenReturn(bookWithoutCategoriesResponseDto2);

            Page<BookWithoutCategoriesResponseDto> expected = new PageImpl<>(
                    List.of(
                            bookWithoutCategoriesResponseDto1,
                            bookWithoutCategoriesResponseDto2
                    ),
                    pageable, 2);

            assertEquals(expected, bookService.getAllByCategoryId(categoryId, pageable));
            verify(bookRepository).getAllByCategoryId(categoryId, pageable);
            verify(bookMapper, times(2)).toDtoWithoutCategories(any());
        }
    }

    @Nested
    @DisplayName("Tests for `getBookById`")
    class GetBookByIdTests {

        @Test
        @DisplayName("should find and return book by id=1")
        void bookIdIsCorrect_ReturnsBook() {
            Book book = makeBookWithId(CORRECT_ID, NAME_PART, BigDecimal.valueOf(11.11), Set.of());

            when(bookRepository.findById(CORRECT_ID)).thenReturn(Optional.of(book));

            assertEquals(book, bookService.getBookById(CORRECT_ID));
            verify(bookRepository).findById(CORRECT_ID);
        }

        @Test
        @DisplayName("should throw EntityNotFoundException for non-existent book")
        void bookIdIsWrong_ThrowsEntityNotFoundException() {
            when(bookRepository.findById(WRONG_ID))
                    .thenReturn(Optional.empty());

            assertThrows(EntityNotFoundException.class,
                    () -> bookService.getBookById(WRONG_ID));
            verify(bookRepository).findById(WRONG_ID);
        }
    }
}
