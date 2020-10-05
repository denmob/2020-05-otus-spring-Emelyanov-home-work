package ru.otus.hw17.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import ru.otus.hw17.rest.projections.CustomBookEntity;
import ru.otus.hw17.service.RestService;

@Slf4j
@Component
@Configuration
@RequiredArgsConstructor
public class MyHealthIndicator implements HealthIndicator {

  private final RestService<CustomBookEntity> bookRestService;

  @Override
  public Health health() {

    if (!isAvailableService()) {
      return Health.down()
          .status(Status.DOWN)
          .withDetail("message", "serverIsDown")
          .build();
    } else {
      return Health.up().withDetail("message", "serverIsUp").build();
    }
  }

  private boolean isAvailableService() {
    String url = "http://localhost:8080/rest/book/search/find-by-author-lastName?authorLastName=Langr";
    return  bookRestService.getObject(url, CustomBookEntity.class) != null;
  }
}
