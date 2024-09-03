package microservices.video.controllers;

import java.net.URI;
import java.util.HashSet;
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

import microservices.video.domain.Video;
import microservices.video.domain.Opinion;
import microservices.video.domain.User;
import microservices.video.dto.UserDTO;
import microservices.video.events.UserProducer;
import microservices.video.repositories.UserRepository;

@Controller("/users")
public class UserController {

	@Inject
	UserRepository repo;
	
	@Inject
	UserProducer producer;
	
	// USER LOGIC
	@Get("/")
	public Iterable<User> list() {
		return repo.findAll();
	}

	@Post("/")
	public HttpResponse<String> add(@Body UserDTO userDetails) {
		String username = userDetails.getUsername();
		if(username.isBlank()) {
			return HttpResponse.noContent();
		}
		if (repo.findByUsername(username).isPresent()) {
			return HttpResponse.badRequest(String.format("User %s already exists",username));
		}
		User user = new User();
		user.setUsername(username);
		
		user = repo.save(user);
		if(user != null) {
			producer.createUser(user.getId(), user);
		}
		
		return HttpResponse.created(URI.create("/users/" + user.getId()));
	}

	@Get("/{id}")
	public UserDTO getUser(Long id) {
		return repo.findOne(id).orElse(null);
	}

	@Transactional
	@Put("/{id}")
	public HttpResponse<Void> updateUser(Long id, @Body UserDTO userDetails) {
		Optional<User> user = repo.findById(id);
		if (user.isEmpty()) {
			return HttpResponse.notFound();
		}

		User u = user.get();
		if (userDetails.getUsername() != null) {
			u.setUsername(userDetails.getUsername());
		}
		repo.save(u);
		return HttpResponse.ok();
	}

	@Transactional
	@Delete("/{id}")
	public HttpResponse<Void> deleteUser(Long id) {
		Optional<User> user = repo.findById(id);
		if (user.isEmpty()) {
			return HttpResponse.notFound();
		}

		repo.delete(user.get());
		return HttpResponse.ok();
	}
	
	// POSTS LOGIC
	@Get("/{id}/posts")
	public Iterable<Video> getPosts(Long id) {
		Optional<User> oUser = repo.findById(id);
		if (oUser.isEmpty()) {
			return null;
		}
		return oUser.get().getPostedVideos();
	}
	
	// VIEWED VIDEO LOGIC
	@Get("/{id}/viewed")
	public Iterable<Video> getViewed(Long id) {
		Optional<User> oUser = repo.findById(id);
		if (oUser.isEmpty()) {
			return null;
		}
		return oUser.get().getViewedVideos();
	}
	
	// LIKED VIDEO LOGIC
	@Get("/{id}/liked")
	public Iterable<Video> getLiked(Long id) {
		Optional<User> oUser = repo.findById(id);
		if (oUser.isEmpty()) {
			return null;
		}
		
		Set<Video> likedVideos = new HashSet<Video>();
		Set<Opinion> opinions = oUser.get().getOpinions();
		for(Opinion opinion : opinions) {
			if(opinion.getStatus()) {
				likedVideos.add(opinion.getVideo());
			}
		}
		return likedVideos;
	}
	
	// DISLIKED VIDEO LOGIC
	@Get("/{id}/disliked")
	public Iterable<Video> getDisliked(Long id) {
		Optional<User> oUser = repo.findById(id);
		if (oUser.isEmpty()) {
			return null;
		}
		
		Set<Video> dislikedVideos = new HashSet<Video>();
		Set<Opinion> opinions = oUser.get().getOpinions();
		for(Opinion opinion : opinions) {
			if(!opinion.getStatus()) {
				dislikedVideos.add(opinion.getVideo());
			}
		}
		return dislikedVideos;
	}
}