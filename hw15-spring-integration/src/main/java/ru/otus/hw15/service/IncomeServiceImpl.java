package ru.otus.hw15.service;

import org.springframework.stereotype.Service;
import ru.otus.hw15.domain.Income;

import java.util.ArrayList;
import java.util.List;

@Service
public class IncomeServiceImpl implements IncomeService {

  private final List<Income> incomeList = new ArrayList<>();

  public IncomeServiceImpl() {
    incomeList.add(new Income("First Income", 1000.0));
  }

  @Override
  public List<Income> getIncome() {
    return incomeList;
  }

  @Override
  public boolean save(Income income) {
    return incomeList.add(income);
  }
}
