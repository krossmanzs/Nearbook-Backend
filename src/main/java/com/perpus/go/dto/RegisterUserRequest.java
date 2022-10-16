package com.perpus.go.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegisterUserRequest {
    @Builder.Default
    private String firstName = "";
    @Builder.Default
    private String lastName  = "";
    @Builder.Default
    private String email = "";
    @Builder.Default
    private String password = "";
}
