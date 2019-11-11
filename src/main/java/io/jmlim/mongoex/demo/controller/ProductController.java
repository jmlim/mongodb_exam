package io.jmlim.mongoex.demo.controller;

import io.jmlim.mongoex.demo.domain.product.Product;
import io.jmlim.mongoex.demo.domain.product.ProductDto;
import io.jmlim.mongoex.demo.exception.ApiValidException;
import io.jmlim.mongoex.demo.repository.ProductRepository;
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
@RequestMapping("/product")
public class ProductController {

    private final ProductRepository repository;

    @PostMapping
    public ResponseEntity<Product> create(@Valid @RequestBody ProductDto productDto, Errors errors) {
        if (errors.hasErrors()) {
            log.info("error {} ", errors);
            throw new ApiValidException(errors.getFieldError().getDefaultMessage());
        }

        Product createdProduct = repository.save(productDto.toEntity());
        return ResponseEntity.ok(createdProduct);
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAll() {
        return ResponseEntity.ok(repository.findAll());
    }

    @PutMapping
    public ResponseEntity<Product> update(@Valid @RequestBody ProductDto productDto, Errors errors) {
        if (errors.hasErrors()) {
            log.info("error {} ", errors);
            throw new ApiValidException(errors.getFieldError().getDefaultMessage());
        }

        String slug = productDto.getSlug();
        log.debug("Updating product with slug :  {}.", slug);
        Optional<Product> optionalProduct = repository.findBySlug(slug);

        return ResponseEntity.ok(optionalProduct.map(c -> {
            Product apply = c.apply(productDto);
            return repository.save(apply);
        }).orElseThrow(NoSuchElementException::new));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity delete(@PathVariable ObjectId id) {
        Optional<Product> optProduct = repository.findById(id);
        optProduct.ifPresent(c -> repository.delete(c));
        optProduct.orElseThrow(NoSuchElementException::new);
        return ResponseEntity.ok().build();
    }

/*    @PutMapping("/add_price_history/{id}")
    public PriceHistory addPriceHistory(@PathVariable ObjectId id, @Valid @RequestBody PriceHistory newPriceHistory) {
        if (Objects.isNull(newPriceHistory)) {
            throw new NullPointerException("newPriceHistory is null");
        }

        log.debug("Updating updatePriceHistory with id :  {}.", id);
        Optional<Product> opt = repository.findById(id);
        opt.orElseThrow(NoSuchElementException::new);

        opt.ifPresent(existData -> {

            List<PriceHistory> priceHistories = existData.getPriceHistory();
            if (Objects.isNull(priceHistories)) {
                priceHistories = new ArrayList<>();
            }
            priceHistories.add(newPriceHistory);
            repository.save(existData);
        });

        return newPriceHistory;
    }*/
}