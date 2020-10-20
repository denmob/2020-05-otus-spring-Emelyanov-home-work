package ru.otus.book.service;

import ru.otus.library.model.Author;

public interface DefaultAuthorService {

  Author getAuthorByLastName(String lastName);
}
