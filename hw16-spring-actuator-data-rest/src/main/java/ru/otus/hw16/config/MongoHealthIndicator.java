package ru.otus.hw16.config;

import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

@Component
@Configuration
@RequiredArgsConstructor
public class MongoHealthIndicator extends AbstractHealthIndicator {

  private final MongoTemplate mongoTemplate;

  @Override
  protected void doHealthCheck(Health.Builder builder) {
    Document result = this.mongoTemplate.executeCommand("{ buildInfo: 1 }");
    builder.up().withDetail("version", result.getString("version"));
  }
}
