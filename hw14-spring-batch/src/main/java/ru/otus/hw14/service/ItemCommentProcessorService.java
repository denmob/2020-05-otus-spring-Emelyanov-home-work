package ru.otus.hw14.service;

import ru.otus.hw14.model.document.CommentDocument;
import ru.otus.hw14.model.entity.CommentEntity;

public interface ItemCommentProcessorService {

  CommentEntity convertDocumentToEntity(CommentDocument commentDocument);
}
