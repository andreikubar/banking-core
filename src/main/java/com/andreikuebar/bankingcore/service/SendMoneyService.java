package com.andreikuebar.bankingcore.service;

import com.andreikuebar.bankingcore.model.Account;
import com.andreikuebar.bankingcore.model.Transaction;
import com.andreikuebar.bankingcore.repository.AccountRepository;
import com.andreikuebar.bankingcore.repository.TransactionRepository;
import com.google.common.collect.ImmutableList;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;

public class SendMoneyService {

    private AccountRepository accountRepository;
    private TransactionRepository transactionRepository;

    public SendMoneyService(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    @Transactional
    public void sendMoney(String sourceAccountNumber, String targetAccountNumber, Long amount){
        Objects.requireNonNull(sourceAccountNumber, "Source AccountNumber must not be null");
        Objects.requireNonNull(targetAccountNumber, "Target AccountNumber must not be null");
        if (amount <= 0){
            throw new InvalidAmountException("Amount must be a positive number");
        }

        List<Account> transferAccounts = accountRepository.findByAccountNumberIsIn(
                ImmutableList.of(sourceAccountNumber, targetAccountNumber));

        Account sourceAccount = transferAccounts.stream()
                .filter(x -> x.getAccountNumber().equals(sourceAccountNumber))
                .findAny().orElseThrow(() ->
                        new AccountNotFoundException(MessageFormat.format("Account {0} not found", sourceAccountNumber)));

        Account targetAccount = transferAccounts.stream()
                .filter(x -> x.getAccountNumber().equals(targetAccountNumber))
                .findAny().orElseThrow(() ->
                        new AccountNotFoundException(MessageFormat.format("Account {0} not found", targetAccountNumber)));

        if (sourceAccount.getBalance() < amount){
            throw new AccountBalanceTooSmallException(MessageFormat.format("Account {0} has not enough funds",
                    sourceAccount.getAccountNumber()));
        }

        transactionRepository.save(new Transaction(sourceAccount, -amount));
        transactionRepository.save(new Transaction(targetAccount, amount));

        sourceAccount.setBalance(sourceAccount.getBalance() - amount);
        targetAccount.setBalance(targetAccount.getBalance() + amount);

        accountRepository.save(sourceAccount);
        accountRepository.save(targetAccount);

    }
}
