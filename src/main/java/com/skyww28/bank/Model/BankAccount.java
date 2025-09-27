package com.skyww28.bank.Model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "bank_accounts")
public class BankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique account id", example = "1")
    private Long id;

    @Column(unique = true, nullable = false)
    @Schema(description = "Count number", example = "1234567890123456789")
    private String accountNumber;

    @Column(nullable = false)
    @Schema(description = "Count balance", example = "1000.00")
    private BigDecimal balance = BigDecimal.ZERO;

    @Column(nullable = false)
    @Schema(description = "Count currency", example = "RUB")
    private String currency;

    @Column(nullable = false)
    @Schema(description = "Count creation date", example = "2025-09-24 00:20:20.366662")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = false)
    @Schema(description = "Count active/inactive", example = "1")
    private boolean active = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @Schema(description = "Unique user account", example = "5")
    private User user;


    public BankAccount(){}

    public BankAccount(String accountNumber, User user, String currency) {
        this.accountNumber = accountNumber;
        this.user = user;
        this.currency = currency;
        this.balance = BigDecimal.ZERO;
        this.createdAt = LocalDateTime.now();
        this.active = true;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String accountNumber) {this.accountNumber = accountNumber; }

    public BigDecimal getBalance() { return balance; }
    public void setBalance(BigDecimal balance) { this.balance = balance; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}
