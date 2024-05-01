package com.auth.authenticationservice.kafka;

import com.auth.authenticationservice.model.RegisterRequest;
import com.auth.authenticationservice.service.AuthService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ConsumeService 
{
	 private final AuthService authService;
	 private RegisterRequest fromPublisher;

	 @Autowired
    public ConsumeService(AuthService authService) {
        this.authService = authService;
    }

    @KafkaListener(topics="MovieApp", groupId="mygroup")

	public void consumeFromTopic(String message) throws JsonProcessingException {
		try {
			RegisterRequest userDetails=convertToJavaObject(message);
			authService.register(userDetails);
			log.info("--------------Consumer message--------: "+ userDetails.getUsername()+"---");
		}
		catch (Exception e){
			log.info(e+"----Exception");
		}

	}

	private RegisterRequest convertToJavaObject(String message) throws JsonProcessingException {
		RegisterRequest userDetails=new ObjectMapper().readValue(message,RegisterRequest.class);
		log.info("from Converter"+userDetails+"-----");
		return  userDetails;
	}

	public RegisterRequest getkafkaMessage(){
		return  fromPublisher;
	}

}