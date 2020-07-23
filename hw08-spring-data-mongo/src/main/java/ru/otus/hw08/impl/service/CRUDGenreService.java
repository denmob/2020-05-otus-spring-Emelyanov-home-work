package ru.otus.hw08.impl.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw08.core.models.Genre;
import ru.otus.hw08.core.repositories.GenreRepository;
import ru.otus.hw08.core.service.CRUDServiceGenre;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CRUDGenreService implements CRUDServiceGenre {

  private final GenreRepository genreRepository;

  @Override
  @Transactional
  public Genre create(Genre entity) {
    return genreRepository.save(entity);
  }

  @Override
  public Optional<Genre> read(String id) {
    return genreRepository.findById(id);
  }

  @Override
  @Transactional
  public boolean delete(String id) {
      return genreRepository.deleteGenreById(id)==1L;
  }

  @Override
  @Transactional
  public Genre update(Genre entity) {
    return genreRepository.save(entity);
  }
}
