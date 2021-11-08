package com.example.demo.exceptions;

import com.example.demo.payload.ErrorMessage;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    //Handle Validation Errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final ResponseEntity<ErrorMessage> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors=new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();

            errors.put(fieldName, errorMessage);
        });

        ErrorMessage errorDetails = new ErrorMessage(new Date(),errors);
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(value = {org.springframework.security.access.AccessDeniedException.class})
    public final ResponseEntity<ErrorMessage> handleAccessDeniedException(AccessDeniedException ex, WebRequest request) {
        Map<String, String> error=new HashMap<>();
        error.put(ex.getMessage(),request.getDescription(false));
        ErrorMessage errorDetails = new ErrorMessage(new Date(),error);
        return new ResponseEntity<>(errorDetails, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value = {InvalidDataAccessApiUsageException.class})
    public final ResponseEntity<ErrorMessage> handleAccessDeniedException(InvalidDataAccessApiUsageException ex, WebRequest request) {
        Map<String, String> error=new HashMap<>();
        System.out.println("xxx");
        error.put(ex.getMessage(),request.getDescription(false));
        ErrorMessage errorDetails = new ErrorMessage(new Date(),error);
        return new ResponseEntity<>(errorDetails, HttpStatus.FORBIDDEN);
    }
    @ExceptionHandler(value = {MissingServletRequestParameterException.class})
    public final ResponseEntity<ErrorMessage> handleMissingServletRequestParameterException(MissingServletRequestParameterException ex, WebRequest request) {
        Map<String, String> error=new HashMap<>();
        error.put(ex.getMessage(),request.getDescription(false));
        ErrorMessage errorDetails = new ErrorMessage(new Date(),error);
        return new ResponseEntity<>(errorDetails, HttpStatus.FORBIDDEN);
    }
    @ExceptionHandler(value = {IllegalArgumentException.class})
    public final ResponseEntity<ErrorMessage> handleIllegalArgumentException(MissingServletRequestParameterException ex, WebRequest request) {
        Map<String, String> error=new HashMap<>();
        error.put(ex.getMessage(),request.getDescription(false));
        ErrorMessage errorDetails = new ErrorMessage(new Date(),error);
        return new ResponseEntity<>(errorDetails, HttpStatus.FORBIDDEN);
    }
}
