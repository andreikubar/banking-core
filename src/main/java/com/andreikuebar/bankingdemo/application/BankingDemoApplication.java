package com.andreikuebar.bankingdemo.application;

import com.andreikuebar.bankingdemo.repository.AccountRepository;
import com.andreikuebar.bankingdemo.repository.TransactionRepository;
import com.andreikuebar.bankingdemo.resource.AccountResource;
import com.andreikuebar.bankingdemo.service.SendMoneyService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableJpaRepositories("com.andreikuebar.bankingdemo.repository")
@EntityScan("com.andreikuebar.bankingdemo.model")
public class BankingDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(BankingDemoApplication.class, args);
    }

    @Bean
    public SendMoneyService sendMoneyService(AccountRepository accountRepository,
                                             TransactionRepository transactionRepository) {
        return new SendMoneyService(accountRepository, transactionRepository);
    }

    @Bean
    public AccountResource accountResource(AccountRepository accountRepository,
                                           TransactionRepository transactionRepository,
                                           SendMoneyService sendMoneyService) {
        return new AccountResource(accountRepository, transactionRepository, sendMoneyService);
    }

    @Profile("!test")
    @Bean
    public DatabaseInitializer databaseInitializer(AccountRepository accountRepository) {
        return new DatabaseInitializer(accountRepository);
    }

}
