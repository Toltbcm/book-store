package org.bookstore.controller;

import static org.bookstore.util.Constant.ADMIN_PASSWORD;
import static org.bookstore.util.Constant.ADMIN_USERNAME;
import static org.bookstore.util.Constant.CORRECT_ID;
import static org.bookstore.util.Constant.NAME_PART;
import static org.bookstore.util.Constant.NOT_FOUND_MESSAGE;
import static org.bookstore.util.Constant.NOT_FOUND_STATUS;
import static org.bookstore.util.Constant.UPDATED_NAME_PART;
import static org.bookstore.util.Constant.URL_SEPARATOR;
import static org.bookstore.util.Constant.WRONG_ID;
import static org.bookstore.util.ModelsAndDtoMaker.makeCreateCategoryRequestDto;
import static org.bookstore.util.ModelsAndDtoMaker.makeUpdateCategoryRequestDto;
import static org.junit.jupiter.api.Assertions.assertEquals;

import lombok.SneakyThrows;
import org.bookstore.dto.request.CreateCategoryRequestDto;
import org.bookstore.dto.request.UpdateCategoryRequestDto;
import org.bookstore.dto.response.CategoryResponseDto;
import org.bookstore.util.ErrorObject;
import org.bookstore.util.PageObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CategoryControllerTests {

    private static final String REQUEST_MAPPING = "/categories";

    @Autowired
    private TestRestTemplate restTemplate;

    @Nested
    @DisplayName("Tests for `getById`:")
    @Sql(scripts = "classpath:database/categories/add-5-categories.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
    @Sql(scripts = "classpath:database/categories/clear.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_CLASS)
    class getById {

        @Test
        @DisplayName("should return CategoryResponseDto with status 200")
        void categoryIdIsCorrect_ReturnsCategoryResponseDtoWithStatus200() {
            ResponseEntity<CategoryResponseDto> response = restTemplate.getForEntity(
                    REQUEST_MAPPING + URL_SEPARATOR + CORRECT_ID, CategoryResponseDto.class);

            assertEquals("Cat " + CORRECT_ID, response.getBody().name());
            assertEquals("Category " + CORRECT_ID, response.getBody().description());
        }

        @Test
        @SneakyThrows
        @DisplayName("should return status 404 for non-existent category")
        void categoryIdIsWrong_ReturnsStatus404() {
            ResponseEntity<ErrorObject> response = restTemplate.getForEntity(
                    REQUEST_MAPPING + URL_SEPARATOR + WRONG_ID, ErrorObject.class);

            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
            assertEquals(NOT_FOUND_STATUS, response.getBody().status());
            assertEquals(NOT_FOUND_MESSAGE + WRONG_ID, response.getBody().message());
        }
    }

    @Nested
    @DisplayName("Tests for `getAll`:")
    @Sql(scripts = "classpath:database/categories/add-5-categories.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
    @Sql(scripts = "classpath:database/categories/clear.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_CLASS)
    class GetAllTests {

        @Test
        @SneakyThrows
        @DisplayName("should return page with five CategoryResponseDtos with status 200")
        void pageableDefault_ReturnsFiveCategoryResponseDtosWithStatus200() {
            ResponseEntity<PageObject> response =
                    restTemplate.getForEntity(REQUEST_MAPPING, PageObject.class);

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(5, response.getBody().content().size());
            assertEquals(1, response.getBody().totalPages());
            assertEquals(5, response.getBody().totalElements());
        }
    }

    @Nested
    @DisplayName("Tests for `create`:")
    @Sql(scripts = "classpath:database/user/add-admin.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
    @Sql(scripts = "classpath:database/user/clear.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_CLASS)
    class CreateTests {

        @Test
        @DisplayName(
                "should save category and return CategoryResponseDto with status 201")
        @Sql(scripts = "classpath:database/categories/clear.sql",
                executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
        void createCategoryRequestDtoAndCategoryIdIsCorrect_SavesCategoryAndReturnsCategoryResponseDtoWithStatus201() {
            CreateCategoryRequestDto requestDto = makeCreateCategoryRequestDto(NAME_PART);

            ResponseEntity<CategoryResponseDto> response = restTemplate
                    .withBasicAuth(ADMIN_USERNAME, ADMIN_PASSWORD)
                    .postForEntity(REQUEST_MAPPING, requestDto, CategoryResponseDto.class);

            assertEquals(HttpStatus.CREATED, response.getStatusCode());
            assertEquals(1L, response.getBody().id());
            assertEquals("Cat 1", response.getBody().name());
            assertEquals("Description 1", response.getBody().description());
        }
    }

    @Nested
    @DisplayName("Tests for `update`:")
    @Sql(scripts = "classpath:database/user/add-admin.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
    @Sql(scripts = "classpath:database/user/clear.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_CLASS)
    class UpdateTests {

        @Test
        @DisplayName(
                "should update category and return CategoryResponseDto with status 200")
        @Sql(scripts = "classpath:database/categories/add-5-categories.sql",
                executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
        @Sql(scripts = "classpath:database/categories/clear.sql",
                executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
        void updateCategoryRequestDtoAndCategoryIdIsCorrect_UpdatesCategoryAndReturnsCategoryResponseDtoWithStatus200() {
            UpdateCategoryRequestDto requestDto = makeUpdateCategoryRequestDto(UPDATED_NAME_PART);

            ResponseEntity<CategoryResponseDto> response = restTemplate
                    .withBasicAuth(ADMIN_USERNAME, ADMIN_PASSWORD)
                    .exchange(REQUEST_MAPPING + URL_SEPARATOR + CORRECT_ID,
                            HttpMethod.PUT,
                            new HttpEntity<>(requestDto),
                            CategoryResponseDto.class);

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(CORRECT_ID, response.getBody().id());
            assertEquals("Cat " + UPDATED_NAME_PART, response.getBody().name());
            assertEquals("Description " + UPDATED_NAME_PART, response.getBody().description());
        }

        @Test
        @SneakyThrows
        @DisplayName(
                "should return status 404 for non-existent id")
        void categoryIdIsWrong_ReturnsStatus404() {
            UpdateCategoryRequestDto requestDto = makeUpdateCategoryRequestDto(UPDATED_NAME_PART);

            ResponseEntity<ErrorObject> response = restTemplate
                    .withBasicAuth(ADMIN_USERNAME, ADMIN_PASSWORD)
                    .exchange(REQUEST_MAPPING + URL_SEPARATOR + WRONG_ID,
                            HttpMethod.PUT,
                            new HttpEntity<>(requestDto),
                            ErrorObject.class);

            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
            assertEquals(NOT_FOUND_STATUS, response.getBody().status());
            assertEquals(NOT_FOUND_MESSAGE + WRONG_ID, response.getBody().message());
        }
    }

    @Nested
    @DisplayName("Tests for `delete`:")
    @Sql(scripts = "classpath:database/user/add-admin.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
    @Sql(scripts = "classpath:database/user/clear.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_CLASS)
    class DeleteTests {

        @Test
        @DisplayName(
                "should delete category and return status 204")
        @Sql(scripts = "classpath:database/categories/add-5-categories.sql",
                executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
        @Sql(scripts = "classpath:database/categories/clear.sql",
                executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
        void categoryIdIsCorrect_DeletesCategoryAndReturnsStatus204() {
            ResponseEntity<ErrorObject> response = restTemplate
                    .withBasicAuth(ADMIN_USERNAME, ADMIN_PASSWORD)
                    .exchange(REQUEST_MAPPING + URL_SEPARATOR + CORRECT_ID,
                            HttpMethod.DELETE,
                            null,
                            ErrorObject.class);

            assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        }

        @Test
        @DisplayName(
                "should return status 404 for non-existent id")
        void categoryIdIsWrong_ReturnStatus404() {
            ResponseEntity<ErrorObject> response = restTemplate
                    .withBasicAuth(ADMIN_USERNAME, ADMIN_PASSWORD)
                    .exchange(REQUEST_MAPPING + URL_SEPARATOR + WRONG_ID,
                            HttpMethod.DELETE,
                            null,
                            ErrorObject.class);

            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
            assertEquals(NOT_FOUND_STATUS, response.getBody().status());
            assertEquals(NOT_FOUND_MESSAGE + WRONG_ID, response.getBody().message());
        }
    }
}
