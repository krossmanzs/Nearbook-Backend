package com.perpus.go.exception.handler;

import com.perpus.go.exception.BadRequestException;
import com.perpus.go.exception.ForbiddenException;
import com.perpus.go.exception.error.ErrorDetails;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.ZonedDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        // https://stackoverflow.com/questions/33663801/how-do-i-customize-default-error-message-from-spring-valid-validation
        String defaultMessage = ex.getBindingResult().getFieldErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(", "));
        ErrorDetails errorDetails = generateErrorDetails(HttpStatus.BAD_REQUEST, defaultMessage, request);
        return new ResponseEntity<>(errorDetails, errorDetails.getHttpStatus());
    }

    @ExceptionHandler(value = {BadRequestException.class})
    public ResponseEntity<Object> handleBadRequestException(
            BadRequestException e,
            WebRequest request
    ) {
        ErrorDetails errorDetails = generateErrorDetails(HttpStatus.BAD_REQUEST, e, request);
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

    private ErrorDetails generateErrorDetails(
            HttpStatus status,
            String message,
            WebRequest request
    ) {
        return new ErrorDetails(
                status,
                ZonedDateTime.now(),
                message,
                request.getDescription(false)
        );
    }
}
