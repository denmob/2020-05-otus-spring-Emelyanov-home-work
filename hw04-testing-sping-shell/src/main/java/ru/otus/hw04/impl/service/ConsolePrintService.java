package ru.otus.hw04.impl.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw04.core.domain.Question;
import ru.otus.hw04.core.domain.Student;
import ru.otus.hw04.core.service.PrintService;
import ru.otus.hw04.core.service.OutputPrinterService;
import ru.otus.hw04.core.service.ReplyMessageService;

@Service
@RequiredArgsConstructor
public class ConsolePrintService implements PrintService {

  private final OutputPrinterService outputPrinterService;
  private final ReplyMessageService replyMessageService;

  @Override
  public void printQuestion(Question question) {
    outputPrinterService.printlnMessage(question.getTitleQuestion());
    int rowNumber = 1;
    for (String option : question.getAnswerOptions()) {
      outputPrinterService.printlnMessage(rowNumber + ". " + option);
      rowNumber++;
    }
  }

  @Override
  public void printResult(Student student) {
    outputPrinterService.printlnMessage(replyMessageService.getResultMessage(student));
  }

  @Override
  public void printWelcomeMessage() {
    outputPrinterService.printlnMessage(replyMessageService.getWelcomeMessage());
  }

  @Override
  public void printHelloMessage(String studentName) {
    outputPrinterService.printlnMessage(replyMessageService.getHelloMessage(studentName));
  }

  @Override
  public void printBeforeAnswerMessage() {
    outputPrinterService.printlnMessage(replyMessageService.getBeforeAnswerMessage());
  }

  @Override
  public void printTestingFinishMessage() {
    outputPrinterService.printlnMessage(replyMessageService.getTestingFinishMessage());
  }

  @Override
  public void printErrorMessage() {
    outputPrinterService.printlnMessage(replyMessageService.getErrorMessage());
  }
}
