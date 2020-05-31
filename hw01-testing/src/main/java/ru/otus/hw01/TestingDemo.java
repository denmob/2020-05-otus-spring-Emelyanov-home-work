package ru.otus.hw01;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.hw01.api.service.TestingService;

public class TestingDemo {
  private static final Logger logger = LoggerFactory.getLogger(TestingDemo.class);

    public static void main(String[] args) {
      ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("spring-context.xml");
      TestingService testingService = classPathXmlApplicationContext.getBean(TestingService.class);
      testingService.startTest();
      logger.info("Test finish with grade:{}",testingService.getGradeForTest());
    }
}
