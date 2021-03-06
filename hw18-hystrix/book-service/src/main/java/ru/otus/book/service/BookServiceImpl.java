package ru.otus.book.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.book.repository.BookRepository;
import ru.otus.library.model.Book;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

  private final BookRepository bookRepository;

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
  public Optional<Book> readBookByTitle(String bookTitle) {
    return bookRepository.findByTitleContains(bookTitle);
  }

  @Override
  public Page<Book> getLastAddedBooks(int count) {
    PageRequest pageRequest = PageRequest.of(0, count, Sort.by(Sort.Direction.DESC, "id"));
    return bookRepository.findAll(pageRequest);
  }

  @Override
  public boolean deleteBookById(String bookId) {
    return bookRepository.deleteBookById(bookId) == 1L;
  }
}
