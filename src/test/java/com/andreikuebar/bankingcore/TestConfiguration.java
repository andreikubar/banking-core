package com.andreikuebar.bankingcore;

import com.andreikuebar.bankingcore.repository.AccountRepository;
import com.andreikuebar.bankingcore.repository.TransactionRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConfiguration {

    @Bean
    public TestDataInitializer testDataInitializer(AccountRepository accountRepository,
                                                   TransactionRepository transactionRepository){
        return new TestDataInitializer(accountRepository, transactionRepository);
    }
}
