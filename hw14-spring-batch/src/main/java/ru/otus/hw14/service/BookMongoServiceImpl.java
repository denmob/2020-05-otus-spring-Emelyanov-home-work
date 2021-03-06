package ru.otus.hw14.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw14.model.document.BookDocument;
import ru.otus.hw14.repository.mongo.BookMongoRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookMongoServiceImpl implements BookMongoService {

  private final BookMongoRepository bookMongoRepository;

  @Override
  @Transactional
  public BookDocument save(BookDocument entity) {
    return bookMongoRepository.save(entity);
  }

  @Override
  public Optional<BookDocument> findById(String id) {
    return bookMongoRepository.findById(id);
  }

  @Override
  public List<BookDocument> findAll() {
    return bookMongoRepository.findAll();
  }

  @Override
  @Transactional
  public void delete(BookDocument entity) {
    bookMongoRepository.delete(entity);
  }

  @Override
  @Transactional
  public void deleteAll() {
    bookMongoRepository.deleteAll();
  }
}
