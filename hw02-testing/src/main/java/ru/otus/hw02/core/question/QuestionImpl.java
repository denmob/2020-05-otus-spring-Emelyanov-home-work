package ru.otus.hw02.core.question;

import ru.otus.hw02.api.question.Question;

import java.util.*;


public class QuestionImpl implements Question {

  private final String question;

  private final List<String> variantsToAnswer;

  public QuestionImpl(String question, List<String> variantsToAnswer) {
    if (question == null || question.isEmpty()) throw new IllegalArgumentException("Argument question is null or empty");
    if (variantsToAnswer == null || variantsToAnswer.isEmpty()) throw new IllegalArgumentException("Argument variantsToAnswer is null or empty");

    this.question = question;
    this.variantsToAnswer = variantsToAnswer;
  }

  @Override
  public String getQuestion() {
    return question;
  }

  @Override
  public List<String> getVariantsToAnswer() {
    return variantsToAnswer;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    QuestionImpl that = (QuestionImpl) o;
    return question.equals(that.question) &&
        variantsToAnswer.equals(that.variantsToAnswer);
  }

  @Override
  public int hashCode() {
    return Objects.hash(question, variantsToAnswer);
  }

  @Override
  public String toString() {
    return "Question{question=" + question + ", variantsToAnswer=" + variantsToAnswer.toString() + '}';
  }
}
