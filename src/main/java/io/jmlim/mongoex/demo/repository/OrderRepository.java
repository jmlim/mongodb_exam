package io.jmlim.mongoex.demo.repository;

import io.jmlim.mongoex.demo.domain.order.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderRepository extends MongoRepository<Order, String> {
}