package ru.otus.library.service;

import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

import java.util.List;

public interface RestService<T> {

  T getEntity(String url, MultiValueMap<String, String> queryParams, Class<T> clazz);

  T postEntity(String url, Object entity, Class<T> clazz);

  T putEntity(String url, Object entity, Class<T> clazz);

  boolean deleteEntity(String url, MultiValueMap<String, String> queryParams, Class<T> clazz);

  List<T> getEntities(String url, MultiValueMap<String, String> queryParams);

  List<T> getEntities(String url);
}
