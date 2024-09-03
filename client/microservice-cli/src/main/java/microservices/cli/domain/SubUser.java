package microservices.cli.domain;

import java.util.Set;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public class SubUser {

	private Long id;
	private String username;
	private Set<SubVideo> viewedVideos;
	private Set<SubHashtag> subscriptions;
	
	// ID Logic
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	// User-name Logic
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	// Viewed Video Logic
	public Set<SubVideo> getViewedVideos() {
		return viewedVideos;
	}
	
	public void setViewedVideos(Set<SubVideo> viewedVideos) {
		this.viewedVideos = viewedVideos;
	}
	
	// Subscriptions Logic
	public Set<SubHashtag> getSubscriptions() {
		return subscriptions;
	}
	
	public void setSubscriptions(Set<SubHashtag> subscriptions) {
		this.subscriptions = subscriptions;
	}
	
}