package ru.otus.hw04.impl.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.hw04.core.domain.Student;
import ru.otus.hw04.core.service.ReplyMessageService;
import ru.otus.hw04.impl.configs.YamlProps;

@Service
@RequiredArgsConstructor
public class ReplyMessageServiceImpl implements ReplyMessageService {

  private final YamlProps yamlProps;
  private final MessageSource messageSource;

  @Override
  public String getHelloMessage(String studentName) {
    return messageSource.getMessage("shell.hello.message", new String[]{studentName}, yamlProps.getLocale());
  }

  @Override
  public String getWelcomeMessage() {
    return messageSource.getMessage("shell.welcome.message", new String[]{}, yamlProps.getLocale());
  }

  @Override
  public String getTestingFinishMessage() {
    return messageSource.getMessage("shell.testing.finish.message", new String[]{}, yamlProps.getLocale());
  }

  @Override
  public String getBeforeAnswerMessage() {
    return messageSource.getMessage("shell.before.answer.message", new String[]{}, yamlProps.getLocale());
  }

  @Override
  public String getResultMessage(Student student) {
    return messageSource.getMessage("print.result.testing", new String[]{student.getName(), String.valueOf(student.getMark())}, yamlProps.getLocale());
  }

  @Override
  public String getErrorMessage() {
    return messageSource.getMessage("input.answer.error", new String[]{}, yamlProps.getLocale());
  }

  @Override
  public String getNotTestingFinishedMessage() {
    return messageSource.getMessage("shell.availability.isNotTestingFinished.message", new String[]{}, yamlProps.getLocale());
  }

  @Override
  public String getNotTestingStartedMessage() {
    return messageSource.getMessage("shell.availability.isNotTestingStarted.message", new String[]{}, yamlProps.getLocale());
  }

  @Override
  public String getCanNotStartTestingMessage() {
    return messageSource.getMessage("shell.availability.isCanNotStartTesting.message", new String[]{}, yamlProps.getLocale());
  }

  @Override
  public String getAlreadyInitStartTestingMessage() {
    return messageSource.getMessage("shell.availability.isAlreadyInitStartTesting.message", new String[]{}, yamlProps.getLocale());
  }

}
