package com.andreikuebar.bankingdemo.repository;

import com.andreikuebar.bankingdemo.model.Account;
import com.andreikuebar.bankingdemo.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findAllByAccount(Account account);
}
