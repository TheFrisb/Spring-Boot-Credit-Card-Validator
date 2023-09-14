package com.example.CreditCardValidator.CardValidator;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.*;

@ControllerAdvice
public class ApiExceptionHandler {
    /*
        This is a custom defined exception handler for the default hibernation validation.
        This is used in the CreditCardDTO when we first receive the inputs.
        I approached this exercise as in a real life scenario on a production-ready website.
        The default hibernation validation exception returns potentially unsafe json with stack-traces,
        and field rules, which we do not want our end users to see.
        Also by custom defining this exception handler, and the one below it,
        I am ensuring an expected JSON format on the frontend, where the error function will handle it the same,
        no matter which exception is raised.
        Also credit card info values will not be logged.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest webRequest) {
        Map<String, Object> body = new HashMap<>();
        Map<String, List<String>> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(fieldError -> {
            // Field errors are passed into an array in case an input field has multiple validation rules in place.
            errors.computeIfAbsent(fieldError.getField(), k -> new ArrayList<>()).add(fieldError.getDefaultMessage());
        });

        body.put("timestamp", LocalDateTime.now().toString());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("error", "Bad Request");
        body.put("message", "Invalid input fields");
        body.put("path", ((ServletWebRequest) webRequest).getRequest().getRequestURI());
        body.put("details", errors);

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    /*
        This is used in the controller for returning errors if the credit card fails a method
        from the CreditCardService.
        This also follows the same JSON format as the exception above it, so both
        input validation and card logic will return the same JSON, but with different error messages.
     */
    @ExceptionHandler(InvalidCreditCardException.class)
    public ResponseEntity<Map<String, Object>> handleCustomBadRequestException(InvalidCreditCardException ex, WebRequest webRequest) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now().toString());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("error", "Bad Request");
        body.put("message", ex.getMessage());
        body.put("path", ((ServletWebRequest) webRequest).getRequest().getRequestURI());
        body.put("details", ex.getDetails());

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}