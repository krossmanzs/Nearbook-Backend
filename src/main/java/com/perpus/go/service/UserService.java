package com.perpus.go.service;

import com.perpus.go.model.User;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.Optional;

public interface UserService {
    public void sendVerificationEmail(User user)
            throws MessagingException, UnsupportedEncodingException;
    public void sendVerificationSuccessEmail(User user)
            throws MessagingException, UnsupportedEncodingException;
    public boolean verify(String email, String verificationCode)
            throws MessagingException, UnsupportedEncodingException;
    public Optional<User> findUserByEmail(String email);
    public String checkUserPasswordByEmail(String email);
}
