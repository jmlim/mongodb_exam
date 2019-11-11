package io.jmlim.mongoex.demo.controller;

import io.jmlim.mongoex.demo.domain.user.User;
import io.jmlim.mongoex.demo.domain.user.UserDto;
import io.jmlim.mongoex.demo.exception.ApiValidException;
import io.jmlim.mongoex.demo.repository.UserRepository;
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
@RequestMapping("/user")
public class UserController {

    private final UserRepository repository;

    @PostMapping
    public ResponseEntity<User> create(@Valid @RequestBody UserDto userDto, Errors errors) {
        if (errors.hasErrors()) {
            log.info("error {} ", errors);
            throw new ApiValidException(errors.getFieldError().getDefaultMessage());
        }

        User createdUser = repository.save(userDto.toEntity());
        return ResponseEntity.ok(createdUser);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAll() {
        return ResponseEntity.ok(repository.findAll());
    }

    @PutMapping
    public ResponseEntity<User> update(@Valid @RequestBody UserDto userDto, Errors errors) {
        if (errors.hasErrors()) {
            log.info("error {} ", errors);
            throw new ApiValidException(errors.getFieldError().getDefaultMessage());
        }

        ObjectId id = userDto.getId();
        log.debug("Updating user with id :  {}.", id);
        Optional<User> optionalUser = repository.findById(id);

        return ResponseEntity.ok(optionalUser.map(c -> {
            User apply = c.apply(userDto);
            return repository.save(apply);
        }).orElseThrow(NoSuchElementException::new));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity delete(@PathVariable ObjectId id) {
        Optional<User> optUser = repository.findById(id);
        optUser.ifPresent(c -> repository.delete(c));
        optUser.orElseThrow(NoSuchElementException::new);
        return ResponseEntity.ok().build();
    }
}