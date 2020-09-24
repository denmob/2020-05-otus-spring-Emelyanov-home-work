package ru.otus.hw15.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.hw15.domain.Income;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class IncomeServiceImpl implements IncomeService {

  private final List<Income> incomeList = new ArrayList<>();

  public IncomeServiceImpl() {
    incomeList.add(new Income("IncomeServiceImpl", 123.0));
  }

  @Override
  public List<Income> getIncome() {
    return incomeList;
  }

  @Override
  public boolean save(Income income) {
    log.info("save new income: {}",income.toString());
    return incomeList.add(income);
  }
}
