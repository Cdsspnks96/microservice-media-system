package microservices.video.controllers;

import java.net.URI;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;
import jakarta.inject.Inject;

import microservices.video.domain.Opinion;
import microservices.video.dto.OpinionDTO;
import microservices.video.events.VideoProducer;
import microservices.video.repositories.OpinionRepository;
import microservices.video.domain.User;
import microservices.video.repositories.UserRepository;
import microservices.video.domain.Video;
import microservices.video.repositories.VideoRepository;

@Controller("/opinions")
public class OpinionController {

	@Inject
	OpinionRepository opinionRepo;
	
	@Inject
	UserRepository userRepo;
	
	@Inject
	VideoRepository videoRepo;
	
	@Inject
	VideoProducer producer;

	@Get("/")
	public Iterable<Opinion> list() {
		return opinionRepo.findAll();
	}

	@Post("/")
	public HttpResponse<Void> add(@Body OpinionDTO opinionDetails) {
		if(opinionDetails.isNotFull()) {
			return HttpResponse.badRequest();
		}
		
		// FIND USER
		Long userId = opinionDetails.getUserId();
		Optional<User> oUser = userRepo.findById(userId);
		if (oUser.isEmpty()) {
			return HttpResponse.notFound();
		}
		
		// FIND VIDEO
		Long videoId = opinionDetails.getVideoId();
		Optional<Video> oVideo = videoRepo.findById(videoId);
		if (oVideo.isEmpty()) {
			return HttpResponse.notFound();
		}
		
		// CHECK IF OPINION EXISTS
		Integer status = opinionDetails.getStatus();
		Set<Opinion> existingOpinions = oUser.get().getOpinions();
		for(Opinion opinion : existingOpinions) {
			if(opinion.getVideo().getId() == videoId) {
				return updateOpinion(opinion.getId(),status);
			}
		}
		
		// CREATE OPINION
		Opinion opinion = new Opinion();
		
		// SET USER
		opinion.setUser(oUser.get());
		
		// SET VIDEO
		opinion.setVideo(oVideo.get());
		
		// SET LIKE STATUS
		opinion.setStatus(status.equals(1));
		
		opinionRepo.save(opinion);
		if (opinion.getStatus()) {
			producer.likeVideo(videoId,oVideo.get());
		} else {
			producer.dislikeVideo(videoId,oVideo.get());
		}
		
		return HttpResponse.created(URI.create("/opinions/" + opinion.getId()));
	}

	@Get("/{id}")
	public Opinion getOpinion(Long id) {
		return opinionRepo.findById(id).orElse(null);
	}

	@Transactional
	@Put("/{id}")
	public HttpResponse<Void> updateOpinion(Long id, @Body Integer likeStatus) {
		if(likeStatus.equals(null)) {
			return HttpResponse.noContent();
		}
		
		Optional<Opinion> oOpinion = opinionRepo.findById(id);
		if (oOpinion.isEmpty()) {
			return HttpResponse.notFound();
		}
		
		Opinion opinion = oOpinion.get();
		Boolean status = likeStatus.equals(1);
		
		if (opinion.getStatus().equals(status)) {
			return HttpResponse.badRequest();
		}
		opinion.setStatus(status);
		opinionRepo.save(opinion);
		
		Video video = opinion.getVideo();
		if (opinion.getStatus()) {
			// Two topics produced since 1-(-1) = 2
			producer.likeVideo(video.getId(),video);
			producer.likeVideo(video.getId(),video);
		} else {
			// Two topics produced since 1-(-1) = 2
			producer.dislikeVideo(video.getId(),video);
			producer.dislikeVideo(video.getId(),video);
		}
		return HttpResponse.ok();
	}

	@Transactional
	@Delete("/{id}")
	public HttpResponse<Void> deleteOpinion(Long id) {
		Optional<Opinion> oOpinion = opinionRepo.findById(id);
		if (oOpinion.isEmpty()) {
			return HttpResponse.notFound();
		}

		Video video = oOpinion.get().getVideo();
		// Need to call opposite opinion to existing to reset to 0
		if (oOpinion.get().getStatus()) {
			producer.dislikeVideo(video.getId(),video);
		} else {
			producer.likeVideo(video.getId(),video);
		}
		
		opinionRepo.delete(oOpinion.get());
		return HttpResponse.ok();
	}
}
