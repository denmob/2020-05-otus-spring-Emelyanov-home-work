package ru.otus.hw15.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.hw15.domain.Income;
import ru.otus.hw15.service.IncomeService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class IncomeController {

  private final IncomeService incomeList;

  @GetMapping("/income")
  public List<Income> getIncome() {
    return incomeList.getIncome();
  }

  @PostMapping("/income")
  public boolean saveIncome(@RequestParam(value = "amount", defaultValue = "890") double amount,
                            @RequestParam(value = "description", defaultValue = "IncomeController") String description) {
    return incomeList.save(new Income(description, amount));
  }
}
