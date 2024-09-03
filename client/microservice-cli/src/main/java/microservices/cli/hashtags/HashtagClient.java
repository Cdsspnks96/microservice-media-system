package microservices.cli.hashtags;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;
import io.micronaut.http.client.annotation.Client;
import microservices.cli.domain.Hashtag;

@Client("${hashtags.url:`http://localhost:8080/hashtags`}")
public interface HashtagClient {

	@Get("/")
	Iterable<Hashtag> list();
	
	@Post("/")
	public HttpResponse<Void> add(@Body String tagName);
	
	@Get("/{id}")
	public Hashtag getTag(Long id);
	
	@Put("/{id}")
	public HttpResponse<Void> updateTag(Long id, @Body String tagName);
	
	@Delete("/{id}")
	public HttpResponse<Void> deleteTag(Long id);
}