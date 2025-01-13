package org.bookstore.constant;

public final class ColumnName {

    public static final class Book {
        public static final String ID = "id";
        public static final String TITLE = "title";
        public static final String AUTHOR = "author";
        public static final String ISBN = "isbn";
        public static final String PRICE = "price";
        public static final String DESCRIPTION = "description";
        public static final String COVER_IMAGE = "cover_image";
        public static final String IS_DELETED = "is_deleted";

        private Book() {
        }
    }

    public static final class User {
        public static final String ID = "id";
        public static final String EMAIL = "email";
        public static final String PASSWORD = "password";
        public static final String FIRST_NAME = "first_name";
        public static final String LAST_NAME = "last_name";
        public static final String SHIPPING_ADDRESS = "shipping_address";

        private User() {
        }
    }

    private ColumnName() {
    }
}
