package ru.otus.hw15.service;

import ru.otus.hw15.domain.Income;

import java.util.List;

public interface IncomeService {

  List<Income> getIncome();

  boolean save(Income income);
}
