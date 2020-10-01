package ru.otus.hw16.service;

import java.util.List;

public interface RestService<T> {

  T getObject(String url, Class<T> aClass);

  List<T> getObjects(String url);
}
