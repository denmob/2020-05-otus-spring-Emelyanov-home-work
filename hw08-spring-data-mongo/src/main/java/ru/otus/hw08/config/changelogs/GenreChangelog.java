package ru.otus.hw08.config.changelogs;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.hw08.core.models.Genre;

@ChangeLog(order = "002")
public class GenreChangelog {

  @ChangeSet(order = "000", id = "dropGenres", author = "dyemelianov", runAlways = true)
  public void dropGenres(MongoTemplate template) {
    template.dropCollection("genres");
  }

  @ChangeSet(order = "001", id = "addGenre01", author = "dyemelianov", runAlways = true)
  public void addGenre01(MongoTemplate template) {
    var genre = Genre.builder().name("Programming").build();
    template.save(genre);
  }

  @ChangeSet(order = "002", id = "addGenre02", author = "dyemelianov", runAlways = true)
  public void addGenre02(MongoTemplate template) {
    var genre = Genre.builder().name("Science").build();
    template.save(genre);
  }

  @ChangeSet(order = "003", id = "addGenre03", author = "dyemelianov", runAlways = true)
  public void addGenre03(MongoTemplate template) {
    var genre = Genre.builder().name("Software").build();
    template.save(genre);
  }
}
