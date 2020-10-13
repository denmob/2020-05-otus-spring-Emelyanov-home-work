package ru.otus.library.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Service
public class RestServiceImpl<T> implements RestService<T> {

  @LoadBalanced
  @Bean
  public RestTemplate getRestTemplate() {
    return new RestTemplate();
  }

  @Autowired
  private RestTemplate restTemplate;

  private final HttpHeaders httpHeaders = new HttpHeaders();
  private HttpEntity<?> httpEntity;

  public RestServiceImpl() {
    httpHeaders.set("Accept", MediaType.APPLICATION_JSON_VALUE);
    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
  }

  @Override
  public T getEntity(String url, MultiValueMap<String, String> multiValueMap, Class<T> clazz) {
    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url).queryParams(multiValueMap);
    httpEntity = new HttpEntity<>(httpHeaders);
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
  public T deleteEntity(String url, MultiValueMap<String, String> multiValueMap, Class<T> clazz) {
    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url).queryParams(multiValueMap);
    httpEntity = new HttpEntity<>(httpHeaders);
    ResponseEntity<T> responseEntity =
        restTemplate.exchange(builder.toUriString(), HttpMethod.DELETE, httpEntity, clazz);
    return responseEntity.getBody();
  }

  @Override
  public List<T> getEntities(String url, MultiValueMap<String, String> multiValueMap) {
    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url).queryParams(multiValueMap);
    httpEntity = new HttpEntity<>(httpHeaders);
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
    httpEntity = new HttpEntity<>(jsonObject, httpHeaders);
    ResponseEntity<T> responseEntity =
        restTemplate.exchange(url, httpMethod, httpEntity, clazz);
    return responseEntity.getBody();
  }
}
