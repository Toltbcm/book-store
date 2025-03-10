package org.bookstore.controller;

import static org.bookstore.util.Constant.ADMIN_PASSWORD;
import static org.bookstore.util.Constant.ADMIN_USERNAME;
import static org.bookstore.util.Constant.CORRECT_ID;
import static org.bookstore.util.Constant.NAME_PART;
import static org.bookstore.util.Constant.SqlPath.ADD_5_BOOKS_PATH;
import static org.bookstore.util.Constant.SqlPath.ADD_5_CATEGORIES_PATH;
import static org.bookstore.util.Constant.SqlPath.ADD_ADMIN_PATH;
import static org.bookstore.util.Constant.SqlPath.ADD_USER_PATH;
import static org.bookstore.util.Constant.SqlPath.CLEAR_BOOKS_PATH;
import static org.bookstore.util.Constant.SqlPath.CLEAR_CATEGORIES_PATH;
import static org.bookstore.util.Constant.SqlPath.CLEAR_USERS_PATH;
import static org.bookstore.util.Constant.StatusAndMessage.FORBIDDEN_MESSAGE;
import static org.bookstore.util.Constant.StatusAndMessage.FORBIDDEN_STATUS;
import static org.bookstore.util.Constant.StatusAndMessage.NOT_FOUND_MESSAGE;
import static org.bookstore.util.Constant.StatusAndMessage.NOT_FOUND_STATUS;
import static org.bookstore.util.Constant.UPDATED_NAME_PART;
import static org.bookstore.util.Constant.URL_SEPARATOR;
import static org.bookstore.util.Constant.USER_PASSWORD;
import static org.bookstore.util.Constant.USER_USERNAME;
import static org.bookstore.util.Constant.WRONG_ID;
import static org.bookstore.util.ModelsAndDtoMaker.makeCreateBookRequestDto;
import static org.bookstore.util.ModelsAndDtoMaker.makeUpdateBookRequestDto;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.List;
import lombok.SneakyThrows;
import org.bookstore.dto.request.CreateBookRequestDto;
import org.bookstore.dto.request.UpdateBookRequestDto;
import org.bookstore.dto.response.BookResponseDto;
import org.bookstore.util.Error;
import org.bookstore.util.Page;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BookControllerTests {

    private static final String REQUEST_MAPPING = "/books";
    private static final String ENTITY_NAME = "book";

    @Autowired
    private TestRestTemplate restTemplate;

    @Nested
    @DisplayName("Tests for `getById")
    @Sql(scripts = ADD_5_BOOKS_PATH, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
    @Sql(scripts = CLEAR_BOOKS_PATH, executionPhase = Sql.ExecutionPhase.AFTER_TEST_CLASS)
    class GetByIdTests {

        @Test
        @DisplayName("should return BookResponseDto with status 200 b id=1")
        void bookIdIsCorrect_ReturnsBookResponseDtoWithStatus200() {
            ResponseEntity<BookResponseDto> response = restTemplate.getForEntity(
                    REQUEST_MAPPING + URL_SEPARATOR + CORRECT_ID, BookResponseDto.class);

            assertNotNull(response.getBody());
            assertEquals("Book title " + CORRECT_ID, response.getBody().title());
            assertEquals("000-0-00-000000-" + CORRECT_ID, response.getBody().isbn());
        }

        @Test
        @DisplayName("should return status 404 for non-existent book")
        void categoryIdIsWrong_ReturnsStatus404() {
            ResponseEntity<Error> response = restTemplate.getForEntity(
                    REQUEST_MAPPING + URL_SEPARATOR + WRONG_ID, Error.class);

            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
            assertNotNull(response.getBody());
            assertEquals(NOT_FOUND_STATUS, response.getBody().status());
            assertEquals(String.format(NOT_FOUND_MESSAGE, ENTITY_NAME, WRONG_ID),
                    response.getBody().message());
        }
    }

    @Nested
    @DisplayName("Tests for `getAll`:")
    @Sql(scripts = ADD_5_BOOKS_PATH, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
    @Sql(scripts = CLEAR_BOOKS_PATH, executionPhase = Sql.ExecutionPhase.AFTER_TEST_CLASS)
    class GetAllTests {

        @Test
        @SneakyThrows
        @DisplayName("should return page with five BookResponseDtos with status 200")
        void pageableDefault_ReturnsFiveBooksResponseDtosWithStatus200() {
            ResponseEntity<Page<BookResponseDto>> response = restTemplate.exchange(
                    REQUEST_MAPPING,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<>() {
                    });

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertNotNull(response.getBody());
            assertEquals(5, response.getBody().content().size());
            assertEquals(1, response.getBody().totalPages());
            assertEquals(5, response.getBody().totalElements());
        }
    }

    @Nested
    @DisplayName("Tests for `create`:")
    @Sql(scripts = ADD_ADMIN_PATH, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
    @Sql(scripts = ADD_USER_PATH, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
    @Sql(scripts = CLEAR_USERS_PATH, executionPhase = Sql.ExecutionPhase.AFTER_TEST_CLASS)
    class CreateTests {

        @Test
        @DisplayName("should save book and return BookResponseDto with status 201")
        @Sql(scripts = ADD_5_CATEGORIES_PATH,
                executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
        @Sql(scripts = CLEAR_BOOKS_PATH, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
        @Sql(scripts = CLEAR_CATEGORIES_PATH, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
        void createBookRequestDto_SavesBookAndReturnsBookResponseDtoWithStatus201() {
            CreateBookRequestDto requestDto =
                    makeCreateBookRequestDto(NAME_PART, BigDecimal.valueOf(11.11), List.of(1L, 2L));

            ResponseEntity<BookResponseDto> response = restTemplate
                    .withBasicAuth(ADMIN_USERNAME, ADMIN_PASSWORD)
                    .postForEntity(REQUEST_MAPPING, requestDto, BookResponseDto.class);

            assertEquals(HttpStatus.CREATED, response.getStatusCode());
            assertNotNull(response.getBody());
            assertEquals(1L, response.getBody().id());
            assertEquals("Book title " + CORRECT_ID, response.getBody().title());
            assertEquals(CORRECT_ID + "000-000-000-0000", response.getBody().isbn());
        }

        @Test
        @DisplayName("should return status 403 for user without role ADMIN")
        void userWithoutAdminRole_ReturnsStatus403() {
            CreateBookRequestDto requestDto =
                    makeCreateBookRequestDto(NAME_PART, BigDecimal.valueOf(11.11), List.of(1L, 2L));

            ResponseEntity<Error> response = restTemplate
                    .withBasicAuth(USER_USERNAME, USER_PASSWORD)
                    .postForEntity(REQUEST_MAPPING, requestDto, Error.class);

            assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
            assertNotNull(response.getBody());
            assertEquals(FORBIDDEN_STATUS, response.getBody().status());
            assertEquals(FORBIDDEN_MESSAGE, response.getBody().message());
        }
    }

    @Nested
    @DisplayName("Tests for `update`:")
    @Sql(scripts = ADD_ADMIN_PATH, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
    @Sql(scripts = ADD_USER_PATH, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
    @Sql(scripts = CLEAR_USERS_PATH, executionPhase = Sql.ExecutionPhase.AFTER_TEST_CLASS)
    class UpdateTests {

        @Test
        @DisplayName("should update book and return BookResponseDto with status 200")
        @Sql(scripts = ADD_5_BOOKS_PATH, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
        @Sql(scripts = CLEAR_BOOKS_PATH, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
        void updateBookRequestDto_UpdatesBookAndReturnsBookResponseDtoWithStatus200() {
            UpdateBookRequestDto requestDto = makeUpdateBookRequestDto(
                    UPDATED_NAME_PART, BigDecimal.valueOf(22.22), List.of(2L, 3L));

            ResponseEntity<BookResponseDto> response = restTemplate
                    .withBasicAuth(ADMIN_USERNAME, ADMIN_PASSWORD)
                    .exchange(REQUEST_MAPPING + URL_SEPARATOR + CORRECT_ID,
                            HttpMethod.PUT,
                            new HttpEntity<>(requestDto),
                            BookResponseDto.class);

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertNotNull(response.getBody());
            assertEquals(CORRECT_ID, response.getBody().id());
            assertEquals("Book title " + UPDATED_NAME_PART, response.getBody().title());
            assertEquals(UPDATED_NAME_PART + "000-000-00", response.getBody().isbn());
        }

        @Test
        @SneakyThrows
        @DisplayName("should return status 404 for non-existent id")
        void bookIdIsWrong_ReturnsStatus404() {
            UpdateBookRequestDto requestDto = makeUpdateBookRequestDto(
                    UPDATED_NAME_PART, BigDecimal.valueOf(22.22), List.of(2L, 3L));

            ResponseEntity<Error> response = restTemplate
                    .withBasicAuth(ADMIN_USERNAME, ADMIN_PASSWORD)
                    .exchange(REQUEST_MAPPING + URL_SEPARATOR + WRONG_ID,
                            HttpMethod.PUT,
                            new HttpEntity<>(requestDto),
                            Error.class);

            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
            assertNotNull(response.getBody());
            assertEquals(NOT_FOUND_STATUS, response.getBody().status());
            assertEquals(String.format(NOT_FOUND_MESSAGE, ENTITY_NAME, WRONG_ID),
                    response.getBody().message());
        }

        @Test
        @DisplayName("should return status 403 for user without role ADMIN")
        void userWithoutAdminRole_ReturnsStatus403() {
            UpdateBookRequestDto requestDto = makeUpdateBookRequestDto(
                    UPDATED_NAME_PART, BigDecimal.valueOf(22.22), List.of(2L, 3L));

            ResponseEntity<Error> response = restTemplate
                    .withBasicAuth(USER_USERNAME, USER_PASSWORD)
                    .exchange(REQUEST_MAPPING + URL_SEPARATOR + CORRECT_ID,
                            HttpMethod.PUT,
                            new HttpEntity<>(requestDto),
                            Error.class);

            assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
            assertNotNull(response.getBody());
            assertEquals(FORBIDDEN_STATUS, response.getBody().status());
            assertEquals(FORBIDDEN_MESSAGE, response.getBody().message());
        }
    }

    @Nested
    @DisplayName("Tests for `delete`:")
    @Sql(scripts = ADD_ADMIN_PATH, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
    @Sql(scripts = ADD_USER_PATH, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
    @Sql(scripts = CLEAR_USERS_PATH, executionPhase = Sql.ExecutionPhase.AFTER_TEST_CLASS)
    class DeleteTests {

        @Test
        @DisplayName("should delete book and return status 204")
        @Sql(scripts = ADD_5_BOOKS_PATH, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
        @Sql(scripts = CLEAR_BOOKS_PATH, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
        void bookIdIsCorrect_DeletesCategoryAndReturnsStatus204() {
            ResponseEntity<Void> response = restTemplate
                    .withBasicAuth(ADMIN_USERNAME, ADMIN_PASSWORD)
                    .exchange(REQUEST_MAPPING + URL_SEPARATOR + CORRECT_ID,
                            HttpMethod.DELETE,
                            null,
                            Void.class);

            assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        }

        @Test
        @DisplayName("should return status 404 for non-existent id")
        void categoryIdIsWrong_ReturnStatus404() {
            ResponseEntity<Error> response = restTemplate
                    .withBasicAuth(ADMIN_USERNAME, ADMIN_PASSWORD)
                    .exchange(REQUEST_MAPPING + URL_SEPARATOR + WRONG_ID,
                            HttpMethod.DELETE,
                            null,
                            Error.class);

            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
            assertNotNull(response.getBody());
            assertEquals(NOT_FOUND_STATUS, response.getBody().status());
            assertEquals(String.format(NOT_FOUND_MESSAGE, ENTITY_NAME, WRONG_ID),
                    response.getBody().message());
        }

        @Test
        @DisplayName("should return status 403 for user without role ADMIN")
        void userWithoutAdminRole_ReturnsStatus403() {
            ResponseEntity<Error> response = restTemplate
                    .withBasicAuth(USER_USERNAME, USER_PASSWORD)
                    .exchange(REQUEST_MAPPING + URL_SEPARATOR + CORRECT_ID,
                            HttpMethod.DELETE,
                            null,
                            Error.class);

            assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
            assertNotNull(response.getBody());
            assertEquals(FORBIDDEN_STATUS, response.getBody().status());
            assertEquals(FORBIDDEN_MESSAGE, response.getBody().message());
        }
    }

    @Nested
    @DisplayName("Tests for `search`:")
    @Sql(scripts = ADD_5_BOOKS_PATH, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
    @Sql(scripts = CLEAR_BOOKS_PATH, executionPhase = Sql.ExecutionPhase.AFTER_TEST_CLASS)
    class SearchTests {

        public static final String URL_PART_SEARCH = "search";

        @Test
        @DisplayName("should return page with two BookResponseDto "
                + "for price between 20.00 and 40.00 with status 200")
        void priceBetween20And40_ReturnsTwoBookResponseDtosWithStatus200() {
            ResponseEntity<Page<BookResponseDto>> response = restTemplate.exchange(
                    REQUEST_MAPPING + URL_SEPARATOR + URL_PART_SEARCH
                            + "?minPrice=20&maxPrice=40",
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<>() {
                    });
            assertNotNull(response.getBody());
            assertEquals(2, response.getBody().content().size());
        }

        @Test
        @DisplayName("should return page with two BookResponseDto "
                + "for price between 20.00 and 50.00 and 3 in title with status 200")
        void priceBetween20And50And3InTitle_ReturnsOneBookResponseDtoWithStatus200_() {
            ResponseEntity<Page<BookResponseDto>> response = restTemplate.exchange(
                    REQUEST_MAPPING + URL_SEPARATOR + URL_PART_SEARCH
                            + "?minPrice=20&maxPrice=50&titlePart=3",
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<>() {
                    });

            assertNotNull(response.getBody());
            assertEquals(1, response.getBody().content().size());
        }

        @Test
        @DisplayName("should return empty Page for price more than 60.00 with status 200")
        void priceMoreThan60_ReturnsZeroBookResponseDtosWithStatus200_() {
            ResponseEntity<Page<BookResponseDto>> response = restTemplate.exchange(
                    REQUEST_MAPPING + URL_SEPARATOR + URL_PART_SEARCH
                            + "?minPrice=60",
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<>() {
                    });

            assertNotNull(response.getBody());
            assertTrue(response.getBody().content().isEmpty());
        }
    }
}
