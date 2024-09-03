package microservices.video.events;

import io.micronaut.configuration.kafka.annotation.KafkaClient;
import io.micronaut.configuration.kafka.annotation.KafkaKey;
import io.micronaut.configuration.kafka.annotation.Topic;

import microservices.video.domain.Video;
import microservices.video.domain.User;
import microservices.video.domain.Hashtag;

@KafkaClient
public interface VideoProducer {

	String TOPIC_POSTED = "video-posted";
	String TOPIC_VIEWED = "video-viewed";
	String TOPIC_TAGGED = "video-tagged";
	String TOPIC_LIKED = "video-liked";
	String TOPIC_DISLIKED = "video-disliked";
	
	@Topic(TOPIC_POSTED)
	void postVideo(@KafkaKey Long id, Video video);

	@Topic(TOPIC_VIEWED)
	void viewVideo(@KafkaKey Long id, User user);
	
	@Topic(TOPIC_TAGGED)
	void tagVideo(@KafkaKey Long id, Hashtag tag);
	
	@Topic(TOPIC_LIKED)
	void likeVideo(@KafkaKey Long id, Video video);
	
	@Topic(TOPIC_DISLIKED)
	void dislikeVideo(@KafkaKey Long id, Video video);

}