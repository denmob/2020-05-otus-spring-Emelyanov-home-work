package ru.otus.hw06.impl.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw06.core.dto.BookWithComments;
import ru.otus.hw06.core.models.Book;
import ru.otus.hw06.core.models.Comment;
import ru.otus.hw06.core.repositories.BookRepositoryJpa;
import ru.otus.hw06.core.repositories.CommentRepositoryJpa;
import ru.otus.hw06.core.service.CRUDServiceBook;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CRUDBookService implements CRUDServiceBook {

  private final BookRepositoryJpa bookRepositoryJpa;
  private final CommentRepositoryJpa commentRepositoryJpa;

  @Override
  public Book create(Book entity) {
    return bookRepositoryJpa.insert(entity);
  }

  @Override
  public Optional<Book> read(long id) {
    return bookRepositoryJpa.getById(id);
  }

  @Override
  public Optional<BookWithComments> readWithComments(long id) {
    Optional<Book> optionalBook = bookRepositoryJpa.getById(id);
    List<Comment> comments = commentRepositoryJpa.getAllByBookId(id);
    if (optionalBook.isPresent() && !comments.isEmpty()) {
      return Optional.of(new BookWithComments(optionalBook.get(), comments));
    }
    return Optional.empty();
  }

  @Override
  public boolean delete(long id) {
    return bookRepositoryJpa.deleteById(id);
  }

  @Override
  public Book update(Book entity) {
    return bookRepositoryJpa.insert(entity);
  }
}
