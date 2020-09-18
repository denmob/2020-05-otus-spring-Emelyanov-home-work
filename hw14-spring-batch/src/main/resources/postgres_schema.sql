CREATE SEQUENCE IF NOT EXISTS book_sequence
    start 1
    increment 1;

CREATE SEQUENCE IF NOT EXISTS author_sequence
    start 1
    increment 1;

CREATE SEQUENCE IF NOT EXISTS genre_sequence
    start 1
    increment 1;

CREATE SEQUENCE IF NOT EXISTS comment_sequence
    start 1
    increment 1;

CREATE TABLE IF NOT EXISTS authors
(
    id         INTEGER NOT NULL PRIMARY KEY DEFAULT NEXTVAL('author_sequence'),
    first_name varchar(200),
    last_name  varchar(200),
    birthday   date
);

CREATE TABLE IF NOT EXISTS genres
(
    id   INTEGER NOT NULL PRIMARY KEY DEFAULT NEXTVAL('genre_sequence'),
    name varchar(200)
);

CREATE TABLE IF NOT EXISTS books
(
    id        INTEGER NOT NULL PRIMARY KEY DEFAULT NEXTVAL('book_sequence'),
    author_id bigint,
    genre_id  bigint,
    title     varchar(200),
    date      date
);

CREATE TABLE IF NOT EXISTS comments
(
    id         INTEGER NOT NULL PRIMARY KEY DEFAULT NEXTVAL('comment_sequence'),
    commentary varchar(200),
    book_id    bigint references books (id) on delete cascade
);
