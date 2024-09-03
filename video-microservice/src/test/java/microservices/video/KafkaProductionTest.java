package microservices.video;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.awaitility.Awaitility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.micronaut.configuration.kafka.annotation.KafkaKey;
import io.micronaut.configuration.kafka.annotation.KafkaListener;
import io.micronaut.configuration.kafka.annotation.Topic;
import io.micronaut.context.annotation.Property;
import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;

import microservices.video.clients.VideoClient;
import microservices.video.clients.UserClient;
import microservices.video.domain.Video;
import microservices.video.domain.User;
import microservices.video.dto.UserDTO;
import microservices.video.events.VideoProducer;
import microservices.video.repositories.VideoRepository;
import microservices.video.repositories.UserRepository;

/**
 * This is an integration test between our producer and Kafka itself: we actually
 * subscribe to the Kafka topic and see if the record is produced. It is an
 * asynchronous process, so we have to use the Awaitility library to describe
 * these expectations.
 */
@Property(name = "spec.name", value = "KafkaProductionTest")
@MicronautTest(transactional = false, environments = "no_streams")
public class KafkaProductionTest {

	@Inject
	VideoClient videoClient;
	
	@Inject
	UserClient userClient;

	@Inject
	VideoRepository videoRepo;

	@Inject
	UserRepository userRepo;
	
	final static String username = "Jeff";
	private static final Map<Long, Video> watchedVideos = new HashMap<>();

	@BeforeEach
	public void setUp() {
		videoRepo.deleteAll();
		userRepo.deleteAll();
		watchedVideos.clear();
		
		UserDTO user = new UserDTO();
		user.setUsername(username);
		userClient.add(user);
	}

	@Test
	public void addVideoViewer() {
		Video v = new Video();
		v.setTitle("Container Security");
		v.setUser(userRepo.findByUsername(username).get());
		videoRepo.save(v);

		User u = new User();
		u.setUsername("antonio");
		userRepo.save(u);

		final Long videoId = v.getId();
		HttpResponse<String> response = videoClient.addViewer(videoId, u.getId());
		assertEquals(HttpStatus.OK, response.getStatus(), "Adding viewer to the video should be successful");

		// Check the event went to Kafka and back
		Awaitility.await()
			.atMost(Duration.ofSeconds(30))
			.until(() -> watchedVideos.containsKey(videoId));
	}

	@Requires(property = "spec.name", value = "KafkaProductionTest")
	@KafkaListener(groupId = "kafka-production-test")
	static class TestConsumer {
		@Topic(VideoProducer.TOPIC_VIEWED)
		void viewVideo(@KafkaKey Long id, Video video) {
			watchedVideos.put(id, video);
		}
	}
}
