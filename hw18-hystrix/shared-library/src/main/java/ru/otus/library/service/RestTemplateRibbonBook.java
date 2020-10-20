package ru.otus.library.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.otus.library.model.dto.BookDto;

@Service
public class RestTemplateRibbonBook extends RestTemplateRibbon<BookDto> {
  public RestTemplateRibbonBook(RestTemplate restTemplate) {
    super(restTemplate);
  }
}
