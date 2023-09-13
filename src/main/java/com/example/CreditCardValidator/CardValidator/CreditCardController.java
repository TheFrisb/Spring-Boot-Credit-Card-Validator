package com.example.CreditCardValidator.CardValidator;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
        ArrayList<String> errorList = new ArrayList<String>();


        if(!creditCardService.isExpiryDateValid(creditCardDTO.getCardExpiry())){
            errorList.add("Expiry date cannot be in the past");
        }

        if(!creditCardService.isValidCVV(creditCardDTO.getCardNumber(), creditCardDTO.getCardCVV())){
            errorList.add("CVV must be exactly 4 digits for Mastercard, 3 digits for other cards.");
        }

        if(!creditCardService.isCardNumberLuhnValid(creditCardDTO.getCardNumber())){
            errorList.add("Luhn fail!");
        }

        if(errorList.isEmpty()){
            return ResponseEntity.status(200).body("valid");
        }
        return ResponseEntity.badRequest().body(errorList);
    }
}
