package io.jmlim.mongoex.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/account")
public class AccountController {

    /*private final AccountRepository repository;

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
    }*/
}