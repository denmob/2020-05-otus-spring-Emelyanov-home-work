package ru.otus.hw14.service;

import ru.otus.hw14.model.table.Book;

public interface ItemBookProcessorService {

  Book convertDocumentToEntity(ru.otus.hw14.model.document.Book book);
}
