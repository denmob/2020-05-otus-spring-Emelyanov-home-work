CREATE SEQUENCE book_sequence
    start 1
    increment 1;

CREATE SEQUENCE author_sequence
    start 1
    increment 1;

CREATE SEQUENCE genre_sequence
    start 1
    increment 1;

CREATE SEQUENCE comment_sequence
    start 1
    increment 1;

create table authors
(
    id         INTEGER NOT NULL PRIMARY KEY DEFAULT NEXTVAL('author_sequence'),
    first_name varchar(200),
    last_name  varchar(200),
    birthday   date
);

create table genres
(
    id   INTEGER NOT NULL PRIMARY KEY DEFAULT NEXTVAL('genre_sequence'),
    name varchar(200)
);

create table books
(
    id        INTEGER NOT NULL PRIMARY KEY DEFAULT NEXTVAL('book_sequence'),
    author_id bigint,
    genre_id  bigint,
    title     varchar(200),
    date      date
);

create table comments
(
    id         INTEGER NOT NULL PRIMARY KEY DEFAULT NEXTVAL('comment_sequence'),
    commentary varchar(200),
    book_id    bigint references books (id) on delete cascade
);
