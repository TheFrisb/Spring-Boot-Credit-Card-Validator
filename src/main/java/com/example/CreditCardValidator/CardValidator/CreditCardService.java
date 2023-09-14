package com.example.CreditCardValidator.CardValidator;

import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.LocalDate;
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

    public boolean isExpiryDateValid(String expiryDateInput){
        LocalDate currentDate = LocalDate.now();
        int currentYear = currentDate.getYear();

        try {
            String[] parts = expiryDateInput.split("/");
            int month = Integer.parseInt(parts[0]);
            int year = Integer.parseInt(parts[1]);
            int century = (currentYear / 100) * 100;
            LocalDate expiryDate = LocalDate.of(century + year, month, 1);

            return expiryDate.isAfter(currentDate);


        } catch (NumberFormatException | ArrayIndexOutOfBoundsException | DateTimeException e) {
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
