package ru.otus.hw04.impl.service;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.hw04.core.domain.Answer;
import ru.otus.hw04.core.domain.Question;
import ru.otus.hw04.core.domain.Student;
import ru.otus.hw04.core.service.InputReaderService;
import ru.otus.hw04.core.service.InteractiveInterfaceService;
import ru.otus.hw04.core.service.OutputPrinterService;
import ru.otus.hw04.impl.configs.YamlProps;

@Service
public class InteractiveInterfaceServiceImpl implements InteractiveInterfaceService {

  private final InputReaderService inputReaderService;
  private final OutputPrinterService outputPrinterService;
  private final MessageSource messageSource;
  private final YamlProps yamlProps;

  public InteractiveInterfaceServiceImpl(InputReaderService inputReaderService, OutputPrinterService outputPrinterService, MessageSource messageSource, YamlProps yamlProps) {
    this.inputReaderService = inputReaderService;
    this.outputPrinterService = outputPrinterService;
    this.messageSource = messageSource;
    this.yamlProps = yamlProps;
  }

  @Override
  public String getNameStudent() {
    outputPrinterService.printlnMessage(createWelcomeMessage());
    return inputReaderService.readName();
  }

  @Override
  public Answer processTest(Question question) {
    outputPrinterService.printlnMessage(question.getTitleQuestion());
    int rowNumber = 1;
    for (String option : question.getAnswerOptions()) {
      outputPrinterService.printlnMessage(rowNumber + ". " + option);
      rowNumber++;
    }
    int answerNum = 0;
    int tryInput = 0;
    do {
      try {
        answerNum = Integer.parseInt(inputReaderService.readAnswer());
        if (!checkOptions(answerNum, question.getAnswerOptions().size())) {
          outputPrinterService.printlnMessage(createErrorMessage());
          answerNum = 0;
          tryInput++;
        }
      } catch (Exception e) {
        answerNum = 0;
        tryInput++;
        outputPrinterService.printlnMessage(createErrorMessage());
      }
    } while ((answerNum == 0) && (tryInput < yamlProps.getTryInputAnswer()));
    return new Answer(question.getTitleQuestion(), answerNum);
  }

  @Override
  public void printResult(Student student) {
    outputPrinterService.printlnMessage(createResultMessage(student));
  }

  private boolean checkOptions(int answer, int sizeAnswerOptions) {
    return answer >= 1 && answer <= sizeAnswerOptions;
  }

  private String createErrorMessage() {
    return messageSource.getMessage("input.answer.error", new String[]{}, yamlProps.getLocale());
  }

  private String createWelcomeMessage() {
    return messageSource.getMessage("welcome.message", new String[]{}, yamlProps.getLocale());
  }

  private String createResultMessage(Student student) {
    return messageSource.getMessage("print.result.testing", new String[]{student.getName(), String.valueOf(student.getMark())}, yamlProps.getLocale());
  }
}
