package microservices.subscription.dto;

import java.util.Set;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public class HashtagDTO {

	private Long id;

	private String tagName;

	private Set<VideoDTO> taggedVideos;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	
	public Set<VideoDTO> getTaggedVideos() {
		return taggedVideos;
	}

	public void setTaggedVideos(Set<VideoDTO> taggedVideos) {
		this.taggedVideos = taggedVideos;
	}
	
}
