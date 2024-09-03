package microservices.trending.domain;

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
public class TrendHashtag {

	@Id
	@GeneratedValue
	private Long id;
	
	@Column(nullable = false, unique = true)
	private String tagName;

	@JsonIgnore
	@ManyToMany(mappedBy = "hashtags", fetch = FetchType.EAGER)
	private Set<TrendVideo> taggedVideos;
	
	@Column(nullable = false)
	private Long likesThisHour;
	
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
	public Set<TrendVideo> getTaggedVideos() {
		return taggedVideos;
	}
	
	public void setTaggedVideos(Set<TrendVideo> taggedVideos) {
		this.taggedVideos = taggedVideos;
	}
	
	// Likes logic
	public Long getLikes() {
		return likesThisHour;
	}
	
	public void setLikes(Long likesThisHour) {
		this.likesThisHour = likesThisHour;
	}
}