package microservices.trending.controllers;

import java.net.URI;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;
import jakarta.inject.Inject;

import microservices.trending.domain.TrendHashtag;
import microservices.trending.repositories.TrendHashtagRepository;
import microservices.trending.repositories.TrendVideoRepository;
import microservices.trending.domain.TrendVideo;

@Controller("/trendhashtags")
public class TrendHashtagController {

	@Inject
	TrendHashtagRepository tagRepo;
	
	@Inject
	TrendVideoRepository videoRepo;
	
	// HASHTAG LOGIC
	
	@Get("/")
	public Iterable<TrendHashtag> list() {
		return tagRepo.findAll();
	}

	@Post("/")
	public HttpResponse<Void> add(@Body String tagName) {
		if(tagName.isBlank()) {
			return HttpResponse.noContent();
		}
		TrendHashtag tag = new TrendHashtag();
		tag.setTagName(tagName);
		tag.setLikes(0L);
		tagRepo.save(tag);
		
		return HttpResponse.created(URI.create("/trendhashtags/" + tag.getId()));
	}

	@Get("/{id}")
	public TrendHashtag getTag(Long id) {
		return tagRepo.findById(id).orElse(null);
	}

	@Transactional
	@Put("/{id}")
	public HttpResponse<Void> updateTag(Long id, @Body String tagName) {
		if(tagName.isBlank()) {
			return HttpResponse.noContent();
		}
		
		Optional<TrendHashtag> oTag = tagRepo.findById(id);
		if (oTag.isEmpty()) {
			return HttpResponse.notFound();
		}
		TrendHashtag tag = oTag.get();
		
		tag.setTagName(tagName);
		tagRepo.save(tag);
		return HttpResponse.ok();
	}

	@Transactional
	@Delete("/{id}")
	public HttpResponse<Void> deleteTag(Long id) {
		Optional<TrendHashtag> oTag = tagRepo.findById(id);
		if (oTag.isEmpty()) {
			return HttpResponse.notFound();
		}

		tagRepo.delete(oTag.get());
		return HttpResponse.ok();
	}
	
	// TAGGED VIDEO LOGIC
	@Get("/{id}/videos")
	public Iterable<TrendVideo> getTaggedVideos(Long id) {
		Optional<TrendHashtag> oTag = tagRepo.findById(id);
		if (oTag.isEmpty()) {
			return null;
		}
		return oTag.get().getTaggedVideos();
	}
	
	// LIKES PER HOUR LOGIC
	
	@Transactional
	public HttpResponse<String> updateLikesPerHour(Long vmid, Long likesThisHour) {
		Optional<TrendVideo> oVideo = videoRepo.findByVmid(vmid);
		if (oVideo.isEmpty()) {
			return HttpResponse.notFound(String.format("Video %d not found", vmid));
		}
		
		oVideo.get().setLikes(likesThisHour);
		for(TrendHashtag hashtag : oVideo.get().getHashtags()) {
			List<Long> likes = new ArrayList<>();
			hashtag.getTaggedVideos().forEach(v -> likes.add(v.getLikes()));
			hashtag.setLikes(likes.stream().mapToLong(Long::longValue).sum());
		}
		return HttpResponse.ok();
	}
	
	@Get("/topTen")
	public Iterable<TrendHashtag> getTopTenTags() {
		Set<TrendHashtag> allTags = new HashSet<>();
		tagRepo.findAll().forEach(allTags::add);
		
		List<TrendHashtag> topTen = allTags
				.stream()
				.sorted(Comparator.comparing(TrendHashtag::getLikes).reversed())
				.collect(Collectors.toList());
		
		if(topTen.size() > 10) {
			return topTen.subList(0, 10);
		} else {
			return topTen;
		}
	}
}
