package com.example.demo.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.Entity.User;
import com.example.demo.Repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private final PasswordEncoder argon2PasswordEncoder = new Argon2PasswordEncoder();
    private final PasswordEncoder scryptPasswordEncoder = new SCryptPasswordEncoder();

    public void registerUser(User user) {
        // Store the plain password
        String plainPassword = user.getPassword();
        user.setPassword(plainPassword);

        // Hash the password using Argon2 (optional, for demonstration)
        String passwordArgon2 = argon2PasswordEncoder.encode(plainPassword);
        user.setPasswordArgon2(passwordArgon2);

        // Hash the password using Scrypt (optional, for demonstration)
        String passwordScrypt = scryptPasswordEncoder.encode(plainPassword);
        user.setPasswordScrypt(passwordScrypt);

        // Save the user to the database
        userRepository.save(user);
    }
    // Method to check if a username already exists
    public boolean usernameExists(String username) {
        Optional<User> existingUser = userRepository.findByUsername(username);
        return existingUser.isPresent();
    }
    public List<User> getAllUsers() {
        return userRepository.findAll();  // Fetches all users from the database
    }
    
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
 

    // Method to save a user
    public User save(User user) {
        return userRepository.save(user);  // Save user to the database
    }
}