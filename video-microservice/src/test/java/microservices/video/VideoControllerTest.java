package microservices.video;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.test.annotation.MockBean;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;

import microservices.video.clients.VideoClient;
import microservices.video.clients.UserClient;
import microservices.video.domain.Video;
import microservices.video.domain.User;
import microservices.video.dto.VideoDTO;
import microservices.video.dto.UserDTO;
import microservices.video.events.VideoProducer;
import microservices.video.repositories.VideoRepository;
import microservices.video.repositories.UserRepository;

/*
 * We have to disable transactions here, as otherwise the controller will not be
 * able to see changes made by the test.
 */
@MicronautTest(transactional = false, environments = "no_streams")
public class VideoControllerTest {

	@Inject
	VideoClient videoClient;
	
	@Inject
	UserClient userClient;

	@Inject
	VideoRepository videoRepo;

	@Inject
	UserRepository userRepo;
	
	final static String username = "Jeff";
	private final Map<Long, Video> watchedVideos = new HashMap<>();

	/*
	 * Code doesn't work because VideoProducer is not a Functional Interface
	 * (VideoProducer has more than one function)
	@MockBean(VideoProducer.class)
	VideoProducer testProducer() {
		return (id, video) -> { watchedVideos.put(id,  video); };
	}
	 */
	
	@MockBean(VideoProducer.class)
	VideoProducer testProducer() {
		return Mockito.mock(VideoProducer.class, invocation -> {
	        String methodName = invocation.getMethod().getName();
	        Object[] args = invocation.getArguments();

	        switch (methodName) {
	            case "viewVideo":
	                Long id = (Long) args[0];
	                Video video = (Video) args[1];
	                watchedVideos.put(id, video);
	                return null;
	            case "postVideo":
	                // Handle posted video logic
	                return null;
	            case "likeVideo":
	                // Handle liked video logic
	                return null;
	            case "dislikeVideo":
	                // Handle disliked video logic
	                return null;
	            // Add cases for other methods as needed
	            default:
	                return Mockito.RETURNS_DEFAULTS.answer(invocation);
	        }
	    });
	}

	@BeforeEach
	public void clean() {
		videoRepo.deleteAll();
		userRepo.deleteAll();
		watchedVideos.clear();
		
		UserDTO user = new UserDTO();
		user.setUsername(username);
		userClient.add(user);
	}

	@Test
	public void noVideos() {
		Iterable<Video> iterVideos = videoClient.list();
		assertFalse(iterVideos.iterator().hasNext(), "Service should not list any video initially");
	}

	@Test
	public void addVideo() {
		final String videoTitle = "Container Security";

		VideoDTO video = new VideoDTO();
		video.setTitle(videoTitle);
		video.setUserID(userRepo.findByUsername(username).get().getId());
		HttpResponse<Void> response = videoClient.add(video);
		assertEquals(HttpStatus.CREATED, response.getStatus(), "Update should be successful");

		List<Video> videos = iterableToList(videoClient.list());
		assertEquals(1, videos.size());
		assertEquals(videoTitle, videos.get(0).getTitle());
		assertEquals(username, videos.get(0).getUser().getUsername());
	}

	@Test
	public void getVideo() {
		Video v = new Video();
		v.setTitle("Container Security");
		v.setUser(userRepo.findByUsername(username).get());
		videoRepo.save(v);

		Video video = videoClient.getVideo(v.getId());
		assertEquals(v.getTitle(), video.getTitle(), "Title should be fetched correctly");
		assertEquals(v.getUser().getUsername(), video.getUser().getUsername(), "User should be fetched correctly");
	}

	@Test
	public void getMissingBook() {
		Video response = videoClient.getVideo(0L);
		assertNull(response, "A missing book should produce a 404");
	}

	@Test
	public void updateVideo() {
		Video v = new Video();
		v.setTitle("Container Security");
		v.setUser(userRepo.findByUsername(username).get());
		videoRepo.save(v);

		final String newTitle = "New Title";
		VideoDTO dtoTitle = new VideoDTO();
		dtoTitle.setTitle(newTitle);
		HttpResponse<Void> response = videoClient.updateVideo(v.getId(), dtoTitle);
		assertEquals(HttpStatus.OK, response.getStatus());

		v = videoRepo.findById(v.getId()).get();
		assertEquals(newTitle, v.getTitle());
	}

	@Test
	public void deleteVideo() {
		Video v = new Video();
		v.setTitle("Container Security");
		v.setUser(userRepo.findByUsername(username).get());
		videoRepo.save(v);

		HttpResponse<Void> response = videoClient.deleteVideo(v.getId());
		assertEquals(HttpStatus.OK, response.getStatus());
		
		assertFalse(videoRepo.existsById(v.getId()));
	}

	@Test
	public void noVideoViewers() {
		Video v = new Video();
		v.setTitle("Container Security");
		v.setUser(userRepo.findByUsername(username).get());
		videoRepo.save(v);

		List<User> readers = iterableToList(videoClient.getViewers(v.getId()));
		assertEquals(0, readers.size(), "Videos should not have any viewers initially");
	}

	@Test
	public void oneVideoViewer() {
		Video v = new Video();
		v.setTitle("Container Security");
		v.setUser(userRepo.findByUsername(username).get());
		v.setViewers(new HashSet<>());
		videoRepo.save(v);

		User u = new User();
		u.setUsername("antonio");
		userRepo.save(u);

		v.getViewers().add(u);
		videoRepo.update(v);

		List<User> response = iterableToList(videoClient.getViewers(v.getId()));
		assertEquals(1, response.size(), "The one viewer that was added should be listed");
	}

	@Test
	public void addVideoViewer() {
		Video v = new Video();
		v.setTitle("Container Security");
		v.setUser(userRepo.findByUsername(username).get());
		videoRepo.save(v);

		final String viewerUsername = "alice";
		User u = new User();
		u.setUsername(viewerUsername);
		userRepo.save(u);

		final Long videoId = v.getId();
		HttpResponse<String> response = videoClient.addViewer(videoId, u.getId());
		assertEquals(HttpStatus.OK, response.getStatus(), "Adding viewer to the video should be successful");

		// Check the producer was called by the addition
		assertTrue(watchedVideos.containsKey(videoId));

		v = videoRepo.findById(videoId).get();
		assertEquals(1, v.getViewers().size(), "Video should now have 1 viewer");
		assertEquals(viewerUsername, v.getViewers().iterator().next().getUsername());
	}

	@Test
	public void deleteVideoViewer() {
		Video v = new Video();
		v.setTitle("Container Security");
		v.setUser(userRepo.findByUsername(username).get());
		
		v.setViewers(new HashSet<>());
		videoRepo.save(v);

		User u = new User();
		u.setUsername("antonio");
		userRepo.save(u);

		v.getViewers().add(u);
		videoRepo.update(v);

		HttpResponse<String> response = videoClient.removeViewer(v.getId(), u.getId());
		assertEquals(HttpStatus.OK, response.getStatus(), "Removing viewer of the video should be successful");

		v = videoRepo.findById(v.getId()).get();
		assertTrue(v.getViewers().isEmpty(), "Video should have no viewers anymore");
	}

	private <T> List<T> iterableToList(Iterable<T> iterable) {
		List<T> l = new ArrayList<>();
		iterable.forEach(l::add);
		return l;
	}
}
