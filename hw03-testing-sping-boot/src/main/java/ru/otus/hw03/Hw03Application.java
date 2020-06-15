package ru.otus.hw03;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import ru.otus.hw03.impl.configs.YamlProps;
import ru.otus.hw03.impl.service.TestingServiceImpl;

@SpringBootApplication
@EnableConfigurationProperties(YamlProps.class)
public class Hw03Application {

  public static void main(String[] args) {
    ApplicationContext applicationContext = SpringApplication.run(Hw03Application.class, args);
    applicationContext.getBean(TestingServiceImpl.class).startTesting();
  }
}
