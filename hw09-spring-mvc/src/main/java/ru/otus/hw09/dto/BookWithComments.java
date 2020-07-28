package ru.otus.hw09.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.hw09.model.Book;
import ru.otus.hw09.model.Comment;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookWithComments {
  private Book book;
  private List<Comment> comments;
}
