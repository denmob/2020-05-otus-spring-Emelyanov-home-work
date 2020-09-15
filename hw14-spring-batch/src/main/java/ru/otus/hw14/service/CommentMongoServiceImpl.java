package ru.otus.hw14.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw14.model.document.CommentDocument;
import ru.otus.hw14.repository.mongo.CommentMongoRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentMongoServiceImpl implements CommentMongoService {

  private final CommentMongoRepository commentMongoRepository;

  @Override
  public Optional<CommentDocument> findById(String id) {
    return commentMongoRepository.findById(id);
  }

  @Override
  public Iterable<CommentDocument> findAll() {
    return commentMongoRepository.findAll();
  }

  @Override
  @Transactional
  public CommentDocument save(CommentDocument entity) {
    return commentMongoRepository.save(entity);
  }

  @Override
  @Transactional
  public void delete(CommentDocument entity) {
    commentMongoRepository.delete(entity);
  }

  @Override
  @Transactional
  public void deleteAll() {
    commentMongoRepository.deleteAll();
  }

  @Override
  public List<CommentDocument> readAllForBook(String bookId) {
    return commentMongoRepository.findAllByBookId(bookId);
  }

  @Override
  public void deleteCommentAllByBookId(String bookId) {
    commentMongoRepository.deleteCommentAllByBookId(bookId);
  }
}
