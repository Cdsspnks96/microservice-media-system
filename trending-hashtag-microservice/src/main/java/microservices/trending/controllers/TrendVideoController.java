package microservices.trending.controllers;

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

import microservices.trending.domain.TrendVideo;
import microservices.trending.dto.VideoDTO;
import microservices.trending.repositories.TrendVideoRepository;
import microservices.trending.domain.TrendHashtag;
import microservices.trending.repositories.TrendHashtagRepository;

@Controller("/trendvideos")
public class TrendVideoController {

	@Inject
	TrendVideoRepository videoRepo;
	
	@Inject
	TrendHashtagRepository hashtagRepo;
	
	// SubVideo LOGIC
	@Get("/")
	public Iterable<TrendVideo> list() {
		return videoRepo.findAll();
	}
	
	@Transactional
	@Post("/")
	public HttpResponse<String> add(@Body VideoDTO videoDetails) {
		TrendVideo subVideo = new TrendVideo();
		subVideo.setTitle(videoDetails.getTitle());
		subVideo.setVmd(videoDetails.getId());
		subVideo.setLikes(0L);
		subVideo = videoRepo.save(subVideo);

		return HttpResponse.created(URI.create("/trendvideos/" + subVideo.getId()));
	}
    
	@Get("/{id}")
	public TrendVideo getVideo(Long id) {
		return videoRepo.findById(id).orElse(null);
	}
	
	@Transactional
	@Delete("/{id}")
	public HttpResponse<Void> deleteVideo(Long id) {
		Optional<TrendVideo> SubVideo = videoRepo.findById(id);
		if (SubVideo.isEmpty()) {
			return HttpResponse.notFound();
		}

		videoRepo.delete(SubVideo.get());
		return HttpResponse.ok();
	}
	
	// HASHTAG LOGIC
	@Get("/{id}/hashtags")
	public Iterable<TrendHashtag> getHashtags(Long id) {
		Optional<TrendVideo> oVideo = videoRepo.findById(id);
		if (oVideo.isEmpty()) {
			return null;
		}
		return oVideo.get().getHashtags();
	}
	
	@Transactional
	@Put("/{videoId}/hashtags/{tagId}")
	public HttpResponse<String> addHashtag(Long videoId, Long tagId) {
		Optional<TrendVideo> oVideo = videoRepo.findById(videoId);
		if (oVideo.isEmpty()) {
			return HttpResponse.notFound(String.format("Video %d not found", videoId));
		}

		Optional<TrendHashtag> oTag = hashtagRepo.findById(tagId);
		if (oTag.isEmpty()) {
			return HttpResponse.notFound(String.format("User %d not found", tagId));
		}

		TrendVideo SubVideo = oVideo.get();
		if (SubVideo.getHashtags().add(oTag.get())) {
			videoRepo.update(SubVideo);
		}

		return HttpResponse.ok(String.format("%d added as hashtag of SubVideo %d", tagId, videoId));
	}
	
	public HttpResponse<String> addHashtagFromVmid(Long vmid, String tagname) {
		Optional<TrendVideo> oVideo = videoRepo.findByVmid(vmid);
		if (oVideo.isEmpty()) {
			return HttpResponse.notFound(String.format("Video %d not found", vmid));
		}
		
		Optional<TrendHashtag> oTag = hashtagRepo.findByTagName(tagname);
		if (oTag.isEmpty()) {
			return HttpResponse.notFound(String.format("Hashtag %s not found", tagname));
		}
		
		return addHashtag(oVideo.get().getId(),oTag.get().getId());
	}
	
	// LIKE LOGIC
	
	@Transactional
	@Put("/{id}")
	public HttpResponse<String> updateLikes(Long id, Long opinion) {
		Optional<TrendVideo> oVideo = videoRepo.findById(id);
		if (oVideo.isEmpty()) {
			return HttpResponse.notFound(String.format("Video %d not found", id));
		}
		TrendVideo video = oVideo.get();
		
		video.setLikes(video.getLikes()+opinion);
		
		return HttpResponse.ok();
	}
	
	public HttpResponse<String> updateLikesFromVmid(Long vmid, Long opinion) {
		Optional<TrendVideo> oVideo = videoRepo.findByVmid(vmid);
		if (oVideo.isEmpty()) {
			return HttpResponse.notFound(String.format("Video %d not found", vmid));
		}
		return updateLikes(oVideo.get().getId(),opinion);
	}
}