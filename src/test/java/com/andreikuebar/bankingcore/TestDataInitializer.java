package com.andreikuebar.bankingcore;

import com.andreikuebar.bankingcore.model.Account;
import com.andreikuebar.bankingcore.repository.AccountRepository;
import com.andreikuebar.bankingcore.repository.TransactionRepository;

public class TestDataInitializer {
    public static final String SOURCE_ACCOUNT_NUMBER = "1";
    public static final String TARGET_ACCOUNT_NUMBER = "2";
    public static final String NON_EXISTENT_ACCOUNT_NUMBER = "3";
    public static final Long AMOUNT_1 = 1000L;
    public static final Long AMOUNT_2 = 2000L;

    private AccountRepository accountRepository;
    private TransactionRepository transactionRepository;

    public TestDataInitializer(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    public void createInitialAccounts(){

        Account account1 = new Account();
        account1.setAccountNumber(SOURCE_ACCOUNT_NUMBER);
        account1.setBalance(AMOUNT_1);

        Account account2 = new Account();
        account2.setAccountNumber(TARGET_ACCOUNT_NUMBER);
        account2.setBalance(AMOUNT_2);

        accountRepository.save(account1);
        accountRepository.save(account2);
    }

    public void cleanUp() {
        transactionRepository.deleteAllInBatch();
        accountRepository.deleteAllInBatch();
    }


}
