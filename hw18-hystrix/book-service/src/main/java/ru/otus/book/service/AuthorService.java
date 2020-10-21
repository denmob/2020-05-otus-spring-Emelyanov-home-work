package ru.otus.book.service;

import ru.otus.library.model.Author;

public interface AuthorService {

  Author getAuthorByLastName(String lastName);
}
