package ru.otus.hw14.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw14.model.entity.CommentEntity;
import ru.otus.hw14.repository.crud.CommentCrudRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentCrudServiceImpl implements CommentCrudService {

  private final CommentCrudRepository commentCrudRepository;

  @Override
  @Transactional
  public CommentEntity save(CommentEntity entity) {
    return commentCrudRepository.save(entity);
  }

  @Override
  @Transactional
  public void delete(CommentEntity entity) {
    commentCrudRepository.delete(entity);
  }

  @Override
  @Transactional
  public void deleteAll() {
    commentCrudRepository.deleteAll();
  }

  @Override
  public List<CommentEntity> getAllByBookEntityId(long bookId) {
    return commentCrudRepository.getAllByBookEntityId(bookId);
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<CommentEntity> findById(String id) {
    return commentCrudRepository.findById(Long.valueOf(id));
  }

  @Override
  @Transactional(readOnly = true)
  public List<CommentEntity> findAll() {
    return (List<CommentEntity>) commentCrudRepository.findAll();
  }
}
