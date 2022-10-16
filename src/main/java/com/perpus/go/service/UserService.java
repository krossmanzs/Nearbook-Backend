package com.perpus.go.service;

import com.perpus.go.model.User;
import com.perpus.go.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User registerNewUserService(
            String fname,
            String lname,
            String email,
            String password
    ) {
        return userRepository.save(new User(fname, lname, email, password));
    }

    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public String checkUserPasswordByEmail(String email) {
        return userRepository.findPasswordByEmail(email);
    }
}
