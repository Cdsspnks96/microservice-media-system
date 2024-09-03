package microservices.subscription.controllers;

import java.net.URI;
import java.util.Arrays;
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

import microservices.subscription.domain.SubHashtag;
import microservices.subscription.domain.SubUser;
import microservices.subscription.domain.SubVideo;
import microservices.subscription.dto.UserDTO;
import microservices.subscription.events.SubsProducer;
import microservices.subscription.repositories.SubHashtagRepository;
import microservices.subscription.repositories.SubUserRepository;

@Controller("/subusers")
public class SubUserController {

	@Inject
	SubUserRepository userRepo;
	
	@Inject
	SubHashtagRepository tagRepo;
	
	@Inject
	SubsProducer producer;
	
	// USER LOGIC
	@Get("/")
	public Iterable<SubUser> list() {
		return userRepo.findAll();
	}

	@Post("/")
	public HttpResponse<Void> add(@Body UserDTO userDetails) {
		SubUser user = new SubUser();
		user.setUsername(userDetails.getUsername());
		userRepo.save(user);
		
		return HttpResponse.created(URI.create("/subusers/" + user.getId()));
	}

	@Get("/{id}")
	public SubUser getUser(Long id) {
		return userRepo.findById(id).orElse(null);
	}

	@Transactional
	@Put("/{id}")
	public HttpResponse<Void> updateUser(Long id, @Body UserDTO userDetails) {
		Optional<SubUser> user = userRepo.findById(id);
		if (user.isEmpty()) {
			return HttpResponse.notFound();
		}

		SubUser u = user.get();
		if (userDetails.getUsername() != null) {
			u.setUsername(userDetails.getUsername());
		}
		userRepo.save(u);
		return HttpResponse.ok();
	}

	@Transactional
	@Delete("/{id}")
	public HttpResponse<Void> deleteUser(Long id) {
		Optional<SubUser> user = userRepo.findById(id);
		if (user.isEmpty()) {
			return HttpResponse.notFound();
		}

		userRepo.delete(user.get());
		return HttpResponse.ok();
	}
	
	// VIEW LOGIC
	@Get("/{id}/viewed")
	public Iterable<SubVideo> getViewed(Long id) {
		Optional<SubUser> oUser = userRepo.findById(id);
		if (oUser.isEmpty()) {
			return null;
		}
		return oUser.get().getViewedVideos();
	}
	
	// SUBSCRIPTION LOGIC
	@Get("/{id}/hashtags")
	public Iterable<SubHashtag> getSubscriptions(Long id) {
		Optional<SubUser> oUser = userRepo.findById(id);
		if (oUser.isEmpty()) {
			return null;
		}
		return oUser.get().getSubscriptions();
	}
	
	@Transactional
	@Put("/{userId}/subscriptions")
	public HttpResponse<String> addSubscriptions(Long userId, String subscriptionCSV) {
		Optional<SubUser> oUser = userRepo.findById(userId);
		if (oUser.isEmpty()) {
			return HttpResponse.notFound(String.format("User %d not found", userId));
		}
		
		List<String> hashtags = Arrays.asList(subscriptionCSV.replaceAll("\\s","").split(","));		
		if(hashtags.isEmpty()) {
			return HttpResponse.notFound(String.format("Hashtags not valid for user %d", userId));
		}
		
		SubUser user = oUser.get();
		for(String hashtag : hashtags) {
			Optional<SubHashtag> oTag = tagRepo.findByTagName(hashtag);
			if (oTag.isEmpty()) {
				return HttpResponse.notFound(String.format("%s tag not found", hashtag));
			}
			if (user.getSubscriptions().add(oTag.get())) {
				user = userRepo.update(user);
				producer.subHashtag(user.getId(), oTag.get());
			}
		}
		
		return HttpResponse.ok(String.format("%s added as subscriptions for user %d", subscriptionCSV, userId));
	}
	
	@Transactional
	@Put("/{userId}/subscriptions/{tagId}")
	public HttpResponse<String> addSubscription(Long userId, Long tagId) {
		Optional<SubUser> oUser = userRepo.findById(userId);
		if (oUser.isEmpty()) {
			return HttpResponse.notFound(String.format("User %d not found", userId));
		}
		
		Optional<SubHashtag> oTag = tagRepo.findById(tagId);
		if (oTag.isEmpty()) {
			return HttpResponse.notFound(String.format("Tag %d not found", tagId));
		}

		SubUser user = oUser.get();
		if (user.getSubscriptions().add(oTag.get())) {
			userRepo.update(user);
			producer.subHashtag(userId, oTag.get());
		}

		return HttpResponse.ok(String.format("User %d subscribed to Hashtag %d", userId, tagId));
	}
	
	@Transactional
	@Delete("/{userId}/subscriptions/{tagId}")
	public HttpResponse<String> removeSubscription(Long userId, Long tagId) {
		Optional<SubUser> oUser = userRepo.findById(userId);
		if (oUser.isEmpty()) {
			return HttpResponse.notFound(String.format("User %d not found", userId));
		}
		
		Optional<SubHashtag> oTag = tagRepo.findById(tagId);
		if (oTag.isEmpty()) {
			return HttpResponse.notFound(String.format("Tag %d not found", tagId));
		}

		SubUser user = oUser.get();
		user.getSubscriptions().removeIf(t -> tagId == t.getId());
		userRepo.update(user);
		producer.unsubHashtag(userId, oTag.get());

		return HttpResponse.ok(String.format("User %d subscribed to Hashtag %d", userId, tagId));
	}
	
	@Get("/{userId}/subscriptions/{tagId}/top10")
	public Iterable<SubVideo> getUserTopTen(Long userId, Long tagId) {
		Optional<SubUser> oUser = userRepo.findById(userId);
		if (oUser.isEmpty()) {
			return null;
		}
		
		Optional<SubHashtag> oTag = tagRepo.findById(tagId);
		if (oTag.isEmpty()) {
			return null;
		}
		
		Set<SubVideo> unviewed = oTag.get().getTaggedVideos();
		Set<Long> vTags = new HashSet<Long>();
		
		oUser.get().getViewedVideos().forEach(v -> vTags.add(v.getId()));
		unviewed.removeIf(v -> vTags.contains(v.getId()));
		
		List<SubVideo> topTen = unviewed
				.stream()
				.sorted(Comparator.comparing(SubVideo::getLikes).reversed())
				.collect(Collectors.toList());
		
		if(topTen.size() > 10) {
			return topTen.subList(0, 10);
		} else {
			return topTen;
		}
	}
}