package ru.otus.hw15;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.config.EnableIntegration;
import ru.otus.hw15.domain.Income;
import ru.otus.hw15.service.IncomeServiceImpl;

@EnableIntegration
@SpringBootApplication
@IntegrationComponentScan
public class Hw15Application {

  public static void main(String[] args) {
    ConfigurableApplicationContext context = SpringApplication.run(Hw15Application.class, args);
    context.getBean(IncomeServiceImpl.class).save(new Income("Hw15Application",567.));
  }
}

