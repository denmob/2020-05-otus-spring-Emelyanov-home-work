package ru.otus.hw03.impl.service;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.hw03.core.domain.Answer;
import ru.otus.hw03.core.domain.Question;
import ru.otus.hw03.core.domain.Student;
import ru.otus.hw03.core.service.InputReaderService;
import ru.otus.hw03.core.service.InteractiveInterfaceService;
import ru.otus.hw03.core.service.OutputPrinterService;
import ru.otus.hw03.impl.configs.YamlProps;

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
    var message = messageSource.getMessage("welcome.message", new String[]{}, yamlProps.getLocale());
    outputPrinterService.printlnMessage(message);
    return inputReaderService.readName();
  }

  @Override
  public Answer processTest(Question question) {
    outputPrinterService.printlnMessage(question.getTitleQuestion());
    for (String optional : question.getAnswerOptions()) {
      outputPrinterService.printlnMessage(optional);
    }
    return new Answer(question.getTitleQuestion(), inputReaderService.readAnswer());
  }

  @Override
  public void printResult(Student student) {
    var message = messageSource.getMessage("print.result.testing", new String[]{student.getName(), String.valueOf(student.getMark())}, yamlProps.getLocale());
    outputPrinterService.printlnMessage(message);
  }
}
