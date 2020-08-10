package ru.otus.hw10.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.otus.hw10.model.Book;
import ru.otus.hw10.rest.dto.BookDto;
import ru.otus.hw10.service.AuthorService;
import ru.otus.hw10.service.BookService;
import ru.otus.hw10.service.GenreService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class BookController {

  private final BookService bookService;
  private final AuthorService authorService;
  private final GenreService genreService;

  @GetMapping("/api/book/list")
  public List<BookDto> getBooks(@RequestParam(value = "countBook", defaultValue = "5") int countBook) {
    return bookService.getLastAddedBooks(countBook).stream().map(BookDto::toDto).collect(Collectors.toList());
  }

  @GetMapping("/api/book/edit/{bookId}")
  public BookDto edit(@PathVariable("bookId") String bookId) {
    Book book = bookService.readBookById(bookId).orElseThrow(NotFoundException::new);
    return BookDto.toDto(book);
  }

  @PostMapping("/api/book/save")
  public BookDto save(@RequestBody BookDto bookDto) {
    Book book = Book.builder()
        .id(bookDto.getId())
        .title(bookDto.getTitle())
        .date(bookDto.getDate())
        .author(authorService.findById(bookDto.getAuthor().getId()).orElseThrow(NotFoundException::new))
        .genre(genreService.findById(bookDto.getGenre().getId()).orElseThrow(NotFoundException::new))
        .build();
    bookService.save(book);
    return BookDto.toDto(book);
  }

  @DeleteMapping("/api/book/delete/{bookId}")
  public void delete(@PathVariable("bookId") String bookId) {
    bookService.deleteBookById(bookId);
  }
}
