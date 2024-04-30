package com.user.userprofileservice.kafka;

import com.user.userprofileservice.dto.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DataPublisherServiceImpl
{
	private final KafkaTemplate<String, User> kafkaTemplate;

	@Autowired
	public DataPublisherServiceImpl(KafkaTemplate<String, User> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}

	public void sendMessage(User user)
	{
		log.info(user.toString());

		kafkaTemplate.send("MovieApp", user);
	}

}