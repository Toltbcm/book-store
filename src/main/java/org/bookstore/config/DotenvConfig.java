package org.bookstore.config;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DotenvConfig {

    @PostConstruct
    public void loadEnv() {
        Dotenv dotenv = Dotenv.configure()
                .filename(".env")
                .load();

        dotenv.entries()
                .forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));
    }
}
