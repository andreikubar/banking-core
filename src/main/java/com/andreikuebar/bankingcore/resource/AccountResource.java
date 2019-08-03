package com.andreikuebar.bankingcore.resource;

import com.andreikuebar.bankingcore.model.*;
import com.andreikuebar.bankingcore.repository.AccountRepository;
import com.andreikuebar.bankingcore.repository.TransactionRepository;
import com.andreikuebar.bankingcore.service.AccountBalanceTooSmallException;
import com.andreikuebar.bankingcore.service.AccountNotFoundException;
import com.andreikuebar.bankingcore.service.InvalidAmountException;
import com.andreikuebar.bankingcore.service.SendMoneyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountResource {

    private AccountRepository accountRepository;
    private TransactionRepository transactionRepository;
    private SendMoneyService sendMoneyService;

    public AccountResource(AccountRepository accountRepository, TransactionRepository transactionRepository, SendMoneyService sendMoneyService) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.sendMoneyService = sendMoneyService;
    }

    @GetMapping(value = "/statement/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public AccountStatementResponse accountStatement(@PathVariable(name = "id") String accountNumber){
        Account account = accountRepository.findById(accountNumber).orElseThrow(
                () -> new AccountNotFoundException(MessageFormat.format("No account with number {0} found", accountNumber))
        );
        List<Transaction> transactions = transactionRepository.findAllByAccount(account);

        List<TransactionInfo> transactionInfos = new ArrayList<>();
        transactions.forEach(transaction -> {
            TransactionInfo transactionInfo = new TransactionInfo(transaction.getTransactionId(), transaction.getAmount());
            transactionInfos.add(transactionInfo);
        });

        return new AccountStatementResponse(
                new AccountInfo(account.getAccountNumber(), account.getBalance()),transactionInfos
        );
    }

    @PostMapping(value = "/transfer")
    public ResponseEntity<Object> sendMoney(@RequestBody TransferRequest transferRequest){
        try {
            sendMoneyService.sendMoney(transferRequest.getSourceAccount(),
                    transferRequest.getTargetAccount(), transferRequest.getAmount());
        } catch (AccountNotFoundException | AccountBalanceTooSmallException | InvalidAmountException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        return ResponseEntity.ok().build();
    }
}
