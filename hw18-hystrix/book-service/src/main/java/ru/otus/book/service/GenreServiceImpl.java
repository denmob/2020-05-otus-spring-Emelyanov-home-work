package ru.otus.book.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.book.feign.GenreServiceProxy;
import ru.otus.library.model.Genre;

@Slf4j
@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

  private final GenreServiceProxy genreServiceProxy;
  private final DefaultDataService defaultDataService;

  @Override
  @HystrixCommand(fallbackMethod = "getGenreDefaultDataService")
  public Genre getGenre(String name) {
    return genreServiceProxy.getGenreByName(name);
  }

  private Genre getGenreDefaultDataService(String name) {
    log.error("Invoke getGenreDefaultDataService");
    return defaultDataService.getGenreByName(name);
  }
}
