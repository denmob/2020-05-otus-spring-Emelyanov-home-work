package ru.otus.hw14.service;

import ru.otus.hw14.model.table.Comment;

public interface ItemCommentProcessorService {

  Comment convertDocumentToEntity(ru.otus.hw14.model.document.Comment comment);
}
