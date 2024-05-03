package com.spotify.userprofile.kafka;

import com.spotify.userprofile.dto.UserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DataPublisherServiceImpl
{
	private final KafkaTemplate<String, UserDetails> kafkaTemplate;

	@Autowired
	public DataPublisherServiceImpl(KafkaTemplate<String, UserDetails> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}

	public void sendMessage(UserDetails user)
	{
		log.info(user.toString());

		kafkaTemplate.send("SpotifyApp", user);
	}

}