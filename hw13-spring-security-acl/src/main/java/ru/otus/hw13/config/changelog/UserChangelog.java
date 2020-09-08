package ru.otus.hw13.config.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.github.cloudyrock.mongock.driver.mongodb.springdata.v3.decorator.impl.MongockTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.otus.hw13.model.User;

@ChangeLog(order = "005")
public class UserChangelog {

  @ChangeSet(order = "000", id = "dropUsers", author = "dyemelianov", runAlways = true)
  public void dropUser(MongockTemplate template) {
    template.dropCollection("users");
  }

  @ChangeSet(order = "001", id = "addUser", author = "dyemelianov", runAlways = true)
  public void addUser(MongockTemplate template, PasswordEncoder bCryptPasswordEncoder) {
    var user = User.builder().username("user").password(bCryptPasswordEncoder.encode("123")).role("ROLE_USER").build();
    template.save(user);
  }

  @ChangeSet(order = "002", id = "addAdmin", author = "dyemelianov", runAlways = true)
  public void addAdmin(MongockTemplate template, PasswordEncoder bCryptPasswordEncoder) {
    var admin = User.builder().username("admin").password(bCryptPasswordEncoder.encode("456")).role("ROLE_ADMIN").build();
    template.save(admin);
  }

  @ChangeSet(order = "003", id = "addTest", author = "dyemelianov", runAlways = true)
  public void addTestAcl(MongockTemplate template, PasswordEncoder bCryptPasswordEncoder) {
    var test = User.builder().username("test").password(bCryptPasswordEncoder.encode("789")).role("ROLE_ACL").build();
    template.save(test);
  }
}
