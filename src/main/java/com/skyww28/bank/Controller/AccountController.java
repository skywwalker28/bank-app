package com.skyww28.bank.Controller;

import com.skyww28.bank.DTO.BankAccountDTO;
import com.skyww28.bank.Exception.AccountNotFoundException;
import com.skyww28.bank.Model.BankAccount;
import com.skyww28.bank.Model.User;
import com.skyww28.bank.Repository.UserRepository;
import com.skyww28.bank.Service.BankAccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@Tag(name = "Accounts", description = "Account management (view/create/deposit/withdraw)")
public class AccountController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BankAccountService bankAccountService;

    private User getCurrentUser(UserDetails userDetails) {
        return userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new AccountNotFoundException("User not found"));
    }

    @GetMapping
    @Operation(summary = "Get all accounts", description = "Returns all active accounts of the current user")
    public List<BankAccount> getAccounts(@AuthenticationPrincipal UserDetails userDetails) {
        User user = getCurrentUser(userDetails);
        return bankAccountService.getUserAccounts(user);
    }

    @PostMapping
    @Operation(summary = "Create account", description = "Create a new account for the current user")
    public BankAccount createAccount(@AuthenticationPrincipal UserDetails userDetails, @RequestParam String currency) {
        User user = getCurrentUser(userDetails);

        return bankAccountService.createAccount(user, currency);
    }

    @PostMapping("/{accountId}/deposit")
    @Operation(summary = "Deposit", description = "Deposits money into the account")
    public BankAccountDTO deposit(@PathVariable Long accountId, @RequestParam BigDecimal amount) {
        BankAccount account = bankAccountService.deposit(accountId, amount);
        return new BankAccountDTO(account);
    }

    @PostMapping("/{accountId}/withdraw")
    @Operation(summary = "Withdraw", description = "Withdraws money from the account")
    public BankAccountDTO withdraw(@PathVariable Long accountId, @RequestParam BigDecimal amount) {
        BankAccount account = bankAccountService.withdraw(accountId, amount);
        return new BankAccountDTO(account);
    }
}
