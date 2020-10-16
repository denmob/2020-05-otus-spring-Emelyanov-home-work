package ru.otus.book.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.book.feign.AuthorServiceProxy;
import ru.otus.library.model.Author;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

  private final AuthorServiceProxy authorServiceProxy;
  private final DefaultDataService defaultDataService;

  @Override
  @HystrixCommand(fallbackMethod = "getAuthorDefaultDataService")
  public Author getAuthor(String lastName) {
    return authorServiceProxy.getAuthorByLastName(lastName);
  }

  private Author getAuthorDefaultDataService(String lastName) {
    log.error("Invoke getAuthorDefaultDataService");
    return defaultDataService.getAuthorByLastName(lastName);
  }
}
