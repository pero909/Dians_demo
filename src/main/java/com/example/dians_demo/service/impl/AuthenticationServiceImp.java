package com.example.dians_demo.service.impl;

import com.example.dians_demo.model.Exceptions.PasswordDontMatchException;
import com.example.dians_demo.model.Exceptions.WrongUserCredentials;
import com.example.dians_demo.model.User;
import com.example.dians_demo.repository.jpa.UserRepository;
import com.example.dians_demo.service.AuthenticationService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationServiceImp implements AuthenticationService {
    private final UserRepository userRepository;

    public AuthenticationServiceImp(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> findByUsernameAndPassword(String username, String password) {
        return this.userRepository.findAllByUsernameAndPassword(username,password);
    }

    @Override
    public void save(String username, String password) {
        this.userRepository.save(new User(username,password));
    }

    @Override
    public void signUp(String username, String password, String repeatPassword) {
        if(!password.equals(repeatPassword)){
            throw new PasswordDontMatchException("Passwords Do not match");
        }else this.userRepository.save(new User(username,password));
    }

    @Override
    public User login(String username, String password) {
        User user = this.userRepository.findByUsernameAndPassword(username,password)
                .orElseThrow(()->new WrongUserCredentials("Wrong Username or Password"));
        return user;
    }
}
