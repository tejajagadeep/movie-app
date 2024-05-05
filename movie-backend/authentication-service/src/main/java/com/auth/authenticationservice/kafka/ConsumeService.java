package com.auth.authenticationservice.kafka;

import com.auth.authenticationservice.model.RegisterRequest;
import com.auth.authenticationservice.model.Role;
import com.auth.authenticationservice.service.AuthService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class ConsumeService {
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
		userDetails.setRole(Role.MEMBER);
		return  userDetails;
	}

	public RegisterRequest getkafkaMessage(){
		return  fromPublisher;
	}

}