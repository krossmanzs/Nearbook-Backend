package com.perpus.go.controller.user;

import com.perpus.go.service.UserService;
import com.perpus.go.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/user/verify")
    public ResponseEntity<String> verifyAccount(
            @RequestParam("email") String email,
            @RequestParam("code") String verificationCode
    ) throws MessagingException, UnsupportedEncodingException {
        // check field
        if (email.isEmpty() || verificationCode.isEmpty()) {
            return new ResponseEntity<>("Please Complete all Fields", HttpStatus.BAD_REQUEST);
        }

        // check email format
        if (Util.patternNotMatches(email, "^(.+)@(\\S+)$")) {
            return new ResponseEntity<>("Wrong Email format", HttpStatus.BAD_REQUEST);
        }

        // verify the user email and code
        boolean verified = userService.verify(email, verificationCode);

        System.out.println("Code: " + verificationCode);

        if (verified) {
            return new ResponseEntity<>("Verification Succeeded!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Verification failed!", HttpStatus.BAD_REQUEST);
        }
    }
}
