# Spring-Boot Credit Card Validator

This application offers validation for credit card information using the Hibernate Starter Validation. 
It checks the CVV, expiry date, card number, and also ensures that the card number adheres to the Luhn algorithm.

![Application Screenshot](https://i.ibb.co/KjBn8Zs/Screenshot-1.png)

## Technologies Used

- Java w Spring Boot
- Hibernate Validator
- Tailwind CSS

## Features

- Validates CVV to ensure it meets the criteria per card type.
- Checks the expiry date of the credit card.
- Verifies the card number using the Luhn algorithm.
- Provides a clear API response for validation or logic errors.
- Custom exception handling for enhanced error clarity and security.

## Secure Error Handling
Sensitive error messages are prevented from leaking. For this reason, custom exception handling has been implemented. It ensures that:

Users receive only generic error messages that do not expose internal system details.
Input validation warnings are supressed so that sensitive card information is not leaked to the console.

## Installation & Setup

1. **Clone the Repository**
    ```bash
    git clone https://github.com/TheFrisb/Spring-Boot-Credit-Card-Validator.git
    cd ..
    ```

2. **Build and Run the Application**
    Make sure you have Maven and Java 20+ installed.
    ```bash
    mvn clean install
    mvn spring-boot:run
    ```

3. The application will start on `http://localhost:8080`. 
You can now open the link and fill out the form or 
you can make API calls to validate credit card details.

## Usage

**Endpoint:** `/api/v1/credit-card/validate/`

**Method:** `POST`

**Sample Request Body:**

```json
{
    "cardNumber": "4568-7701-3205-8102", // Whitespace and - is automatically removed and can be used as separators
    "cardExpiry": "01/28",
    "cardCVV": "734"
}
```

*Success:
```json
details: {}
message: "Credit card is valid"
status: 200
success: "success"
timestamp:  "2023-09-15T13:02:15.715990900"
```
When errors are encountered, the error messages will be returned as a list,
so that if a field has more than 1 validation or logic error, multiple messages can be returned.
However in this specific application only 1 error message is returned per field

*Error (invalid inputs):
```json
details: {   
    cardCVV: ["Field cardCVV must be 3 or 4 digits"]
    cardExpiry: ["Field cardExpiry must be in the format MM/YY (Slash included)"]
    cardNumber: ["Field cardNumber must contain 16 to 19 digits"]
}
error: "Bad Request"
message: "Invalid input fields"
path: "/api/v1/credit-card/validate/"
status: 400
timestamp: "2023-09-15T13:03:18.769477"
```

*Error (logic error):
```json
details: {
    cardCVV: ["CVV must be exactly 4 digits for American Express, 3 digits for other cards"]
    cardExpiry: ["Expiry date must be in the future"]
    cardNumber: ["Card number can not exist or is no longer valid (Luhn algorithm)"]
}
error: "Bad Request"
message: "Invalid credit card"
path: "/api/v1/credit-card/validate/"
status: 400
timestamp: "2023-09-15T13:05:32.329195600"
```
