package ru.otus.hw14.service;

import ru.otus.hw14.model.document.BookDocument;
import ru.otus.hw14.model.entity.BookEntity;

public interface ItemBookProcessorService {

  BookEntity convertDocumentToEntity(BookDocument bookDocument);
}
