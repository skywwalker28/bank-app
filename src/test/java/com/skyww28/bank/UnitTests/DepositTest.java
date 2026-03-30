package com.skyww28.bank.UnitTests;

import com.skyww28.bank.model.BankAccount;
import com.skyww28.bank.model.User;
import com.skyww28.bank.repository.BankAccountRepository;
import com.skyww28.bank.repository.BankAccountTransactions;
import com.skyww28.bank.service.BankAccountService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DepositTest {

    @Mock
    private BankAccountRepository bankAccountRepository;

    @Mock
    private BankAccountTransactions bankAccountTransactions;

    @InjectMocks
    private BankAccountService bankAccountService;

    @Test
    public void testDeposit(){
        BankAccount bankAccount = new BankAccount("12345678901234567890", new User(), "USD");
        bankAccount.setBalance(new BigDecimal("1000"));

        when(bankAccountRepository.findById(1L)).thenReturn(Optional.of(bankAccount));
        when(bankAccountRepository.save(any(BankAccount.class))).thenReturn(bankAccount);
        when(bankAccountTransactions.save(any())).thenAnswer(i -> i.getArgument(0));

        BankAccount result = bankAccountService.deposit(1L, new BigDecimal("1500"));

        assertNotNull(result);
        assertEquals(new BigDecimal("2500"), result.getBalance());

        verify(bankAccountRepository).findById(1L);
        verify(bankAccountRepository).save(any(BankAccount.class));
        verify(bankAccountTransactions).save(any());
    }
}
