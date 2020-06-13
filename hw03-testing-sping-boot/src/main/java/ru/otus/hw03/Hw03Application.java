package ru.otus.hw03;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import ru.otus.hw03.impl.configs.YamlProps;

@SpringBootApplication
@EnableConfigurationProperties(YamlProps.class)
public class Hw03Application {

  public static void main(String[] args) {
    SpringApplication.run(Hw03Application.class, args);
  }

}
