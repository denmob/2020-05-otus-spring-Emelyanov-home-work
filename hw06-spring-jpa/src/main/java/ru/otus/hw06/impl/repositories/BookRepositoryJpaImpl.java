package ru.otus.hw06.impl.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw06.core.repositories.BookRepositoryJpa;
import ru.otus.hw06.core.models.Book;

import javax.persistence.*;
import java.util.*;

@Transactional
@Repository
@RequiredArgsConstructor
@SuppressWarnings({"ConstantConditions", "SqlDialectInspection"})
public class BookRepositoryJpaImpl implements BookRepositoryJpa {

  @PersistenceContext
  private EntityManager em;

  @Override
  public int count() {
    String sql = "select count(a) from Book a";
    return em.createQuery(sql, int.class).getSingleResult();
  }

  @Override
  public Book insert(Book book) {
    if (book.getId() <= 0) {
      em.persist(book);
      return book;
    } else {
      return em.merge(book);
    }
  }

  @Override
  public Optional<Book> getById(long id) {
    return Optional.ofNullable(em.find(Book.class, id));
  }

  @Override
  public List<Book> getAll() {
    EntityGraph<?> entityGraph = em.getEntityGraph("book-genre-entity-graph");
    TypedQuery<Book> query = em.createQuery("select b from Book b join fetch b.author", Book.class);
    query.setHint("javax.persistence.fetchgraph", entityGraph);
    return query.getResultList();
  }

  @Override
  public void deleteById(long id) {
    String sql = "delete from Book b where b.id = :id";
    Query query = em.createQuery(sql);
    query.setParameter("id", id);
    query.executeUpdate();
  }

}
