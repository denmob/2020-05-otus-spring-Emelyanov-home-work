package ru.otus.hw07.impl.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw07.core.dto.BookWithComments;
import ru.otus.hw07.core.models.Book;
import ru.otus.hw07.core.models.Comment;
import ru.otus.hw07.core.repositories.BookRepository;
import ru.otus.hw07.core.repositories.CommentRepository;
import ru.otus.hw07.core.service.CRUDServiceBook;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CRUDBookService implements CRUDServiceBook {

  private final BookRepository bookRepository;
  private final CommentRepository commentRepository;

  @Override
  @Transactional
  public Book create(Book entity) {
    return bookRepository.save(entity);
  }

  @Override
  public Optional<Book> read(long id) {
    return bookRepository.findById(id);
  }

  @Override
  public Optional<BookWithComments> readWithComments(long id) {
    Optional<Book> optionalBook = read(id);
    List<Comment> comments = commentRepository.getAllByBookId(id);
    if (optionalBook.isPresent() && !comments.isEmpty()) {
      return Optional.of(new BookWithComments(optionalBook.get(), comments));
    }
    return Optional.empty();
  }

  @Override
  @Transactional
  public void delete(long id) {
    bookRepository.deleteById(id);
  }

  @Override
  @Transactional
  public Book update(Book entity) {
    return bookRepository.save(entity);
  }
}
