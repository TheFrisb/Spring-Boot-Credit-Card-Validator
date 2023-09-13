package com.example.CreditCardValidator.CardValidator;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;


public class CreditCardDTO {
    /*
    CLEAN SET METHODS WITH TRIM
    ADD MIN 16 AND MAX FOR CARDNUMBER!
     */
    @NotBlank(message = "Field cardNumber must not be empty")
    @Size(min = 16, max = 19, message = "Field cardNumber must contain 16 to 19 digits.")
    private String cardNumber;

    @NotBlank(message = "Field cardExpiry must not be empty")
    @Pattern(regexp = "^(0[1-9]|1[0-2])\\/\\d{2}$", message = "Field cardExpiry must be in the format MM/YY (Slash included)")
    private String cardExpiry;

    @NotBlank(message = "Field cardCVV must not be empty")
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
