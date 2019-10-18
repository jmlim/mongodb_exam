package io.jmlim.mongoex.demo.controller;

import io.jmlim.mongoex.demo.domain.Account;
import io.jmlim.mongoex.demo.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/account")
public class AccountController {

    private final AccountRepository repository;

    @GetMapping
    public List<Account> getAllAccounts() {
        return repository.findAll();
    }

    @GetMapping(value = "/{id}")
    public Account getAccountById(@PathVariable("id") String id) {
        Optional<Account> optAccount = repository.findById(id);
        return optAccount.orElse(new Account());
    }

    @PutMapping(value = "/{id}")
    public Account modifyAccountById(@PathVariable("id") String id, @Valid @RequestBody Account account) {
        account.setId(id);
        Account updatedAccount = repository.save(account);
        return updatedAccount;
    }

    @PostMapping
    public Account createAccount(@Valid @RequestBody Account account) {
        Account createdAccount = repository.save(account);
        return createdAccount;
    }

    @DeleteMapping(value = "/{id}")
    public void deletePet(@PathVariable String id) {
        Optional<Account> optAccount = repository.findById(id);
        optAccount.ifPresent(account -> repository.delete(account));
        optAccount.orElseThrow(NoSuchElementException::new);
    }
}