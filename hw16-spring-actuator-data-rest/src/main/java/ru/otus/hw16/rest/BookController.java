package ru.otus.hw16.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.otus.hw16.model.Book;
import ru.otus.hw16.rest.dto.BookDto;
import ru.otus.hw16.service.AuthorService;
import ru.otus.hw16.service.BookService;
import ru.otus.hw16.service.GenreService;

@RestController
@RequiredArgsConstructor
public class BookController {

  private final BookService bookService;
  private final AuthorService authorService;
  private final GenreService genreService;

  @PutMapping(value = "/api/book", consumes = MediaType.APPLICATION_JSON_VALUE)
  public BookDto put(@RequestBody BookDto bookDto) {
    return BookDto.toDto(bookService.save(buildBookFromDto(bookDto)));
  }

  private Book buildBookFromDto(BookDto bookDto) {
    return Book.builder()
        .id(bookDto.getId())
        .title(bookDto.getTitle())
        .date(bookDto.getDate())
        .author(authorService.findById(bookDto.getAuthor().getId()).orElseThrow(NotFoundException::new))
        .genre(genreService.findById(bookDto.getGenre().getId()).orElseThrow(NotFoundException::new))
        .build();
  }
}
