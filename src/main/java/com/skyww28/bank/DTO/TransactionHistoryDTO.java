package com.skyww28.bank.DTO;

import com.skyww28.bank.Model.Transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionHistoryDTO {
    private Long id;
    private BigDecimal amount;
    private String type;
    private String description;
    private LocalDateTime timestamp;
    private String fromAccountNumber;
    private String toAccountNumber;

    public TransactionHistoryDTO(Transaction transaction) {
        this.id = transaction.getId();
        this.amount = transaction.getAmount();
        this.type = transaction.getType();
        this.description = transaction.getDescription();
        this.timestamp = transaction.getTimestamp();
        this.fromAccountNumber = transaction.getFromAccount() != null ?
                transaction.getFromAccount().getAccountNumber() : null;
        this.toAccountNumber = transaction.getToAccount() != null ?
                transaction.getToAccount().getAccountNumber() : null;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public BigDecimal getAmount() {return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    public String getFromAccountNumber() { return fromAccountNumber; }
    public void setFromAccountNumber(String fromAccountNumber) { this.fromAccountNumber = fromAccountNumber; }

    public String getToAccountNumber() { return toAccountNumber; }
    public void setToAccountNumber(String toAccountNumber) { this.toAccountNumber = toAccountNumber; }
}
