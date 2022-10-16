package com.perpus.go.controller.user;

import com.perpus.go.dto.LoginUserRequest;
import com.perpus.go.dto.UserDetailResponse;
import com.perpus.go.model.User;
import com.perpus.go.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class LoginController {

    @Autowired
    private UserService userService;

    @PostMapping("/user/login")
    public ResponseEntity<?> checkPassword(@RequestBody LoginUserRequest login)  {
        if (userService.findUserByEmail(login.getEmail()).isEmpty()) {
            return new ResponseEntity<>("Email does not registered", HttpStatus.NOT_FOUND);
        }

        String passDb = userService.checkUserPasswordByEmail(login.getEmail());

        if (!BCrypt.checkpw(login.getPassword(), passDb)) {
            return new ResponseEntity<>("Incorrect email or password", HttpStatus.BAD_REQUEST);
        }

        User user = userService.findUserByEmail(login.getEmail()).get();
        UserDetailResponse userDetailResponse = new UserDetailResponse(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getCreatedAt()
        );

        return new ResponseEntity<>(userDetailResponse, HttpStatus.OK);
    }

}
