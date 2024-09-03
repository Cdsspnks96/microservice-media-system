package microservices.video.events;

import io.micronaut.configuration.kafka.annotation.KafkaClient;
import io.micronaut.configuration.kafka.annotation.KafkaKey;
import io.micronaut.configuration.kafka.annotation.Topic;

import microservices.video.domain.Hashtag;

@KafkaClient
public interface HashtagProducer {

	String TOPIC_TAG_CREATED = "hashtag-created";

	@Topic(TOPIC_TAG_CREATED)
	void createTag(@KafkaKey Long id, Hashtag tag);

}