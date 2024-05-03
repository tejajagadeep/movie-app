package com.spotify.authentication.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spotify.authentication.models.AuthRequest;
import com.spotify.authentication.models.UserInfo;
import com.spotify.authentication.repositories.UserCredentialsRepository;
import com.spotify.authentication.services.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

//@Component
public class KafkaConsumer {
    /*
    @Autowired
    private UserInfoService userInfoService;
    @KafkaListener(topics = "user-details", groupId = "group_id2")
    public void consume(String message)
    {
        try {
            UserInfo userInfo = convertToObject(message);
            userInfoService.adduser(userInfo);
            System.out.println("message = " + userInfo.getUsername());

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private UserInfo convertToObject(String message) throws JsonProcessingException {
        UserInfo userInfo= new ObjectMapper().readValue(message, UserInfo.class);
        return userInfo;
    }
     */
}
