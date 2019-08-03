package com.andreikuebar.bankingcore.repository;

import com.andreikuebar.bankingcore.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import javax.persistence.LockModeType;
import java.util.List;

public interface AccountRepository extends JpaRepository<Account, String> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<Account> findByAccountNumberIsIn(List<String> accountNumbers);
}
