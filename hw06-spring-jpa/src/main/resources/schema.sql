create table authors (id bigserial PRIMARY KEY, first_name varchar(200), last_name varchar(200), birthday date);

create table genres (id bigserial PRIMARY KEY, name varchar(200));

create table books (id number PRIMARY KEY, author_id bigint, genre_id bigint, title varchar(200), date date);

create table comments (id bigserial PRIMARY KEY, name varchar(200), book_id bigint);
