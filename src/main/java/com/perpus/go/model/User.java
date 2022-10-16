package com.perpus.go.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
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
    private int Id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Date createdAt;
    @Transient
    private Date updatedAt;
}
