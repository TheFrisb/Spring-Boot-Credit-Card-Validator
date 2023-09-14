package com.example.CreditCardValidator.CardValidator;

import java.util.List;
import java.util.Map;

/*
    Custom Runtime Exception,
    handled in ApiExceptionHandler
 */
public class InvalidCreditCardException extends RuntimeException{
    private final Map<String, List<String>> details;

    public InvalidCreditCardException(String message, Map<String, List<String>> details) {
        super(message);
        this.details = details;
    }

    public Map<String, List<String>> getDetails() {
        return details;
    }
}
