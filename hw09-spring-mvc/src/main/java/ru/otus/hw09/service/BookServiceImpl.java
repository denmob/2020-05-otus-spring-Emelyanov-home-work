package ru.otus.hw09.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw09.dto.BookWithComments;
import ru.otus.hw09.model.Book;
import ru.otus.hw09.repository.BookRepository;
import ru.otus.hw09.repository.CommentRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

  private final BookRepository bookRepository;
  private final CommentRepository commentRepository;

  @Override
  @Transactional
  public Book save(Book entity) {
    return bookRepository.save(entity);
  }

  @Override
  public Optional<Book> readBookById(String bookId) {
    return bookRepository.findById(bookId);
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<Book> readBookByTitleContains(String bookTitle) {
    return bookRepository.findByTitleContains(bookTitle);
  }

  @Override
  public Optional<BookWithComments> readBookWithCommentsById(String bookId) {
    Optional<Book> optionalBook = bookRepository.findById(bookId);
    return optionalBook.map(book -> new BookWithComments(book, commentRepository.findAllByBookId(optionalBook.get().getId())));
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<BookWithComments> readBookWithCommentsByTitleContains(String bookTitle) {
    Optional<Book> optionalBook = bookRepository.findByTitleContains(bookTitle);
    return optionalBook.map(book -> new BookWithComments(book, commentRepository.findAllByBookId(optionalBook.get().getId())));
  }

  @Override
  @Transactional
  public boolean deleteByTitleEquals(String bookTitle) {
    Optional<Book> optionalBook = readBookByTitleContains(bookTitle);
    if (optionalBook.isPresent()) {
      bookRepository.deleteBookById(optionalBook.get().getId());
      commentRepository.deleteCommentAllByBookId(optionalBook.get().getId());
      return true;
    }
    return false;
  }

  @Override
  public Page<Book> getLastAddedBooks(int count) {
    return bookRepository.findAll(PageRequest.of(0, count, Sort.by(Sort.Direction.DESC, "id")));
  }

  @Override
  @Transactional
  public Book update(Book entity) {
    return bookRepository.save(entity);
  }
}
