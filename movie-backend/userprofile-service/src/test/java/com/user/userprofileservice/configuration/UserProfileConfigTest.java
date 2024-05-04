package com.user.userprofileservice.configuration;

import io.micrometer.observation.ObservationRegistry;
import io.micrometer.observation.aop.ObservedAspect;
import org.apache.kafka.clients.admin.NewTopic;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class UserProfileConfigTest {

    @Mock
    private ObservationRegistry observationRegistry;

    @Test
    void testModelMapperBeanCreation() {
        // Given
        UserProfileConfig config = new UserProfileConfig();

        // When
        ModelMapper modelMapper = config.modelMapper();

        // Then
        assertNotNull(modelMapper);
    }

    @Test
    void testNewTopicBeanCreation() {
        // Given
        UserProfileConfig config = new UserProfileConfig();

        // When
        NewTopic newTopic = config.newTopic();

        // Then
        assertNotNull(newTopic);
    }
}
