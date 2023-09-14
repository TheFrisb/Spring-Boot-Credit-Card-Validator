package com.example.CreditCardValidator.CardValidator;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping(path = "/api/v1/credit-card")
public class CreditCardController {

    private final CreditCardService creditCardService;
    @Autowired
    public CreditCardController(CreditCardService creditCardService){
        this.creditCardService = creditCardService;
    }

    @PostMapping("/validate/")
    public ResponseEntity<?> validateCreditCard(@Valid @RequestBody CreditCardDTO creditCardDTO){
        Map<String, List<String>> errorDetails = new HashMap<>();

        if(!creditCardService.isValidCVV(creditCardDTO.getCardNumber(), creditCardDTO.getCardCVV())){
            errorDetails.put("cardCVV", Collections.singletonList("CVV must be exactly 4 digits for American Express, 3 digits for other cards"));
        }

        if(!creditCardService.isExpiryDateValid(creditCardDTO.getCardExpiry())){
            errorDetails.put("cardExpiry", Collections.singletonList("Expiry date must be in the future"));
        }

        if(!creditCardService.isCardNumberLuhnValid(creditCardDTO.getCardNumber())){
            errorDetails.put("cardNumber", Collections.singletonList("Card number can not exist or is no longer valid (Luhn algorithm)"));
        }

        if (!errorDetails.isEmpty()) {
            throw new InvalidCreditCardException("Invalid credit card", errorDetails);
        }

        Map<String, Object> JsonResponse = new HashMap<>();
        JsonResponse.put("timestamp", LocalDateTime.now().toString());
        JsonResponse.put("status", 200);
        JsonResponse.put("success", "success");
        JsonResponse.put("message", "Credit card is valid");
        JsonResponse.put("details", new HashMap<>());

        return ResponseEntity.status(200).body(JsonResponse);
    }
}
