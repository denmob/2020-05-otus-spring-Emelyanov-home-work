package ru.otus.hw07.impl.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw07.core.models.Genre;
import ru.otus.hw07.core.repositories.GenreRepository;
import ru.otus.hw07.core.service.CRUDServiceGenre;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CRUDGenreService implements CRUDServiceGenre {

  private final GenreRepository genreRepository;

  @Override
  public Genre create(Genre entity) {
    return genreRepository.save(entity);
  }

  @Override
  public Optional<Genre> read(long id) {
    return genreRepository.findById(id);
  }

  @Override
  public void delete(long id) {
      genreRepository.deleteById(id);
  }

  @Override
  public Genre update(Genre entity) {
    return genreRepository.save(entity);
  }
}
