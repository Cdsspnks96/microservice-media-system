package microservices.cli.videos;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;
import io.micronaut.http.client.annotation.Client;
import microservices.cli.domain.Video;
import microservices.cli.dto.VideoDTO;
import microservices.cli.domain.User;
import microservices.cli.domain.Hashtag;

@Client("${videos.url:`http://localhost:8080/videos`}")
public interface VideoClient {
	
	// VIDEO LOGIC
	@Get("/")
	Iterable<Video> list();
	
	@Post("/")
	HttpResponse<Void> add(@Body VideoDTO videoDetails);
	
	@Get("/{id}")
	Video getVideo(Long id);

	@Put("/{id}")
	HttpResponse<Void> updateVideo(Long id, @Body VideoDTO videoDetails);

	@Delete("/{id}")
	HttpResponse<Void> deleteVideo(Long id);
	
	// HASHTAG LOGIC
	@Get("/{id}/hashtags")
	Iterable<Hashtag> getHashtags(Long id);
	
	@Put("/{videoId}/hashtags")
	HttpResponse<String> addHashtags(Long videoId, String hashtagCSV);

	@Put("/{videoId}/hashtags/{tagId}")
	HttpResponse<String> addHashtag(Long videoId, Long tagId);
	
	// VIEWERS LOGIC
	@Get("/{id}/viewers")
	Iterable<User> getViewers(Long id);

	@Put("/{videoId}/viewers/{userId}")
	HttpResponse<String> addViewer(Long videoId, Long userId);	

	@Delete("/{videoId}/viewers/{userId}")
	HttpResponse<String> removeViewer(Long videoId, Long userId);
	
}