package com.cts.moviebooking.kafka;

import com.cts.moviebooking.dto.UserDetailsResponseDto;
import com.cts.moviebooking.security.AuthenticationService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@Getter
@Setter
public class UserDetailsRetrievedConsumer {

    private final AuthenticationService authenticationService;

    private String jwtToken;



    @Autowired
    public UserDetailsRetrievedConsumer(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

//    @KafkaListener(topics = "user-details-retrieved-topic", groupId = "<your-kafka-consumer-group-id>")
//    public void consume(UserDetailsResponseDto userDetails) {
//        try {
//            System.out.println("Received user name: " + userDetails.getUserName());
//
//            // Assuming generateToken returns the token string
//            jwtToken = authenticationService.generateToken(userDetails.getUserName(), userDetails.getEmail(), userDetails.getRole());
//
//            System.out.println("Token " + jwtToken);
//            // You can perform additional actions if needed
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    @KafkaListener(topics = "user-details-retrieved-topic", groupId = "<your-kafka-consumer-group-id>")
    public CompletableFuture<String> consume(UserDetailsResponseDto userDetails) {
        CompletableFuture<String> future = new CompletableFuture<>();
        try {

            System.out.println("Received user name: " + userDetails.getUserName());


            jwtToken = authenticationService.generateToken(userDetails.getUserName(), userDetails.getEmail(), userDetails.getRole());

            System.out.println("Token " + jwtToken);
            future.complete(jwtToken);
        } catch (Exception e) {

            e.printStackTrace();
            future.completeExceptionally(e);
        }
        return future;
    }

}

