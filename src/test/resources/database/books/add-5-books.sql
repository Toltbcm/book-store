INSERT INTO categories(id, name, description, is_deleted)
VALUES (1, 'cat-1', 'Category 1', false),
       (2, 'cat-2', 'Category 2', false),
       (3, 'cat-3', 'Category 3', false),
       (4, 'cat-4', 'Category 4', false),
       (5, 'cat-5', 'Category 5', false);

INSERT INTO books(id, title, author, isbn, price, description, cover_image, is_deleted)
VALUES (1, 'Book title 1', 'Author 1', '000-0-00-000000-1', 11.11, 'Book description 1',
        'https://cover_image_1.jpg', false),
       (2, 'Book title 2', 'Author 2', '000-0-00-000000-2', 22.22, 'Book description 2',
        'https://cover_image_2.jpg', false),
       (3, 'Book title 3', 'Author 3', '000-0-00-000000-3', 33.33, 'Book description 3',
        'https://cover_image_3.jpg', false),
       (4, 'Book title 4', 'Author 4', '000-0-00-000000-4', 44.44, 'Book description 4',
        'https://cover_image_4.jpg', false),
       (5, 'Book title 5', 'Author 5', '000-0-00-000000-5', 55.55, 'Book description 5',
        'https://cover_image_5.jpg', false);

INSERT INTO books_categories(book_id, category_id)
VALUES (1, 1),
       (1, 2),
       (1, 4),
       (1, 5),
       (2, 3),
       (3, 1),
       (3, 3),
       (3, 5),
       (4, 2),
       (4, 5),
       (5, 3),
       (5, 4);
