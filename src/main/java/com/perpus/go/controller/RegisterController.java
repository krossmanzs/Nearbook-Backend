package com.perpus.go.controller;

import com.perpus.go.dto.RegisterUserRequest;
import com.perpus.go.model.User;
import com.perpus.go.service.UserService;
import com.perpus.go.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class RegisterController {

    @Autowired
    private UserService userService;

    @PostMapping("/user/register")
    public ResponseEntity<String> registerNewUser(@RequestBody RegisterUserRequest userDetail) {
        String fName = userDetail.getFirstName();
        String lName = userDetail.getLastName();
        String email = userDetail.getEmail();
        String password = userDetail.getPassword();

        // check field
        if (fName.isEmpty() || lName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            return new ResponseEntity<>("Please Complete all Fields", HttpStatus.BAD_REQUEST);
        }

        // check email format
        if (!Util.patternMatches(email, "^(.+)@(\\S+)$")) {
            return new ResponseEntity<>("Wrong Email format", HttpStatus.BAD_REQUEST);
        }

        password = (BCrypt.hashpw(password, BCrypt.gensalt()));

        User user = userService.registerNewUserService(fName, lName, email, password);

        if (user != null) {
            return new ResponseEntity<>("User Registered Successfully", HttpStatus.OK);
        }

        return new ResponseEntity<>("Failed to Register User", HttpStatus.BAD_REQUEST);
    }
}
