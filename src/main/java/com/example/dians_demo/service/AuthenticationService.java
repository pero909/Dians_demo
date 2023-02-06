package com.example.dians_demo.service;

import java.util.Optional;

public interface AuthenticationService {
    Optional<User> findByUsernameAndPassword(String username,String password);
    void save(String username,String password);
    void signUp(String username,String password,String repeatPassword);
    User login(String username,String password);
}
