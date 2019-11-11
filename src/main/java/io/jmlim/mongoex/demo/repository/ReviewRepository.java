package io.jmlim.mongoex.demo.repository;

import io.jmlim.mongoex.demo.domain.review.Review;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ReviewRepository extends MongoRepository<Review, ObjectId> {
}