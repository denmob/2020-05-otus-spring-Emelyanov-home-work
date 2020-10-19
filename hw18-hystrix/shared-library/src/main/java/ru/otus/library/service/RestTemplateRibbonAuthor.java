package ru.otus.library.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.otus.library.model.dto.AuthorDto;

@Service
public class RestTemplateRibbonAuthor extends RestTemplateRibbon<AuthorDto> {
  public RestTemplateRibbonAuthor(RestTemplate restTemplate) {
    super(restTemplate);
  }
}
