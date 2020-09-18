package ru.otus.hw14.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw14.model.document.GenreDocument;
import ru.otus.hw14.repository.mongo.GenreMongoRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GenreMongoServiceImpl implements GenreMongoService {

  private final GenreMongoRepository genreMongoRepository;

  @Override
  public Optional<GenreDocument> findById(String id) {
    return genreMongoRepository.findById(id);
  }

  @Override
  public List<GenreDocument> findAll() {
    return genreMongoRepository.findAll();
  }

  @Override
  @Transactional
  public GenreDocument save(GenreDocument entity) {
    return genreMongoRepository.save(entity);
  }

  @Override
  @Transactional
  public void delete(GenreDocument entity) {
    genreMongoRepository.delete(entity);
  }

  @Override
  @Transactional
  public void deleteAll() {
    genreMongoRepository.deleteAll();
  }
}
