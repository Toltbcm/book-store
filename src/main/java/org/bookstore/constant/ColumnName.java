package org.bookstore.constant;

public final class ColumnName {

    public static final class BookColumns {
        public static final String ID = "id";
        public static final String TITLE = "title";
        public static final String AUTHOR = "author";
        public static final String ISBN = "isbn";
        public static final String PRICE = "price";
        public static final String DESCRIPTION = "description";
        public static final String COVER_IMAGE = "cover_image";
        public static final String IS_DELETED = "is_deleted";

        private BookColumns() {
        }
    }

    private ColumnName() {
    }
}
