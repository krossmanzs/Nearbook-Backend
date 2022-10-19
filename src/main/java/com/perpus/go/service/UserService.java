package com.perpus.go.service;

import com.perpus.go.dto.RegisterKtpUserRequest;
import com.perpus.go.dto.RegisterUserRequest;
import com.perpus.go.model.Role;
import com.perpus.go.model.User;
import com.perpus.go.model.ktp.Agama;
import com.perpus.go.model.ktp.Kawin;

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
    public String generateNewVerificationCode(String email);
    public void registerNewUserService(RegisterUserRequest registerUserRequest);
    public void resetPasswordByEmail(String email, String password, String code);
    public void addRoleToUser(String username, String roleName);

    public void saveRole(Role role);

    public void saveKawin(Kawin kawin);

    public void saveAgama(Agama agama);

    public void saveKtpForUser(RegisterKtpUserRequest ktp, String email);
}
