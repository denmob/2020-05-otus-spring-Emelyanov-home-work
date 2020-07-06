package ru.otus.hw06.impl.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw06.core.models.Author;
import ru.otus.hw06.core.repositories.AuthorRepositoryJpa;
import ru.otus.hw06.core.service.CRUDService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CRUDAuthorService implements CRUDService<Author> {

  private final AuthorRepositoryJpa authorRepositoryJpa;

  @Override
  public Author create(Author entity) {
    return authorRepositoryJpa.insert(entity);
  }

  @Override
  public Optional<Author> read(long id) {
    return authorRepositoryJpa.getById(id);
  }

  @Override
  public boolean delete(long id) {
    return authorRepositoryJpa.deleteById(id);
  }

  @Override
  public Author update(Author entity) {
    return authorRepositoryJpa.insert(entity);
  }
}
