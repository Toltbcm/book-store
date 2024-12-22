package org.bookstore;

import java.math.BigDecimal;
import org.bookstore.model.Book;
import org.bookstore.service.BookService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackages = "org.bookstore")
public class BookStoreApplication {

    private final BookService bookService;

    public BookStoreApplication(BookService bookService) {
        this.bookService = bookService;
    }

    public static void main(String[] args) {
        SpringApplication.run(BookStoreApplication.class, args);

    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            Book book = new Book();
            book.setTitle("Fairy tales");
            book.setAuthor("Bob");
            book.setIsbn("000-0-00-000000-0");
            book.setPrice(BigDecimal.valueOf(33.55));
            book.setDescription("Bla bla bla");
            book.setCoverImage("000-0-00-000000-0.jpg");

            bookService.save(book);
        };
    }
}
