package com.perpus.go.controller.user;

import com.perpus.go.dto.ForgotPasswordByEmailRequest;
import com.perpus.go.dto.RegisterKtpUserRequest;
import com.perpus.go.exception.BadRequestException;
import com.perpus.go.exception.NotFoundException;
import com.perpus.go.model.User;
import com.perpus.go.service.UserService;
import com.perpus.go.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/user/verify")
    public ResponseEntity<String> verifyAccount(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String accessToken,
            @RequestParam("code") String verificationCode
    ) throws MessagingException, UnsupportedEncodingException {
        String email = Util.getEmailFromAccessToken(accessToken);

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

        if (verified) {
            return new ResponseEntity<>("Verification Succeeded!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Verification failed!", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/user/save")
    public ResponseEntity<String> uploadFile(
            @RequestParam("id") int id,
            @RequestParam("image") MultipartFile multipartFile
    ) throws IOException {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        String uploadDir = "test-photos/" + id;
        String extension = Util.getFileExtension(fileName).toLowerCase(Locale.ROOT);
        List<String> availableExtension = List.of("png","jpg","jpeg");
        fileName = String.format("ktp1_%d.%s", id, extension);

        if (availableExtension.contains(extension)) {
            Util.saveFile(uploadDir, fileName, multipartFile);
            return new ResponseEntity<>("File uploaded successfully",HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Wrong file format!",HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/user/reset/get_code")
    public ResponseEntity<?> getResetCode(@RequestParam("email") String email)
            throws MessagingException, UnsupportedEncodingException {
        if (Util.patternNotMatches(email, "^(.+)@(\\S+)$")) {
            return new ResponseEntity<>("Wrong Email format", HttpStatus.BAD_REQUEST);
        }

        User user = userService.findUserByEmail(email)
                .orElseThrow(() -> new NotFoundException("Email not found"));
        userService.sendPasswordResetCodeEmail(user);

        return new ResponseEntity<>("Code successfully sent to " + email,HttpStatus.OK);
    }

    @PostMapping("/user/reset/password_by_email")
    public ResponseEntity<?> resetPasswordByEmail(@RequestBody ForgotPasswordByEmailRequest resetPasswordRequest) {
        if (Util.patternNotMatches(resetPasswordRequest.getEmail(), "^(.+)@(\\S+)$")) {
            throw new BadRequestException("Wrong Email format");
        }

        if (Util.patternNotMatches(resetPasswordRequest.getPassword(),
                "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$")) {
            throw new BadRequestException("Wrong Password Format: Minimum eight characters, at least one letter, one number and one special character.");
        }

        userService.resetPasswordByEmail(
                resetPasswordRequest.getEmail(),
                resetPasswordRequest.getPassword(),
                resetPasswordRequest.getCode()
        );

        return new ResponseEntity<>("Reset password successfully",HttpStatus.OK);
    }

    @PostMapping("/user/register-ktp")
    public void registerKtp(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String accessToken,
            @RequestBody RegisterKtpUserRequest registerKtpUserRequest
    ) {
        String email = Util.getEmailFromAccessToken(accessToken);
        userService.saveKtpForUser(registerKtpUserRequest, email);
    }
}
