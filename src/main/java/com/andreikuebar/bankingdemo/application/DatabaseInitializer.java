package com.andreikuebar.bankingdemo.application;

import com.andreikuebar.bankingdemo.model.Account;
import com.andreikuebar.bankingdemo.repository.AccountRepository;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

public class DatabaseInitializer {

    private AccountRepository accountRepository;

    public DatabaseInitializer(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        Account account = new Account();
        account.setAccountNumber("1");
        account.setBalance(1000L);
        accountRepository.save(account);
        account = new Account();
        account.setAccountNumber("2");
        account.setBalance(2000L);
        accountRepository.save(account);
    }
}
