package microservices.cli.domain;

import java.util.Set;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public class SubHashtag {

	private Long id;
	private String tagName;
	private Set<SubVideo> taggedVideos;
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
	
	@Override
	public String toString() {
		return "Tag [id=" + id + ", tag name=" + tagName + "]";
	}
}