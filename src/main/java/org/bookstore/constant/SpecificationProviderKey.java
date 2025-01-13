package org.bookstore.constant;

public final class SpecificationProviderKey {

    public static final class Book {
        public static final String TITLE_PART = "title_part";
        public static final String AUTHOR_PART = "author_part";
        public static final String ISBN_PART = "isbn_part";
        public static final String DESCRIPTION_PART = "description_part";
        public static final String MIN_PRICE = "min_price";
        public static final String MAX_PRICE = "max_price";

        private Book() {
        }
    }

    private SpecificationProviderKey() {
    }
}