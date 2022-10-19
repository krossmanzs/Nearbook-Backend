package com.perpus.go.exception.handler;

import com.perpus.go.exception.BadRequestException;
import com.perpus.go.exception.error.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = {BadRequestException.class})
    public ResponseEntity<Object> handleBadRequestException(
            BadRequestException e,
            WebRequest request
    ) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ErrorDetails errorDetails = new ErrorDetails(
                badRequest,
                ZonedDateTime.now(),
                e.getMessage(),
                request.getDescription(false)
        );

        return new ResponseEntity<>(errorDetails, badRequest);
    }
}
