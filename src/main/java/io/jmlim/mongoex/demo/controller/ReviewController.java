package io.jmlim.mongoex.demo.controller;

import io.jmlim.mongoex.demo.domain.review.Review;
import io.jmlim.mongoex.demo.domain.review.ReviewDto;
import io.jmlim.mongoex.demo.exception.ApiValidException;
import io.jmlim.mongoex.demo.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/review")
public class ReviewController {

    private final ReviewRepository repository;

    @PostMapping
    public ResponseEntity<Review> create(@Valid @RequestBody ReviewDto reviewDto, Errors errors) {
        if (errors.hasErrors()) {
            log.info("error {} ", errors);
            throw new ApiValidException(errors.getFieldError().getDefaultMessage());
        }

        Review createdReview = repository.save(reviewDto.toEntity());
        return ResponseEntity.ok(createdReview);
    }

    @GetMapping
    public ResponseEntity<List<Review>> getAll() {
        return ResponseEntity.ok(repository.findAll());
    }

    @PutMapping
    public ResponseEntity<Review> update(@Valid @RequestBody ReviewDto reviewDto, Errors errors) {
        if (errors.hasErrors()) {
            log.info("error {} ", errors);
            throw new ApiValidException(errors.getFieldError().getDefaultMessage());
        }

        ObjectId id = reviewDto.getId();
        log.debug("Updating review with id :  {}.", id);
        Optional<Review> optionalReview = repository.findById(id);

        return ResponseEntity.ok(optionalReview.map(c -> {
            Review apply = c.apply(reviewDto);
            return repository.save(apply);
        }).orElseThrow(NoSuchElementException::new));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity delete(@PathVariable ObjectId id) {
        Optional<Review> optReview = repository.findById(id);
        optReview.ifPresent(c -> repository.delete(c));
        optReview.orElseThrow(NoSuchElementException::new);
        return ResponseEntity.ok().build();
    }
}