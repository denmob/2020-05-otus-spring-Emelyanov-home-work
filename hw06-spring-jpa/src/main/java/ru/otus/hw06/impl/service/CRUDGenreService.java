package ru.otus.hw06.impl.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw06.core.models.Genre;
import ru.otus.hw06.core.repositories.GenreRepositoryJpa;
import ru.otus.hw06.core.service.CRUDService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CRUDGenreService implements CRUDService<Genre> {

  private final GenreRepositoryJpa genreRepositoryJpa;

  @Override
  public Genre create(Genre entity) {
    return genreRepositoryJpa.insert(entity);
  }

  @Override
  public Optional<Genre> read(long id) {
    return genreRepositoryJpa.getById(id);
  }

  @Override
  public boolean delete(long id) {
    return genreRepositoryJpa.deleteById(id);
  }

  @Override
  public Genre update(Genre entity) {
    return genreRepositoryJpa.insert(entity);
  }
}
