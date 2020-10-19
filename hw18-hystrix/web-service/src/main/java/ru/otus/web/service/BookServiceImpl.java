package ru.otus.web.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.library.model.dto.AuthorDto;
import ru.otus.library.model.dto.BookDto;
import ru.otus.library.model.dto.GenreDto;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

  private final HystrixService hystrixService;
  private final AuthorService authorService;
  private final GenreService genreService;

  @Override
  public List<BookDto> getBooks(String countBook) {
    return hystrixService.getBooks(countBook);
  }

  @Override
  public BookDto getBook(String id) {
    return hystrixService.getBook(id);
  }

  @Override
  public BookDto saveBook(BookDto book) {
    book.setAuthor(AuthorDto.toAuthor(authorService.getAuthorById(book.getAuthor().getId())));
    book.setGenre(GenreDto.toGenre(genreService.getGenreById(book.getGenre().getId())));
    return hystrixService.saveBook(book);
  }

  @Override
  public BookDto editBook(BookDto book) {
    book.setAuthor(AuthorDto.toAuthor(authorService.getAuthorById(book.getAuthor().getId())));
    book.setGenre(GenreDto.toGenre(genreService.getGenreById(book.getGenre().getId())));
    return hystrixService.editBook(book);
  }

  @Override
  public boolean deleteBook(String id) {
    return hystrixService.deleteBook(id);
  }
}
