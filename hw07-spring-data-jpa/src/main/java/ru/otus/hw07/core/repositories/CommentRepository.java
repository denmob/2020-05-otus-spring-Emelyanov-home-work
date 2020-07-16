package ru.otus.hw07.core.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.otus.hw07.core.models.Comment;

import java.util.List;

public interface CommentRepository extends CrudRepository<Comment,Long> {

  List<Comment> getAllByBookId(long bookId);
}
