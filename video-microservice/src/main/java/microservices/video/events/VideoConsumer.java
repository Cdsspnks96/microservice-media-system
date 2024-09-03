package microservices.video.events;

import io.micronaut.configuration.kafka.annotation.KafkaKey;
import io.micronaut.configuration.kafka.annotation.KafkaListener;
import io.micronaut.configuration.kafka.annotation.Topic;

import microservices.video.domain.Video;

@KafkaListener(groupId = "video-debug")
public class VideoConsumer {
	
	@Topic(VideoStreams.TOPIC_VIEWED_BY_DAY)
	public void videosViewedMetric(@KafkaKey WindowedIdentifier window, Long count) {
		System.out.printf("New value for key %s: %d%n", window, count);
	}

	@Topic(VideoProducer.TOPIC_VIEWED)
	public void viewVideo(@KafkaKey Long id, Video video) {
		System.out.printf("Video viewed: %d%n", id);
	}
}
