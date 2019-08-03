package com.andreikuebar.bankingcore.repository;

import com.andreikuebar.bankingcore.model.Account;
import com.andreikuebar.bankingcore.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findAllByAccount(Account account);
}
