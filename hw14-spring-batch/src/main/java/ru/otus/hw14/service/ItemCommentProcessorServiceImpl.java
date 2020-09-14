package ru.otus.hw14.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw14.model.document.Book;
import ru.otus.hw14.model.table.Comment;

@Service
@RequiredArgsConstructor
public class ItemCommentProcessorServiceImpl implements ItemCommentProcessorService {

  private final BookMongoService bookMongoService;
  private final BookCrudService bookCrudService;

  @Override
  public Comment convertDocumentToEntity(ru.otus.hw14.model.document.Comment comment) {

    Book bookDocument = bookMongoService.findById(comment.getBookId())
        .orElseThrow(() -> new NotFoundException(String.format("BookDocument.id %s not found", comment.getBookId())));

    ru.otus.hw14.model.table.Book bookEntity = bookCrudService.findByTitleAndDate(bookDocument.getTitle(), bookDocument.getDate())
        .orElseThrow(() -> new NotFoundException(String.format("BookEntity.title %s and BookEntity.date %s not found", bookDocument.getTitle(), bookDocument.getDate())));

    return Comment.builder().book(bookEntity).commentary(comment.getCommentary()).build();
  }
}
