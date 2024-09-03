package microservices.video.events;

import io.micronaut.configuration.kafka.annotation.KafkaClient;
import io.micronaut.configuration.kafka.annotation.KafkaKey;
import io.micronaut.configuration.kafka.annotation.Topic;

import microservices.video.domain.User;

@KafkaClient
public interface UserProducer {

	String TOPIC_USER_CREATED = "user-created";

	@Topic(TOPIC_USER_CREATED)
	void createUser(@KafkaKey Long id, User user);

}