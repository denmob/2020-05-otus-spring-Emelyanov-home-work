package ru.otus.hw04;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.otus.hw04.impl.configs.YamlProps;

@SpringBootApplication
@EnableConfigurationProperties(YamlProps.class)
public class Hw04Application {

  public static void main(String[] args) {
     SpringApplication.run(Hw04Application.class, args);
  }
}
