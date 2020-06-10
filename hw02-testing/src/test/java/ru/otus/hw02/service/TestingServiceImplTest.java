package ru.otus.hw02.service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.hw02.api.gui.InteractiveInterface;
import ru.otus.hw02.api.reader.DataReader;
import ru.otus.hw02.api.service.TestingService;
import ru.otus.hw02.core.gui.InteractiveInterfaceConsole;
import ru.otus.hw02.core.reader.CsvReader;
import ru.otus.hw02.core.service.TestingServiceImpl;
import ru.otus.hw02.core.test.TestValidatorImpl;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class TestingServiceImplTest {

  private TestingService testingService;
  private InteractiveInterface interactiveInterface;
  private DataReader reader = new CsvReader(",");

  @BeforeEach
  void before() {
    interactiveInterface = mock(InteractiveInterfaceConsole.class);
    testingService = new TestingServiceImpl(new TestValidatorImpl("answers.csv", reader), interactiveInterface);
  }

  @Test
  @DisplayName("mock test with result 0")
  void startTestEmpty() {
    testingService.startTest();
    assertThat(testingService.getGradeForTest()).isEqualTo(0);
  }

  @Test
  @DisplayName("mock test with one valid answer")
  void startTestWithOneAnswerValid() {
    Map<String, Integer> answer = new HashMap<>();
    answer.put("869 reads correctly", 5);
    given(interactiveInterface.getResult()).willReturn(answer);
    testingService.startTest();
    assertThat(testingService.getGradeForTest()).isEqualTo(1);
  }

  @Test
  @DisplayName("mock test with one valid answer")
  void startTestWithOneAnswerInvalid() {
    Map<String, Integer> answer = new HashMap<>();
    answer.put("869 reads correctly", 1);
    given(interactiveInterface.getResult()).willReturn(answer);
    testingService.startTest();
    assertThat(testingService.getGradeForTest()).isEqualTo(0);
  }


  @Test
  @DisplayName("mock test with one valid answer and another random answer")
  void startTestWithOneAnswerValidAndRandom() {
    Map<String, Integer> answer = new HashMap<>();
    answer.put("869 reads correctly", 5);
    answer.put("startTestWithOneAnswerAndRandom", 99);
    answer.put("hello", 66);
    given(interactiveInterface.getResult()).willReturn(answer);
    testingService.startTest();
    assertThat(testingService.getGradeForTest()).isEqualTo(1);
  }

  @Test
  @DisplayName("mock test with two valid answer and another random answer")
  void startTestWithTwoAnswerValidAndRandom() {
    Map<String, Integer> answer = new HashMap<>();
    answer.put("869 reads correctly", 5);
    answer.put("startTestWithOneAnswerAndRandom", 99);
    answer.put("hello", 66);
    answer.put("The word is spelled correctly", 5);
    given(interactiveInterface.getResult()).willReturn(answer);
    testingService.startTest();
    assertThat(testingService.getGradeForTest()).isEqualTo(2);
  }
}
