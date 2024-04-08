package com.spotify.userProfile.services;

import com.spotify.userProfile.models.AuthRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaMessageProducer {
    private static final String TOPIC = "user-details";
    @Autowired
    private KafkaTemplate<String, AuthRequest> kafkaTemplate;
    public void sendMessage(AuthRequest authRequest){
        kafkaTemplate.send(TOPIC, authRequest);
    }
}
