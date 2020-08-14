package ru.otus.hw04.impl.service;

import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.hw04.core.service.QuestionsService;
import ru.otus.hw04.impl.configs.YamlProps;

import static org.mockito.BDDMockito.given;

@EnableConfigurationProperties(YamlProps.class)
@SpringBootTest(classes = {FileReaderServiceImpl.class,YamlProps.class,QuestionsServiceImpl.class})
class QuestionsServiceImplTest {

  @Autowired
  private FileReaderServiceImpl fileReaderService;

  @Mock
  public YamlProps yamlProps;

  private QuestionsServiceImpl questionsService;

  @BeforeEach
  void beforeEach() {
    questionsService = null;
  }

  @Test
  @DisplayName("getQuestions is not empty")
  void getQuestionsNotEmpty() {
    given(yamlProps.getQuestionsFile()).willReturn("question_en.csv");
    given(yamlProps.getCsvSplit()).willReturn("/");
    questionsService = new QuestionsServiceImpl(yamlProps, fileReaderService);
    Assertions.assertFalse(questionsService.getQuestions().isEmpty());
  }

  @Test
  @DisplayName("getQuestions return one question and five options")
  void getQuestionsOneQuestion() {
    given(yamlProps.getQuestionsFile()).willReturn("question_en.csv");
    given(yamlProps.getCsvSplit()).willReturn(",");
    questionsService = new QuestionsServiceImpl(yamlProps, fileReaderService);
    Assertions.assertEquals(1, questionsService.getQuestions().size());
    Assertions.assertEquals(5, questionsService.getQuestions().get(0).getAnswerOptions().size());
  }

  @Test
  void getQuestions() {
    given(yamlProps.getQuestionsFile()).willReturn("question_en.csv");
    given(yamlProps.getCsvSplit()).willReturn(",");
    questionsService = new QuestionsServiceImpl(yamlProps, fileReaderService);
    Assertions.assertEquals(1, questionsService.getQuestions().size());
  }
}
