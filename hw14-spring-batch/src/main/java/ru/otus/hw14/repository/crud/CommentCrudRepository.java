package ru.otus.hw14.repository.crud;

import org.springframework.data.repository.CrudRepository;
import ru.otus.hw14.model.table.Comment;

import java.util.List;

public interface CommentCrudRepository extends CrudRepository<Comment,Long> {

  List<Comment> getAllByBookId(long bookId);
}
