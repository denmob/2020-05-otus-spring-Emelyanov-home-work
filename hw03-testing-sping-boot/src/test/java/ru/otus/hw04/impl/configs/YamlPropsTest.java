package ru.otus.hw04.impl.configs;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class YamlPropsTest {

  @Autowired
  private YamlProps yamlProps;

  @Test
  void getQuestionsFile() {
    assertEquals("question_en.csv", yamlProps.getQuestionsFile());
  }

  @Test
  void getAnswersFile() {
    assertEquals("answer_en.csv", yamlProps.getAnswersFile());
  }

  @Test
  void getSplit() {
    assertEquals(",", yamlProps.getCsvSplit());
  }

  @Test
  void getLocale() {
    assertEquals(Locale.forLanguageTag("en"), yamlProps.getLocale());
  }

}
