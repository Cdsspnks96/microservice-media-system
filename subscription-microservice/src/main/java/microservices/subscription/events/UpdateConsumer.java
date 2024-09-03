package microservices.subscription.events;

import io.micronaut.configuration.kafka.annotation.KafkaKey;
import io.micronaut.configuration.kafka.annotation.KafkaListener;
import io.micronaut.configuration.kafka.annotation.Topic;
import jakarta.inject.Inject;
import microservices.subscription.controllers.SubVideoController;
import microservices.subscription.dto.*;

@KafkaListener(groupId = "SMDB-UPDATE")
public class UpdateConsumer {
	
	@Inject
	SubVideoController videoController;

	@Topic(SubsProducer.TOPIC_TAGGED)
	public void videoTagged(@KafkaKey Long id, HashtagDTO hashtag) {
		videoController.addHashtagFromVmid(id, hashtag.getTagName());
	}
	
	@Topic(SubsProducer.TOPIC_VIEWED)
	public void videoViewed(@KafkaKey Long id, UserDTO user) {
		videoController.addViewerFromVmid(id, user.getUsername());
	}
	
	@Topic(SubsProducer.TOPIC_LIKED)
	public void videoLiked(@KafkaKey Long id, VideoDTO video) {
		videoController.updateLikesFromVmid(id, 1L);
	}
	
	@Topic(SubsProducer.TOPIC_DISLIKED)
	public void videoDisliked(@KafkaKey Long id, VideoDTO video) {
		videoController.updateLikesFromVmid(id, (-1L));
	}
}
