package com.andreikuebar.bankingdemo.model;

import java.util.List;

public class AccountStatementResponse {
    private AccountInfo accountInfo;
    private List<TransactionInfo> transactions;

    public AccountStatementResponse(AccountInfo accountInfo, List<TransactionInfo> transactions) {
        this.accountInfo = accountInfo;
        this.transactions = transactions;
    }

    public AccountInfo getAccountInfo() {
        return accountInfo;
    }

    public void setAccountInfo(AccountInfo accountInfo) {
        this.accountInfo = accountInfo;
    }

    public List<TransactionInfo> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<TransactionInfo> transactions) {
        this.transactions = transactions;
    }
}
