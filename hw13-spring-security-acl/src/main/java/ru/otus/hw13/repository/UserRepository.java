package ru.otus.hw13.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.hw13.model.User;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User,String>{

  Optional<User> findUserByUsername(String userName);
}
