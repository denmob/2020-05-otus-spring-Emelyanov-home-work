package ru.otus.hw04.impl.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import ru.otus.hw04.core.service.ReplyMessageService;
import ru.otus.hw04.core.service.TestingService;
import ru.otus.hw04.impl.configs.YamlProps;

@ShellComponent
@RequiredArgsConstructor
public class ShellCommands {

  private final TestingService testingService;
  private final ReplyMessageService replyMessageService;
  private final YamlProps yamlProps;

  @ShellMethod(value = "Greeting command", key = {"n", "name"})
  @ShellMethodAvailability(value = "isInitStartTesting")
  void greeting(String userName) {
    testingService.createStudent(userName);
  }

  @ShellMethod(value = "Start testing", key = {"s", "start"})
  @ShellMethodAvailability(value = "isCanStartTesting")
  void startTesting() {
    testingService.startTesting();
  }

  @ShellMethod(value = "Set answer command", key = {"a", "answer"})
  @ShellMethodAvailability(value = "isTestingStarted")
  void setAnswer(String studentAnswer) {
    boolean resultSetAnswer;
    int tryInput = 0;
    do {
      resultSetAnswer = testingService.setAnswer(studentAnswer);
      tryInput++;
    } while ((!resultSetAnswer) && (tryInput < yamlProps.getTryInputAnswer()));
  }

  @ShellMethod(value = "Print result command", key = {"p", "print"})
  @ShellMethodAvailability(value = "isTestingFinished")
  void printResult() {
    testingService.printResult();
  }

  Availability isTestingFinished() {
    return testingService.isFinishTesting() ? Availability.available() : Availability.unavailable(replyMessageService.getNotTestingFinishedMessage());
  }

  Availability isTestingStarted() {
    return testingService.isStartedTesting() ? Availability.available() : Availability.unavailable(replyMessageService.getNotTestingStartedMessage());
  }

  Availability isCanStartTesting() {
    return (testingService.isCanStartTesting()) ? Availability.available() : Availability.unavailable(replyMessageService.getCanNotStartTestingMessage());
  }

  Availability isInitStartTesting() {
    return (!testingService.isFinishTesting() && !testingService.isStartedTesting()) ? Availability.available() : Availability.unavailable(replyMessageService.getAlreadyInitStartTestingMessage());
  }

}
