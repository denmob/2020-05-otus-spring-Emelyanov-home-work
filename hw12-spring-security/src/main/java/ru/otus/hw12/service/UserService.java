package ru.otus.hw12.service;

import ru.otus.hw12.model.User;

import java.util.Optional;

public interface UserService {

  Optional<User> findUserByUsername(String userName);
}
