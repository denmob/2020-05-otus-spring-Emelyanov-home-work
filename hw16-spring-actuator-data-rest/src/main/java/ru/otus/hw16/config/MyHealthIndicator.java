package ru.otus.hw16.config;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
@Configuration
public class MyHealthIndicator implements HealthIndicator {

  private final Random random = new Random();

  @Override
  public Health health() {
    boolean serverIsDown = random.nextBoolean();
    if (serverIsDown) {
      return Health.down()
          .status(Status.DOWN)
          .withDetail("message", "serverIsDown")
          .build();
    } else {
      return Health.up().withDetail("message", "serverIsUp").build();
    }
  }
}
