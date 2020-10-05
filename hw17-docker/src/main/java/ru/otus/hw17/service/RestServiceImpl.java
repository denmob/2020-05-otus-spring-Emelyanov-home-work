package ru.otus.hw17.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class RestServiceImpl<T> implements RestService<T> {

  private final RestOperations restOperations = new RestTemplate();

  @Override
  public T getObject(String url, Class<T> aClass) {
    try {
      return restOperations.getForObject(url, aClass);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
    return null;
  }

  @Override
  public List<T> getObjects(String url) {
    try {
      ResponseEntity<List<T>> listResponseEntity =
          restOperations.exchange(
              url,
              HttpMethod.GET,
              null,
              new ParameterizedTypeReference<>() {
              }
          );
      return listResponseEntity.getBody();
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
    return new ArrayList<>();
  }
}
