package ru.otus.hw08.impl.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw08.core.dto.BookWithComments;
import ru.otus.hw08.core.models.Book;
import ru.otus.hw08.core.repositories.BookRepository;
import ru.otus.hw08.core.repositories.CommentRepository;
import ru.otus.hw08.core.service.CRUDServiceBook;

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
  @Transactional(readOnly = true)
  public Optional<Book> readByTitleContains(String bookTitle) {
    return bookRepository.findByTitleContains(bookTitle);
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<BookWithComments> readWithComments(String bookTitle) {
    Optional<Book> optionalBook = bookRepository.findByTitleContains(bookTitle);
    return optionalBook.map(book -> new BookWithComments(book, commentRepository.findAllByBookId(optionalBook.get().getId())));
  }

  @Override
  @Transactional
  public boolean deleteByTitleEquals(String bookTitle) {
    Optional<Book> optionalBook = readByTitleContains(bookTitle);
    if (optionalBook.isPresent()) {
      bookRepository.deleteBookById(optionalBook.get().getId());
      commentRepository.deleteCommentAllByBookId(optionalBook.get().getId());
      return true;
    }
    return false;
  }

  @Override
  @Transactional
  public Book update(Book entity) {
    return bookRepository.save(entity);
  }
}
