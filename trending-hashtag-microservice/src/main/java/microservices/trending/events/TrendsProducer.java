package microservices.trending.events;

import io.micronaut.configuration.kafka.annotation.KafkaClient;

@KafkaClient
public interface TrendsProducer {

	String TOPIC_POSTED = "video-posted";
	String TOPIC_TAG_CREATED = "hashtag-created";
	
	String TOPIC_TAGGED = "video-tagged";
	String TOPIC_LIKED = "video-liked";
	String TOPIC_DISLIKED = "video-disliked";

}