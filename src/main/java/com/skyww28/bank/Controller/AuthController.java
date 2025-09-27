package com.skyww28.bank.Controller;

import com.skyww28.bank.DTO.UserDTO;

import com.skyww28.bank.Exception.UserAlreadyExistxception;
import com.skyww28.bank.Model.User;
import com.skyww28.bank.Repository.UserRepository;
import com.skyww28.bank.Service.BankAccountService;
import com.skyww28.bank.Service.CurrencyConverterService;
import com.skyww28.bank.Service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
@Tag(name = "Banking API", description = "API for banking operations management")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private BankAccountService bankAccountService;

    @Autowired
    private CurrencyConverterService currencyConverterService;

    @GetMapping
    @Operation(summary = "User dashboard", description = "Main page after login")
    public String dashboard(@AuthenticationPrincipal UserDetails userDetails) {
        return userDetails.getUsername();
    }



    @PostMapping("/register")
    @Operation(summary = "Register user", description = "Create a new user in the system")
    public UserDTO registerUser(@RequestParam String username, @RequestParam String password,
                                @RequestParam String email) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new UserAlreadyExistxception("Username already exist");
        }
        User newUser = new User(username, password, email);
        userRepository.save(newUser);
        return new UserDTO(username, email);
    }
}
