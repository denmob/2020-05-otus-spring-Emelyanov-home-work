package ru.otus.hw13.security.acls.domain;

import lombok.*;
import org.springframework.security.acls.model.Sid;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class MongoSid implements Sid {

  private String id;

  private String name;

  private boolean isPrincipal;
}
