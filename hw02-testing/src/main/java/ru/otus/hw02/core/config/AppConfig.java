package ru.otus.hw02.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.hw02.api.gui.InteractiveInterface;
import ru.otus.hw02.api.service.QuestionsService;
import ru.otus.hw02.core.gui.InteractiveInterfaceConsole;


@Configuration
public class AppConfig {

  @Bean
  InteractiveInterface interactiveInterfaceConsole(QuestionsService questionsService) {
    return new InteractiveInterfaceConsole(questionsService, System.in, System.out);
  }
}
