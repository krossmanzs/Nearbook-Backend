package com.perpus.go.service;

import com.perpus.go.model.User;
import com.perpus.go.repository.UserRepository;
import com.perpus.go.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;
import java.io.UnsupportedEncodingException;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JavaMailSender mailSender;

    public User registerNewUserService(
            String fname,
            String lname,
            String email,
            String password
    ) {
        return userRepository.save(new User(fname, lname, email, password, Util.generateVerificationCode()));
    }

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
    @Transactional
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

    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public String checkUserPasswordByEmail(String email) {
        return userRepository.findPasswordByEmail(email);
    }
}
