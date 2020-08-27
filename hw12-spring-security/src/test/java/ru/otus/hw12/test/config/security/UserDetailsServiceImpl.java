package ru.otus.hw12.test.config.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.otus.hw12.model.User;
import ru.otus.hw12.security.model.SecurityUserDetails;

import java.util.Arrays;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  private final List<SecurityUserDetails> users;

  public UserDetailsServiceImpl() {
    User user = User.builder().username("user").password("123").role("ROLE_USER").build();
    User test = User.builder().username("test").password("9999").role("ROLE_TEST").build();
    User admin = User.builder().username("admin").password("456").role("ROLE_ADMIN").build();
    users = Arrays.asList(new SecurityUserDetails(user), new SecurityUserDetails(admin), new SecurityUserDetails(test));
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    for (SecurityUserDetails securityUserDetails : users) {
      if (securityUserDetails.getUser().getUsername().equals(username)) {
        return securityUserDetails;
      }
    }
    return new SecurityUserDetails(new User());
  }
}
