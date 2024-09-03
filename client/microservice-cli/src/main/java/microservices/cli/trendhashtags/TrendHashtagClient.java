package microservices.cli.trendhashtags;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;
import io.micronaut.http.client.annotation.Client;
import microservices.cli.domain.TrendHashtag;
import microservices.cli.domain.TrendVideo;

@Client("${trendhashtags.url:`http://localhost:8082/trendhashtags`}")
public interface TrendHashtagClient {

	@Get("/")
	Iterable<TrendHashtag> list();
	
	@Post("/")
	public HttpResponse<Void> add(@Body String tagName);
	
	@Get("/{id}")
	public TrendHashtag getTag(Long id);
	
	@Put("/{id}")
	public HttpResponse<Void> updateTag(Long id, @Body String tagName);
	
	@Delete("/{id}")
	public HttpResponse<Void> deleteTag(Long id);
	
	@Get("/{id}/videos")
	public Iterable<TrendVideo> getTaggedVideos(Long id);
	
	@Get("/topTen")
	public Iterable<TrendHashtag> getTopTenTags();
}