package ru.otus.hw03.impl.configs;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Getter
@Component
@ConfigurationProperties(prefix = "application")
public class YamlProps {

  private String questionsFile;
  private String answersFile;
  private String csvSplit;
  private Locale locale;

  public void setQuestionsFile(String questionsFile) {
    this.questionsFile = questionsFile.replace("{}",locale.toString());
  }

  public void setAnswersFile(String answersFile) {
    this.answersFile = answersFile.replace("{}",locale.toString());
  }

  public void setCsvSplit(String csvSplit) {
    this.csvSplit = csvSplit;
  }

  public void setLocale(Locale locale) {
    this.locale = locale;
  }
}
