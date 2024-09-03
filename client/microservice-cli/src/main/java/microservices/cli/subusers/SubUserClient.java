package microservices.cli.subusers;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;
import io.micronaut.http.client.annotation.Client;

import microservices.cli.domain.SubVideo;
import microservices.cli.domain.SubHashtag;
import microservices.cli.domain.User;

@Client("${subusers.url:`http://localhost:8081/subusers`}")
public interface SubUserClient {

	// USER LOGIC
	@Get("/")
	Iterable<User> list();

	@Post("/")
	HttpResponse<Void> add(@Body User userDetails);

	@Get("/{id}")
	User getUser(Long id);

	@Put("/{id}")
	HttpResponse<Void> updateUser(Long id, @Body User userDetails);

	@Delete("/{id}")
	HttpResponse<Void> deleteUser(Long id);
	
	// VIEW LOGIC
	@Get("/{id}/viewed")
	Iterable<SubVideo> getViewed(Long id);
	
	// SUBSCRIPTION LOGIC
	@Get("/{id}/hashtags")
	public Iterable<SubHashtag> getSubscriptions(Long id);
	
	@Put("/{userId}/subscriptions")
	public HttpResponse<String> addSubscriptions(Long userId, String subscriptionCSV);
	
	@Put("/{userId}/subscriptions/{tagId}")
	public HttpResponse<String> addSubscription(Long userId, Long tagId);
	
	@Delete("/{userId}/subscriptions/{tagId}")
	public HttpResponse<String> removeSubscription(Long userId, Long tagId);
	
	@Get("/{userId}/subscriptions/{tagId}/top10")
	public Iterable<SubVideo> getUserTopTen(Long userId, Long tagId);
}