package ru.otus.hw08.core.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.hw08.core.models.Book;
import ru.otus.hw08.core.models.Comment;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookWithComments {
  private Book book;
  private List<Comment> comments = new ArrayList<>();
}
