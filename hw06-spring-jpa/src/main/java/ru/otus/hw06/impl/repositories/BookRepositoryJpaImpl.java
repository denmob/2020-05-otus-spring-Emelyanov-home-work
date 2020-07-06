package ru.otus.hw06.impl.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw06.core.repositories.BookRepositoryJpa;
import ru.otus.hw06.core.models.Book;

import javax.persistence.*;
import java.util.*;

@Repository
@RequiredArgsConstructor
@SuppressWarnings({"ConstantConditions", "SqlDialectInspection"})
public class BookRepositoryJpaImpl implements BookRepositoryJpa {

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  @Transactional(readOnly = true)
  public long count() {
    String sql = "select count(a) from Book a";
    return entityManager.createQuery(sql, Long.class).getSingleResult();
  }

  @Override
  @Transactional(readOnly = false)
  public Book insert(Book book) {
    if (book.getId() <= 0) {
      entityManager.persist(book);
      return book;
    } else {
      return entityManager.merge(book);
    }
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<Book> getById(long id) {
    String sql = "select b from Book b join fetch b.author left join fetch b.comments where b.id=:id";
    EntityGraph<?> entityGraph = entityManager.getEntityGraph("book-genre-entity-graph");
    TypedQuery<Book> query = entityManager.createQuery(sql, Book.class);
    query.setParameter("id", id);
    query.setHint("javax.persistence.fetchgraph", entityGraph);
    return Optional.ofNullable(query.getSingleResult());
  }

  @Override
  @Transactional(readOnly = true)
  public List<Book> getAll() {
    EntityGraph<?> entityGraph = entityManager.getEntityGraph("book-genre-entity-graph");
    TypedQuery<Book> query = entityManager.createQuery("select b from Book b join fetch b.author left join fetch b.comments", Book.class);
    query.setHint("javax.persistence.fetchgraph", entityGraph);
    return query.getResultList();
  }

  @Override
  @Transactional(readOnly = false)
  public boolean deleteById(long id) {
    String sql = "delete from Book b where b.id = :id";
    Query query = entityManager.createQuery(sql);
    query.setParameter("id", id);
    return query.executeUpdate() > 0;
  }

}
