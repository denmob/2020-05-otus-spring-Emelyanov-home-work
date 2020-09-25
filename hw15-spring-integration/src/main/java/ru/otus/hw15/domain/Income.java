package ru.otus.hw15.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Income extends AccountTransaction {

  @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
  public Income(@JsonProperty("description") String description,@JsonProperty("amount") Double amount) {
    super(description, amount);
  }
}
