package microservices.cli.domain;

import java.util.Set;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public class TrendHashtag {

	private Long id;
	private String tagName;
	private Set<TrendVideo> taggedVideos;
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
	
	@Override
	public String toString() {
		return "Tag [id=" + id + ", tag name=" + tagName + "]";
	}
}