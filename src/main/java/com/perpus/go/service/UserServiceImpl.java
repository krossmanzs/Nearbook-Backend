package com.perpus.go.service;

import com.perpus.go.exception.BadRequestException;
import com.perpus.go.exception.NotFoundException;
import com.perpus.go.model.User;
import com.perpus.go.repository.UserRepository;
import com.perpus.go.util.Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("username: " + email);
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            log.error("User {} not found in the database", email);
            throw new UsernameNotFoundException(String.format("%s not found", email));
        } else {
            log.info("User found in the database");
        }

        return new org.springframework.security.core.userdetails.User(
                user.get().getEmail(),
                user.get().getPassword(),
                new ArrayList<>()
        );
    }

    public User registerNewUserService(
            String fname,
            String lname,
            String email,
            String password
    ) {
        return userRepository.save(new User(fname, lname, email, password, Util.generateVerificationCode()));
    }

    @Override
    public void sendVerificationEmail(User user)
            throws MessagingException, UnsupportedEncodingException {
        String subject = "Go-Perpus Verification Code";
        String senderName = "Go-Perpus";
        String mailContent = "<p>Dear " + user.getFirstName() + ",</p>";
        mailContent += "<p>Here is your account verification code: <b>" + user.getVerificationCode() + "</b></p>";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom("linuxpringsewu@gmail.com", senderName);
        helper.setTo(user.getEmail());
        helper.setText(mailContent, true);
        helper.setSubject(subject);

        mailSender.send(message);
    }

    @Override
    public void sendVerificationSuccessEmail(User user)
            throws MessagingException, UnsupportedEncodingException {
        String subject = "Go-Perpus Account Verification Success";
        String senderName = "Go-Perpus";
        String mailContent = "<p>Dear " + user.getFirstName() + ",</p>";
        mailContent += "<p>Congratulations, now you can continue to use our app.</p>";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom("linuxpringsewu@gmail.com", senderName);
        helper.setTo(user.getEmail());
        helper.setText(mailContent, true);
        helper.setSubject(subject);

        mailSender.send(message);
    }

    @Override
    public boolean verify(String email, String verificationCode)
            throws MessagingException, UnsupportedEncodingException {
        User user = userRepository.findByVerficationcode(email, verificationCode);

        if (user == null || user.isEnabled()) {
            return false;
        } else {
            userRepository.enable(user.getId());
            sendVerificationSuccessEmail(user);
            return true;
        }
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public String checkUserPasswordByEmail(String email) {
        return userRepository.findPasswordByEmail(email);
    }

    @Override
    public String generateNewVerificationCode(String email) {
        Optional<User> optionalUser = findUserByEmail(email);

        if (optionalUser.isEmpty()) {
            throw new BadRequestException("User with " + email + " not found");
        }

        User user = optionalUser.get();

        String code = Util.generateVerificationCode();
        user.setVerificationCode(code);

        return code;
    }

    @Override
    public void resetPasswordByEmail(String email, String password, String code) {
        Optional<User> optionalUser = findUserByEmail(email);

        if (optionalUser.isEmpty()) {
            throw new NotFoundException("User with " + email + " not found");
        }

        User user = userRepository.findByVerficationcode(email, code);

        if (user != null) {
            user.setPassword(Util.generatedHashedPassword(password));
            user.setVerificationCode(Util.generateVerificationCode());
        } else {
            throw new BadRequestException("Invalid code");
        }
    }
}
