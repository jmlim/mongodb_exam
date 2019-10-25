package io.jmlim.mongoex.demo.repository;

import io.jmlim.mongoex.demo.domain.product.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ProductRepository extends MongoRepository<Product, String> {

    Optional<Product> findBySlug(String slug);
}
