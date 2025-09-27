package com.skyww28.bank.DTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import com.skyww28.bank.Model.BankAccount;

public class BankAccountDTO {
    private Long id;
    private String accountNumber;
    private BigDecimal balance;
    private String currency;
    private LocalDateTime createdAt;
    private boolean active;

    public BankAccountDTO(Long id, String accountNumber, BigDecimal balance,
                          String currency, LocalDateTime createAt, boolean active) {
        this.id = id;
        this.accountNumber =accountNumber;
        this.balance = balance;
        this.currency = currency;
        this.createdAt = createAt;
        this.active = active;
    }

    public BankAccountDTO(BankAccount account) {
        this.id = account.getId();
        this.accountNumber = account.getAccountNumber();
        this.balance = account.getBalance();
        this.currency = account.getCurrency();
        this.createdAt = account.getCreatedAt();
        this.active = account.isActive();
    }




    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }

    public BigDecimal getBalance() { return balance; }
    public void setBalance(BigDecimal balance) { this.balance = balance; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}
