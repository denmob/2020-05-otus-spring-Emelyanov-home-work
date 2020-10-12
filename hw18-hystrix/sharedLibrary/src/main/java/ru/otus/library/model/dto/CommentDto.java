package ru.otus.library.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.library.model.Comment;

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
