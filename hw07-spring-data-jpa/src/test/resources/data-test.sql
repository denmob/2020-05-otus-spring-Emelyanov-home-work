delete from books;
delete from comments;
delete from genres;
delete from authors;

insert into authors(id, first_Name, last_Name, birthday)
values (1, 'Jeff', 'Langr', '1969-11-08'),
       (2, 'Joshua', 'Bloch', '1961-08-29'),
       (3, 'Cay S.', 'Horstmann ', '1959-03-19');

insert into genres(id, name)
values (1, 'Programming'),
       (2, 'Science'),
       (3, 'Software');

insert into books(id, author_id, genre_id, title, date)
values (1, 1, 3, 'TestBook 1', '2015-05-01'),
       (2, 2, 1, 'TestBook 2', '2018-01-01'),
       (3, 3, 2, 'TestBook 3', '2016-05-17');

insert into comments(id, commentary, book_id)
values (1, 'Test 1', 1),
       (2, 'Test 2', 1);





