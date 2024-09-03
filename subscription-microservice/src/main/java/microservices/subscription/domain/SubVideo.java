package microservices.subscription.domain;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.micronaut.serde.annotation.Serdeable;

@Entity
@Serdeable
public class SubVideo {

	@Id
	@GeneratedValue
	private Long id;
	
	@Column(nullable = false)
	private Long vmid;
	
	@Column(nullable = false)
	private String title;
	
	@Column(nullable = false)
	private Long likes;
	
	@JsonIgnore
	@ManyToMany
	private Set<SubHashtag> hashtags;
	
	@JsonIgnore
	@ManyToMany
	private Set<SubUser> viewers; 
	
	// ID LOGIC
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getVmid() {
		return vmid;
	}

	public void setVmd(Long id) {
		this.vmid = id;
	}
	
	// Title Logic
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	// Likes Logic
	public Long getLikes() {
		return likes;
	}

	public void setLikes(Long likes) {
		this.likes = likes;
	}
	
	// Video hash-tags
	public Set<SubHashtag> getHashtags() {
		return hashtags;
	}

	public void setHashtags(Set<SubHashtag> hashtags) {
		this.hashtags = hashtags;
	}
	
	// Video viewers
	public Set<SubUser> getViewers() {
		return viewers;
	}
	
	public void setViewers(Set<SubUser> viewers) {
		this.viewers = viewers;
	}
}