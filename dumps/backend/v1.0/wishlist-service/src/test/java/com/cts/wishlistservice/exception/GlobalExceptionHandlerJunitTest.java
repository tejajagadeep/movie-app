package com.cts.wishlistservice.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.resilience4j.spring6.timelimiter.configure.IllegalReturnTypeException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.impl.DefaultClaims;
import io.jsonwebtoken.impl.DefaultHeader;

import java.security.SignatureException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {GlobalExceptionHandler.class})
@ExtendWith(SpringExtension.class)
class GlobalExceptionHandlerJunitTest {
    @Autowired
    private GlobalExceptionHandler globalExceptionHandler;

    /**
     * Method under test: {@link GlobalExceptionHandler#handleException(Exception)}
     */
    @Test
    void testHandleException() {
        // Arrange and Act
        ResponseEntity<Object> actualHandleExceptionResult = globalExceptionHandler.handleException(new Exception("foo"));

        // Assert
        assertEquals("foo", ((ErrorResponse) actualHandleExceptionResult.getBody()).getMessage());
        assertEquals(500, actualHandleExceptionResult.getStatusCodeValue());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, ((ErrorResponse) actualHandleExceptionResult.getBody()).getStatus());
        assertTrue(actualHandleExceptionResult.hasBody());
        assertTrue(actualHandleExceptionResult.getHeaders().isEmpty());
    }

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
     * {@link GlobalExceptionHandler#handleUnAuthorizedException(UnAuthorizedException)}
     */
    @Test
    void testHandleUnAuthorizedException() {
        // Arrange and Act
        ResponseEntity<Object> actualHandleUnAuthorizedExceptionResult = globalExceptionHandler
                .handleUnAuthorizedException(new UnAuthorizedException("An error occurred"));

        // Assert
        HttpStatusCode expectedStatus = actualHandleUnAuthorizedExceptionResult.getStatusCode();
        assertSame(expectedStatus, ((ErrorResponse) actualHandleUnAuthorizedExceptionResult.getBody()).getStatus());
    }

    /**
     * Method under test:
     * {@link GlobalExceptionHandler#handleUnAuthorizedException(UnAuthorizedException)}
     */
    @Test
    void testHandleUnAuthorizedException2() {
        // Arrange
        UnAuthorizedException ex = mock(UnAuthorizedException.class);
        when(ex.getMessage()).thenReturn("Not all who wander are lost");

        // Act
        ResponseEntity<Object> actualHandleUnAuthorizedExceptionResult = globalExceptionHandler
                .handleUnAuthorizedException(ex);

        // Assert
        verify(ex).getMessage();
        HttpStatusCode expectedStatus = actualHandleUnAuthorizedExceptionResult.getStatusCode();
        assertSame(expectedStatus, ((ErrorResponse) actualHandleUnAuthorizedExceptionResult.getBody()).getStatus());
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

    /**
     * Method under test:
     * {@link GlobalExceptionHandler#handleSignatureException(SignatureException)}
     */
    @Test
    void testHandleSignatureException() {
        // Arrange and Act
        ResponseEntity<Object> actualHandleSignatureExceptionResult = globalExceptionHandler
                .handleSignatureException(new SignatureException("foo"));

        // Assert
        HttpStatusCode expectedStatus = actualHandleSignatureExceptionResult.getStatusCode();
        assertSame(expectedStatus, ((ErrorResponse) actualHandleSignatureExceptionResult.getBody()).getStatus());
    }

    /**
     * Method under test:
     * {@link GlobalExceptionHandler#handleExpiredJwtException(ExpiredJwtException)}
     */
    @Test
    void testHandleExpiredJwtException() {
        // Arrange
        DefaultHeader<?> header = new DefaultHeader<>();

        // Act
        ResponseEntity<Object> actualHandleExpiredJwtExceptionResult = globalExceptionHandler
                .handleExpiredJwtException(new ExpiredJwtException(header, new DefaultClaims(), "An error occurred"));

        // Assert
        HttpStatusCode expectedStatus = actualHandleExpiredJwtExceptionResult.getStatusCode();
        assertSame(expectedStatus, ((ErrorResponse) actualHandleExpiredJwtExceptionResult.getBody()).getStatus());
    }

    /**
     * Method under test:
     * {@link GlobalExceptionHandler#handleExpiredJwtException(ExpiredJwtException)}
     */
    @Test
    void testHandleExpiredJwtException2() {
        // Arrange
        ExpiredJwtException ex = mock(ExpiredJwtException.class);
        when(ex.getMessage()).thenReturn("Not all who wander are lost");

        // Act
        ResponseEntity<Object> actualHandleExpiredJwtExceptionResult = globalExceptionHandler.handleExpiredJwtException(ex);

        // Assert
        verify(ex).getMessage();
        HttpStatusCode expectedStatus = actualHandleExpiredJwtExceptionResult.getStatusCode();
        assertSame(expectedStatus, ((ErrorResponse) actualHandleExpiredJwtExceptionResult.getBody()).getStatus());
    }

    /**
     * Method under test:
     * {@link GlobalExceptionHandler#handleMalformedJwtException(MalformedJwtException)}
     */
    @Test
    void testHandleMalformedJwtException() {
        // Arrange and Act
        ResponseEntity<Object> actualHandleMalformedJwtExceptionResult = globalExceptionHandler
                .handleMalformedJwtException(new MalformedJwtException("An error occurred"));

        // Assert
        HttpStatusCode expectedStatus = actualHandleMalformedJwtExceptionResult.getStatusCode();
        assertSame(expectedStatus, ((ErrorResponse) actualHandleMalformedJwtExceptionResult.getBody()).getStatus());
    }

    /**
     * Method under test:
     * {@link GlobalExceptionHandler#handleMalformedJwtException(MalformedJwtException)}
     */
    @Test
    void testHandleMalformedJwtException2() {
        // Arrange
        MalformedJwtException ex = mock(MalformedJwtException.class);
        when(ex.getMessage()).thenReturn("Not all who wander are lost");

        // Act
        ResponseEntity<Object> actualHandleMalformedJwtExceptionResult = globalExceptionHandler
                .handleMalformedJwtException(ex);

        // Assert
        verify(ex).getMessage();
        HttpStatusCode expectedStatus = actualHandleMalformedJwtExceptionResult.getStatusCode();
        assertSame(expectedStatus, ((ErrorResponse) actualHandleMalformedJwtExceptionResult.getBody()).getStatus());
    }
}
