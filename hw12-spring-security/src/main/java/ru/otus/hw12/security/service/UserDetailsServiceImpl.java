package ru.otus.hw12.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.otus.hw12.model.User;
import ru.otus.hw12.security.model.SecurityUserDetails;
import ru.otus.hw12.service.UserService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

  private final UserService userService;

  @Override
  public UserDetails loadUserByUsername(String username) {
    Optional<User> optionalUser = userService.findUserByUsername(username);
    return optionalUser.map(SecurityUserDetails::new).orElseThrow(() -> new UsernameNotFoundException(String.format("User %s not found", username)));
  }
}
