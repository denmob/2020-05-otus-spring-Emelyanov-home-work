package ru.otus.hw16.service;

public interface RestService<T> {

  T getObject(String url , Class<T> aClass);
}
