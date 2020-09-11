delete
from books;
delete
from comments;
delete
from genres;
delete
from authors;

insert into authors(first_name, last_name, birthday)
values ('Jeff', 'Langr', '1969-11-08'),
       ('Joshua', 'Bloch', '1961-08-29'),
       ('Cay S.', 'Horstmann ', '1959-03-19');

insert into genres(name)
values ('Programming'),
       ('Science'),
       ('Software');

insert into books(author_id, genre_id, title, date)
values (1, 3, 'Pragmatic Unit Testing in Java 8 with JUnit', '2015-05-01'),
       (2, 1, 'Effective Java', '2018-01-01'),
       (3, 2, 'Java Core Fundamentals', '2016-05-17');

insert into comments(commentary, book_id)
values ('Test 1', 1),
       ('Test 2', 1),
       ('Test 3', 2),
       ('Test 4', 2);





