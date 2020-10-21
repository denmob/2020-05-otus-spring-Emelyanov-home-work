package ru.otus.book.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.otus.library.feign.AuthorServiceProxy;
import ru.otus.library.model.Author;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

  private final AuthorServiceProxy authorServiceProxy;
  private final DefaultAuthorService defaultAuthorService;

  @Override
  @HystrixCommand(commandKey = "getAuthorByLastName", fallbackMethod = "getAuthorDefaultDataService", commandProperties = {
      @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000"),
      @HystrixProperty(name = "execution.isolation.strategy", value = "SEMAPHORE")})
  public Author getAuthorByLastName(String lastName) {
    log.info("Invoke getAuthorByLastName");
    return authorServiceProxy.getAuthorByLastName(lastName);
  }

  @SuppressWarnings("unused")
  private Author getAuthorDefaultDataService(String lastName) {
    log.error("Invoke getAuthorDefaultDataService");
    return defaultAuthorService.getAuthorByLastName(lastName);
  }
}
