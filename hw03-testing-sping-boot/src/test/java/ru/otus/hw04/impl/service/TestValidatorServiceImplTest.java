package ru.otus.hw04.impl.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.hw04.core.domain.Answer;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TestValidatorServiceImplTest {

  @Autowired
  private TestValidatorServiceImpl testValidatorService;

  private List<Answer> answers;

  @BeforeEach
  void beforeEach() {
    answers = new ArrayList<>();
  }

  @Test
  @DisplayName("getMarkForQuestion with one valid answer")
  void getMarkForQuestionOneValidAnswer() {
    Answer answer = new Answer("The word is spelled correctly", 5);
    answers.add(answer);
    assertEquals(1, testValidatorService.getMarkForQuestion(answers));
  }

  @Test
  @DisplayName("getMarkForQuestion with four valid answer")
  void getMarkForQuestionFourValidAnswer() {
    answers.add(new Answer("The word is spelled correctly", 5));
    answers.add(new Answer("Find a synonym for the word <To worry>", 4));
    answers.add(new Answer("869 reads correctly", 5));
    answers.add(new Answer("Find a common word for a given group of words", 2));
    assertEquals(4, testValidatorService.getMarkForQuestion(answers));
  }

  @Test
  @DisplayName("getMarkForQuestion with invalid title answer")
  void getMarkForQuestionInvalidTitleAnswer() {
    Answer answer = new Answer("???", 5);
    answers.add(answer);
    assertEquals(0, testValidatorService.getMarkForQuestion(answers));
  }

  @Test
  @DisplayName("getMarkForQuestion with invalid optional answer")
  void getMarkForQuestionInvalidOptionalAnswer() {
    Answer answer = new Answer("The word is spelled correctly", 99);
    answers.add(answer);
    assertEquals(0, testValidatorService.getMarkForQuestion(answers));
  }
}
