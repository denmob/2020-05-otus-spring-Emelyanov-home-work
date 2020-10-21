package ru.otus.library.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RestTemplateRibbon<T> implements MyRestTemplate<T> {

  private final RestTemplate restTemplate;

  @Override
  public T getEntity(String url, MultiValueMap<String, String> queryParams, Class<T> clazz) {
    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url).queryParams(queryParams);

    HttpEntity<?> httpEntity = new HttpEntity<>(getHttpHeaders());
    ResponseEntity<T> responseEntity =
        restTemplate.exchange(builder.toUriString(), HttpMethod.GET, httpEntity, clazz);
    return responseEntity.getBody();
  }

  @Override
  @SneakyThrows
  public T postEntity(String url, Object entity, Class<T> clazz) {
    return exchangeEntity(url, HttpMethod.POST, entity, clazz);
  }

  @Override
  @SneakyThrows
  public T putEntity(String url, Object entity, Class<T> clazz) {
    return exchangeEntity(url, HttpMethod.PUT, entity, clazz);
  }

  @Override
  public boolean deleteEntity(String url, MultiValueMap<String, String> queryParams, Class<T> clazz) {
    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url).queryParams(queryParams);
    HttpEntity<?> httpEntity = new HttpEntity<>(getHttpHeaders());
    ResponseEntity<T> responseEntity =
        restTemplate.exchange(builder.toUriString(), HttpMethod.DELETE, httpEntity, clazz);
    return responseEntity.getStatusCode().is2xxSuccessful();
  }

  @Override
  public List<T> getEntities(String url, MultiValueMap<String, String> queryParams) {
    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url).queryParams(queryParams);
    HttpEntity<?> httpEntity = new HttpEntity<>(getHttpHeaders());
    ResponseEntity<List<T>> listResponseEntity =
        restTemplate.exchange(builder.toUriString(), HttpMethod.GET, httpEntity, new ParameterizedTypeReference<>() {
        });
    return listResponseEntity.getBody();
  }

  @Override
  public List<T> getEntities(String url) {
    return getEntities(url, new LinkedMultiValueMap<>());
  }

  @SneakyThrows
  private T exchangeEntity(String url, HttpMethod httpMethod, Object entity, Class<T> clazz) {
    String jsonObject = new ObjectMapper().writeValueAsString(entity);
    HttpEntity<?> httpEntity = new HttpEntity<>(jsonObject, getHttpHeaders());
    ResponseEntity<T> responseEntity =
        restTemplate.exchange(url, httpMethod, httpEntity, clazz);
    return responseEntity.getBody();
  }

  private HttpHeaders getHttpHeaders() {
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.set("Accept", MediaType.APPLICATION_JSON_VALUE);
    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
    return httpHeaders;
  }
}
