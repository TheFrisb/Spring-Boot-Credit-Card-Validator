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
                /*
                    If the form is valid, submit it for processing
                    creditCardForm.submit()..
                 */
            },

            error: function(xhr, textStatus, error){
                let response = xhr.responseJSON;
                let message = response.message;
                let errors = response.details;
                creditCardFormErrorsContainer.empty();
                for(let key in errors){
                    let errorMessages = errors[key];
                    errorMessages.forEach(message => {
                        let errorElement = `<p class="text-xs">${message}</p>`;
                        creditCardFormErrorsContainer.append(errorElement);
                    });
                }
                creditCardForm.removeClass("card-success-gradient card-normal-gradient");
                creditCardForm.addClass("card-error-gradient");
            }
        });

    });

    $("#cardExpiryDate").bind('keyup','keydown', function(e) {
        let input = $(this);
        let inputLength = input.val().length;
        if (e.keyCode !== 8){ // if not backspace
            let value = input.val();

            if(inputLength === 2){
                value += '/';
                input.val(value);
            }

            if(inputLength === 3){
                /*
                    If the user typed 3 digits or more, and then deleted some and is stuck on length 2,
                    automatically add the slash before the next digit when it is entered
                 */
                if(!value.includes("/")){
                    let month = value.substring(0, 2);
                    let year = value.substring(2);
                    value = month + "/" + year;
                    input.val(value);
                }
            }
        }
    })
})