package microservices.trending.events;

import io.micronaut.configuration.kafka.annotation.KafkaKey;
import io.micronaut.configuration.kafka.annotation.KafkaListener;
import io.micronaut.configuration.kafka.annotation.Topic;
import jakarta.inject.Inject;
import microservices.trending.controllers.TrendHashtagController;

@KafkaListener(groupId = "hourly-likes")
public class TrendConsumer {
	
	@Inject
	TrendHashtagController tagController;
	
	@Topic(TrendsStreams.TOPIC_LIKED_BY_DAY)
	public void videosViewedMetric(@KafkaKey WindowedIdentifier window, Long count) {
		tagController.updateLikesPerHour(window.getId(), count);
	}
}