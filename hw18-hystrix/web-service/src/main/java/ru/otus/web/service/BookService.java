package ru.otus.web.service;

import ru.otus.library.model.dto.BookDto;

import java.util.List;

public interface BookService {

  List<BookDto> getBooks(String countBook);

  BookDto getBook(String id);

  BookDto saveBook(BookDto bookDto);

  BookDto editBook(BookDto bookDto);

  boolean deleteBook(String id);
}
