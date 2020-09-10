package ru.otus.hw14.model.table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookWithComments {

  private Book book;
  private List<Comment> comments = new ArrayList<>();
}
