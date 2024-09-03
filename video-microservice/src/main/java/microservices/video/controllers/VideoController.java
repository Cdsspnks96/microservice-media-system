package microservices.video.controllers;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;
import jakarta.inject.Inject;

import microservices.video.domain.Video;
import microservices.video.dto.VideoDTO;
import microservices.video.repositories.VideoRepository;
import microservices.video.domain.User;
import microservices.video.repositories.UserRepository;
import microservices.video.domain.Hashtag;
import microservices.video.repositories.HashtagRepository;

import microservices.video.events.VideoProducer;

@Controller("/videos")
public class VideoController {

	@Inject
	VideoRepository videoRepo;
	
	@Inject
	UserRepository userRepo;
	
	@Inject
	HashtagRepository hashtagRepo;
	
	@Inject
	VideoProducer producer;
	
	// VIDEO LOGIC
	
	@Get("/")
	public Iterable<Video> list() {
		return videoRepo.findAll();
	}
	
	@Transactional
	@Post("/")
	public HttpResponse<Void> add(@Body VideoDTO videoDetails) {
		Video video = new Video();
		video.setTitle(videoDetails.getTitle());
		
		Long userID = videoDetails.getUserID();
		Optional<User> oUser = userRepo.findById(userID);
		if (oUser.isEmpty()) {
			return HttpResponse.notFound();
		}
		video.setUser(oUser.get());
		
		video = videoRepo.save(video);
		if(video != null) {
			producer.postVideo(video.getId(), video);
		}

		return HttpResponse.created(URI.create("/videos/" + video.getId()));
	}
    
	@Get("/{id}")
	public Video getVideo(Long id) {
		return videoRepo.findById(id).orElse(null);
	}
	
	@Transactional
	@Put("/{id}")
	public HttpResponse<Void> updateVideo(Long id, @Body VideoDTO videoDetails) {
		Optional<Video> oVideo = videoRepo.findById(id);
		if (oVideo.isEmpty()) {
			return HttpResponse.notFound();
		}

		Video video = oVideo.get();
		if (videoDetails.getTitle() != null) {
			video.setTitle(videoDetails.getTitle());
		}
		
		if (videoDetails.getUserID() != null) {
			Long userID = videoDetails.getUserID();
			Optional<User> oUser = userRepo.findById(userID);
			if (oUser.isEmpty()) {
				return HttpResponse.notFound();
			}
			video.setUser(oUser.get());
		}
		
		videoRepo.update(video);
		return HttpResponse.ok();
	}
	
	@Transactional
	@Delete("/{id}")
	public HttpResponse<Void> deleteVideo(Long id) {
		Optional<Video> video = videoRepo.findById(id);
		if (video.isEmpty()) {
			return HttpResponse.notFound();
		}

		videoRepo.delete(video.get());
		return HttpResponse.ok();
	}
	
	// HASHTAG LOGIC
	@Get("/{id}/hashtags")
	public Iterable<Hashtag> getHashtags(Long id) {
		Optional<Video> oVideo = videoRepo.findById(id);
		if (oVideo.isEmpty()) {
			return null;
		}
		return oVideo.get().getHashtags();
	}
	
	@Transactional
	@Put("/{videoId}/hashtags")
	public HttpResponse<String> addHashtags(Long videoId, String hashtagCSV) {
		Optional<Video> oVideo = videoRepo.findById(videoId);
		if (oVideo.isEmpty()) {
			return HttpResponse.notFound(String.format("Video %d not found", videoId));
		}
		
		List<String> hashtags = Arrays.asList(hashtagCSV.replaceAll("\\s","").split(","));		
		if(hashtags.isEmpty()) {
			return HttpResponse.notFound(String.format("Hashtags not valid for video %d", videoId));
		}
		
		Video video = oVideo.get();
		for(String hashtag : hashtags) {
			Optional<Hashtag> oTag = hashtagRepo.findByTagName(hashtag);
			if (oTag.isEmpty()) {
				return HttpResponse.notFound(String.format("%s tag not found", hashtag));
			}
			if (video.getHashtags().add(oTag.get())) {
				video = videoRepo.update(video);
				producer.tagVideo(video.getId(), oTag.get());
			}
		}
		
		return HttpResponse.ok(String.format("%s added as tags for video %d", hashtagCSV, videoId));
	}
	
	@Transactional
	@Put("/{videoId}/hashtags/{tagId}")
	public HttpResponse<String> addHashtag(Long videoId, Long tagId) {
		Optional<Video> oVideo = videoRepo.findById(videoId);
		if (oVideo.isEmpty()) {
			return HttpResponse.notFound(String.format("Video %d not found", videoId));
		}

		Optional<Hashtag> oTag = hashtagRepo.findById(tagId);
		if (oTag.isEmpty()) {
			return HttpResponse.notFound(String.format("Hashtag %d not found", tagId));
		}

		Video video = oVideo.get();
		if (video.getHashtags().add(oTag.get())) {
			videoRepo.update(video);
			producer.tagVideo(video.getId(), oTag.get());
		}

		return HttpResponse.ok(String.format("%d added as hashtag of video %d", tagId, videoId));
	}
	
	// VIEWER LOGIC
	@Get("/{id}/viewers")
	public Iterable<User> getViewers(Long id) {
		Optional<Video> oVideo = videoRepo.findById(id);
		if (oVideo.isEmpty()) {
			return null;
		}
		return oVideo.get().getViewers();
	}
	
	@Transactional
	@Put("/{videoId}/viewers/{userId}")
	public HttpResponse<String> addViewer(Long videoId, Long userId) {
		Optional<Video> oVideo = videoRepo.findById(videoId);
		if (oVideo.isEmpty()) {
			return HttpResponse.notFound(String.format("Video %d not found", videoId));
		}

		Optional<User> oUser = userRepo.findById(userId);
		if (oUser.isEmpty()) {
			return HttpResponse.notFound(String.format("User %d not found", userId));
		}

		Video video = oVideo.get();
		if (video.getViewers().add(oUser.get())) {
			videoRepo.update(video);
			producer.viewVideo(videoId, oUser.get());
		}

		return HttpResponse.ok(String.format("User %d added as viewer of video %d", userId, videoId));
	}

	@Transactional
	@Delete("/{videoId}/viewers/{userId}")
	public HttpResponse<String> removeViewer(Long videoId, Long userId) {
		Optional<Video> oVideo = videoRepo.findById(videoId);
		if (oVideo.isEmpty()) {
			return HttpResponse.notFound(String.format("Video %d not found", videoId));
		}

		// DELETE should be idempotent, so it's OK if the mentioned user is not a viewer of the video.
		Video video = oVideo.get();
		video.getViewers().removeIf(u -> userId == u.getId());
		videoRepo.update(video);

		return HttpResponse.ok();
	}
}