package org.bookstore.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Map;
import lombok.SneakyThrows;
import org.bookstore.dto.response.CategoryResponseDto;
import org.bookstore.service.BookService;
import org.bookstore.service.CategoryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CategoryControllerTests {

    private static final String REQUEST_MAPPING = "/categories";
    private static final String URL_SEPARATOR = "/";

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private BookService bookService;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;


    @Nested
    @DisplayName("Tests for `getById`:")
    @Sql(scripts = "classpath:database/categories/add-5-categories.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
    @Sql(scripts = "classpath:database/categories/clear.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_CLASS)
    class getById {

        @Test
        @DisplayName("should return CategoryResponseDto with status 200")
        void categoryIdIsCorrect_ReturnsCategoryResponseDtoWithStatusCode200() {
            Long id = 2L;

            ResponseEntity<CategoryResponseDto> response =
                    restTemplate.getForEntity(
                            REQUEST_MAPPING + URL_SEPARATOR + id, CategoryResponseDto.class);

            assertEquals("Cat 2", response.getBody().name());
            assertEquals("Category 2", response.getBody().description());
        }

        @Test
        @SneakyThrows
        @DisplayName("should return status 404 for non-existent category")
        void categoryIdIsWrong_ReturnsStatusCode404() {
            Long id = 77L;

            ResponseEntity<String> response =
                    restTemplate.getForEntity(
                            REQUEST_MAPPING + URL_SEPARATOR + id + id, String.class);

            Map<String, Object> responseMap = objectMapper.readValue(
                    response.getBody(), new TypeReference<>() {
                    });

            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
            assertEquals("404: Not Found", responseMap.get("status"));
            assertEquals("Can't find category by id: " + id, responseMap.get("message"));
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
        void pageableDefault_ReturnsFiveCategoryResponseDtosWithStatusCode200() {
            ResponseEntity<String> response =
                    restTemplate.getForEntity(REQUEST_MAPPING, String.class);

            Map<String, Object> responseMap = objectMapper.readValue(
                    response.getBody(), new TypeReference<>() {
                    });

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(5, ((List<Object>) responseMap.get("content")).size());
            assertEquals(1, responseMap.get("totalPages"));
            assertEquals(5, responseMap.get("totalElements"));
        }
    }
}
