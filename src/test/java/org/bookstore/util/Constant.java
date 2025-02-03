package org.bookstore.util;

public class Constant {
    public static final String URL_SEPARATOR = "/";
    public static final String ADMIN_USERNAME = "admin@test.com";
    public static final String ADMIN_PASSWORD = "admin_test_password";
    public static final String USER_USERNAME = "user@test.com";
    public static final String USER_PASSWORD = "user_test_password";
    public static final String NOT_FOUND_MESSAGE = "Can't find category by id: ";
    public static final String NOT_FOUND_STATUS = "404: Not Found";
    public static final long CORRECT_ID = 1L;
    public static final long WRONG_ID = 77L;
    public static final String NAME_PART = "1";
    public static final String UPDATED_NAME_PART = "updated";

    public static class SqlPath {
        public static final String ADD_5_BOOKS_PATH = "classpath:database/books/add-5-books.sql";
        public static final String CLEAR_BOOKS_PATH = "classpath:database/books/clear.sql";
        public static final String ADD_5_CATEGORIES_PATH = "classpath:database/categories/add-5-categories.sql";
        public static final String CLEAR_CATEGORIES_PATH = "classpath:database/categories/clear.sql";
        public static final String ADD_ADMIN_PATH = "classpath:database/user/add-admin.sql";
        public static final String ADD_USER_PATH = "classpath:database/user/add-user.sql";
        public static final String CLEAR_USERS_PATH = "classpath:database/user/clear.sql";
    }
}
