package ru.otus.web.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import ru.otus.library.model.dto.AuthorDto;
import ru.otus.library.service.RestTemplateRibbonAuthor;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

  private final RestTemplateRibbonAuthor restTemplateRibbonAuthor;
  @Value("${app.url.author-service}")
  private String urlAuthorService;

  @Override
  public List<AuthorDto> getAuthors() {
    return restTemplateRibbonAuthor.getEntities(urlAuthorService);
  }

  @Override
  public AuthorDto getAuthorById(String id) {
    MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
    queryParams.add("id", id);
    return restTemplateRibbonAuthor.getEntity(urlAuthorService, queryParams, AuthorDto.class);
  }
}
