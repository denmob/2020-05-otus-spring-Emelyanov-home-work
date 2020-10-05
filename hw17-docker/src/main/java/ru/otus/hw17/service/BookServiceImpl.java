package ru.otus.hw17.service;

import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw17.config.MonitoredService;
import ru.otus.hw17.model.Book;
import ru.otus.hw17.repository.BookRepository;

@Service
@MonitoredService
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

  private final BookRepository bookRepository;

  @Override
  @Transactional
  @Timed(extraTags = {"componentClass", "BookServiceImpl", "methodName", "save", "componentType", "service"})
  public Book save(Book entity) {
    return bookRepository.save(entity);
  }
}
