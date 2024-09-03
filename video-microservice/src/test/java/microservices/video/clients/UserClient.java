package microservices.video.clients;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;
import io.micronaut.http.client.annotation.Client;
import microservices.video.domain.User;
import microservices.video.dto.UserDTO;
import microservices.video.domain.Video;

@Client("/users")
public interface UserClient {

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
	
	@Get("/{id}/posts")
	Iterable<Video> getPosts(Long id);
	
	@Get("/{id}/viewed")
	Iterable<Video> getViewed(Long id);
	
}