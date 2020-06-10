package ru.otus.hw02;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import ru.otus.hw02.api.service.TestingService;
import ru.otus.hw02.core.config.AppConfig;

@Import(AppConfig.class)
@ComponentScan
@PropertySource("classpath:app.properties")
public class TestingDemo {

  public static void main(String[] args) {
    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(TestingDemo.class);
    TestingService testingService = applicationContext.getBean(TestingService.class);
    testingService.startTest();
  }
}
