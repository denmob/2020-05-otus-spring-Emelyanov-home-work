package ru.otus.library.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.otus.library.model.dto.CommentDto;

@Service
public class RestTemplateRibbonComment extends RestTemplateRibbon<CommentDto> {
  public RestTemplateRibbonComment(RestTemplate restTemplate) {
    super(restTemplate);
  }
}
