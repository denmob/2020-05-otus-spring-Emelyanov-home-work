package ru.otus.hw11.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.hw11.model.Comment;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {

  private String id;

  private String commentary;

  private String bookId;

  private Date timestamp;

  public static CommentDto toDto(Comment comment) {
    return new CommentDto(comment.getId(), comment.getCommentary(), comment.getBookId(), comment.getTimestamp());
  }
}
