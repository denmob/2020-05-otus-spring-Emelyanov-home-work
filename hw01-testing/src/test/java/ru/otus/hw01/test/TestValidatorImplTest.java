package ru.otus.hw01.test;

import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.hw01.api.test.TestValidator;
import ru.otus.hw01.core.test.TestValidatorImpl;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TestValidatorImplTest {

  private TestValidator testValidator;

  @BeforeEach
  void before() {
    testValidator = new TestValidatorImpl();
  }

  @Test
  @DisplayName("two valid test")
  void getGradeForTest() {
    Map<String, Integer> answers = new HashMap<>();
    answers.put("The word is spelled correctly", 5);
    answers.put("test", 0);
    answers.put("869 reads correctly", 5);
    assertThat(testValidator.getGradeForTest(answers)).isEqualTo(2);
  }

  @Test
  @DisplayName("null argument")
  void getGradeForTestNull() {
    assertThrows(IllegalArgumentException.class, () -> {
      testValidator.getGradeForTest(null);
    });
  }

  @Test
  @DisplayName("empty argument")
  void getGradeForTestEmpty() {
    assertThat(testValidator.getGradeForTest(new HashMap<>())).isEqualTo(0);
    ;
  }

}
