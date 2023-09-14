package com.example.CreditCardValidator.CardValidator;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

// import org.hibernate.validator.constraints.CreditCardNumber;


public class CreditCardDTO {
    /*
        The setter methods remove leading and trailing
        white spaces for the CVV and Expiry Date
        to enable correct testing against the Regex pattern,
        and for the Card Number, '-' and white spaces are removed.
        That's why we can safely use min=16 and max=19, since invalid characters
        will be removed before testing.
     */

    /*
        We can of course use the built-in annotation for the cardNumber field
        @CreditCardNumber here and make the code more clear and easier,
        but for the sake of this exercise,
        it will only check the length here,
        and the Luhn algorithm will be implemented
        in the CreditCardService class.
     */

    @Size(min = 16, max = 19, message = "Field cardNumber must contain 16 to 19 digits.")
    @Pattern(regexp = "^[0-9]+$", message = "Field cardNumber must contain only numeric characters.")
    private String cardNumber;

    @Pattern(regexp = "^(0[1-9]|1[0-2])\\/\\d{2}$", message = "Field cardExpiry must be in the format MM/YY (Slash included)")
    private String cardExpiry;

    @Pattern(regexp = "^\\d{3,4}$", message = "Field cardCVV must be 3 or 4 digits")
    private String cardCVV;


    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber.replaceAll("[ -]", "");;
    }

    public String getCardExpiry() {
        return cardExpiry;
    }

    public void setCardExpiry(String cardExpiry) {
        this.cardExpiry = cardExpiry.trim();
    }

    public String getCardCVV() {
        return cardCVV;
    }

    public void setCardCVV(String cardCVV) {
        this.cardCVV = cardCVV.trim();
    }
}
