package ru.otus.hw04.impl.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import ru.otus.hw04.core.domain.Student;
import ru.otus.hw04.impl.configs.Localization;
import ru.otus.hw04.impl.configs.YamlProps;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@EnableConfigurationProperties(YamlProps.class)
@SpringBootTest(classes = {ReplyMessageServiceImpl.class,YamlProps.class, Localization.class})
class ReplyMessageServiceImplTest {

  @MockBean
  private MessageSource messageSource;

  @Autowired
  private ReplyMessageServiceImpl replyMessageService;

  @Test
  void getHelloMessage() {
    String studentName = "Den";
    String expect = "Hello Den";
    when(messageSource.getMessage("shell.hello.message", new String[]{studentName}, Locale.ENGLISH)).thenReturn(expect);

    String actual = replyMessageService.getHelloMessage(studentName);

    assertEquals(expect, actual);
  }


  @Test
  void getWelcomeMessage() {
    String expect = "WelcomeMessage";
    when(messageSource.getMessage("shell.welcome.message", new String[]{}, Locale.ENGLISH)).thenReturn(expect);

    String actual = replyMessageService.getWelcomeMessage();

    assertEquals(expect, actual);
  }

  @Test
  void getTestingFinishMessage() {
    String expect = "TestingFinishMessage";
    when(messageSource.getMessage("shell.testing.finish.message", new String[]{}, Locale.ENGLISH)).thenReturn(expect);

    String actual = replyMessageService.getTestingFinishMessage();

    assertEquals(expect, actual);
  }

  @Test
  void getBeforeAnswerMessage() {
    String expect = "BeforeAnswerMessage";
    when(messageSource.getMessage("shell.before.answer.message", new String[]{}, Locale.ENGLISH)).thenReturn(expect);

    String actual = replyMessageService.getBeforeAnswerMessage();

    assertEquals(expect, actual);
  }

  @Test
  void getResultMessage() {
    Student student = new Student("Den");
    String expect = "ResultMessage";
    when(messageSource.getMessage("print.result.testing", new String[]{student.getName(), String.valueOf(student.getMark())}, Locale.ENGLISH)).thenReturn(expect);

    String actual = replyMessageService.getResultMessage(student);

    assertEquals(expect, actual);
  }

  @Test
  void getErrorMessage() {
    String expect = "ErrorMessage";
    when(messageSource.getMessage("input.answer.error", new String[]{}, Locale.ENGLISH)).thenReturn(expect);

    String actual = replyMessageService.getErrorMessage();

    assertEquals(expect, actual);
  }

  @Test
  void getNotTestingFinishedMessage() {
    String expect = "NotTestingFinishedMessage";
    when(messageSource.getMessage("shell.availability.isNotTestingFinished.message", new String[]{}, Locale.ENGLISH)).thenReturn(expect);

    String actual = replyMessageService.getNotTestingFinishedMessage();

    assertEquals(expect, actual);
  }

  @Test
  void getNotTestingStartedMessage() {
    String expect = "NotTestingStartedMessage";
    when(messageSource.getMessage("shell.availability.isNotTestingStarted.message", new String[]{}, Locale.ENGLISH)).thenReturn(expect);

    String actual = replyMessageService.getNotTestingStartedMessage();

    assertEquals(expect, actual);
  }

  @Test
  void getCanNotStartTestingMessage() {
    String expect = "CanNotStartTestingMessage";
    when(messageSource.getMessage("shell.availability.isCanNotStartTesting.message", new String[]{}, Locale.ENGLISH)).thenReturn(expect);

    String actual = replyMessageService.getCanNotStartTestingMessage();

    assertEquals(expect, actual);
  }

  @Test
  void getAlreadyInitStartTestingMessage() {
    String expect = "AlreadyInitStartTestingMessage";
    when(messageSource.getMessage("shell.availability.isAlreadyInitStartTesting.message", new String[]{}, Locale.ENGLISH)).thenReturn(expect);

    String actual = replyMessageService.getAlreadyInitStartTestingMessage();

    assertEquals(expect, actual);
  }
}
