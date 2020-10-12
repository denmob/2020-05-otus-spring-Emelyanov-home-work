package ru.otus.web.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.library.RestService;
import ru.otus.library.RestServiceImpl;
import ru.otus.library.model.Book;
import ru.otus.library.model.dto.BookDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookController {

  private static final RestService<BookDto> REST_SERVICE = new RestServiceImpl<>();

  @GetMapping("/api/book")
  public List<BookDto> getBooks() {
    return REST_SERVICE.getEntities("http://localhost:8001/api/book");
  }

  @GetMapping("/api/book/{bookId}")
  public BookDto edit(@PathVariable("bookId") String bookId) {
    // Book book = bookService.readBookById(bookId).orElseThrow(NotFoundException::new);
    return BookDto.toDto(new Book());
  }

  @PostMapping(value = "/api/book", consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public BookDto post(@RequestBody BookDto bookDto) {
    // return BookDto.toDto(bookService.save(buildBookFromDto(bookDto)));
    return bookDto;
  }

  @PutMapping(value = "/api/book", consumes = MediaType.APPLICATION_JSON_VALUE)
  public BookDto put(@RequestBody BookDto bookDto) {
    //return BookDto.toDto(bookService.save(buildBookFromDto(bookDto)));
    return bookDto;
  }

  @DeleteMapping("/api/book/{bookId}")
  public ResponseEntity<Void> delete(@PathVariable("bookId") String bookId) {
    // bookService.deleteBookById(bookId);
    return ResponseEntity.ok().build();
  }

//  private Book buildBookFromDto(BookDto bookDto) {
//    return Book.builder()
//        .id(bookDto.getId())
//        .title(bookDto.getTitle())
//        .date(bookDto.getDate())
//        .author(authorService.findById(bookDto.getAuthor().getId()).orElseThrow(NotFoundException::new))
//        .genre(genreService.findById(bookDto.getGenre().getId()).orElseThrow(NotFoundException::new))
//        .build();
//  }
}
