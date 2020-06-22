package ru.otus.hw04.impl.service;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.hw04.core.domain.Question;
import ru.otus.hw04.core.domain.Student;
import ru.otus.hw04.core.service.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {QuestionsServiceImpl.class, TestValidatorServiceImpl.class, ConsolePrintService.class})
class TestingServiceImplTest {

  @MockBean
  private QuestionsServiceImpl questionsService;

  @MockBean
  private TestValidatorServiceImpl testValidatorService;

  @MockBean
  private ConsolePrintService consolePrintService;

  private TestingServiceImpl testingService;
  private final String studentName = "Den";

  @BeforeEach
  void beforeEach() {
    testingService = new TestingServiceImpl(questionsService, testValidatorService, consolePrintService);
    testingService.createStudent(studentName);
  }

  @AfterEach
  void afterEach() {
    testingService = null;
  }

  @Test
  void constructorInvokeWelcomeMessage() {
    verify(consolePrintService, times(1)).printWelcomeMessage();
  }

  @Test
  void createStudent() {
    Mockito.doNothing().when(consolePrintService).printHelloMessage(studentName);

    verify(consolePrintService, times(1)).printHelloMessage(studentName);
  }

  @Test
  @DisplayName("startTesting with questions is not empty")
  void startTestingWithOneQuestion() {
    List<Question> questions = new ArrayList<>();
    List<String> options = new ArrayList<>();
    options.add("option 1");
    options.add("option 2");
    Question question = new Question("test question", options);
    questions.add(question);
    Mockito.when(questionsService.getQuestions()).thenReturn(questions);

    testingService.startTesting();

    verify(consolePrintService, times(1)).printQuestion(question);
    verify(consolePrintService, times(1)).printBeforeAnswerMessage();
  }

  @Test
  @DisplayName("startTesting with empty questions")
  void startTestingWithEmptyQuestion() {
    List<Question> questions = new ArrayList<>();
    Mockito.when(questionsService.getQuestions()).thenReturn(questions);

    testingService.startTesting();

    verify(consolePrintService, times(1)).printTestingFinishMessage();
  }

  @Test
  @DisplayName("set valid answer")
  void setAnswerValid() {
    List<Question> questions = new ArrayList<>();
    List<String> options = new ArrayList<>();
    options.add("option 1");
    options.add("option 2");
    Question question = new Question("test question", options);
    questions.add(question);
    Mockito.when(questionsService.getQuestions()).thenReturn(questions);

    testingService.startTesting();
    testingService.setAnswer("1");

    verify(consolePrintService, times(1)).printQuestion(question);
    verify(consolePrintService, times(1)).printBeforeAnswerMessage();

  }

  @Test
  @DisplayName("set incorrect integer answer")
  void setAnswerInvalidInt() {
    List<Question> questions = new ArrayList<>();
    List<String> options = new ArrayList<>();
    options.add("option 1");
    options.add("option 2");
    Question question = new Question("test question", options);
    questions.add(question);
    Mockito.when(questionsService.getQuestions()).thenReturn(questions);

    testingService.startTesting();
    testingService.setAnswer("3");

    verify(consolePrintService, times(1)).printErrorMessage();
  }

  @Test
  @DisplayName("set incorrect char answer")
  void setAnswerInvalidChar() {
    List<Question> questions = new ArrayList<>();
    List<String> options = new ArrayList<>();
    options.add("option 1");
    options.add("option 2");
    Question question = new Question("test question", options);
    questions.add(question);
    Mockito.when(questionsService.getQuestions()).thenReturn(questions);

    testingService.startTesting();
    testingService.setAnswer("a");

    verify(consolePrintService, times(1)).printErrorMessage();
  }

  @Test
  void printResult() {
    Student student = new Student(studentName);
    doNothing().when(consolePrintService).printResult(student);

    testingService.printResult();

    verify(consolePrintService, times(1)).printResult(student);
  }

  @Test
  void isFinishTesting() {
    assertFalse(testingService.isFinishTesting());
  }

  @Test
  void isStartedTesting() {
    assertTrue(testingService.isStartedTesting());
  }

  @Test
  void isCanStartTesting() {
    assertTrue(testingService.isCanStartTesting());
  }

}
