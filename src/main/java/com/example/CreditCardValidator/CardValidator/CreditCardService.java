package com.example.CreditCardValidator.CardValidator;

import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


@Service
public class CreditCardService {

    public boolean isCardNumberLuhnValid(String cardNumber){
        int sum = 0;
        boolean alternate = false;

        for (int i = cardNumber.length() - 1; i >= 0; i--) {
            int n = Integer.parseInt(cardNumber.substring(i, i + 1));
            if (alternate) {
                n *= 2;
                if (n > 9) {
                    n = (n % 10) + 1;
                }
            }
            sum += n;
            alternate = !alternate;
        }

        return (sum % 10 == 0);
    }

    public boolean isExpiryDateValid(String expiryDate){
        SimpleDateFormat sdf = new SimpleDateFormat("MM/yy");
        Date currentDate = new Date();
        try {
            Date inputDate = sdf.parse(expiryDate);
            return inputDate.after(currentDate);
        } catch (ParseException e) {
            System.out.println("Thrown exception at isExpiryValid for input date: " + expiryDate);
            return false;
        }
    }
    public boolean isValidCVV(String cardNumber, String CVV){
        if(cardNumber.startsWith("34") || cardNumber.startsWith("37")){
            // Card is American Express, hence CVV must be 4 digits
            return CVV.length() == 4;
        }
        return CVV.length() == 3;
    }
}
