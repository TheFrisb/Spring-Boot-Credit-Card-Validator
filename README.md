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
Input validation warnings are supressed so that sensitive card information are not leaked to the console.

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
