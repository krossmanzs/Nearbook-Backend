package com.perpus.go.model.user;

import com.perpus.go.dto.user.RegisterUserRequest;
import com.perpus.go.model.user.ktp.Ktp;
import com.perpus.go.util.Util;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "User")
public class User {
    @Id
    @GeneratedValue
    private int Id;
    @NotNull
    private String name;
    @NotNull
    private String email;
    @NotNull
    private String password;

    @NotNull
    private String gender;

    @ManyToMany(targetEntity = Role.class,fetch = FetchType.EAGER)
    private Collection<Role> roles;

    @OneToOne(targetEntity = Ktp.class,cascade = CascadeType.ALL)
    private Ktp ktp;

    @CreationTimestamp
    private Date createdAt;
    @Transient
    @UpdateTimestamp
    private Date updatedAt; // TODO: 10/17/2022 change all Date to LocalDate

    @Column(length = 6)
    private String verificationCode;
    @NotNull
    private boolean verifiedEmail;

    @NotNull
    private boolean verifiedKtp;

    public User(RegisterUserRequest registerUserRequest) {
        this.name = registerUserRequest.getName();
        this.email = registerUserRequest.getEmail();
        this.password = registerUserRequest.getPassword();
        this.verificationCode = Util.generateVerificationCode();
        this.verifiedKtp = false;
        this.verifiedEmail = false;
        this.roles = new ArrayList<>();
        this.gender = registerUserRequest.getGender();
    }
}
