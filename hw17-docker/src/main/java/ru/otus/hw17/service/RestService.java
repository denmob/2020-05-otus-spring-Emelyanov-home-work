package ru.otus.hw17.service;

import java.util.List;

public interface RestService<T> {

  T getObject(String url, Class<T> aClass);

  List<T> getObjects(String url);
}
