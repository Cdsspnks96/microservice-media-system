package microservices.cli.opinions;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;
import io.micronaut.http.client.annotation.Client;
import microservices.cli.domain.Opinion;
import microservices.cli.dto.OpinionDTO;

@Client("${opinions.url:`http://localhost:8080/opinions`}")
public interface OpinionClient {

	@Get("/")
	Iterable<Opinion> list();
	
	@Post("/")
	public HttpResponse<Void> add(@Body OpinionDTO opinionDetails);
	
	@Get("/{id}")
	public Opinion getOpinion(Long id);
	
	@Put("/{id}")
	public HttpResponse<Void> updateOpinion(Long id, @Body Integer likeStatus);
	
	@Delete("/{id}")
	public HttpResponse<Void> deleteOpinion(Long id);
}