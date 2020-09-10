
create table authors
(
    id         bigint PRIMARY KEY,
    first_name varchar(200),
    last_name  varchar(200),
    birthday   date
);

create table genres
(
    id   bigint PRIMARY KEY,
    name varchar(200)
);

create table books
(
    id        bigint PRIMARY KEY,
    author_id bigint,
    genre_id  bigint,
    title     varchar(200),
    date      date
);

create table comments
(
    id         bigint PRIMARY KEY,
    commentary varchar(200),
    book_id    bigint references books (id) on delete cascade
);
