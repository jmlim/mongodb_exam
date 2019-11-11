package io.jmlim.mongoex.demo.repository;

import io.jmlim.mongoex.demo.domain.product.Product;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends MongoRepository<Product, ObjectId> {

    Optional<Product> findBySlug(String slug);

    List<Product> findByCategoryIdsIn(List<ObjectId> categoryIds);
}
