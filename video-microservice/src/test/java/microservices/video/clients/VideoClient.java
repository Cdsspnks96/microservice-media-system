package microservices.video.clients;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;
import io.micronaut.http.client.annotation.Client;
import microservices.video.domain.Video;
import microservices.video.dto.VideoDTO;
import microservices.video.domain.User;
import microservices.video.domain.Hashtag;

@Client("/videos")
public interface VideoClient {

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
	
	@Get("/{id}/hashtags")
	Iterable<Hashtag> getHashtags(Long id);
	
	@Put("/{videoId}/hashtags")
	HttpResponse<String> addHashtags(Long videoId, String hashtagCSV);

	@Put("/{videoId}/hashtags/{tagId}")
	HttpResponse<String> addHashtag(Long videoId, Long tagId);
	
	@Get("/{id}/viewers")
	Iterable<User> getViewers(Long id);

	@Put("/{videoId}/viewers/{userId}")
	HttpResponse<String> addViewer(Long videoId, Long userId);	

	@Delete("/{videoId}/viewers/{userId}")
	HttpResponse<String> removeViewer(Long videoId, Long userId);
}
