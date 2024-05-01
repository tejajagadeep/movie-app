package com.cts.movieservice.exception;

import io.github.resilience4j.spring6.timelimiter.configure.IllegalReturnTypeException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.security.SignatureException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {GlobalExceptionHandler.class})
@ExtendWith(SpringExtension.class)
class GlobalExceptionHandlerJunitTest {
    @Autowired
    private GlobalExceptionHandler globalExceptionHandler;



    /**
     * Method under test:
     * {@link GlobalExceptionHandler#handleInvalidUserException(ResourceNotFoundException)}
     */
    @Test
    void testHandleInvalidUserException() {
        // Arrange and Act
        ResponseEntity<ErrorResponse> actualHandleInvalidUserExceptionResult = globalExceptionHandler
                .handleInvalidUserException(new ResourceNotFoundException("An error occurred"));

        // Assert
        HttpStatusCode expectedStatus = actualHandleInvalidUserExceptionResult.getStatusCode();
        assertSame(expectedStatus, actualHandleInvalidUserExceptionResult.getBody().getStatus());
    }

    /**
     * Method under test:
     * {@link GlobalExceptionHandler#handleInvalidUserException(ResourceNotFoundException)}
     */
    @Test
    void testHandleInvalidUserException2() {
        // Arrange
        ResourceNotFoundException ex = mock(ResourceNotFoundException.class);
        when(ex.getMessage()).thenReturn("Not all who wander are lost");

        // Act
        ResponseEntity<ErrorResponse> actualHandleInvalidUserExceptionResult = globalExceptionHandler
                .handleInvalidUserException(ex);

        // Assert
        verify(ex).getMessage();
        HttpStatusCode expectedStatus = actualHandleInvalidUserExceptionResult.getStatusCode();
        assertSame(expectedStatus, actualHandleInvalidUserExceptionResult.getBody().getStatus());
    }

    /**
     * Method under test:
     * {@link GlobalExceptionHandler#handleNullPointerException(NullPointerException)}
     */
    @Test
    void testHandleNullPointerException() {
        // Arrange and Act
        ResponseEntity<Object> actualHandleNullPointerExceptionResult = globalExceptionHandler
                .handleNullPointerException(new NullPointerException("foo"));

        // Assert
        HttpStatusCode expectedStatus = actualHandleNullPointerExceptionResult.getStatusCode();
        assertSame(expectedStatus, ((ErrorResponse) actualHandleNullPointerExceptionResult.getBody()).getStatus());
    }

    /**
     * Method under test:
     * {@link GlobalExceptionHandler#handleIllegalArgumentException(IllegalArgumentException)}
     */
    @Test
    void testHandleIllegalArgumentException() {
        // Arrange and Act
        ResponseEntity<Object> actualHandleIllegalArgumentExceptionResult = globalExceptionHandler
                .handleIllegalArgumentException(new IllegalArgumentException("foo"));

        // Assert
        HttpStatusCode expectedStatus = actualHandleIllegalArgumentExceptionResult.getStatusCode();
        assertSame(expectedStatus, ((ErrorResponse) actualHandleIllegalArgumentExceptionResult.getBody()).getStatus());
    }

    /**
     * Method under test:
     * {@link GlobalExceptionHandler#handleIllegalArgumentException(IllegalArgumentException)}
     */
    @Test
    void testHandleIllegalArgumentException2() {
        // Arrange
        IllegalReturnTypeException ex = mock(IllegalReturnTypeException.class);
        when(ex.getMessage()).thenReturn("Not all who wander are lost");

        // Act
        ResponseEntity<Object> actualHandleIllegalArgumentExceptionResult = globalExceptionHandler
                .handleIllegalArgumentException(ex);

        // Assert
        verify(ex).getMessage();
        HttpStatusCode expectedStatus = actualHandleIllegalArgumentExceptionResult.getStatusCode();
        assertSame(expectedStatus, ((ErrorResponse) actualHandleIllegalArgumentExceptionResult.getBody()).getStatus());
    }
}
