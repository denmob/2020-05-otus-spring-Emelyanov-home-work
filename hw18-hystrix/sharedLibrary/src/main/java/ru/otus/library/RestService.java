package ru.otus.library;

import org.springframework.util.MultiValueMap;

import java.util.List;

public interface RestService<T> {

  T getEntity(String url, MultiValueMap<String, String> multiValueMap, Class<T> clazz);

  List<T> getEntities(String url);
}
