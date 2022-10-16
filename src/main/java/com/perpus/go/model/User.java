package com.perpus.go.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    private String email;
    @NotNull
    private String password;
    @CreationTimestamp
    private Date createdAt;
    @Transient
    @UpdateTimestamp
    private Date updatedAt; // TODO: 10/17/2022 change all Date to LocalDate

    @Column(length = 6)
    private String verificationCode;
    private boolean enabled;

    public User(String firstName, String lastName, String email, String password, String verificationCode) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.verificationCode = verificationCode;
        this.enabled = false;
    }
}
