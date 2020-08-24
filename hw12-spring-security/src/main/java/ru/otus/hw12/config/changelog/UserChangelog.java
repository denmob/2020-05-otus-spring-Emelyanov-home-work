package ru.otus.hw12.config.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.otus.hw12.model.User;

@ChangeLog(order = "005")
public class UserChangelog {

  private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

  @ChangeSet(order = "000", id = "dropUsers", author = "dyemelianov", runAlways = true)
  public void dropUser(MongoTemplate template) {
    template.dropCollection("users");
  }

  @ChangeSet(order = "001", id = "addUser", author = "dyemelianov", runAlways = true)
  public void addUser(MongoTemplate template) {
    var user = User.builder().username("user").password(bCryptPasswordEncoder.encode("12345")).role("USER_ROLE").build();
    template.save(user);
  }

  @ChangeSet(order = "002", id = "addAdmin", author = "dyemelianov", runAlways = true)
  public void addAdmin(MongoTemplate template) {
    var genre = User.builder().username("admin").password(bCryptPasswordEncoder.encode("67890")).role("ADMIN_ROLE").build();
    template.save(genre);
  }
}
