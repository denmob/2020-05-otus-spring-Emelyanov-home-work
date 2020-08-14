package ru.otus.hw04.impl.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.hw04.core.domain.Question;
import ru.otus.hw04.core.domain.Student;
import ru.otus.hw04.core.service.OutputPrinterService;
import ru.otus.hw04.core.service.ReplyMessageService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest(classes = {OutputPrinterService.class, ReplyMessageService.class, ConsolePrintService.class})
class ConsolePrintServiceTest {

  @MockBean
  private OutputPrinterServiceImpl outputPrinterService;

  @MockBean
  private ReplyMessageServiceImpl replyMessageService;

  @Autowired
  private ConsolePrintService consolePrintService;

  @Test
  void printQuestion() {
    List<String> options = new ArrayList<>();
    options.add("option 1");
    options.add("option 2");
    Question question = new Question("test question", options);
    Mockito.doNothing().when(outputPrinterService).printlnMessage(anyString());

    consolePrintService.printQuestion(question);

    verify(outputPrinterService, times(1)).printlnMessage(question.getTitleQuestion());
    verify(outputPrinterService, times(1)).printlnMessage("1. " + question.getAnswerOptions().get(0));
    verify(outputPrinterService, times(1)).printlnMessage("2. " + question.getAnswerOptions().get(1));
  }

  @Test
  void printResult() {
    Student student = new Student("Den");
    String expect = "Hello Den";
    Mockito.when(replyMessageService.getResultMessage(student)).thenReturn(expect);
    Mockito.doNothing().when(outputPrinterService).printlnMessage(expect);

    consolePrintService.printResult(student);

    verify(replyMessageService, times(1)).getResultMessage(student);
    verify(outputPrinterService, times(1)).printlnMessage(expect);
  }

  @Test
  void printWelcomeMessage() {
    String expect = "Welcome Den";
    Mockito.when(replyMessageService.getWelcomeMessage()).thenReturn(expect);
    Mockito.doNothing().when(outputPrinterService).printlnMessage(expect);

    consolePrintService.printWelcomeMessage();

    verify(replyMessageService, times(1)).getWelcomeMessage();
    verify(outputPrinterService, times(1)).printlnMessage(expect);
  }

  @Test
  void printHelloMessage() {
    Student student = new Student("Den");
    String expect = "Hello Den";
    Mockito.when(replyMessageService.getHelloMessage(student.getName())).thenReturn(expect);
    Mockito.doNothing().when(outputPrinterService).printlnMessage(expect);

    consolePrintService.printHelloMessage(student.getName());

    verify(replyMessageService, times(1)).getHelloMessage(student.getName());
    verify(outputPrinterService, times(1)).printlnMessage(expect);
  }

  @Test
  void printBeforeAnswerMessage() {
    String expect = "printBeforeAnswerMessage";
    Mockito.when(replyMessageService.getBeforeAnswerMessage()).thenReturn(expect);
    Mockito.doNothing().when(outputPrinterService).printlnMessage(expect);

    consolePrintService.printBeforeAnswerMessage();

    verify(replyMessageService, times(1)).getBeforeAnswerMessage();
    verify(outputPrinterService, times(1)).printlnMessage(expect);
  }

  @Test
  void printTestingFinishMessage() {
    String expect = "getTestingFinishMessage";
    Mockito.when(replyMessageService.getTestingFinishMessage()).thenReturn(expect);
    Mockito.doNothing().when(outputPrinterService).printlnMessage(expect);

    consolePrintService.printTestingFinishMessage();

    verify(replyMessageService, times(1)).getTestingFinishMessage();
    verify(outputPrinterService, times(1)).printlnMessage(expect);
  }

  @Test
  void printErrorMessage() {
    String expect = "printErrorMessage";
    Mockito.when(replyMessageService.getErrorMessage()).thenReturn(expect);
    Mockito.doNothing().when(outputPrinterService).printlnMessage(expect);

    consolePrintService.printErrorMessage();

    verify(replyMessageService, times(1)).getErrorMessage();
    verify(outputPrinterService, times(1)).printlnMessage(expect);
  }
}
