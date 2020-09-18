package ru.otus.hw14.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw14.model.document.AuthorDocument;
import ru.otus.hw14.repository.mongo.AuthorMongoRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorMongoServiceImpl implements AuthorMongoService {

  private final AuthorMongoRepository authorMongoRepository;

  @Override
  @Transactional
  public AuthorDocument save(AuthorDocument entity) {
    return authorMongoRepository.save(entity);
  }

  @Override
  public Optional<AuthorDocument> findById(String id) {
    return authorMongoRepository.findById(id);
  }

  @Override
  public List<AuthorDocument> findAll() {
    return authorMongoRepository.findAll();
  }

  @Override
  @Transactional
  public void delete(AuthorDocument entity) {
    authorMongoRepository.delete(entity);
  }

  @Override
  @Transactional
  public void deleteAll() {
    authorMongoRepository.deleteAll();
  }
}
