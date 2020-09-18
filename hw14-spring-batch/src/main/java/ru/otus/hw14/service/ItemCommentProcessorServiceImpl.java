package ru.otus.hw14.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw14.model.document.BookDocument;
import ru.otus.hw14.model.document.CommentDocument;
import ru.otus.hw14.model.entity.BookEntity;
import ru.otus.hw14.model.entity.CommentEntity;

@Service
@RequiredArgsConstructor
public class ItemCommentProcessorServiceImpl implements ItemCommentProcessorService {

  private final BookMongoService bookMongoService;
  private final BookCrudService bookCrudService;

  @Override
  public CommentEntity convertDocumentToEntity(CommentDocument commentDocument) {

    BookDocument bookDocument = bookMongoService.findById(commentDocument.getBookId())
        .orElseThrow(() -> new NotFoundException(String.format("BookDocument.id %s not found", commentDocument.getBookId())));

    BookEntity bookEntity = bookCrudService.findByTitleAndDate(bookDocument.getTitle(), bookDocument.getDate())
        .orElseThrow(() -> new NotFoundException(String.format("BookEntity.title %s and BookEntity.date %s not found", bookDocument.getTitle(), bookDocument.getDate())));

    return CommentEntity.builder().bookEntity(bookEntity).commentary(commentDocument.getCommentary()).build();
  }
}
