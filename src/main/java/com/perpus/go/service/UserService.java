package com.perpus.go.service;

import com.perpus.go.model.User;
import com.perpus.go.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
