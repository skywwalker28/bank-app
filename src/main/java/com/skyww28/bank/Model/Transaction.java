package com.skyww28.bank.Model;

import jakarta.persistence.*;


import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal amount;
    private String type;
    private String description;
    private LocalDateTime timestamp;

    @ManyToOne
    private BankAccount fromAccount;

    @ManyToOne
    private BankAccount toAccount;

    public Transaction(){}

    public Transaction(BigDecimal amount, String type, String description, LocalDateTime timestamp,
                       BankAccount fromAccount, BankAccount toAccount) {
        this.amount = amount;
        this.type = type;
        this.description = description;
        this.timestamp = timestamp;
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    public BankAccount getFromAccount() { return fromAccount; }
    public void setFromAccount(BankAccount fromAccount) { this.fromAccount = fromAccount; }

    public BankAccount getToAccount() { return toAccount; }
    public void setToAccount( BankAccount toAccount) { this.toAccount = toAccount; }
}
