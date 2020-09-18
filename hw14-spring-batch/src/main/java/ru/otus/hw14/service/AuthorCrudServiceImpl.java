package ru.otus.hw14.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw14.model.entity.AuthorEntity;
import ru.otus.hw14.repository.crud.AuthorCrudRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorCrudServiceImpl implements AuthorCrudService {

  private final AuthorCrudRepository authorCrudRepository;

  @Override
  @Transactional(readOnly = true)
  public Optional<AuthorEntity> findById(String id) {
    return authorCrudRepository.findById(Long.valueOf(id));
  }

  @Override
  public Optional<AuthorEntity> findByFirstNameAndLastNameAndBirthday(String firstName, String lastName, @NonNull Date birthday) {
    return authorCrudRepository.findByFirstNameAndLastNameAndBirthday(firstName, lastName, birthday);
  }

  @Override
  @Transactional
  public AuthorEntity save(AuthorEntity entity) {
    return authorCrudRepository.save(entity);
  }

  @Override
  @Transactional(readOnly = true)
  public List<AuthorEntity> findAll() {
    return (List<AuthorEntity>) authorCrudRepository.findAll();
  }

  @Override
  @Transactional
  public void delete(AuthorEntity entity) {
    authorCrudRepository.delete(entity);
  }

  @Override
  @Transactional
  public void deleteAll() {
    authorCrudRepository.deleteAll();
  }
}
