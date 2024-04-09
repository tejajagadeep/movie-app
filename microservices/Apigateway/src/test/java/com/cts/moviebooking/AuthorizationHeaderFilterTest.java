//package com.cts.moviebooking;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.web.reactive.server.WebTestClient;
//import reactor.core.publisher.Mono;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@ExtendWith(MockitoExtension.class)
//class AuthorizationHeaderFilterIntegrationTest {
//
//    @Autowired
//    private WebTestClient webTestClient;
//
//    @Mock
//    private AuthorizationHeaderFilter authorizationHeaderFilter;
//
//    @Test
//    void testValidJwtForLoginService() {
//        // Set up a valid JWT
//        String validJwt = "yourvalidjwttoken.yffg.yfgwuyegfyugwe";
//
//        // Mock the behavior of the AuthorizationHeaderFilter
//        Mockito.when(authorizationHeaderFilter.isJwtValid(validJwt)).thenReturn(true);
//
//        // Send a request with the valid JWT
//        webTestClient.get()
//                .uri("/Loginservice/api/v1/auth/checkHi")
//                .header("Authorization", "Bearer " + validJwt)
//                .exchange();
//
//    }
//
//
//}
