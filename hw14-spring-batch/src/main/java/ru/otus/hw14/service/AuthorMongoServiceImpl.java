package ru.otus.hw14.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw14.model.document.Author;
import ru.otus.hw14.repository.mongo.AuthorMongoRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorMongoServiceImpl implements AuthorMongoService {

  private final AuthorMongoRepository authorMongoRepository;

  @Override
  @Transactional
  public Author save(Author entity) {
    return authorMongoRepository.save(entity);
  }

  @Override
  public Optional<Author> findById(String id) {
    return authorMongoRepository.findById(id);
  }

  @Override
  public Iterable<Author> findAll() {
    return authorMongoRepository.findAll();
  }

  @Override
  @Transactional
  public void delete(Author entity) {
    authorMongoRepository.delete(entity);
  }

  @Override
  @Transactional
  public void deleteAll() {
    authorMongoRepository.deleteAll();
  }
}
