package ru.otus.hw01.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.hw01.api.test.TestProcess;
import ru.otus.hw01.core.test.TestProcessImpl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TestProcessImplTest {

  private TestProcess testProcess;

  @BeforeEach
  void before() {
    testProcess = new TestProcessImpl();
  }

  @Test
  @DisplayName("get empty map")
  void getTestResult() {
    assertThat(testProcess.getTestResult()).isEmpty();
  }

  @Test
  @DisplayName("put null")
  void putAnswerNull() {
    assertThrows(IllegalArgumentException.class, () -> {
      testProcess.putAnswer(null, 0);
    });
  }

  @Test
  @DisplayName("put empty")
  void putAnswerEmpty() {
    assertThrows(IllegalArgumentException.class, () -> {
      testProcess.putAnswer(new String(), 0);
    });
  }

  @Test
  @DisplayName("put incorrect value")
  void putAnswerIncorrect() {
    assertThrows(IllegalArgumentException.class, () -> {
      testProcess.putAnswer("putAnswerRandom", -1);
    });
  }

  @Test
  @DisplayName("put valid and check result")
  void putAnswerRandomAndCheckResult() {
    testProcess.putAnswer("putAnswerRandom", 5);
    assertThat(testProcess.getTestResult()).isNotEmpty();
    assertThat(testProcess.getTestResult().get("putAnswerRandom")).isEqualTo(5);
  }

}
