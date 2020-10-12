package ru.otus.library;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Service
public class RestServiceImpl<T> implements RestService<T> {

  private final RestOperations restOperations = new RestTemplate();
  private final HttpEntity<?> httpEntity;

  public RestServiceImpl() {
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.set("Accept", MediaType.APPLICATION_JSON_VALUE);
    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
    httpEntity = new HttpEntity<>(httpHeaders);
  }

  @Override
  public T getEntity(String url, MultiValueMap<String, String> multiValueMap, Class<T> clazz) {
    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url).queryParams(multiValueMap);
    ResponseEntity<T> responseEntity =
        restOperations.exchange(builder.toUriString(), HttpMethod.GET, httpEntity, clazz);
    return responseEntity.getBody();
  }

  @Override
  public List<T> getEntities(String url) {
    ResponseEntity<List<T>> listResponseEntity =
        restOperations.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<>() {
    });
    return listResponseEntity.getBody();
  }
}
