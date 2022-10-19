package com.perpus.go.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegisterUserRequest {
    @Builder.Default
    private String name = "";
    @Builder.Default
    private String gender = "";
    @Builder.Default
    private String email = "";
    @Builder.Default
    private String password = "";
}
