package ru.otus.book.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;
import ru.otus.book.feign.FeignServiceProxy;
import ru.otus.library.model.Author;

@Slf4j
@Component
@RefreshScope
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

  private final FeignServiceProxy feignServiceProxy;
  private final DefaultDataService defaultDataService;

  @Override
  @HystrixCommand(commandKey = "getAuthorByLastName", fallbackMethod = "getAuthorDefaultDataService", commandProperties = {
      @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000"),
      @HystrixProperty(name = "execution.isolation.strategy", value = "SEMAPHORE")})
  public Author getAuthorByLastName(String lastName) {
    log.info("Invoke getAuthorByLastName");
    return feignServiceProxy.getAuthorByLastName(lastName);
  }

  @SuppressWarnings("unused")
  private Author getAuthorDefaultDataService(String lastName) {
    log.error("Invoke getAuthorDefaultDataService");
    return defaultDataService.getAuthorByLastName(lastName);
  }
}
