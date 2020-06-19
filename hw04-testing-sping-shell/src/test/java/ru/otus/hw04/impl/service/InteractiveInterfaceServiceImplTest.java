package ru.otus.hw04.impl.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import ru.otus.hw04.core.domain.Answer;
import ru.otus.hw04.core.domain.Question;
import ru.otus.hw04.core.domain.Student;
import ru.otus.hw04.impl.configs.YamlProps;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
class InteractiveInterfaceServiceImplTest {

  @MockBean
  private InputReaderServiceImpl inputReaderService;

  @MockBean
  private OutputPrinterServiceImpl outputPrinterService;

  @Autowired
  private InteractiveInterfaceServiceImpl inputReadInteractiveInterfaceService;

  @Autowired
  private QuestionsServiceImpl questionsService;

  @Autowired
  private MessageSource messageSource;

  @Autowired
  private YamlProps yamlProps;

  @Test
  @DisplayName("check return getNameStudent")
  void getNameStudent() {
    String expected = "Den";
    Mockito.when(inputReaderService.readName()).thenReturn(expected);

    String actual = inputReadInteractiveInterfaceService.getNameStudent();

    Assertions.assertEquals(expected, actual);
  }

  @Test
  @DisplayName("check processTest answer option 5")
  void processTestValidOption() {
    Question question = questionsService.getQuestions().get(0);
    Mockito.when(inputReaderService.readAnswer()).thenReturn("5");

    Answer answer = inputReadInteractiveInterfaceService.processTest(question);

    verify(outputPrinterService, times(6)).printlnMessage(anyString());
    Assertions.assertEquals(question.getTitleQuestion(), answer.getTitleQuestion());
    Assertions.assertEquals(5, answer.getAnswerOption());
  }

  @Test
  @DisplayName("check processTest answer option 6 (no valid)")
  void processTestIncorrectOption() {
    Question question = questionsService.getQuestions().get(0);
    Mockito.when(inputReaderService.readAnswer()).thenReturn("6");

    Answer answer = inputReadInteractiveInterfaceService.processTest(question);

    verify(outputPrinterService, times(9)).printlnMessage(anyString());
    Assertions.assertEquals(question.getTitleQuestion(), answer.getTitleQuestion());
    Assertions.assertEquals(0, answer.getAnswerOption());
  }

  @Test
  @DisplayName("check processTest answer invalid option char")
  void processTestIncorrectOptionWithThrow() {
    Question question = questionsService.getQuestions().get(0);
    Mockito.when(inputReaderService.readAnswer()).thenReturn("tt");

    Answer answer = inputReadInteractiveInterfaceService.processTest(question);

    verify(outputPrinterService, times(9)).printlnMessage(anyString());
    Assertions.assertEquals(question.getTitleQuestion(), answer.getTitleQuestion());
    Assertions.assertEquals(0, answer.getAnswerOption());
  }

  @Test
  void printResult() {
    Mockito.doNothing().when(outputPrinterService).printlnMessage(anyString());
    Student student = new Student("Den");
    var message = messageSource.getMessage("print.result.testing", new String[]{student.getName(), String.valueOf(student.getMark())}, yamlProps.getLocale());
    inputReadInteractiveInterfaceService.printResult(student);

    verify(outputPrinterService, times(1)).printlnMessage(message);
  }
}
