package com.andreikuebar.bankingcore.model;

public class TransactionInfo {
    private Long transactionId;
    private Long amount;

    public TransactionInfo(Long transactionId, Long amount) {
        this.transactionId = transactionId;
        this.amount = amount;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }
}
