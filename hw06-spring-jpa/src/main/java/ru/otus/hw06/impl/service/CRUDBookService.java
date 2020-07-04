package ru.otus.hw06.impl.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw06.core.models.Book;
import ru.otus.hw06.core.repositories.BookRepositoryJpa;
import ru.otus.hw06.core.service.CRUDService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CRUDBookService implements CRUDService<Book> {

  private final BookRepositoryJpa bookRepositoryJpa;

  @Override
  public Book create(Book entity) {
    return bookRepositoryJpa.insert(entity);
  }

  @Override
  public Optional<Book> read(long id) {
    return bookRepositoryJpa.getById(id);
  }

  @Override
  public boolean delete(long id) {
     bookRepositoryJpa.deleteById(id);
    return true;
  }

  @Override
  public Book update(Book entity) {
    return bookRepositoryJpa.insert(entity);
  }
}
