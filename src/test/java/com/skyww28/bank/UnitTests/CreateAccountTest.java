package com.skyww28.bank.UnitTests;

import com.skyww28.bank.Model.BankAccount;
import com.skyww28.bank.Model.User;
import com.skyww28.bank.Repository.BankAccountRepository;
import com.skyww28.bank.Service.BankAccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreateAccountTest {

    @Mock
    BankAccountRepository bankAccountRepository;

    User user;
    BankAccount expectedBankAccount;

    @InjectMocks
    BankAccountService bankAccountService;

    @BeforeEach
    public void setup(){
        user = new User("username", "password", "email");
        expectedBankAccount = new BankAccount("12345678901234567890", user, "USD");

    }

    @Test
    void createAccount() {
        when(bankAccountRepository.existsByAccountNumber(any())).thenReturn(false);
        when(bankAccountRepository.save(any(BankAccount.class))).thenReturn(expectedBankAccount);

        BankAccount result = bankAccountService.createAccount(user, "USD");

        assertSame(expectedBankAccount, result);
    }

    @Test
    void createAccountWithInvalidAccountNumber() {
        when(bankAccountRepository.existsByAccountNumber(any())).
                thenReturn(true)
                .thenReturn(false);
        when(bankAccountRepository.save(any(BankAccount.class))).thenReturn(expectedBankAccount);

        BankAccount result = bankAccountService.createAccount(user, "USD");
        assertSame(expectedBankAccount, result);

        verify(bankAccountRepository, times(2)).existsByAccountNumber(any());
    }
}
