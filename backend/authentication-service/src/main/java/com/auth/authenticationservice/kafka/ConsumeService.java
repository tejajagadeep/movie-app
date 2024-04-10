package com.auth.authenticationservice.kafka;

import com.auth.authenticationservice.model.UserDetails;
import com.auth.authenticationservice.repository.UserRepo;
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
	 @Autowired
	 UserRepo userRepo;
	 private UserDetails fromPublisher;

	@KafkaListener(topics="MovieApp", groupId="mygroup")

	public void consumeFromTopic(String message) throws JsonProcessingException {
		try {
			UserDetails userDetails=convertToJavaObject(message);
			userRepo.save(userDetails);
			log.info("--------------Consumer message--------: "+ userDetails.getUsername()+"---"
					+userDetails.getPassword());
		}
		catch (Exception e){
			log.info(e+"----Exception");
		}

	}

	private UserDetails convertToJavaObject(String message) throws JsonProcessingException {
		UserDetails userDetails=new ObjectMapper().readValue(message,UserDetails.class);
		log.info("from Converter"+userDetails+"-----");
		return  userDetails;
	}

	public UserDetails getkafkaMessage(){
		return  fromPublisher;
	}

}