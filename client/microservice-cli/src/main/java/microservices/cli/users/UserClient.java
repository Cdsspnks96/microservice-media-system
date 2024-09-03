package microservices.cli.users;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;
import io.micronaut.http.client.annotation.Client;
import microservices.cli.domain.User;
import microservices.cli.dto.UserDTO;
import microservices.cli.domain.Video;

@Client("${users.url:`http://localhost:8080/users`}")
public interface UserClient {

	// USER LOGIC
	@Get("/")
	Iterable<User> list();

	@Post("/")
	HttpResponse<Void> add(@Body UserDTO userDetails);

	@Get("/{id}")
	UserDTO getUser(Long id);

	@Put("/{id}")
	HttpResponse<Void> updateUser(Long id, @Body UserDTO userDetails);

	@Delete("/{id}")
	HttpResponse<Void> deleteUser(Long id);
	
	// POST LOGIC
	@Get("/{id}/posts")
	Iterable<Video> getPosts(Long id);
	
	// VIEW LOGIC
	@Get("/{id}/viewed")
	Iterable<Video> getViewed(Long id);
	
	// LIKED VIDEO LOGIC
	@Get("/{id}/liked")
	public Iterable<Video> getLiked(Long id);
	
	// DISLIKED VIDEO LOGIC
	@Get("/{id}/disliked")
	public Iterable<Video> getDisliked(Long id);
	
}