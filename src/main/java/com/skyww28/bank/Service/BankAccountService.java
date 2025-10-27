package com.skyww28.bank.Service;

import com.skyww28.bank.DTO.BankAccountDTO;
import com.skyww28.bank.Exception.AccountNotFoundException;
import com.skyww28.bank.Exception.InactiveAccountException;
import com.skyww28.bank.Exception.InsufficientFundsException;
import com.skyww28.bank.Exception.InvalidTransferException;
import com.skyww28.bank.Model.BankAccount;
import com.skyww28.bank.Model.Transaction;
import com.skyww28.bank.Model.User;
import com.skyww28.bank.Repository.BankAccountRepository;
import com.skyww28.bank.Repository.BankAccountTransactions;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
public class BankAccountService {

    @Autowired
    public BankAccountRepository bankAccountRepository;

    @Autowired
    public BankAccountTransactions bankAccountTransactions;

    @Autowired
    public CurrencyConverterService currencyConverterService;

    public BankAccount createAccount(User user, String currency) {
        String accountNumber = generateAccountNumber();

        while (bankAccountRepository.existsByAccountNumber(accountNumber)) {
            accountNumber = generateAccountNumber();
        }

        BankAccount account = new BankAccount(accountNumber, user, currency);
        return bankAccountRepository.save(account);
    }


    public List<BankAccountDTO> getUserAccounts(User user) {
        List<BankAccount> accounts = bankAccountRepository.findByUserAndActiveTrue(user);
        return BankAccountDTO.fromEntityList(accounts);
    }


    public BankAccount getAccountById(Long accountId) {
        return bankAccountRepository.findById(accountId).
                orElseThrow(() -> new AccountNotFoundException("Account not found"));
    }

    @Transactional
    public BankAccount deposit(Long accountId, BigDecimal amount) {
        BankAccount account = getAccountById(accountId);
        account.setBalance(account.getBalance().add(amount));

        Transaction transactional = new Transaction(amount, "DEPOSIT",
                "Cash deposit", LocalDateTime.now(), null, account);

        bankAccountTransactions.save(transactional);
        return bankAccountRepository.save(account);
    }

    @Transactional
    public BankAccount withdraw(Long accountId, BigDecimal amount) {
        BankAccount account = getAccountById(accountId);

        if (account.getBalance().compareTo(amount) < 0) {
            throw new InsufficientFundsException("Not enough money on account");
        }

        account.setBalance(account.getBalance().subtract(amount));

        Transaction transaction = new Transaction(amount.negate(), "WITHDRAWAL",
                "Cash withdrawal", LocalDateTime.now(), account, null);
        bankAccountTransactions.save(transaction);
        return bankAccountRepository.save(account);
    }

    @Transactional
    public void transfer(Long fromAccountId, Long toAccountId, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidTransferException("Transfer amount must be positive");
        }

        BankAccount fromAccount = getAccountById(fromAccountId);
        BankAccount toAccount = getAccountById(toAccountId);

        if (!fromAccount.isActive() || !toAccount.isActive()) {
            throw new InactiveAccountException("One of the accounts is inactive");
        }

        if (fromAccount.getId().equals(toAccount.getId())) {
            throw new InvalidTransferException("Cannot transfer to the same account");
        }

        BigDecimal amountToTransfer = amount;
        String fromDescription = "Transfer to account " + toAccount.getAccountNumber();
        String toDescription = "Transfer from account " + fromAccount.getAccountNumber();

        if (!fromAccount.getCurrency().equals(toAccount.getCurrency())) {
            amountToTransfer = currencyConverterService.convert(
                    amount, fromAccount.getCurrency(), toAccount.getCurrency()
            );

            BigDecimal exchangeRate = currencyConverterService.getExchangeRate(
                    fromAccount.getCurrency(), toAccount.getCurrency()
            );

            fromDescription += String.format(" (%.2f %s converted to %.2f %s at rate %.2f)",
                    amount, fromAccount.getCurrency(),
                    amountToTransfer, toAccount.getCurrency(),
                    exchangeRate);

            toDescription += String.format(" (%.2f %s received from %.2f %s at rate %.2f)",
                    amountToTransfer, toAccount.getCurrency(),
                    amount, fromAccount.getCurrency(),
                    exchangeRate);
        }

        if (fromAccount.getBalance().compareTo(amount) < 0) {
            throw new InvalidTransferException("Insufficient funds");
        }

        fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
        toAccount.setBalance(toAccount.getBalance().add(amountToTransfer));

        bankAccountRepository.save(fromAccount);
        bankAccountRepository.save(toAccount);

        Transaction fromTransaction = new Transaction(
                amount.negate(),
                "TRANSFER",
                fromDescription,
                LocalDateTime.now(),
                fromAccount,
                null
        );
        bankAccountTransactions.save(fromTransaction);

        Transaction toTransaction = new Transaction(
                amountToTransfer,
                "TRANSFER",
                toDescription,
                LocalDateTime.now(),
                null,
                toAccount
        );
        bankAccountTransactions.save(toTransaction);
    }

    public BankAccount findByAccountNumber(String accountNumber) {
        return bankAccountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException("Account not found: " + accountNumber));
    }


    private String generateAccountNumber() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 20; i++) {
            sb.append(random.nextInt(10));
        }

        return sb.toString();
    }

    public List<Transaction> getAccountTransactions(Long accountId) {
        return bankAccountTransactions.findByAccountId(accountId);
    }

    public List<Transaction> getTransactionsByType(Long accountId, String type) {
        return bankAccountTransactions.findByAccountIdAndType(accountId, type);
    }
}
