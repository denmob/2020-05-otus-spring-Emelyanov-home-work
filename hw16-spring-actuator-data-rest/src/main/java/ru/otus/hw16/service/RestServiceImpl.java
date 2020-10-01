package ru.otus.hw16.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class RestServiceImpl<T> implements RestService<T> {

  private final RestOperations restTemplate = new RestTemplate();

  @Override
  public T getObject(String url, Class<T> aClass) {
    return restTemplate.getForObject(url, aClass);
  }
}
