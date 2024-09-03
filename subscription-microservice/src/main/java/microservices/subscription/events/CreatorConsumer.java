package microservices.subscription.events;

import io.micronaut.configuration.kafka.annotation.KafkaKey;
import io.micronaut.configuration.kafka.annotation.KafkaListener;
import io.micronaut.configuration.kafka.annotation.Topic;
import jakarta.inject.Inject;

import microservices.subscription.dto.*;
import microservices.subscription.controllers.*;

@KafkaListener(groupId = "SMDB-CREATE")
public class CreatorConsumer {
	
	@Inject
	SubVideoController videoController;
	
	@Inject
	SubUserController userController;
	
	@Inject
	SubHashtagController tagController;

	@Topic(SubsProducer.TOPIC_POSTED)
	public void videoCreated(@KafkaKey Long id, VideoDTO video) {
		videoController.add(video);
		videoController.addViewerFromVmid(id, video.getUser().getUsername());
	}
	
	@Topic(SubsProducer.TOPIC_USER_CREATED)
	public void userCreated(@KafkaKey Long id, UserDTO user) {
		userController.add(user);
	}
	
	@Topic(SubsProducer.TOPIC_TAG_CREATED)
	public void hashtagCreated(@KafkaKey Long id, HashtagDTO tag) {
		tagController.add(tag.getTagName());
	}
}
