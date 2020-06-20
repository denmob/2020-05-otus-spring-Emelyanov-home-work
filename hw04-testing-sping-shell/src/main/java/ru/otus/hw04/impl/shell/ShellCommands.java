package ru.otus.hw04.impl.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import ru.otus.hw04.core.service.ReplyMessageService;
import ru.otus.hw04.core.service.TestingService;

@ShellComponent
@RequiredArgsConstructor
public class ShellCommands {

  private final TestingService testingService;
  private final ReplyMessageService replyMessageService;

  @ShellMethod(value = "Start command", key = {"g", "go"})
  @ShellMethodAvailability(value = "isInitStartTesting")
  public String start() {
    return testingService.getWelcomeMessage();
  }

  @ShellMethod(value = "Greeting command", key = {"n", "name"})
  @ShellMethodAvailability(value = "isInitStartTesting")
  public String greeting(String userName) {
    return testingService.getHelloMessage(userName);
  }

  @ShellMethod(value = "Start testing", key = {"s", "start"})
  @ShellMethodAvailability(value = "isCanStartTesting")
  public void startTesting() {
    testingService.startTesting();
  }

  @ShellMethod(value = "Set answer command", key = {"a", "answer"})
  @ShellMethodAvailability(value = "isTestingStarted")
  public void setAnswer(String answer) {
    testingService.setAnswer(answer);
  }

  @ShellMethod(value = "Print result command", key = {"p", "print"})
  @ShellMethodAvailability(value = "isTestingFinished")
  public String printResult() {
    return testingService.printResult();
  }

  private Availability isTestingFinished() {
    return testingService.isFinishTesting() ? Availability.available() : Availability.unavailable(replyMessageService.getNotTestingFinishedMessage());
  }

  private Availability isTestingStarted() {
    return testingService.isStartedTesting() ? Availability.available() : Availability.unavailable(replyMessageService.getNotTestingStartedMessage());
  }

  private Availability isCanStartTesting() {
    return (testingService.isCanStartTesting()) ? Availability.available() : Availability.unavailable(replyMessageService.getCanNotStartTestingMessage());
  }

  private Availability isInitStartTesting() {
    return (!testingService.isFinishTesting() && !testingService.isStartedTesting()) ? Availability.available() : Availability.unavailable(replyMessageService.getAlreadyInitStartTestingMessage());
  }

}
