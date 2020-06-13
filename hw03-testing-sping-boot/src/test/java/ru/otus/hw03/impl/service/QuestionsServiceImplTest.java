package ru.otus.hw03.impl.service;

import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.hw03.core.service.FileReaderService;
import ru.otus.hw03.core.service.QuestionsService;
import ru.otus.hw03.impl.configs.YamlProps;

import static org.mockito.BDDMockito.given;


@SpringBootTest
class QuestionsServiceImplTest {

  @Autowired
  private FileReaderServiceImpl fileReaderService;

  @Mock
  public YamlProps yamlProps;

  private QuestionsService questionsService;

  @BeforeEach
  void beforeEach() {
    questionsService = null;
  }

  @Test
  @DisplayName("getQuestions is not empty")
  void getQuestionsNotEmpty() {
    given(yamlProps.getQuestionsFile()).willReturn("question.csv");
    given(yamlProps.getCsvSplit()).willReturn("/");
    questionsService = new QuestionsServiceImpl(yamlProps, fileReaderService);
    Assertions.assertFalse(questionsService.getQuestions().isEmpty());
  }

  @Test
  @DisplayName("getQuestions return one question")
  void getQuestionsOneQuestion() {
    given(yamlProps.getQuestionsFile()).willReturn("question.csv");
    given(yamlProps.getCsvSplit()).willReturn("/");
    questionsService = new QuestionsServiceImpl(yamlProps, fileReaderService);
    Assertions.assertEquals(1, questionsService.getQuestions().size());
  }
}
