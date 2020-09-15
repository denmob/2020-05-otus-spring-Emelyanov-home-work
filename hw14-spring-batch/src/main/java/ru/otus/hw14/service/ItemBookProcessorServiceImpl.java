package ru.otus.hw14.service;


import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw14.model.document.BookDocument;
import ru.otus.hw14.model.entity.AuthorEntity;
import ru.otus.hw14.model.entity.BookEntity;
import ru.otus.hw14.model.entity.GenreEntity;


@Service
@RequiredArgsConstructor
public class ItemBookProcessorServiceImpl implements ItemBookProcessorService {

  private final AuthorCrudService authorCrudService;
  private final GenreCrudService genreCrudService;

  @Override
  public BookEntity convertDocumentToEntity(@NonNull BookDocument bookDocument) {

    AuthorEntity authorEntity = authorCrudService.findByFirstNameAndLastNameAndBirthday(
        bookDocument.getAuthorDocument().getFirstName(),
        bookDocument.getAuthorDocument().getLastName(),
        bookDocument.getAuthorDocument().getBirthday())
        .orElseThrow(() -> new NotFoundException("Author not found"));

    GenreEntity genreEntity = genreCrudService.findByName(bookDocument.getGenreDocument().getName())
        .orElseThrow(() -> new NotFoundException("Genre not found"));

    return BookEntity.builder()
        .title(bookDocument.getTitle())
        .date(bookDocument.getDate())
        .authorEntity(authorEntity)
        .genreEntity(genreEntity)
        .build();
  }
}
