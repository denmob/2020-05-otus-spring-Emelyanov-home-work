package ru.otus.hw06.core.service;

public interface CRUDService<T> {

  T create(T entity);

  T read(T entity);

  boolean delete(long id);

  T update(T entity);

}
