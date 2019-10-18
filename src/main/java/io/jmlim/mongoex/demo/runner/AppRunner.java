package io.jmlim.mongoex.demo.runner;

import io.jmlim.mongoex.demo.domain.Account;
import io.jmlim.mongoex.demo.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * 실행 후
 * > db
 * test
 * > use test
 * switched to db test
 * > db.accounts.find({}) 로 확인해보기.
 **/

@Component
@RequiredArgsConstructor
public class AppRunner implements ApplicationRunner {

    //private final MongoTemplate mongoTemplate;

    private final AccountRepository accountRepository;


    @Override
    public void run(ApplicationArguments args) {

        Optional<Account> optAccount = accountRepository.findByEmail("hackerljm@gmail.com");

        if (!optAccount.isPresent()) {
            Account account = optAccount.orElse(Account.builder().email("hackerljm@gmail.com").username("림정묵").build());
            accountRepository.save(account);
        }
    }
}

