package ru.otus.hw14.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw14.model.document.Genre;
import ru.otus.hw14.repository.mongo.GenreMongoRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GenreMongoServiceImpl implements GenreMongoService {

  private final GenreMongoRepository genreMongoRepository;

  @Override
  public Optional<Genre> findById(String id) {
    return genreMongoRepository.findById(id);
  }

  @Override
  public Iterable<Genre> findAll() {
    return genreMongoRepository.findAll();
  }

  @Override
  @Transactional
  public Genre save(Genre entity) {
    return genreMongoRepository.save(entity);
  }

  @Override
  @Transactional
  public void delete(Genre entity) {
    genreMongoRepository.delete(entity);
  }

  @Override
  @Transactional
  public void deleteAll() {
    genreMongoRepository.deleteAll();
  }
}
