package com.andreikuebar.bankingcore.application;

import com.andreikuebar.bankingcore.repository.AccountRepository;
import com.andreikuebar.bankingcore.repository.TransactionRepository;
import com.andreikuebar.bankingcore.resource.AccountResource;
import com.andreikuebar.bankingcore.service.SendMoneyService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableJpaRepositories("com.andreikuebar.bankingcore.repository")
@EntityScan("com.andreikuebar.bankingcore.model")
public class BankingCoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankingCoreApplication.class, args);
	}

	@Bean
    public SendMoneyService sendMoneyService(AccountRepository accountRepository,
                                             TransactionRepository transactionRepository){
	    return new SendMoneyService(accountRepository, transactionRepository);
    }

    @Bean
	public AccountResource accountResource(AccountRepository accountRepository,
										   TransactionRepository transactionRepository,
										   SendMoneyService sendMoneyService){
		return new AccountResource(accountRepository, transactionRepository, sendMoneyService);
	}

    @Profile("!test")
	@Bean
    public DatabaseInitializer databaseInitializer(AccountRepository accountRepository){
		return new DatabaseInitializer(accountRepository);
	}

}
