package com.example.dians_demo.repository.jpa;

import com.example.dians_demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findAllByUsernameAndPassword(String usernam,String password);
}
