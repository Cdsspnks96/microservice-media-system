package microservices.trending.domain;

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
public class TrendVideo {

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
	private Set<TrendHashtag> hashtags;
	
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
	public Set<TrendHashtag> getHashtags() {
		return hashtags;
	}

	public void setHashtags(Set<TrendHashtag> hashtags) {
		this.hashtags = hashtags;
	}
}