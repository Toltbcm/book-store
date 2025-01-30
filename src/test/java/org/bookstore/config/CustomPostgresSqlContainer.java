package org.bookstore.config;

import org.testcontainers.containers.PostgreSQLContainer;

public class CustomPostgresSqlContainer extends PostgreSQLContainer<CustomPostgresSqlContainer> {

    private static final String DB_IMAGE = "postgres:17-alpine3.21";
    private static CustomPostgresSqlContainer postgresSqlContainer;

    private CustomPostgresSqlContainer() {
        super(DB_IMAGE);
    }

    public static synchronized CustomPostgresSqlContainer getInstance() {
        if (postgresSqlContainer == null) {
            postgresSqlContainer = new CustomPostgresSqlContainer();
        }
        return postgresSqlContainer;
    }

    @Override
    public void start() {
        super.start();
        System.setProperty("TEST_DB_URL", postgresSqlContainer.getJdbcUrl());
        System.setProperty("TEST_DB_USERNAME", postgresSqlContainer.getUsername());
        System.setProperty("TEST_DB_PASSWORD", postgresSqlContainer.getPassword());
    }

    @Override
    public void stop() {
        super.stop();
    }
}
