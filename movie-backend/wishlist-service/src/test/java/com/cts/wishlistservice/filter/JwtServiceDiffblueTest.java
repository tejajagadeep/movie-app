package com.cts.wishlistservice.filter;

import io.jsonwebtoken.Claims;

import java.util.function.Function;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {JwtService.class})
@ExtendWith(SpringExtension.class)
class JwtServiceDiffblueTest {
    @Autowired
    private JwtService jwtService;

    /**
     * Method under test: {@link JwtService#extractUsername(String)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testExtractUsername() {
        // TODO: Diffblue Cover was only able to create a partial test for this method:
        //   Reason: No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   io.jsonwebtoken.io.DecodingException: Illegal base64 character: '.'
        //       at io.jsonwebtoken.io.Base64.ctoi(Base64.java:221)
        //       at io.jsonwebtoken.io.Base64.decodeFast(Base64.java:270)
        //       at io.jsonwebtoken.io.Base64Decoder.decode(Base64Decoder.java:36)
        //       at io.jsonwebtoken.io.Base64Decoder.decode(Base64Decoder.java:23)
        //       at io.jsonwebtoken.io.ExceptionPropagatingDecoder.decode(ExceptionPropagatingDecoder.java:36)
        //       at com.cts.wishlistservice.filter.JwtService.getSigningKey(JwtService.java:21)
        //       at com.cts.wishlistservice.filter.JwtService.extractAllClaims(JwtService.java:28)
        //       at com.cts.wishlistservice.filter.JwtService.extractClaim(JwtService.java:39)
        //       at com.cts.wishlistservice.filter.JwtService.extractUsername(JwtService.java:35)
        //   See https://diff.blue/R013 to resolve this issue.

        // Arrange and Act
        jwtService.extractUsername("ABC123");
    }

    /**
     * Method under test: {@link JwtService#extractClaim(String, Function)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testExtractClaim() {
        // TODO: Diffblue Cover was only able to create a partial test for this method:
        //   Reason: Unable to load class.
        //   Class: reactor.netty.http.server.HttpServer
        //   Please check that the class is available on your test runtime classpath.
        //   See https://diff.blue/R005 to resolve this issue.

        // Arrange
        // TODO: Populate arranged inputs
        String token = "";
        Function<Claims, Object> claimsResolver = null;

        // Act
        Object actualExtractClaimResult = this.jwtService.extractClaim(token, claimsResolver);

        // Assert
        // TODO: Add assertions on result
    }

    /**
     * Method under test: {@link JwtService#isTokenValid(String, String)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testIsTokenValid() {
        // TODO: Diffblue Cover was only able to create a partial test for this method:
        //   Reason: No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   io.jsonwebtoken.io.DecodingException: Illegal base64 character: '.'
        //       at io.jsonwebtoken.io.Base64.ctoi(Base64.java:221)
        //       at io.jsonwebtoken.io.Base64.decodeFast(Base64.java:270)
        //       at io.jsonwebtoken.io.Base64Decoder.decode(Base64Decoder.java:36)
        //       at io.jsonwebtoken.io.Base64Decoder.decode(Base64Decoder.java:23)
        //       at io.jsonwebtoken.io.ExceptionPropagatingDecoder.decode(ExceptionPropagatingDecoder.java:36)
        //       at com.cts.wishlistservice.filter.JwtService.getSigningKey(JwtService.java:21)
        //       at com.cts.wishlistservice.filter.JwtService.extractAllClaims(JwtService.java:28)
        //       at com.cts.wishlistservice.filter.JwtService.extractClaim(JwtService.java:39)
        //       at com.cts.wishlistservice.filter.JwtService.extractUsername(JwtService.java:35)
        //       at com.cts.wishlistservice.filter.JwtService.isTokenValid(JwtService.java:44)
        //   See https://diff.blue/R013 to resolve this issue.

        // Arrange and Act
        jwtService.isTokenValid("ABC123", "janedoe");
    }
}
