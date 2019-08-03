package com.andreikuebar.bankingdemo.repository;

import com.andreikuebar.bankingdemo.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import javax.persistence.LockModeType;
import java.util.List;

public interface AccountRepository extends JpaRepository<Account, String> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<Account> findByAccountNumberIsIn(List<String> accountNumbers);
}
