package ru.otus.book.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import ru.otus.library.feign.GenreServiceProxy;
import ru.otus.library.model.Genre;

@Slf4j
@Service
@RefreshScope
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

  private final GenreServiceProxy genreServiceProxy;
  private final DefaultDataService defaultDataService;

  @Override
  @HystrixCommand(commandKey = "getGenreByName", fallbackMethod = "getGenreDefaultDataService", commandProperties = {
      @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000"),
      @HystrixProperty(name = "execution.isolation.strategy", value = "SEMAPHORE")})
  public Genre getGenreByName(String name) {
    log.info("Invoke getGenreByName");
    return genreServiceProxy.getGenreByName(name);
  }

  @SuppressWarnings("unused")
  private Genre getGenreDefaultDataService(String name) {
    log.error("Invoke getGenreDefaultDataService");
    return defaultDataService.getGenreByName(name);
  }
}
