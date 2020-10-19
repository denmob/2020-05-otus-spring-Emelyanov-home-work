package ru.otus.library.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.otus.library.model.dto.GenreDto;

@Service
public class RestTemplateRibbonGenre extends RestTemplateRibbon<GenreDto> {
  public RestTemplateRibbonGenre(RestTemplate restTemplate) {
    super(restTemplate);
  }
}
