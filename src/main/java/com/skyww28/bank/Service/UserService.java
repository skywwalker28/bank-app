package com.skyww28.bank.Service;

import com.skyww28.bank.Exception.UserAlreadyExistxception;
import com.skyww28.bank.Model.User;
import com.skyww28.bank.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerUser(String username, String password, String email) {
        System.out.println("Register user: " + username);

        if (userRepository.existsByUsername(username)) {
            throw new UserAlreadyExistxception("Username already exist");
        }

        if (userRepository.existsByEmail(email)) {
            throw new UserAlreadyExistxception("Email already exist");
        }

        String encodedPassword = passwordEncoder.encode(password);

        User user = new User(username, encodedPassword, email);
        User savedUser = userRepository.save(user);

        return savedUser;
    }
}
