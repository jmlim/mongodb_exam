package io.jmlim.mongoex.demo.repository;

import io.jmlim.mongoex.demo.domain.category.Category;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CategoryRepository extends MongoRepository<Category, ObjectId> {

    Optional<Category> findBySlug(String slug);
}