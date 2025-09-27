package com.skyww28.bank.Controller;

import com.skyww28.bank.DTO.BankAccountDTO;
import com.skyww28.bank.DTO.TransactionDTO;
import com.skyww28.bank.DTO.TransactionHistoryDTO;
import com.skyww28.bank.Model.BankAccount;
import com.skyww28.bank.Model.Transaction;
import com.skyww28.bank.Service.BankAccountService;
import com.skyww28.bank.Service.CurrencyConverterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/transactions")
@Tag(name = "Transactions", description = "Transfer and transaction history")
public class TransactionController {

    @Autowired
    private BankAccountService bankAccountService;

    @Autowired
    private CurrencyConverterService currencyConverterService;


    @PostMapping("/transfer/{fromAccountId}")
    @Operation(summary = "Transfer counts", description = "Performs transfer between counts")
    public ResponseEntity<TransactionDTO> processTransfer(@PathVariable Long fromAccountId,
                                  @RequestParam String toAccountNumber,
                                  @RequestParam BigDecimal amount
                                 ) {

        BankAccount toAccount = bankAccountService.findByAccountNumber(toAccountNumber);
        bankAccountService.transfer(fromAccountId, toAccount.getId(), amount);

        BankAccount updateFrom = bankAccountService.getAccountById(fromAccountId);
        BankAccount updateTo = bankAccountService.findByAccountNumber(toAccountNumber);

        BankAccountDTO updateFromDTO = new BankAccountDTO(updateFrom);
        BankAccountDTO updateToDTO = new BankAccountDTO(updateTo);

        TransactionDTO result = new TransactionDTO(updateFromDTO, updateToDTO);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/account/{accountId}")
    @Operation(summary = "Transaction history", description = "Get transactions for a bank account")
    public List<TransactionHistoryDTO> viewTransactions(@PathVariable Long accountId,
                                                        @RequestParam(required = false) String type) {

        List<Transaction> transaction;
        if (type != null && !type.isEmpty()) {
            transaction =  bankAccountService.getTransactionsByType(accountId, type);
        } else {
            transaction =  bankAccountService.getAccountTransactions(accountId);
        }

        return transaction.stream()
                .map(TransactionHistoryDTO::new)
                .collect(Collectors.toList());
    }
}
