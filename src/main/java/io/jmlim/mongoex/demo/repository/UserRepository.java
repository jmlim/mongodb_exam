package io.jmlim.mongoex.demo.repository;

import io.jmlim.mongoex.demo.domain.user.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, ObjectId> {


    Optional<User> findByUsername(String username);
}