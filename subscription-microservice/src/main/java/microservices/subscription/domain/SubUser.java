package microservices.subscription.domain;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.micronaut.serde.annotation.Serdeable;

@Entity
@Serdeable
public class SubUser {

	@Id
	@GeneratedValue
	private Long id;
	
	@Column(nullable = false, unique = true)
	private String username;
	
	@JsonIgnore
	@ManyToMany(mappedBy = "viewers", fetch = FetchType.EAGER)
	private Set<SubVideo> viewedVideos; 
	
	@JsonIgnore
	@ManyToMany
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