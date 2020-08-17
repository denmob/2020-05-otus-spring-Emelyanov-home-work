package ru.otus.hw11.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.hw11.model.Genre;
import ru.otus.hw11.repository.GenreRepository;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

  private final GenreRepository genreRepository;

  @Override
  @Transactional
  public Mono<Genre> save(Genre entity) {
    return genreRepository.save(entity);
  }

  @Override
  public Mono<Genre> findByNameEquals(String name) {
    return genreRepository.findByNameEquals(name);
  }

  @Override
  public Mono<Genre> findById(String genreId) {
    return genreRepository.findById(genreId);
  }

  @Override
  @Transactional
  public Mono<Void> deleteByNameEquals(String name) {
    return genreRepository.deleteByNameEquals(name);
  }

  @Override
  public Flux<Genre> findAll() {
    return genreRepository.findAll();
  }
}
