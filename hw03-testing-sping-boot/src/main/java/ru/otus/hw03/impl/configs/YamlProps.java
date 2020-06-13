package ru.otus.hw03.impl.configs;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
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

}
