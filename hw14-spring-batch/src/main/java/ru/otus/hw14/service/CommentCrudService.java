package ru.otus.hw14.service;

import ru.otus.hw14.model.entity.CommentEntity;

import java.util.List;
import java.util.Optional;

public interface CommentCrudService {

  Optional<CommentEntity> findById(String id);

  List<CommentEntity> findAll();

  CommentEntity save(CommentEntity entity);

  void delete(CommentEntity entity);

  void deleteAll();

  List<CommentEntity> getAllByBookEntityId(long bookEntityId);
}
