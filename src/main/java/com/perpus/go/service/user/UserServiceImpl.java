package com.perpus.go.service.user;

import com.perpus.go.dto.user.RegisterKtpUserRequest;
import com.perpus.go.dto.user.RegisterUserRequest;
import com.perpus.go.exception.BadRequestException;
import com.perpus.go.exception.NotFoundException;
import com.perpus.go.model.user.Role;
import com.perpus.go.model.user.User;
import com.perpus.go.model.user.ktp.Agama;
import com.perpus.go.model.user.ktp.Kawin;
import com.perpus.go.model.user.ktp.Ktp;
import com.perpus.go.repository.user.AgamaRepository;
import com.perpus.go.repository.user.KawinRepository;
import com.perpus.go.repository.user.RoleRepository;
import com.perpus.go.repository.user.UserRepository;
import com.perpus.go.util.Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final JavaMailSender mailSender;
    private final RoleRepository roleRepository;
    private final KawinRepository kawinRepository;
    private final AgamaRepository agamaRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    log.error("User {} not found in the database", email);
                    throw new UsernameNotFoundException(String.format("%s not found", email));
                });

        log.info("User found in the database");

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                authorities
        );
    }

    public void registerNewUserService(RegisterUserRequest registerUserRequest) {
        registerUserRequest.setPassword(Util.generatedHashedPassword(registerUserRequest.getPassword()));
        userRepository.save(new User(registerUserRequest));
        addRoleToUser(registerUserRequest.getEmail(), "ROLE_USER");
    }

    @Override
    public void sendPasswordResetCodeEmail(User user) throws MessagingException, UnsupportedEncodingException {
        String subject = "Go-Perpus Reset Password Code";
        String senderName = "Go-Perpus";
        String mailContent = "<p>Dear " + user.getName() + ",</p>";
        user.setVerificationCode(Util.generateVerificationCode());
        mailContent += "<p>Here is your reset password code: <b>" + user.getVerificationCode() + "</b></p>";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom("linuxpringsewu@gmail.com", senderName);
        helper.setTo(user.getEmail());
        helper.setText(mailContent, true);
        helper.setSubject(subject);

        mailSender.send(message);
    }

    @Override
    public void sendVerificationEmail(User user)
            throws MessagingException, UnsupportedEncodingException {
        String subject = "Go-Perpus Verification Code";
        String senderName = "Go-Perpus";
        String mailContent = "<p>Dear " + user.getName() + ",</p>";
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
        String mailContent = "<p>Dear " + user.getName() + ",</p>";
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

        if (user == null || user.isVerifiedEmail()) {
            return false;
        } else {
            userRepository.verifyEmail(user.getId());
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

    @Override
    public void addRoleToUser(String email, String roleName) {
        log.info("Adding Role {} to user {}", roleName, email );
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BadRequestException(String.format("User %s not found", email)));
        Role role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new BadRequestException(String.format("Role %s not valid", roleName)));
        user.getRoles().add(role);
    }

    @Override
    public void saveRole(Role role) {
        log.info("Saving new role {} to the database", role.getName());
        if (roleRepository.findByName(role.getName()).isEmpty()) {
            roleRepository.save(role);
        }
    }

    @Override
    public void saveKawin(Kawin kawin) {
        log.info("Saving new kawin {} to the database", kawin.getStatus());
        if (kawinRepository.findByKode(kawin.getKode()).isEmpty()) {
            kawinRepository.save(kawin);
        }
    }

    @Override
    public void saveAgama(Agama agama) {
        log.info("Saving new agama {} to the database", agama.getName());
        if (agamaRepository.findByName(agama.getName()).isEmpty()) {
            agamaRepository.save(agama);
        }
    }

    @Override
    public void saveKtpForUser(RegisterKtpUserRequest userKtpRequest, String email) {
        log.info("Saving new ktp {} for user {} to the database", userKtpRequest.getNamaLengkap(), email);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BadRequestException(String.format("User %s not found", email)));

        if (user.getKtp() != null) {
            throw new BadRequestException("Ktp already registered");
        }

        Agama agama = agamaRepository.findByName(userKtpRequest.getAgama())
                .orElseThrow(() -> new BadRequestException(String.format("Agama %s not valid", userKtpRequest.getAgama())));

        Kawin kawin = kawinRepository.findByKode(userKtpRequest.getKodeKawin())
                .orElseThrow(() -> new BadRequestException(String.format("Kawin with code %s not valid", userKtpRequest.getKodeKawin())));

        Ktp ktp = new Ktp(userKtpRequest);
        ktp.setAgama(agama);
        ktp.setKawin(kawin);
        user.setKtp(ktp);
        user.setVerifiedKtp(true);
    }
}
