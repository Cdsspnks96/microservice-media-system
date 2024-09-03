package microservices.subscription.controllers;

import java.net.URI;
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

import microservices.subscription.domain.SubVideo;
import microservices.subscription.dto.VideoDTO;
import microservices.subscription.repositories.SubVideoRepository;
import microservices.subscription.domain.SubUser;
import microservices.subscription.repositories.SubUserRepository;
import microservices.subscription.domain.SubHashtag;
import microservices.subscription.repositories.SubHashtagRepository;

@Controller("/subvideos")
public class SubVideoController {

	@Inject
	SubVideoRepository videoRepo;
	
	@Inject
	SubUserRepository userRepo;
	
	@Inject
	SubHashtagRepository hashtagRepo;
	
	// SubVideo LOGIC
	@Get("/")
	public Iterable<SubVideo> list() {
		return videoRepo.findAll();
	}
	
	@Transactional
	@Post("/")
	public HttpResponse<String> add(@Body VideoDTO videoDetails) {
		SubVideo subVideo = new SubVideo();
		subVideo.setTitle(videoDetails.getTitle());
		subVideo.setVmd(videoDetails.getId());
		subVideo.setLikes(0L);
		subVideo = videoRepo.save(subVideo);

		return HttpResponse.created(URI.create("/subvideos/" + subVideo.getId()));
	}
    
	@Get("/{id}")
	public SubVideo getVideo(Long id) {
		return videoRepo.findById(id).orElse(null);
	}
	
	@Transactional
	@Delete("/{id}")
	public HttpResponse<Void> deleteVideo(Long id) {
		Optional<SubVideo> SubVideo = videoRepo.findById(id);
		if (SubVideo.isEmpty()) {
			return HttpResponse.notFound();
		}

		videoRepo.delete(SubVideo.get());
		return HttpResponse.ok();
	}
	
	// HASHTAG LOGIC
	@Get("/{id}/hashtags")
	public Iterable<SubHashtag> getHashtags(Long id) {
		Optional<SubVideo> oVideo = videoRepo.findById(id);
		if (oVideo.isEmpty()) {
			return null;
		}
		return oVideo.get().getHashtags();
	}
	
	@Transactional
	@Put("/{videoId}/hashtags/{tagId}")
	public HttpResponse<String> addHashtag(Long videoId, Long tagId) {
		Optional<SubVideo> oVideo = videoRepo.findById(videoId);
		if (oVideo.isEmpty()) {
			return HttpResponse.notFound(String.format("Video %d not found", videoId));
		}

		Optional<SubHashtag> oTag = hashtagRepo.findById(tagId);
		if (oTag.isEmpty()) {
			return HttpResponse.notFound(String.format("Hashtag %d not found", tagId));
		}

		SubVideo SubVideo = oVideo.get();
		if (SubVideo.getHashtags().add(oTag.get())) {
			videoRepo.update(SubVideo);
		}

		return HttpResponse.ok(String.format("%d added as hashtag of video %d", tagId, videoId));
	}
	
	public HttpResponse<String> addHashtagFromVmid(Long vmid, String tagname) {
		Optional<SubVideo> oVideo = videoRepo.findByVmid(vmid);
		if (oVideo.isEmpty()) {
			return HttpResponse.notFound(String.format("Video %d not found", vmid));
		}
		
		Optional<SubHashtag> oTag = hashtagRepo.findByTagName(tagname);
		if (oTag.isEmpty()) {
			return HttpResponse.notFound(String.format("Hashtag %s not found", tagname));
		}
		
		return addHashtag(oVideo.get().getId(),oTag.get().getId());
	}
	
	// VIEWER LOGIC
	@Get("/{id}/viewers")
	public Iterable<SubUser> getViewers(Long id) {
		Optional<SubVideo> oVideo = videoRepo.findById(id);
		if (oVideo.isEmpty()) {
			return null;
		}
		return oVideo.get().getViewers();
	}
	
	@Transactional
	@Put("/{videoId}/viewers/{userId}")
	public HttpResponse<String> addViewer(Long videoId, Long userId) {
		Optional<SubVideo> oVideo = videoRepo.findById(videoId);
		if (oVideo.isEmpty()) {
			return HttpResponse.notFound(String.format("Video %d not found", videoId));
		}

		Optional<SubUser> oUser = userRepo.findById(userId);
		if (oUser.isEmpty()) {
			return HttpResponse.notFound(String.format("User %d not found", userId));
		}

		SubVideo SubVideo = oVideo.get();
		if (SubVideo.getViewers().add(oUser.get())) {
			videoRepo.update(SubVideo);
		}

		return HttpResponse.ok(String.format("User %d added as viewer of video %d", userId, videoId));
	}
	
	public HttpResponse<String> addViewerFromVmid(Long vmid, String username) {
		Optional<SubVideo> oVideo = videoRepo.findByVmid(vmid);
		if (oVideo.isEmpty()) {
			return HttpResponse.notFound(String.format("Video %d not found", vmid));
		}
		
		Optional<SubUser> oUser = userRepo.findByUsername(username);
		if (oUser.isEmpty()) {
			return HttpResponse.notFound(String.format("User %s not found", username));
		}

		return addViewer(oVideo.get().getId(),oUser.get().getId());
	}

	@Transactional
	@Delete("/{videoId}/viewers/{userId}")
	public HttpResponse<String> removeViewer(Long videoId, Long userId) {
		Optional<SubVideo> oVideo = videoRepo.findById(videoId);
		if (oVideo.isEmpty()) {
			return HttpResponse.notFound(String.format("Video %d not found", videoId));
		}

		// DELETE should be idempotent, so it's OK if the mentioned user is not a viewer of the SubVideo.
		SubVideo SubVideo = oVideo.get();
		SubVideo.getViewers().removeIf(u -> userId == u.getId());
		videoRepo.update(SubVideo);

		return HttpResponse.ok();
	}
	
	// LIKE LOGIC
	
	@Transactional
	@Put("/{id}")
	public HttpResponse<String> updateLikes(Long id, Long opinion) {
		Optional<SubVideo> oVideo = videoRepo.findById(id);
		if (oVideo.isEmpty()) {
			return HttpResponse.notFound(String.format("Video %d not found", id));
		}
		SubVideo video = oVideo.get();
		
		video.setLikes(video.getLikes()+opinion);
		
		return HttpResponse.ok();
	}
	
	public HttpResponse<String> updateLikesFromVmid(Long vmid, Long opinion) {
		Optional<SubVideo> oVideo = videoRepo.findByVmid(vmid);
		if (oVideo.isEmpty()) {
			return HttpResponse.notFound(String.format("Video %d not found", vmid));
		}
		return updateLikes(oVideo.get().getId(),opinion);
	}
}