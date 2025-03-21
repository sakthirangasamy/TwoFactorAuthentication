package com.example.demo.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.Entity.User;
import com.example.demo.Repository.UserRepository;

@Service
public class LoginService {

    @Autowired
    private UserRepository userRepository;

    // Password encoder to hash passwords
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
