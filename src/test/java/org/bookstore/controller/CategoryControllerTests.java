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
import static org.bookstore.util.ModelsAndDtoMaker.makeCreateCategoryRequestDto;
import static org.bookstore.util.ModelsAndDtoMaker.makeUpdateCategoryRequestDto;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.bookstore.dto.request.CreateCategoryRequestDto;
import org.bookstore.dto.request.UpdateCategoryRequestDto;
import org.bookstore.dto.response.CategoryResponseDto;
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
class CategoryControllerTests {

    private static final String REQUEST_MAPPING = "/categories";
    private static final String ENTITY_NAME = "category";

    @Autowired
    private TestRestTemplate restTemplate;

    @Nested
    @DisplayName("Tests for `getById`:")
    @Sql(scripts = ADD_5_CATEGORIES_PATH, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
    @Sql(scripts = CLEAR_CATEGORIES_PATH, executionPhase = Sql.ExecutionPhase.AFTER_TEST_CLASS)
    class GetByIdTests {

        @Test
        @DisplayName("should return CategoryResponseDto with status 200 by id=1")
        void categoryIdIsCorrect_ReturnsCategoryResponseDtoWithStatus200() {
            ResponseEntity<CategoryResponseDto> response = restTemplate.getForEntity(
                    REQUEST_MAPPING + URL_SEPARATOR + CORRECT_ID, CategoryResponseDto.class);

            assertNotNull(response.getBody());
            assertEquals("Cat " + CORRECT_ID, response.getBody().name());
            assertEquals("Category " + CORRECT_ID, response.getBody().description());
        }

        @Test
        @DisplayName("should return status 404 for non-existent category")
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
    @Sql(scripts = ADD_5_CATEGORIES_PATH, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
    @Sql(scripts = CLEAR_CATEGORIES_PATH, executionPhase = Sql.ExecutionPhase.AFTER_TEST_CLASS)
    class GetAllTests {

        @Test
        @DisplayName("should return page with five CategoryResponseDtos with status 200")
        void pageableDefault_ReturnsFiveCategoryResponseDtosWithStatus200() {
            ResponseEntity<Page<CategoryResponseDto>> response =
                    restTemplate.exchange(REQUEST_MAPPING,
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
        @DisplayName("should save category and return CategoryResponseDto with status 201")
        @Sql(scripts = CLEAR_CATEGORIES_PATH, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
        void createCategoryRequestDto_SavesCategoryAndReturnsCategoryResponseDtoWithStatus201() {
            CreateCategoryRequestDto requestDto = makeCreateCategoryRequestDto(NAME_PART);

            ResponseEntity<CategoryResponseDto> response = restTemplate
                    .withBasicAuth(ADMIN_USERNAME, ADMIN_PASSWORD)
                    .postForEntity(REQUEST_MAPPING, requestDto, CategoryResponseDto.class);

            assertEquals(HttpStatus.CREATED, response.getStatusCode());
            assertNotNull(response.getBody());
            assertEquals(1L, response.getBody().id());
            assertEquals("Cat 1", response.getBody().name());
            assertEquals("Description 1", response.getBody().description());
        }

        @Test
        @DisplayName("should return status 403 for user without role ADMIN")
        void userWithoutAdminRole_ReturnsStatus403() {
            CreateCategoryRequestDto requestDto = makeCreateCategoryRequestDto(NAME_PART);

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
        @DisplayName("should update category and return CategoryResponseDto with status 200")
        @Sql(scripts = ADD_5_CATEGORIES_PATH,
                executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
        @Sql(scripts = CLEAR_CATEGORIES_PATH, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
        void updateCategoryRequestDto_UpdatesCategoryAndReturnsCategoryResponseDtoWithStatus200() {
            UpdateCategoryRequestDto requestDto = makeUpdateCategoryRequestDto(UPDATED_NAME_PART);

            ResponseEntity<CategoryResponseDto> response = restTemplate
                    .withBasicAuth(ADMIN_USERNAME, ADMIN_PASSWORD)
                    .exchange(REQUEST_MAPPING + URL_SEPARATOR + CORRECT_ID,
                            HttpMethod.PUT,
                            new HttpEntity<>(requestDto),
                            CategoryResponseDto.class);

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertNotNull(response.getBody());
            assertEquals(CORRECT_ID, response.getBody().id());
            assertEquals("Cat " + UPDATED_NAME_PART, response.getBody().name());
            assertEquals("Description " + UPDATED_NAME_PART, response.getBody().description());
        }

        @Test
        @DisplayName("should return status 404 for non-existent id")
        void categoryIdIsWrong_ReturnsStatus404() {
            UpdateCategoryRequestDto requestDto = makeUpdateCategoryRequestDto(UPDATED_NAME_PART);

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
            UpdateCategoryRequestDto requestDto = makeUpdateCategoryRequestDto(UPDATED_NAME_PART);

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
        @DisplayName("should delete category and return status 204")
        @Sql(scripts = ADD_5_CATEGORIES_PATH,
                executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
        @Sql(scripts = CLEAR_CATEGORIES_PATH, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
        void categoryIdIsCorrect_DeletesCategoryAndReturnsStatus204() {
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
    @DisplayName("Tests for `getBooksByCategory`:")
    class GetBooksByCategoryTests {

        private static final String URL_PART_BOOKS = "books";

        @Test
        @DisplayName("should return page with two CategoryResponseDtos with status 200 "
                + "by category id=1")
        @Sql(scripts = ADD_5_BOOKS_PATH, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
        @Sql(scripts = CLEAR_BOOKS_PATH, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
        void pageableDefault_ReturnsTwoCategoryResponseDtosWithStatus200() {
            ResponseEntity<Page<CategoryResponseDto>> response = restTemplate.exchange(
                    REQUEST_MAPPING + URL_SEPARATOR + CORRECT_ID
                            + URL_SEPARATOR + URL_PART_BOOKS,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<>() {
                    });

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertNotNull(response.getBody());
            assertEquals(2, response.getBody().content().size());
            assertEquals(1, response.getBody().totalPages());
            assertEquals(2, response.getBody().totalElements());
        }
    }
}
