package ru.otus.hw06.impl.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw06.core.models.Book;
import ru.otus.hw06.core.repositories.BookRepositoryJpa;
import ru.otus.hw06.core.service.CRUDService;

@Service
@RequiredArgsConstructor
public class CRUDBookService implements CRUDService<Book> {

  private final BookRepositoryJpa bookRepositoryJpa;

  @Override
  public Book create(Book entity) {
    return null;
  }

  @Override
  public Book read(Book entity) {
    return null;
  }

  @Override
  public boolean delete(long id) {
    return false;
  }

  @Override
  public Book update(Book entity) {
    return null;
  }
}
