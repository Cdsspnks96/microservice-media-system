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
public class SubHashtag {

	@Id
	@GeneratedValue
	private Long id;
	
	@Column(nullable = false, unique = true)
	private String tagName;

	@JsonIgnore
	@ManyToMany(mappedBy = "hashtags", fetch = FetchType.EAGER)
	private Set<SubVideo> taggedVideos;
	
	@JsonIgnore
	@ManyToMany(mappedBy = "subscriptions", fetch = FetchType.EAGER)
	private Set<SubUser> subscribers;
	
	// ID Logic
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	// Tag-name Logic
	public String getTagName() {
		return tagName;
	}
	
	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
	
	// Tagged Video Logic
	public Set<SubVideo> getTaggedVideos() {
		return taggedVideos;
	}
	
	public void setTaggedVideos(Set<SubVideo> taggedVideos) {
		this.taggedVideos = taggedVideos;
	}
	
	// Subscriber Logic
	public Set<SubUser> getSubscribers() {
		return subscribers;
	}
	
	public void setSubscribers(Set<SubUser> subscribers) {
		this.subscribers = subscribers;
	}
}