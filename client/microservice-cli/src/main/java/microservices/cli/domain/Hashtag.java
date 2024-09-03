package microservices.cli.domain;

import java.util.Set;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public class Hashtag {

	private Long id;
	private String tagName;
	private Set<Video> taggedVideos;

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

	public Set<Video> getTaggedVideos() {
		return taggedVideos;
	}

	public void setTaggedVideos(Set<Video> taggedVideos) {
		this.taggedVideos = taggedVideos;
	}

	@Override
	public String toString() {
		return "Tag [id=" + id + ", tag name=" + tagName + "]";
	}

}
