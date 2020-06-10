package ru.otus.hw02.api.service;

import ru.otus.hw02.api.question.Question;

import java.util.List;

public interface QuestionsService {

  List<Question> getQuestions();
}
