package ru.otus.hw04.impl.configs;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "application")
public class YamlProps {

  private String questionsFile;
  private String answersFile;
  private String csvSplit;
  private Locale locale;
  private Integer tryInputAnswer;

  public void setQuestionsFile(String questionsFile) {
    this.questionsFile = questionsFile.replace("{}", locale.toString());
  }

  public void setAnswersFile(String answersFile) {
    this.answersFile = answersFile.replace("{}", locale.toString());
  }
}
