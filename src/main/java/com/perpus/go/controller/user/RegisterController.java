package com.perpus.go.controller.user;

import com.perpus.go.dto.user.RegisterUserRequest;
import com.perpus.go.model.user.User;
import com.perpus.go.service.user.UserService;
import com.perpus.go.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/api/v1")
public class RegisterController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> registerNewUser(@RequestBody RegisterUserRequest userDetail)
            throws MessagingException, UnsupportedEncodingException {
        String name = userDetail.getName();
        String email = userDetail.getEmail();
        String password = userDetail.getPassword();

        // check field
        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            return new ResponseEntity<>("Please Complete all Fields", HttpStatus.BAD_REQUEST);
        }

        // check email format
        if (Util.patternNotMatches(email, "^(.+)@(\\S+)$")) {
            return new ResponseEntity<>("Wrong Email format", HttpStatus.BAD_REQUEST);
        }

        // check if email exists
        if (userService.findUserByEmail(email).isPresent()) {
            return new ResponseEntity<>("Email Already exist", HttpStatus.CONFLICT);
        }

        // check password format
        // Minimum eight characters, at least one letter, one number and one special character
        if (Util.patternNotMatches(password, "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$")) {
            return new ResponseEntity<>("Wrong Password Format: Minimum eight characters, at least one letter, one number and one special character.", HttpStatus.BAD_REQUEST);
        }

        userService.registerNewUserService(userDetail);
        userService.sendVerificationEmail(new User(userDetail));

        return new ResponseEntity<>("User Registered Successfully", HttpStatus.OK);
    }
}
