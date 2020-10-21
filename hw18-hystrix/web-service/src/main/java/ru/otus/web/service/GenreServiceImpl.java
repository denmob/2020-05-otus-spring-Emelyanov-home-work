package ru.otus.web.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import ru.otus.library.model.dto.GenreDto;
import ru.otus.library.service.RestTemplateRibbonGenre;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

  private final RestTemplateRibbonGenre restTemplateRibbonGenre;
  @Value("${app.url.genre-service}")
  private String urlGenreService;

  @Override
  public List<GenreDto> getGenres() {
    return restTemplateRibbonGenre.getEntities(urlGenreService);
  }

  @Override
  public GenreDto getGenreById(String id) {
    MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
    queryParams.add("id", id);
    return restTemplateRibbonGenre.getEntity(urlGenreService, queryParams, GenreDto.class);
  }
}
