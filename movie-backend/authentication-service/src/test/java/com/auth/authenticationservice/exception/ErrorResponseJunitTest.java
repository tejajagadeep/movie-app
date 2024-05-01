package com.auth.authenticationservice.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ErrorResponseJunitTest {
    /**
     * Method under test: default or parameterless constructor of
     * {@link ErrorResponse}
     */
    @Test
    void testNewErrorResponse() {
        // Arrange and Act
        ErrorResponse actualErrorResponse = new ErrorResponse();

        // Assert
        assertNull(actualErrorResponse.getMessage());
        assertNull(actualErrorResponse.getTimeStamp());
        assertNull(actualErrorResponse.getStatus());
    }

    @Test
    void testToString() {
        // Create an instance of ErrorResponse and set values for its fields
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setTimeStamp(new Date());
        errorResponse.setMessage("Test error message");
        errorResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR); // Assuming HttpStatus is imported

        // Expected string representation of the ErrorResponse object
        String expectedToString = "ErrorResponse(timeStamp=" + errorResponse.getTimeStamp() +
                ", message=" + errorResponse.getMessage() +
                ", status=" + errorResponse.getStatus() + ")";

        // Call the toString method and verify the result
        String actualToString = errorResponse.toString();

        assertEquals(expectedToString, actualToString);
    }
}
