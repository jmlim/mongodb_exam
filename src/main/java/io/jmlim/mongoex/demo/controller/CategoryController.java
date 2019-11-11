package io.jmlim.mongoex.demo.controller;

import io.jmlim.mongoex.demo.domain.category.Category;
import io.jmlim.mongoex.demo.domain.category.CategoryDto;
import io.jmlim.mongoex.demo.exception.ApiValidException;
import io.jmlim.mongoex.demo.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping("/category")
public class CategoryController {

    private final CategoryRepository repository;

    @PostMapping
    public ResponseEntity<Category> create(@Valid @RequestBody CategoryDto categoryDto, Errors errors) {
        if (errors.hasErrors()) {
            log.info("error {} ", errors);
            throw new ApiValidException(errors.getFieldError().getDefaultMessage());
        }

        Category createdCategory = repository.save(categoryDto.toEntity());
        return ResponseEntity.ok(createdCategory);
    }

    @GetMapping
    public ResponseEntity<List<Category>> getAll() {
        return ResponseEntity.ok(repository.findAll());
    }

    @PutMapping
    public ResponseEntity<Category> update(@Valid @RequestBody CategoryDto categoryDto, Errors errors) {
        if (errors.hasErrors()) {
            log.info("error {} ", errors);
            throw new ApiValidException(errors.getFieldError().getDefaultMessage());
        }

        String slug = categoryDto.getSlug();
        log.debug("Updating category with slug :  {}.", slug);
        Optional<Category> optCategory = repository.findBySlug(slug);

        return ResponseEntity.ok(optCategory.map(c -> {
            Category apply = c.apply(categoryDto);
            return repository.save(apply);
        }).orElseThrow(NoSuchElementException::new));
    }

    @DeleteMapping(value = "/{slug}")
    public ResponseEntity delete(@PathVariable String slug) {
        Optional<Category> optCategory = repository.findBySlug(slug);
        optCategory.ifPresent(c -> repository.delete(c));
        optCategory.orElseThrow(NoSuchElementException::new);
        return ResponseEntity.ok().build();
    }
}