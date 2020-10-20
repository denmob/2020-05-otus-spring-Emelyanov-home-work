package ru.otus.library.feign;

import feign.hystrix.FallbackFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import ru.otus.library.model.dto.GenreDto;
import ru.otus.library.service.RestTemplateRibbonGenre;

@Slf4j
@Component
@RequiredArgsConstructor
public class GenreServiceProxyFallbackFactory implements FallbackFactory<GenreServiceProxy> {

  private final RestTemplateRibbonGenre restTemplateRibbonGenre;
  @Value("${app.url.genre-service:http://genre-service/api/genre}")
  private String urlGenreService;

  @Override
  public GenreServiceProxy create(Throwable cause) {
    return new GenreServiceProxy() {
      @Override
      public GenreDto getGenreId(String id) {
        log.error("Invoke fallbackFactory getGenreId: {}", id);
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("id", id);
        return restTemplateRibbonGenre.getEntity(urlGenreService, queryParams, GenreDto.class);
      }

      @Override
      public GenreDto getGenreByName(String name) {
        log.error("Invoke fallbackFactory getGenreByName: {}", name);
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("name", name);
        return restTemplateRibbonGenre.getEntity(urlGenreService, queryParams, GenreDto.class);
      }
    };
  }
}
