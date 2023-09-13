$(document).ready(function (){
    console.log("Jquery loaded!");
    const creditCardForm = $("#CreditCardValidationForm");
    const confettiCanvas = $("body");
    const jsConfetti = new JSConfetti();

    creditCardForm.submit(function (e) { 
        e.preventDefault();
        let cardNumber = creditCardForm.find("#cardPanNumber").val();
        let cardExpiry = creditCardForm.find("#cardExpiryDate").val();
        let cardCVV = creditCardForm.find("#cardCVV").val();
        
        $.ajax({
            type: 'POST',
            url: '/api/v1/credit-card/validate',
            contentType: 'application/json',
            data: JSON.stringify({
                'cardNumber': cardNumber,
                'cardExpiry': cardExpiry,
                'cardCVV': cardCVV,
            }),
            success: function (response) {
                creditCardForm.removeClass("card-error-gradient card-normal-gradient");
                creditCardForm.addClass("card-success-gradient");
                jsConfetti.addConfetti();
    
            },
            error: function(xhr, textStatus, error){
                let response = xhr.responseJSON;
                let message = response.message;
                let errors = response.errors;

                creditCardForm.removeClass("card-error-gradient card-normal-gradient");
                creditCardForm.addClass("card-success-gradient");
            }
        });

        
    });
})