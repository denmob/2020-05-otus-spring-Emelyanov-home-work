package ru.otus.hw15;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.config.EnableIntegration;

@EnableIntegration
@SpringBootApplication
@IntegrationComponentScan
public class Hw15Application {

  public static void main(String[] args) {
    SpringApplication.run(Hw15Application.class, args);
  }
}

