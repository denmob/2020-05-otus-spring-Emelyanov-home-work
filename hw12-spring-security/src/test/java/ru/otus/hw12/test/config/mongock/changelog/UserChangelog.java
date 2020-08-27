package ru.otus.hw12.test.config.mongock.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.hw12.model.User;

@ChangeLog(order = "005")
public class UserChangelog {

  @ChangeSet(order = "000", id = "dropUsers", author = "dyemelianov", runAlways = true)
  public void dropUser(MongoTemplate template) {
    template.dropCollection("users");
  }

  @ChangeSet(order = "001", id = "addUser", author = "dyemelianov", runAlways = true)
  public void addUser(MongoTemplate template) {
    var user = User.builder().username("user").password("123").role("ROLE_USER").build();
    template.save(user);
  }

  @ChangeSet(order = "002", id = "addAdmin", author = "dyemelianov", runAlways = true)
  public void addAdmin(MongoTemplate template) {
    var genre = User.builder().username("admin").password("456").role("ROLE_ADMIN").build();
    template.save(genre);
  }
}
