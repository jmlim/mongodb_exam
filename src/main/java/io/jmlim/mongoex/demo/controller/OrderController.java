package io.jmlim.mongoex.demo.controller;


import io.jmlim.mongoex.demo.domain.order.Order;
import io.jmlim.mongoex.demo.domain.order.OrderDto;
import io.jmlim.mongoex.demo.exception.ApiValidException;
import io.jmlim.mongoex.demo.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderRepository repository;

    @PostMapping
    public ResponseEntity<Order> create(@Valid @RequestBody OrderDto orderDto, Errors errors) {
        if (errors.hasErrors()) {
            log.info("error {} ", errors);
            throw new ApiValidException(errors.getFieldError().getDefaultMessage());
        }

        Order createdOrder = repository.save(orderDto.toEntity());
        return ResponseEntity.ok(createdOrder);
    }

    @GetMapping
    public ResponseEntity<Page<Order>> getAll(@PageableDefault(page = 1, size = 10) Pageable pageable) {
        return ResponseEntity.ok(repository.findAll(pageable));
    }

    @PutMapping
    public ResponseEntity<Order> update(@Valid @RequestBody OrderDto orderDto, Errors errors) {
        if (errors.hasErrors()) {
            log.info("error {} ", errors);
            throw new ApiValidException(errors.getFieldError().getDefaultMessage());
        }

        ObjectId id = orderDto.getId();
        log.debug("Updating order with id :  {}.", id);
        Optional<Order> optOrder = repository.findById(id);

        return ResponseEntity.ok(optOrder.map(order -> {
            Order apply = order.apply(orderDto);
            return repository.save(apply);
        }).orElseThrow(NoSuchElementException::new));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity delete(@PathVariable ObjectId id) {
        Optional<Order> optOrder = repository.findById(id);
        optOrder.ifPresent(c -> repository.delete(c));
        optOrder.orElseThrow(NoSuchElementException::new);
        return ResponseEntity.ok().build();
    }
}