package ru.otus.hw02.gui;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hw02.api.gui.InteractiveInterface;
import ru.otus.hw02.api.gui.InteractiveInterfaceException;
import ru.otus.hw02.api.question.Question;
import ru.otus.hw02.api.service.QuestionsService;
import ru.otus.hw02.core.gui.InteractiveInterfaceConsole;
import ru.otus.hw02.core.question.QuestionImpl;
import ru.otus.hw02.core.service.QuestionsServiceImpl;

import java.io.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class InteractiveInterfaceConsoleTest {

  private static final Logger logger = LoggerFactory.getLogger(InteractiveInterfaceConsoleTest.class);

  private InteractiveInterface interactiveInterface;

  private InputStream interfaceInputStream;
  private OutputStream interfaceOutputStream;

  private QuestionsService questionsService;

  @BeforeEach
  void beforeEach() {
    questionsService = mock(QuestionsServiceImpl.class);
    interfaceOutputStream = new ByteArrayOutputStream(1024);
  }

  @Test
  @DisplayName("create with all null argument")
  void interactiveInterfaceCreateWithNullArgument() {
    assertThrows(IllegalArgumentException.class, () ->
        new InteractiveInterfaceConsole(null, null, null));
  }

  @Test
  @DisplayName("create with null streams")
  void interactiveInterfaceCreateWithNullStreams() {
    String message = assertThrows(IllegalArgumentException.class, () ->
        new InteractiveInterfaceConsole(questionsService, null, null)).getMessage();
    assertEquals("Argument interfaceInputStream is null", message);
  }

  @Test
  @DisplayName("create with null outputStream")
  void interactiveInterfaceCreateWithNullOutputStream() {
    String message = assertThrows(IllegalArgumentException.class, () ->
        new InteractiveInterfaceConsole(questionsService, new ByteArrayInputStream("expected".getBytes()), null)).getMessage();
    assertEquals("Argument interfaceOutputStream is null", message);
  }

  @Test
  @DisplayName("correct test with input")
  void welcomeValid() {
    String expected = "Den";
    interfaceInputStream = new ByteArrayInputStream(expected.getBytes());
    interactiveInterface = new InteractiveInterfaceConsole(questionsService, interfaceInputStream, interfaceOutputStream);

    interactiveInterface.welcome();

    String actual = interfaceOutputStream.toString();
    logger.info(actual);
    assertTrue(actual.contains(expected));
  }

  @Test
  @DisplayName("test with empty input")
  void welcomeEmpty() {
    interfaceInputStream = new ByteArrayInputStream("".getBytes());
    interactiveInterface = new InteractiveInterfaceConsole(questionsService, interfaceInputStream, interfaceOutputStream);
    assertThrows(InteractiveInterfaceException.class, () -> interactiveInterface.welcome());
  }

  @Test
  @DisplayName("processing one valid question")
  void processingOneQuestionValid() {
    String question = "The word is spelled correctly";
    List<String> answers = new ArrayList<>();
    answers.add("Diffecult");
    answers.add("Dufficult");
    answers.add("Difficalt");
    answers.add("Deefficult");
    answers.add("Difficult");

    String answer = "5";
    interfaceInputStream = new ByteArrayInputStream(answer.getBytes());
    interactiveInterface = new InteractiveInterfaceConsole(questionsService, interfaceInputStream, interfaceOutputStream);

    Map.Entry<String, Integer> result = interactiveInterface.processingOneQuestion(question, answers);
    assertEquals(question, result.getKey());
    assertEquals(answer, result.getValue().toString());
  }

  @Test
  @DisplayName("processing one empty question")
  void processingOneQuestionWithEmptyQuestion() {
    interfaceInputStream = new ByteArrayInputStream("answer".getBytes());
    interactiveInterface = new InteractiveInterfaceConsole(questionsService, interfaceInputStream, interfaceOutputStream);
    String message = assertThrows(IllegalArgumentException.class, () ->
        interactiveInterface.processingOneQuestion("", new ArrayList<>())).getMessage();
    assertEquals("Argument questions is null or empty", message);
  }

  @Test
  @DisplayName("processing one empty question")
  void processingOneQuestionWithEmptyAnswers() {
    interfaceInputStream = new ByteArrayInputStream("answer".getBytes());
    interactiveInterface = new InteractiveInterfaceConsole(questionsService, interfaceInputStream, interfaceOutputStream);
    String message = assertThrows(IllegalArgumentException.class, () ->
        interactiveInterface.processingOneQuestion("question", new ArrayList<>())).getMessage();
    assertEquals("Argument answers is null or empty", message);
  }

}
