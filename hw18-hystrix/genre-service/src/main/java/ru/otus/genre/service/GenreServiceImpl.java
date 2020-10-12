package ru.otus.genre.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.genre.repository.GenreRepository;
import ru.otus.library.model.Genre;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

  private final GenreRepository genreRepository;

  @Override
  @Transactional
  public Genre save(Genre entity) {
    return genreRepository.save(entity);
  }

  @Override
  public Optional<Genre> findByNameEquals(String name) {
    return genreRepository.findByNameEquals(name);
  }

  @Override
  public Optional<Genre> findById(String genreId) {
    return genreRepository.findById(genreId);
  }

  @Override
  @Transactional
  public boolean deleteByNameEquals(String name) {
    return genreRepository.deleteByNameEquals(name) == 1L;
  }

  @Override
  public List<Genre> findAll() {
    return genreRepository.findAll();
  }
}
