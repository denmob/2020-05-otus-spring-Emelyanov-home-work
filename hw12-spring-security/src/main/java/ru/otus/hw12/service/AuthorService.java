package ru.otus.hw12.service;

import ru.otus.hw12.model.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorService {

  Author save(Author entity);

  List<Author> findAll();
}
