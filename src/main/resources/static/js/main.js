$(document).ready(function (){
    console.log("Jquery loaded!");
    const creditCardForm = $("#CreditCardValidationForm");
    const creditCardFormErrorsContainer = creditCardForm.find('.errorsContainer');
    const confettiCanvas = $("body");
    const jsConfetti = new JSConfetti();

    creditCardForm.submit(function (e) { 
        e.preventDefault();
        let cardNumber = creditCardForm.find("#cardPanNumber").val();
        let cardExpiry = creditCardForm.find("#cardExpiryDate").val();
        let cardCVV = creditCardForm.find("#cardCVV").val();
        console.log("cardNumber " + cardNumber);
        console.log("cardExpiry " + cardExpiry);
        console.log("cardCVV " + cardCVV);
        $.ajax({
            type: 'POST',
            url: '/api/v1/credit-card/validate/',
            contentType: 'application/json',
            data: JSON.stringify({
                'cardNumber': cardNumber,
                'cardExpiry': cardExpiry,
                'cardCVV': cardCVV,
            }),
            success: function (response) {
                creditCardForm.removeClass("card-error-gradient card-normal-gradient");
                creditCardForm.addClass("card-success-gradient");
                creditCardFormErrorsContainer.empty();
                jsConfetti.addConfetti();
            },
            error: function(xhr, textStatus, error){
                let response = xhr.responseJSON;
                let message = response.message;
                let errors = response.errors;
                console.log(errors);
                // <p class="text-xs">Error message</p>
                creditCardForm.removeClass("card-success-gradient card-normal-gradient");
                creditCardForm.addClass("card-error-gradient");
            }
        });

        
    });
})