package microservices.subscription.events;

import io.micronaut.configuration.kafka.annotation.KafkaClient;
import io.micronaut.configuration.kafka.annotation.KafkaKey;
import io.micronaut.configuration.kafka.annotation.Topic;
import microservices.subscription.domain.SubHashtag;

@KafkaClient
public interface SubsProducer {

	String TOPIC_POSTED = "video-posted";
	String TOPIC_USER_CREATED = "user-created";
	String TOPIC_TAG_CREATED = "hashtag-created";
	
	String TOPIC_TAGGED = "video-tagged";
	String TOPIC_VIEWED = "video-viewed";
	String TOPIC_LIKED = "video-liked";
	String TOPIC_DISLIKED = "video-disliked";
	
	String TOPIC_SUB = "tag-subbed";
	String TOPIC_UNSUB = "tag-unsubbed";

	@Topic(TOPIC_SUB)
	void subHashtag(@KafkaKey Long id, SubHashtag subHashtag);
	
	@Topic(TOPIC_UNSUB)
	void unsubHashtag(@KafkaKey Long id, SubHashtag subHashtag);

}