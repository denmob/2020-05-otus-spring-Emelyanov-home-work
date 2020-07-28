package ru.otus.hw09.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw09.model.Genre;
import ru.otus.hw09.repository.GenreRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

  private final GenreRepository genreRepository;

  @Override
  @Transactional
  public Genre create(Genre entity) {
    return genreRepository.save(entity);
  }

  @Override
  public Optional<Genre> readNameEquals(String name) {
    return genreRepository.findByNameEquals(name);
  }

  @Override
  @Transactional
  public boolean deleteNameEquals(String name) {
      return genreRepository.deleteByNameEquals(name)==1L;
  }

  @Override
  @Transactional
  public Genre update(Genre entity) {
    return genreRepository.save(entity);
  }
}
