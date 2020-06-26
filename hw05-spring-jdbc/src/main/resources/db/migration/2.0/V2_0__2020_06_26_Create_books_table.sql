--date: 2020-06-26
--author: denmob

create table books (id number PRIMARY KEY, author_id bigint, genre_id bigint, title varchar(200), date date);

alter table books ADD FOREIGN KEY (author_id) REFERENCES authors(id);

alter table books ADD FOREIGN KEY (genre_id) REFERENCES genres(id);


