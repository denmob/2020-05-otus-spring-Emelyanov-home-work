package ru.otus.hw10.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.otus.hw10.rest.dto.BookDto;
import ru.otus.hw10.service.AuthorService;
import ru.otus.hw10.service.BookService;
import ru.otus.hw10.service.CommentService;
import ru.otus.hw10.service.GenreService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class BookController {

  private final BookService bookService;
  private final AuthorService authorService;
  private final GenreService genreService;
  private final CommentService commentService;

  @GetMapping("/api/books")
  public List<BookDto> getAllPersons() {
    return bookService.getLastAddedBooks(5).stream().map(BookDto::toDto)
        .collect(Collectors.toList());
  }
}
