package microservices.trending.events;

import io.micronaut.configuration.kafka.annotation.KafkaKey;
import io.micronaut.configuration.kafka.annotation.KafkaListener;
import io.micronaut.configuration.kafka.annotation.Topic;
import jakarta.inject.Inject;
import microservices.trending.dto.*;
import microservices.trending.controllers.TrendVideoController;

@KafkaListener(groupId = "THMDB-UPDATE")
public class UpdateConsumer {
	
	@Inject
	TrendVideoController videoController;

	@Topic(TrendsProducer.TOPIC_TAGGED)
	public void videoTagged(@KafkaKey Long id, HashtagDTO hashtag) {
		videoController.addHashtagFromVmid(id, hashtag.getTagName());
	}
}
