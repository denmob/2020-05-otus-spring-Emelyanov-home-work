package ru.otus.hw14.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw14.model.table.Author;
import ru.otus.hw14.repository.crud.AuthorCrudRepository;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorCrudServiceImpl implements AuthorCrudService {

  private final AuthorCrudRepository authorCrudRepository;

  @Override
  @Transactional(readOnly = true)
  public Optional<Author> findById(String id) {
    return authorCrudRepository.findById(Long.valueOf(id));
  }

  @Override
  public Optional<Author> findByFirstNameAndLastNameAndBirthday(String firstName, String lastName, @NonNull Date birthday) {
    return authorCrudRepository.findByFirstNameAndLastNameAndBirthday(firstName, lastName, birthday);
  }

  @Override
  @Transactional
  public Author save(Author entity) {
    return authorCrudRepository.save(entity);
  }

  @Override
  @Transactional(readOnly = true)
  public Iterable<Author> findAll() {
    return authorCrudRepository.findAll();
  }

  @Override
  @Transactional
  public void delete(Author entity) {
    authorCrudRepository.delete(entity);
  }

  @Override
  @Transactional
  public void deleteAll() {
    authorCrudRepository.deleteAll();
  }
}
