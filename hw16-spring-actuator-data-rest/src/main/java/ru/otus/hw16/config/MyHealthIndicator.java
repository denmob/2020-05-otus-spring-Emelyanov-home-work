package ru.otus.hw16.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Slf4j
@Component
@Configuration
public class MyHealthIndicator implements HealthIndicator {

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
    RestTemplate restTemplate = new RestTemplate();
    String url = "http://localhost:8080/rest/book/search/find-by-author-lastName?authorLastName=Langr";
    String resp = restTemplate.getForObject(url, String.class);
    JsonParser springParser = JsonParserFactory.getJsonParser();
    Map<String, Object> map = springParser.parseMap(resp);
    return map.get("title").equals("Pragmatic Unit Testing in Java 8 with JUnit");
  }
}
