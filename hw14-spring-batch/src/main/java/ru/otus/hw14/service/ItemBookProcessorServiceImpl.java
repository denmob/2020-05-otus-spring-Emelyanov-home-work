package ru.otus.hw14.service;


import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw14.model.table.Author;
import ru.otus.hw14.model.table.Book;
import ru.otus.hw14.model.table.Genre;


@Service
@RequiredArgsConstructor
public class ItemBookProcessorServiceImpl implements ItemBookProcessorService {

  private final AuthorCrudService authorCrudService;
  private final GenreCrudService genreCrudService;

  @Override
  public Book convertDocumentToEntity(@NonNull ru.otus.hw14.model.document.Book book) {

    Author author = authorCrudService.findByFirstNameAndLastNameAndBirthday(
        book.getAuthor().getFirstName(),
        book.getAuthor().getLastName(),
        book.getAuthor().getBirthday())
        .orElseThrow(() -> new NotFoundException("Author not found"));

    Genre genre = genreCrudService.findByName(book.getGenre().getName())
        .orElseThrow(() -> new NotFoundException("Genre not found"));

    return Book.builder()
        .title(book.getTitle())
        .date(book.getDate())
        .author(author)
        .genre(genre)
        .build();
  }
}
