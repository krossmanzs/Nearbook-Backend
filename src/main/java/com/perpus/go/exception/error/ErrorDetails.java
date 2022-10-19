package com.perpus.go.exception.error;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDetails {
    private HttpStatus httpStatus;
    private ZonedDateTime timeStamp;
    private String message;
    private String details;
}
