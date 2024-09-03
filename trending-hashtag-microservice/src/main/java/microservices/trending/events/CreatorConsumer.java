package microservices.trending.events;

import io.micronaut.configuration.kafka.annotation.KafkaKey;
import io.micronaut.configuration.kafka.annotation.KafkaListener;
import io.micronaut.configuration.kafka.annotation.Topic;
import jakarta.inject.Inject;

import microservices.trending.dto.*;
import microservices.trending.controllers.*;

@KafkaListener(groupId = "THMDB-CREATE")
public class CreatorConsumer {
	
	@Inject
	TrendVideoController videoController;
	
	@Inject
	TrendHashtagController tagController;

	@Topic(TrendsProducer.TOPIC_POSTED)
	public void videoCreated(@KafkaKey Long id, VideoDTO video) {
		videoController.add(video);
	}
	
	@Topic(TrendsProducer.TOPIC_TAG_CREATED)
	public void hashtagCreated(@KafkaKey Long id, HashtagDTO tag) {
		tagController.add(tag.getTagName());
	}
}
