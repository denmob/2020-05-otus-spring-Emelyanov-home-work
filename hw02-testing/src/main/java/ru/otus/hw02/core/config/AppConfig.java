package ru.otus.hw02.core.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.hw02.api.gui.InteractiveInterface;
import ru.otus.hw02.api.reader.DataReader;
import ru.otus.hw02.core.gui.InteractiveInterfaceConsole;
import ru.otus.hw02.core.reader.CsvReader;

@Configuration
public class AppConfig {

  @Value("${test.questions}")
  private String questionsFile;

  @Value("${test.answer}")
  private String answerFile;

  @Value("${csv.split}")
  private String csvSplitBy;

  @Bean
  DataReader csvReaderQuestion() {
    return new CsvReader(questionsFile, csvSplitBy);
  }

  @Bean
  DataReader csvReaderAnswer() {
    return new CsvReader(answerFile, csvSplitBy);
  }

  @Bean
  InteractiveInterface interactiveInterfaceConsole(DataReader csvReaderQuestion) {
    return new InteractiveInterfaceConsole(csvReaderQuestion, System.in, System.out);
  }

}
