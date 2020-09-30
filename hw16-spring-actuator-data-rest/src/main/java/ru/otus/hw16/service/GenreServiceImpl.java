package ru.otus.hw16.service;

import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw16.config.MonitoredService;
import ru.otus.hw16.model.Genre;
import ru.otus.hw16.repository.GenreRepository;

import java.util.List;
import java.util.Optional;

@Service
@MonitoredService
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

  private final GenreRepository genreRepository;

  @Override
  @Transactional
  @Timed(extraTags = {"componentClass", "GenreServiceImpl", "methodName", "save", "componentType", "service"})
  public Genre save(Genre entity) {
    return genreRepository.save(entity);
  }

  @Override
  @Timed(extraTags = {"componentClass", "GenreServiceImpl", "methodName", "findById", "componentType", "service"})
  public Optional<Genre> findById(String genreId) {
    return genreRepository.findById(genreId);
  }

  @Override
  @Timed(extraTags = {"componentClass", "GenreServiceImpl", "methodName", "findAll", "componentType", "service"})
  public List<Genre> findAll() {
    return genreRepository.findAll();
  }
}
