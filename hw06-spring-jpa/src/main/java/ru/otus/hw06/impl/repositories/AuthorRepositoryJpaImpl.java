package ru.otus.hw06.impl.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw06.core.repositories.AuthorRepositoryJpa;
import ru.otus.hw06.core.models.Author;

import javax.persistence.*;
import java.util.*;

@Repository
@RequiredArgsConstructor
@SuppressWarnings({"ConstantConditions", "SqlDialectInspection"})
public class AuthorRepositoryJpaImpl implements AuthorRepositoryJpa {

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public long count() {
    String sql = "select count(a) from Author a";
    return entityManager.createQuery(sql, Long.class).getSingleResult();
  }

  @Override
  @Transactional
  public Author insert(Author author) {
    if (author.getId() <= 0) {
      entityManager.persist(author);
      return author;
    } else {
      return entityManager.merge(author);
    }
  }

  @Override
  public Optional<Author> getById(long id) {
    return Optional.ofNullable(entityManager.find(Author.class, id));
  }

  @Override
  public List<Author> getAll() {
    String sql = "select a from Author a";
    TypedQuery<Author> query = entityManager.createQuery(sql, Author.class);
    return query.getResultList();
  }

  @Override
  @Transactional
  public boolean deleteById(long id) {
    Optional<Author> optionalAuthor = getById(id);
    if (optionalAuthor.isPresent()) {
      entityManager.remove(optionalAuthor.get());
      return true;
    }
    return false;
  }

}
