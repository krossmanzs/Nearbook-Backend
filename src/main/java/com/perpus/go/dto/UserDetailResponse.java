package com.perpus.go.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailResponse {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private Date createdAt;
    private boolean verified;
}
