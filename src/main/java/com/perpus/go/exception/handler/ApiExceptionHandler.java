package com.perpus.go.exception.handler;

import com.perpus.go.exception.BadRequestException;
import com.perpus.go.exception.ForbiddenException;
import com.perpus.go.exception.NotFoundException;
import com.perpus.go.exception.error.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler /*extends ResponseEntityExceptionHandler*/ {
    @ExceptionHandler(value = {BadRequestException.class})
    public ResponseEntity<Object> handleBadRequestException(
            BadRequestException e,
            WebRequest request
    ) {
        ErrorDetails errorDetails = generateErrorDetails(HttpStatus.BAD_REQUEST, e, request);
        return new ResponseEntity<>(errorDetails, errorDetails.getHttpStatus());
    }

    @ExceptionHandler(value = {NotFoundException.class})
    public ResponseEntity<Object> handleNotFoundException(
            BadRequestException e,
            WebRequest request
    ) {
        ErrorDetails errorDetails = generateErrorDetails(HttpStatus.NOT_FOUND,e, request);
        return new ResponseEntity<>(errorDetails, errorDetails.getHttpStatus());
    }

    @ExceptionHandler(value = {ForbiddenException.class})
    public ResponseEntity<Object> handleForbiddenException(
            BadRequestException e,
            WebRequest request
    ) {
        ErrorDetails errorDetails = generateErrorDetails(HttpStatus.FORBIDDEN,e, request);
        return new ResponseEntity<>(errorDetails, errorDetails.getHttpStatus());
    }

    private ErrorDetails generateErrorDetails(
            HttpStatus status,
            Throwable throwable,
            WebRequest request
    ) {
        return new ErrorDetails(
                status,
                ZonedDateTime.now(),
                throwable.getMessage(),
                request.getDescription(false)
        );
    }
}
