package com.andreikuebar.bankingcore;

import com.andreikuebar.bankingcore.application.BankingCoreApplication;
import com.andreikuebar.bankingcore.model.Account;
import com.andreikuebar.bankingcore.repository.AccountRepository;
import com.andreikuebar.bankingcore.service.AccountNotFoundException;
import com.andreikuebar.bankingcore.service.InvalidAmountException;
import com.andreikuebar.bankingcore.service.SendMoneyService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static com.andreikuebar.bankingcore.TestDataInitializer.*;

@SpringBootTest(classes = {BankingCoreApplication.class, TestConfiguration.class})
@ActiveProfiles("test")
public class BankingCoreApplicationTests {

    @Autowired
    private SendMoneyService sendMoneyService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TestDataInitializer testDataInitializer;

    @Test
    public void sendMoneyOnce() {
        Long transferAmount = 100L;
        sendMoneyService.sendMoney(SOURCE_ACCOUNT_NUMBER, TARGET_ACCOUNT_NUMBER, transferAmount);
        Account account1 = accountRepository.findById(SOURCE_ACCOUNT_NUMBER).get();
        Account account2 = accountRepository.findById(TARGET_ACCOUNT_NUMBER).get();
        Assertions.assertEquals(account1.getBalance().longValue(), AMOUNT_1 - transferAmount);
        Assertions.assertEquals(account2.getBalance().longValue(), AMOUNT_2 + transferAmount);
    }

    @Test
    public void nonExistentAccountTransferAttempt() {
        Assertions.assertThrows(AccountNotFoundException.class,
                () -> sendMoneyService.sendMoney(NON_EXISTENT_ACCOUNT_NUMBER, TARGET_ACCOUNT_NUMBER, 100L));
        Assertions.assertThrows(AccountNotFoundException.class,
                () -> sendMoneyService.sendMoney(SOURCE_ACCOUNT_NUMBER, NON_EXISTENT_ACCOUNT_NUMBER, 100L));

    }

    @Test
    public void invalidAmountAttempt() {
        Assertions.assertThrows(InvalidAmountException.class,
                () -> sendMoneyService.sendMoney(SOURCE_ACCOUNT_NUMBER, TARGET_ACCOUNT_NUMBER, -1L));
        Assertions.assertThrows(InvalidAmountException.class,
                () -> sendMoneyService.sendMoney(SOURCE_ACCOUNT_NUMBER, TARGET_ACCOUNT_NUMBER, 0L));
    }

    @BeforeEach
    private void createTestAccounts() {
        testDataInitializer.createInitialAccounts();
    }

    @AfterEach
    private void cleanUp() {
        testDataInitializer.cleanUp();
    }

}
