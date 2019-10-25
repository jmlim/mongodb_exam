package io.jmlim.mongoex.demo;

import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

@DataMongoTest
public class SpringBootMongodbApplicationTests {

   /* @Autowired
    AccountRepository accountRepository;

    @Test
    public void findByEmail() {
        Account account = Account.builder().email("hackerljm@gmail.com").username("림정묵").build();

        accountRepository.save(account);

        Optional<Account> byId = accountRepository.findById(account.getId());
        assertThat(byId).isNotEmpty();
        Optional<Account> byEmail = accountRepository.findByEmail(account.getEmail());
        assertThat(byEmail).isNotEmpty();
        assertThat(byEmail.get().getUsername()).isEqualTo("림정묵");
    }*/
}