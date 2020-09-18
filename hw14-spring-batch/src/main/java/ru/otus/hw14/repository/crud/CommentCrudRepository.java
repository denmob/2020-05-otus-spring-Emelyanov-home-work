package ru.otus.hw14.repository.crud;

import org.springframework.data.repository.CrudRepository;
import ru.otus.hw14.model.entity.CommentEntity;

import java.util.List;

public interface CommentCrudRepository extends CrudRepository<CommentEntity,Long> {

  List<CommentEntity> getAllByBookEntityId(long bookEntityId);
}
