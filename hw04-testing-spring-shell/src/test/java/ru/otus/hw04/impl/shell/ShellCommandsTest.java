package ru.otus.hw04.impl.shell;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.shell.Availability;
import ru.otus.hw04.core.service.ReplyMessageService;
import ru.otus.hw04.core.service.TestingService;
import ru.otus.hw04.impl.service.ConsolePrintService;
import ru.otus.hw04.impl.service.ReplyMessageServiceImpl;
import ru.otus.hw04.impl.service.TestingServiceImpl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ShellCommandsTest {

  @MockBean
  private TestingServiceImpl testingService;

  @MockBean
  private ReplyMessageServiceImpl replyMessageService;

  @Autowired
  private ShellCommands shellCommands;

  @Test
  void greeting() {
    String studentName = "Den";
    doNothing().when(testingService).createStudent(studentName);

    shellCommands.greeting(studentName);

    verify(testingService, times(1)).createStudent(studentName);
  }

  @Test
  void startTesting() {
    doNothing().when(testingService).startTesting();

    shellCommands.startTesting();

    verify(testingService, times(1)).startTesting();
  }

  @Test
  @DisplayName("set valid answer")
  void setAnswerValid() {
    when(testingService.setAnswer("1")).thenReturn(true);

    shellCommands.setAnswer("1");

    verify(testingService, times(1)).setAnswer("1");
  }

  @Test
  @DisplayName("set valid answer")
  void setAnswerInvalid() {
    when(testingService.setAnswer("1")).thenReturn(false);

    shellCommands.setAnswer("1");

    verify(testingService, times(3)).setAnswer("1");
  }

  @Test
  void printResult() {
    doNothing().when(testingService).printResult();

    shellCommands.printResult();

    verify(testingService, times(1)).printResult();
  }

  @Test
  void isTestingFinished() {
    when(testingService.isFinishTesting()).thenReturn(false);
    when(replyMessageService.getNotTestingFinishedMessage()).thenReturn("NotTestingFinishedMessage");

    Availability availability = shellCommands.isTestingFinished();

    assertEquals(Availability.unavailable("NotTestingFinishedMessage").getReason(),availability.getReason());
  }

  @Test
  void isTestingStarted() {
    when(testingService.isStartedTesting()).thenReturn(false);
    when(replyMessageService.getNotTestingStartedMessage()).thenReturn("NotTestingStartedMessage");

    Availability availability = shellCommands.isTestingStarted();

    assertEquals(Availability.unavailable("NotTestingStartedMessage").getReason(),availability.getReason());
  }

  @Test
  void isCanStartTesting() {
    when(testingService.isCanStartTesting()).thenReturn(false);
    when(replyMessageService.getCanNotStartTestingMessage()).thenReturn("CanNotStartTestingMessage");

    Availability availability = shellCommands.isCanStartTesting();

    assertEquals(Availability.unavailable("CanNotStartTestingMessage").getReason(),availability.getReason());
  }

  @Test
  void isInitStartTesting() {
    when(testingService.isFinishTesting()).thenReturn(true);
    when(replyMessageService.getAlreadyInitStartTestingMessage()).thenReturn("AlreadyInitStartTestingMessage");

    Availability availability = shellCommands.isInitStartTesting();

    assertEquals(Availability.unavailable("AlreadyInitStartTestingMessage").getReason(),availability.getReason());
  }
}
